/* license-start
 * 
 * Copyright (C) 2008 - 2013 Crispico, <http://www.crispico.com/>.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 3.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details, at <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 *   Crispico - Initial API and implementation
 *
 * license-end
 */
package org.flowerplatform.communication.temp.tree.remote;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.flowerplatform.common.log.AuditDetails;
import org.flowerplatform.common.log.LogUtil;
import org.flowerplatform.common.util.RunnableWithParam;
import org.flowerplatform.communication.channel.CommunicationChannel;
import org.flowerplatform.communication.stateful_service.IStatefulClientLocalState;
import org.flowerplatform.communication.stateful_service.NamedLockPool;
import org.flowerplatform.communication.stateful_service.RemoteInvocation;
import org.flowerplatform.communication.stateful_service.StatefulService;
import org.flowerplatform.communication.stateful_service.StatefulServiceInvocationContext;
import org.flowerplatform.communication.tree.GenericTreeContext;
import org.flowerplatform.communication.tree.NodeInfo;
import org.flowerplatform.communication.tree.NodeInfoClient;
import org.flowerplatform.communication.tree.remote.GenericTreeStatefulClientLocalState;
import org.flowerplatform.communication.tree.remote.PathFragment;
import org.flowerplatform.communication.tree.TreeInfoClient;
import org.flowerplatform.communication.tree.remote.TreeNode;

/**
 * Service used to provide functionality for generic trees.
 * 
 * <p>
 * There are 3 different types of trees:
 * <ul>
 * 	<li> non-dispatched trees (simple trees) -> only shows tree data
 * 	<li> dispatched trees -> shows and updates tree data by dispatching notifications to 
 * 		a list of subscribed clients
 * 	<li> partial dispatched trees -> only a limited number of tree nodes are dispatched;
 * 		if a node is dispatched, all its upper structure must be also in dispatched mode.
 * </ul>
 * 
 * To provide functionality for a non-dispatched tree, the following methods must be implemented:
 * <ul>
 * 	<li> {@link #populateTreeNode()}
 * 	<li> {@link #getPathFragmentForNode()}
 * 	<li> {@link #getNodeByPathFragment()}
 * 	<li> {@link #getChildrenForNode()}
 * 	<li> {@link #getParent()}
 * </ul>
 * 
 * To provide functionality for a dispatched/partial dispatched tree, the following methods must be implemented
 * in addition to the list mentioned above:
 * <ul>
 * 	<li> {@link #isDispatchEnabled()()} 
 * </ul>
 * Also, a service for dispatched trees is responsible to listen for label/content updates
 * and to call {@link #dispatchLabelUpdate()}/{@link #dispatchContentUpdate()}.
 * 
 * <p>
 * For a dispatched tree, when its lifeline (on client side) ends, it must perform cleanup, i.e.
 * close the root node. E.g. in Eclipse, a listener must be put when the dialog is closing and a call to <code>closeNode(null, -1, null)</code>
 * must be done in order to clean the data used. <br>
 * In web, all services must extend <code>WebGenericTreeService</code> which already performs this requirement when a client is destroyed.
 * This stands for trees that are always open (e.g. Project Explorer). If you use dispatched trees with limited lifeline (e.g. in a dialog)
 * the same remarks for the above Eclipse example still stands.
 *  
 * 
 * <p>
 * For trees with inplace editing active, their services must implement the following methods:
 * <ul>
 * 	<li> {@link #setInplaceEditorText(List, String)}
 * 	<li> {@link #getInplaceEditorText(List)}
 * </ul>
 * 
 * @author Cristi
 * @author Cristina
 * 
 * 
 */
public abstract class GenericTreeStatefulService extends StatefulService {
	
	/**
	 * 
	 */
	public static final String WHOLE_TREE_KEY = "wholeTree";
	
	/**
	 * 
	 */
	private static final String EXPAND_NODE_KEY = "expandNode";
	
	/**
	 * 
	 */
	private static final String SELECT_NODE_KEY = "selectNode";
	
	/**
	 * Adding this key to <code>context</code> will retrieve the node's 
	 * path by going up the {@link NodeInfo} hierarchy, instead of using
	 * {@link #getParent(Object)}. Should be used when the node was deleted
	 * to ensure that it will be removed from {@link #openNodes} map, for
	 * example, if deleting model files.
	 * 
	 * @see #dispatchExpandedUpdate(Object, CommunicationChannel, boolean)
	 * @see #getPathForNode(Object, Map)
	 * 
	 * @author Mariana
	 * 
	 */
	private static final String GO_UP_ON_NODE_INFO_KEY = "goUpOnNodeInfo";
	
	/**
	 * This key will be used in the getNodeByPath method. 
	 * If the context parameter will have this key set, then the node
	 * will be search only by using the rootNodeInfo and the pathFragments 
	 * making no use for the stored tree structure 
	 * 
	 * @see #getNodeByPath(List, Map)
	 * @see ProjectExplorerTreeService#getNodeByPath(List, Map)
	 * 
	 */
	public static final String GO_DOWN_ON_PATH_FRAGMENT_KEY = "goDownOnPathFragment";
	
	/**
	 * Notifications will only be dispatched to the client with {@link CommunicationChannel#getClientId()}
	 * mapped by this key in the <code>context</code> map.
	 * 
	 * @see #dispatchContentUpdate(Object, ClientInvocationOptions, Map)
	 * 
	 * @author Mariana
	 */
	protected static final String DISPATCH_ONLY_FOR_CLIENT = "dispatchOnlyForClient";
		
	/**
	 * 
	 */
	protected Map<Object, NodeInfo> openNodes = new ConcurrentHashMap<Object, NodeInfo>();
	
	/**
	 * Holds the tree structure of all displayed nodes on clients (opened or not).
	 * 
	 * 
	 */
	private NodeInfo rootNodeInfo;
	
	/**
	 * Holds the contexts of all subscribed trees.
	 */
	protected Map<TreeInfoClient, GenericTreeContext> treeContexts = new ConcurrentHashMap<TreeInfoClient, GenericTreeContext>();
	
	/**
	 * We use this instead of normal locking, because, during subscription there is a small
	 * time window where 2 threads subscribing for the same resource could create the {@link NodeInfo}
	 * twice. Of course, we could have locked on the entire map, which would have solved this, but with
	 * a big performance impact. 
	 * 
	 * @see NamedLockPool
	 * @see #subscribe()
	 */
	protected NamedLockPool namedLockPool = new NamedLockPool();
	
	/**
	 * 
	 */
	private static final Logger logger = LoggerFactory.getLogger(GenericTreeStatefulService.class);
		
	///////////////////////////////////////////////////////////////
	// JMX Methods
	///////////////////////////////////////////////////////////////
	
	/**
	 * 
	 */
	public String printNodeInfos() {
		StringBuffer sb = new StringBuffer();
		
		for (NodeInfo node : openNodes.values()) {			
			sb.append(node).append("\n");
			for (NodeInfoClient client : node.getClients()) {
				sb.append("  ").append(client).append("\n");
			}
		}
		
		return sb.toString();
	}
	
//	public String printTreeStatefulContext(String webCommunicationChannelIdFilter, String linePrefix) {
//		// clean parameters
//		if ("".equals(webCommunicationChannelIdFilter) || "String".equals(webCommunicationChannelIdFilter)) {
//			webCommunicationChannelIdFilter = null;
//		}
//		if ("String".equals(linePrefix)) { 
//			linePrefix = "";
//		}
//						
//		StringBuffer sb = new StringBuffer();
//	
//		for (TreeInfoClient tree : treeContexts.keySet()) {
//			// execute if no filter or if filter matches
//			if (webCommunicationChannelIdFilter == null || webCommunicationChannelIdFilter.equals(tree.getCommunicationChannel().getClientId())) {
//				GenericTreeContext treeContext = getTreeContext(tree.getCommunicationChannel(), tree.getStatefulClientId());
//				sb.append(linePrefix).append("  ").append(treeContext.getStatefulContext()).append("\n");
//			}
//		}				
//		return sb.toString();	
//	}
	
	private class CommunicationChannelAndNodeInfos {
		private CommunicationChannel communicationChannel;
		private List<NodeInfo> nodeInfos = new ArrayList<NodeInfo>();
	}
			
//	/**
//	 * 
//	 */
//	public String printStatefulDataPerCommunicationChannel(String webCommunicationChannelIdFilter, String linePrefix) {		
//		// clean parameters
//		if ("".equals(webCommunicationChannelIdFilter) || "String".equals(webCommunicationChannelIdFilter)) {
//			webCommunicationChannelIdFilter = null;
//		}
//		if ("String".equals(linePrefix)) { 
//			linePrefix = "";
//		}
//				
//		StringBuffer sb = new StringBuffer();
//		Map<String, CommunicationChannelAndNodeInfos> map = new HashMap<String, CommunicationChannelAndNodeInfos>();
//				
//		// build the inverse hierarchy
//		for (NodeInfo node : openNodes.values()) {	
//			for (NodeInfoClient client : node.getClients()) {
//				// execute if no filter or if filter matches
//				if (webCommunicationChannelIdFilter == null || webCommunicationChannelIdFilter.equals(client.getCommunicationChannel().getClientId())) {
//					
//					// find or create entry
//					CommunicationChannelAndNodeInfos entry = map.get(client.getCommunicationChannel().getClientId());
//					if (entry == null) {
//						entry = new CommunicationChannelAndNodeInfos();
//						entry.communicationChannel = client.getCommunicationChannel();
//						map.put(client.getCommunicationChannel().getClientId(), entry);
//					}							
//					// add node to the list
//					entry.nodeInfos.add(node);
//				}
//			}			
//		}				
//		// print
//		for (CommunicationChannelAndNodeInfos entry : map.values()) {
//			sb.append(linePrefix).append(entry.communicationChannel).append("\n");
//			for (NodeInfo nodeInfo : entry.nodeInfos) {
//				sb.append(linePrefix).append("  ").append(nodeInfo).append("\n");
//			}
//		}			
//		return sb.toString();
//	}
	
	public Collection<String> getStatefulClientIdsForCommunicationChannel(CommunicationChannel communicationChannel) {
		List<String> ids = new ArrayList<String>();
		for (NodeInfo nodeInfo : openNodes.values()) {		
			for (NodeInfoClient clientInfo : nodeInfo.getClients()) {
				if (clientInfo.getCommunicationChannel().equals(communicationChannel) && !ids.contains(clientInfo.getStatefulClientId(this))) {				
					ids.add(clientInfo.getStatefulClientId(this));
				}				
			}
		}
		return ids;
	}
	
	///////////////////////////////////////////////////////////////
	// Normal methods
	///////////////////////////////////////////////////////////////

	/**
	 * Should return <code>true</code> if the service provides functionality
	 * for a dispatched tree. <br>
	 * By default, returns <code>false</code> (functionality for simple tree).
	 * <p>
	 * If a node is provided, then it should return whether or not
	 * the given node will be seen as dispatched. <br>
	 * This is the case of a "partial dispatched tree":
	 * some nodes are dispatched, but not all.
	 * <p>
	 * Note: all the above structure of a dispatched node must be in dispatched mode
	 * (the parents must be seen as dispatched nodes) in order to work properly.
	 * 
	 */
	protected boolean isDispatchEnabled(Object node) {
		return false;
	}

	/**
	 * Factory method returning the instance of tree node used.
	 * 
	 * @see #openNode()
	 * @see #dispatchContentUpdate()
	 * @see #dispatchLabelUpdate()
	 * 
	 */
	protected TreeNode createTreeNode() {
		return new TreeNode();
	}

	public abstract String getStatefulClientPrefixId();
	
	/**
	 * Subclasses that implement this method must the path fragment for given node.
	 * <p>
	 * If path fragment isn't human readable, subclasses must return a suggestive string instead.
	 *  
	 * 
	 */
	public abstract String getLabelForLog(Object node);
	
	/**
	 * This method might be used for trees that send all the data at the beginning (i.e. no
	 * more openNode/closeNode afterwards).
	 * 
	 * @param recurse - if <code>true</code>, creates the whole tree structure for given node.
	 * 					Otherwise creates only its direct children.
	 * 
	 */
	private void populateChildren(CommunicationChannel channel, String statefulClientId, Object node, TreeNode treeNode, GenericTreeContext context, boolean recurse) {
		// create and populate the children list
		for (Object child : getChildrenForNode(node, context)) {
			TreeNode childNode = createTreeNode();
			populateTreeNodeInternal(child, childNode, context);	
			treeNode.getChildren().add(childNode);
			childNode.setParent(treeNode);
			
			if (recurse) { // get child whole structure
				populateChildren(channel, statefulClientId, child, childNode, context, recurse);
			}
			
			// for dispatched trees, update the server tree structure
			if (isDispatchEnabled(child)) {	
				addNodeInfo(channel, statefulClientId, child, false, false, context);
			}
		}		
	}
	
	/**
	 * Adds node information to tree structure and 
	 * updates the {@link #openNodes} map if requested.
	 * <p>
	 * At the end, subscribe the given channel and clientId to node.
	 * 
	 * 
	 */
	private void addNodeInfo(CommunicationChannel channel, String statefulClientId, Object node, boolean isRoot, boolean addInOpenNodes, GenericTreeContext context) {
		NodeInfo nodeInfo = openNodes.get(node);
		if (nodeInfo == null) {	
			namedLockPool.lock(node);	
			try {
				if (isRoot) { // must create the root node
					logger.trace("Root node never opened; adding it to structure & map");				
					// if root node, create one, don't add it to open nodes					
					rootNodeInfo = new NodeInfo();
					rootNodeInfo.setNode(node);					
					nodeInfo = rootNodeInfo;
					openNodes.put(node, nodeInfo);				
				} else { // not opened
					// get parent open node
					Object parent = getParent(node, context);
					if (parent == null) { // this shouldn't happen
						throw new RuntimeException("Parent node not found for node " + getLabelForLog(node));
					}
					NodeInfo parentInfo = openNodes.get(parent); // parent must be opened
					if (parentInfo == null) {
						logger.debug("Parent info for {} was already closed!", parent);
						return;
					}
					// search node info in list of parent's children
					for (NodeInfo childInfo : parentInfo.getChildren()) {
						if (childInfo.getNode().equals(node)) {
							nodeInfo = childInfo;
							break;
						}
					}
					if (nodeInfo == null) { // not found in parent, add it
						logger.trace("Node {} never added in structure; adding it", getLabelForLog(node));	
						// create new open node entry and populate it
						nodeInfo = new NodeInfo();
						nodeInfo.setNode(node);
						nodeInfo.setParent(parentInfo);
						nodeInfo.setPathFragment(getPathFragmentForNode(node, context));					
						// add node to tree
						parentInfo.getChildren().add(nodeInfo);	
					}
					if (addInOpenNodes) { // add node to map
						logger.trace("Node {} added to openNodes", getLabelForLog(node));	
						openNodes.put(node, nodeInfo);
					}
				}
			} finally {
				namedLockPool.unlock(node);
			}
		}				
		
		// add new subscribed client if necessary 
		boolean exists = false;		
		for (NodeInfoClient nodeClient : nodeInfo.getClients()) {	
			if (nodeClient.getCommunicationChannel().equals(channel) &&
				nodeClient.getStatefulClientId(this).equals(statefulClientId)) {
				exists = true;
				break;
			}
		}
		if (!exists) {
			logger.trace("Subscribing client [{} with statefulClientId={}] to node {}", new Object[] { channel, statefulClientId, getLabelForLog(node)});
//			nodeInfo.addNodeInfoClient(new NodeInfoClient(channel, statefulClientId, this));
		} else {
			logger.trace("Client [{} with statefulClientId={}] already subscribed to node {}", new Object[] { channel, statefulClientId, getLabelForLog(node)});
		}		
	}
	
	/**
	 * Should be called by java listener for label changes.
	 * Available only for dispatched trees.
	 * 
	 * <p>
	 * Verifies if the parent is opened. If <code>true</code>,
	 * creates and populates a {@link TreeNode} with new data and sends updates to all
	 * subscribed clients. 	
	 * 
	 */
	public void dispatchLabelUpdate(Object node) {
		NodeInfo nodeInfo = openNodes.get(node); // check if opened
		if (nodeInfo == null) { // not found, check if parent is opened and gets its info
			// check if parent is opened
			Object parent = getParent(node);
			if (parent == null) {
				// this shouldn't normally happen, because the root node is not visible
				// so a label update doesn't make sense
				return;
			}
			nodeInfo = openNodes.get(parent);
		}		
		if (nodeInfo != null) {
			for (NodeInfoClient nodeClient : nodeInfo.getClients()) {	
				dispatchLabelUpdateForClient(node, nodeClient);
			}
		}					
	}
	
	protected void dispatchLabelUpdateForClient(Object node, NodeInfoClient nodeClient) {	
		GenericTreeContext context = getTreeContext(nodeClient.getCommunicationChannel(), nodeClient.getStatefulClientId(this));
		
		List<PathFragment> path = getPathForNode(node, context);
		TreeNode treeNode = createTreeNode();
		populateTreeNodeInternal(node, treeNode, context);	
		
		if (logger.isTraceEnabled()) {
			logger.trace("Dispatching label update for node {} to client [{} with statefulClientId={}]", new Object[] { getLabelForLog(node), nodeClient.getCommunicationChannel(), nodeClient.getStatefulClientId(this) });
		}
		updateNode(
				nodeClient.getCommunicationChannel(), 
				nodeClient.getStatefulClientId(this), 
				path, treeNode, 
				false, false, false, false);
	}
	
	/**
	 * Should be called by java listener for content changes.
	 * Available only for dispatched trees.
	 * 
	 * <p>
	 * Verifies if the node is opened.
	 * If <code>true</code>, compares the list of new children with 
	 * the one stored in {@link NodeInfo}. <br>
	 * The ones displayed but not found in the new list are considered to be deleted,
	 * so a cleanup is called. <br>
	 * The ones displayed and opened but not found in the new list will be closed.
	 * All their children structure it will be also updated to close their opened nodes.
	 * 
	 * <p>
	 * Also, creates and populates a new tree node (including object children) and
	 * sends updates to all subscribed clients. 
	 * 
	 */
	public void dispatchContentUpdate(Object node, Object clientInvocationOptions) {
		NodeInfo nodeInfo = openNodes.get(node);
		if (nodeInfo == null) { // not opened, return
			return;
		}		
		// send updates to all subscribed clients
		for (NodeInfoClient nodeClient : nodeInfo.getClients()) {	
			GenericTreeContext context = getTreeContext(nodeClient.getCommunicationChannel(), nodeClient.getStatefulClientId(this));			
			if (context != null && context.containsKey(DISPATCH_ONLY_FOR_CLIENT)) {
//				if (!nodeClient.getCommunicationChannel().getClientId().equals(context.get(DISPATCH_ONLY_FOR_CLIENT))) {
//					continue; // don't send this update to the other clients except the client in the context map
//				}
			}
			dispatchContentUpdateForClient(node, nodeClient);
		}
	}
	
	protected void dispatchContentUpdateForClient(Object node, NodeInfoClient nodeClient) {	
		GenericTreeContext context = getTreeContext(nodeClient.getCommunicationChannel(), nodeClient.getStatefulClientId(this));
		
		NodeInfo nodeInfo = openNodes.get(node);
		// cleanup map if unavailable nodes
		Collection<?> allChildren = getChildrenForNode(node, context);
		List<NodeInfo> openChildren = nodeInfo.getChildren();

		HashMap<Object, NodeInfo> oldNodes = new HashMap<Object, NodeInfo>();
		for (NodeInfo oldChild : openChildren) {
			oldNodes.put(oldChild.getNode(), oldChild);
		}
		// create/populate tree
		TreeNode treeNode = createTreeNode();
		treeNode.setChildren(new ArrayList<TreeNode>());
		populateTreeNodeInternal(node, treeNode, context);

		// create/populate children
		for (Object child : allChildren) {
			TreeNode childNode = createTreeNode();
			populateTreeNodeInternal(child, childNode, context);
			treeNode.getChildren().add(childNode);
			childNode.setParent(treeNode);
			oldNodes.remove(child);
		}

		// cleanup old nodes
		for (NodeInfo oldChild : oldNodes.values()) {
			cleanupAfterNodeClosed(oldChild, nodeClient.getStatefulClientId(this), nodeClient.getCommunicationChannel(), null);
		}

		List<PathFragment> path = getPathForNode(node, context);
		if (logger.isTraceEnabled()) {
			logger.trace(
					"Dispatching content update for node {} to client [{} with statefulClientId={}]",
					new Object[] { getLabelForLog(node),
							nodeClient.getCommunicationChannel(),
							nodeClient.getStatefulClientId(this) });
		}
		updateNode(nodeClient.getCommunicationChannel(),
				nodeClient.getStatefulClientId(this), path, treeNode,
				false, false, false, true);
				
	}

	/**
	 * Notifies the client to expand or collapse the node in its active tree.
	 * 
	 * @param node node to expand/collapse
	 * @param client 
	 * @param expandNode true to expand the node, false to collapse
	 * @author Mariana
	 * 
	 */
	public void dispatchExpandedUpdate(Object node, CommunicationChannel client, boolean expandNode) {
		NodeInfo nodeInfo = openNodes.get(node);
		if (nodeInfo == null) {
			return;
		}
		for (NodeInfoClient nodeClient : nodeInfo.getClients()) {
			if (nodeClient.getCommunicationChannel().equals(client)) {
				dispatchExpandedUpdateForClient(nodeInfo.getNode(), expandNode, nodeClient);
				break;
			}
		}
	}

	protected void dispatchExpandedUpdateForClient(Object node, boolean expandNode, NodeInfoClient nodeClient) {
		GenericTreeContext treeContext = getTreeContext(nodeClient.getCommunicationChannel(), nodeClient.getStatefulClientId(this));
		
		TreeNode treeNode = createTreeNode();
		populateTreeNodeInternal(node, treeNode, treeContext);
	
		Map<Object, Object> context = new HashMap<Object, Object>();
		// adding this key will ensure that the correct path will be found using the NodeInfo hierarchy
		context.put(GO_UP_ON_NODE_INFO_KEY, true);		
		treeContext.setClientContext(context);
		List<PathFragment> path = getPathForNode(node, treeContext);
		
		updateNode(
				nodeClient.getCommunicationChannel(), 
				nodeClient.getStatefulClientId(this), 
				path, treeNode, 
				expandNode, !expandNode, false, false);	
									
		if (!expandNode) {
			closeNode(new StatefulServiceInvocationContext(nodeClient.getCommunicationChannel(), null, nodeClient.getStatefulClientId(this)), path, context);
		}
	}

	public GenericTreeContext getTreeContext(CommunicationChannel channel, String statefulClientId) {
		TreeInfoClient treeInfoClient = new TreeInfoClient(channel, statefulClientId);
		
		if (!treeContexts.containsKey(treeInfoClient)) {
			treeContexts.put(treeInfoClient, new GenericTreeContext(null));
		}
		return treeContexts.get(treeInfoClient);
	}

	public void revealNode(StatefulServiceInvocationContext context, Object node) {		
		if (logger.isTraceEnabled()) {
			logger.trace("Revealing node {} to client [{} with statefulClientId={}]", new Object[] { getLabelForLog(node), context.getCommunicationChannel(), context.getStatefulClientId() });
		}
		invokeClientMethod(
				context.getCommunicationChannel(), 
				context.getStatefulClientId(), 
				"revealNode", new Object[] {getPathForNode(node, getTreeContext(context.getCommunicationChannel(), context.getStatefulClientId()))});		
	}

	/**
	 * Cleans up the {@link #openNodes} and {@link #rootNodeInfo}.
	 * <p>
	 * Removes the given client form list of subscribed clients. <br>
	 * After that, verifies if the node has multiple subscribed clients.
	 * If not, deletes the node and the entry found on parent's list of children.
	 * 
	 * <p>
	 * This steps are done by iterating recursively on children list.
	 * 
	 * @param channel - if <code>null</code>, the node is
	 * 					considered to be deleted so it will be removed from map.
	 * 
	 * @see #closeNode()
	 * @see #dispatchContentUpdate()
	 * @see #cleanupChildren()
	 * 
	 * 
	 */
	protected void cleanupAfterNodeClosed(Object node, String statefulClientId, CommunicationChannel channel, RunnableWithParam<Void, NodeInfo> removeNodeInfoRunnable) {
		if (logger.isTraceEnabled()) {
			logger.trace("Cleanup node {} to client [{} with statefulClientId={}]", new Object[] { getLabelForLog(node), channel, statefulClientId });			
		}
		
		if (removeNodeInfoRunnable == null) {
			removeNodeInfoRunnable = new RunnableWithParam<Void, NodeInfo>() {				
				public Void run(NodeInfo nodeInfo) {
					logger.debug("Removing node from openNodes {}", nodeInfo);
					openNodes.remove(nodeInfo.getNode());					
					return null;
				}
			};
		}		
		NodeInfo nodeInfo = (NodeInfo) ((node instanceof NodeInfo) ? node : openNodes.get(node));
		if (nodeInfo == null) {
			if (rootNodeInfo.getNode().equals(node)) {
				nodeInfo = rootNodeInfo;
			} else {
				NodeInfo parentNodeInfo = openNodes.get(getParent(node, getTreeContext(channel,statefulClientId)));
				for (NodeInfo child : parentNodeInfo.getChildren()) {
					if (node.equals(child.getNode())) {
						nodeInfo = child;
						break;
					}
				}
			}
		}		
		if (openNodes.containsKey(nodeInfo.getNode())) { // open node
			for (final Iterator<NodeInfo> it = nodeInfo.getChildren().iterator(); it.hasNext();) {	
				NodeInfo childInfo = it.next();			
//				NodeInfoClient childNodeInfoClient = childInfo.getNodeInfoClientByCommunicationChannelThreadSafe(channel, statefulClientId, this);
//				if (childNodeInfoClient != null) {
//					cleanupAfterNodeClosed(childInfo, statefulClientId, channel, new RunnableWithParam<Void, NodeInfo>() {
//						
//						public Void run(NodeInfo nodeInfo) {
//							logger.debug("Removing node from openNodes & rootNode {}", nodeInfo);
//							openNodes.remove(nodeInfo.getNode());							
//							it.remove();
//							return null;
//						}
//					});		
//				}
			}
		}
			
		boolean removeOpenNode = false;
		if (channel == null) { // no channel, no open node, mark to be deleted
			removeOpenNode = true;			
		} else {
			if (nodeInfo.equals(rootNodeInfo)) {
				for (Iterator<TreeInfoClient> iter = treeContexts.keySet().iterator(); iter.hasNext(); ) {
					TreeInfoClient treeInfoClient = iter.next();
					if (treeInfoClient.getCommunicationChannel().equals(channel) &&
							(statefulClientId == null || treeInfoClient.getStatefulClientId().equals(statefulClientId))) {
						// found
						treeContexts.remove(treeInfoClient);
						if (statefulClientId != null) { // only this node must be removed, so return
							break;
						}
					}
				}			
			}
			
//			NodeInfoClient client = nodeInfo.removeNodeInfoClientByCommunicationChannel(channel, statefulClientId, this);
//			if (statefulClientId != null && client == null) { // a specific client wasn't found, maybe it was removed while executing this method
//				logger.debug("The client = {} is not subscribed to the Node Info with path = {}", channel, nodeInfo.getPathFragment());
//			}
			
			if (logger.isTraceEnabled()) {
				logger.trace("Removing client = {} to NodeInfo with path = {}. Now there are {} clients subscribed to this resource.", new Object[] {channel, nodeInfo.getPathFragment(), nodeInfo.getClients().size() });
			}
			
			if (nodeInfo.getClients().size() == 0) { // no other clients, mark to be deleted
				removeOpenNode = true;
			}		
		}
		
		if (removeOpenNode)	{
			namedLockPool.lock(nodeInfo.getNode());
			try {
				if (logger.isTraceEnabled()) {
					logger.trace("Removing open node {} for statefulClientId={}]", new Object[] { getLabelForLog(node), statefulClientId });			
				}
				removeNodeInfoRunnable.run(nodeInfo);
				if (nodeInfo.equals(rootNodeInfo)) {
					treeContexts.clear();
				}
			} finally {
				namedLockPool.unlock(nodeInfo.getNode());
			}
		}
	}

	/**
	 * 
	 */
	public List<PathFragment> getPathForNode(Object node) {
		return getPathForNode(node, null);
	}

	/**
	 * Subclasses that implement this method must provide 
	 * a list of children for given node. If the parameter
	 * is the dummy object used as root, that means that the content for
	 * the root node should be returned.
	 * 
	 * <p>
	 * Mandatory for all tree types.
	 * 
	 * <p>
	 * Also a context can be provided to filter the children list.
	 * 
	 */
	public abstract Collection<?> getChildrenForNode(Object node, GenericTreeContext context);

	/**
	 * Should return whether the current node has children or not, preferably
	 * by an efficient method (i.e. something better than <code>getChildrenForNode() != null</code>).
	 * This recommendation is related to possible performance impact. E.g. this method
	 * may trigger the load mechanism of a resource. 
	 * 
	 * @author Cristi
	 * @return a <code>Boolean</code> which has 3 states. The 3rd state (i.e. null) may
	 * be handy if implementing services that delegate to other "sub"-services.
	 */
	public /*abstract*/ Boolean nodeHasChildren(Object node, GenericTreeContext context) {
		return false;
	}

	/**
	 * Subclasses that implement this method must provide 
	 * a parent for given node.	
	 *  
	 * 
	 */
	public abstract Object getParent(Object node, GenericTreeContext context);

	/**
	 * Populates {@link TreeNode#getPathFragment()} with data form {@link #getPathFragmentForNode()} and
	 * then delegates to the abstract method {@link #populateChildren()}.
	 * 
	 * @author Cristi
	 * 
	 */
	private void populateTreeNodeInternal(Object source, TreeNode destination, GenericTreeContext context) {
		destination.setPathFragment(getPathFragmentForNode(source, context));
		destination.setHasChildren(nodeHasChildren(source, context));
		populateTreeNode(source, destination, context);
	}

	/**
	 * Subclasses that implement this method must 
	 * populate the <code>destination</code> tree node with
	 * information stored in <code>source</code>.
	 * 
	 * <p>
	 * This must operate only on current node properties, not on its children
	 * (e.g. label, icon, etc.). {@link TreeNode#isHasChildren()} and {@link TreeNode#getPathFragment())
	 * are already automatically populated. 
	 * 
	 * <p>
	 * This method is never invoked for the root node.
	 * 
	 * 
	 * @author Cristi
	 * @return The return result is not taken into account by the platform. By convention, everyone
	 * should return <code>true</code>. The return value may be used by tree services that have "sub"
	 * tree services that they use for delegation (e.g. if result == null => the sub-service didn't know
	 * how to handle the call). 
	 */
	public abstract boolean populateTreeNode(Object source, TreeNode destination, GenericTreeContext context);

	/**
	 * Subclasses that implement this method must provide 
	 * a node for given {@link PathFragment}.
	 * 	
	 * 
	 */
	public abstract Object getNodeByPathFragment(Object parent, PathFragment pathFragment, GenericTreeContext context);

	/**
	 * Subclasses that implement this method must provide 
	 * a {@link PathFragment} for given node.
	 * 	 
	 * 
	 */
	public abstract PathFragment getPathFragmentForNode(Object node);

	/**
	 * @author Mariana
	 */
	public PathFragment getPathFragmentForNode(Object node, GenericTreeContext context) {
		return getPathFragmentForNode(node);
	}
	
	/**
	 * Returns an object for the given path, 
	 * including for root (i.e. <code>fullPath = null</code>. 
	 * 
	 * <p>
	 * In the case of root, a dummy
	 * object is accepted as well (e.g. an instance of this
	 * service, etc.), but it shouldn't be null.
	 * 
	 * <br>
	 * Note:
	 * This method must be implemented if other implementation seems to be more effective.
	 * 	 
	 * 
	 */
	/**
	 * @param fullPath
	 * @param context
	 * @return
	 * 
	 */
	public Object getNodeByPath(List<PathFragment> fullPath, GenericTreeContext context) {		
		NodeInfo nodeInfo;
		NodeInfo parentInfo;		
		if (isDispatchEnabled(null) && rootNodeInfo != null) {
			// get the root node
			nodeInfo = rootNodeInfo;
		} else {
			// create a dummy root node
			nodeInfo = new NodeInfo();
			nodeInfo.setParent(null);
			nodeInfo.setNode(getNodeByPathFragment(null, null, context));
		}
		if (fullPath != null) {
			for (PathFragment pathFragment : fullPath) {
				// hold the parent
				parentInfo = nodeInfo;
				// this will be filled if node found
				nodeInfo = null;
				// if in the call context for this method there is no special
				// requirement for taking the node in real time by using only its
				// pathFragment and if node dispatched we first try to search in 
				// the stored tree structure
				if (context != null && !context.containsKey(GO_DOWN_ON_PATH_FRAGMENT_KEY) && parentInfo != null && isDispatchEnabled(parentInfo.getNode())) {							
					for (NodeInfo child : parentInfo.getChildren()) {		
						if (child.getPathFragment().getName().equals(pathFragment.getName()) && 
							child.getPathFragment().getType().equals(pathFragment.getType())) {
							nodeInfo = child;
							break;
						}
					}				
				}
				// if special requirement for taking the node in real time by using only its
				// pathFragment or not dispatched or not found in tree structure
				if (nodeInfo == null) {
					// get node from fragment and create a dummy nodeInfo
					// to be used in next iteration
					Object node = getNodeByPathFragment(parentInfo.getNode(), pathFragment, context);
					
					// There was a problem with the given path because no node is uniquely identify by it
					if (node == null)
						return null;
					else {
						// create a dummy nodeInfo
						nodeInfo = new NodeInfo();
						nodeInfo.setParent(parentInfo);
						nodeInfo.setNode(node);
					}
				}
			}
		}
		return nodeInfo.getNode();
	}

	/**
	 * Returns a path for give node by iterating recursively up through parents.
	 * Recursive method.
	 * 
	 * @see #getParent()
	 * 
	 * 
	 */
	public List<PathFragment> getPathForNode(Object node, GenericTreeContext context) {		
		List<PathFragment> path = new ArrayList<PathFragment>();
		// when renaming occurs, the node is destroyed and recreated in another thread
		// because if this, there are cases when it comes in this method as null
		if (node == null) {
			return null;
		}
		
		while (node != null && !node.equals(getNodeByPath(null, context))) {
			// search in map
			NodeInfo parentNodeInfo = openNodes.get(node);
			if (parentNodeInfo != null) { // found, get its path fragment
				path.add(0, parentNodeInfo.getPathFragment());
			} else { // not found
				path.add(0, getPathFragmentForNode(node, context));
			}
			
			// go up
			// in case the node was deleted, we won't find the correct path using the resource's parents
			// instead we go up using NodeInfo
			if (context != null && context.get(GO_UP_ON_NODE_INFO_KEY) != null) {
				node = parentNodeInfo.getParent().getNode();
			} else {
				node = getParent(node, context);
			}
		}		

		return path;		
	}

	/**
	 * 
	 */
	public Object getNodeByPath(List<PathFragment> fullPath) {
		return getNodeByPath(fullPath, null);
	}

	public Object getParent(Object node) {
		return getParent(node, null);
	}

	/**
	 * 
	 */
	protected void updateNode(CommunicationChannel channel, String statefulClientId, List<PathFragment> path, TreeNode treeNode, boolean expandNode, boolean colapseNode, boolean selectNode, boolean isContentUpdate, Object clientInvocationOptions) {
		invokeClientMethod(
				channel, 
				statefulClientId, 
				"updateNode", 
				new Object[] {path, treeNode, expandNode, colapseNode, selectNode});		
	}

	/**
	 * 
	 */
	public void startInplaceEditor(StatefulServiceInvocationContext context, String contributionId, List<PathFragment> nodePath, Boolean autoCreateElementAfterEditing) { 
		invokeClientMethod(
				context.getCommunicationChannel(), 
				context.getStatefulClientId(), 
				"startInplaceEditor", 
				new Object[] {contributionId, nodePath, autoCreateElementAfterEditing});	
	
	}

	/**
	 * Creates the client tree structure and
	 * modifies the server structure by updating {@link #rootNodeInfo} and {@link #openNodes}.	
	 * 
	 * @return - the {@link TreeNode} created
	 * 
	 * @see #populateChildren()
	 * @see #addNodeInfo()
	 * 
	 */
	public TreeNode openNodeInternal(CommunicationChannel channel, String statefulClientId, List<PathFragment> fullPath, Map<Object, Object> context) {
		GenericTreeContext treeContext = getTreeContext(channel, statefulClientId);
		treeContext.setClientContext(context);
		
		// gets the source node corresponding to given path			
		Object source = getNodeByPath(fullPath, treeContext);
		// create and populate the destination node
		TreeNode treeNode = createTreeNode();
		treeNode.setChildren(new ArrayList<TreeNode>());
		if (fullPath != null) {
			// we populate the node only for non-root nodes
			populateTreeNodeInternal(source, treeNode, treeContext);
		} else {
			treeNode.setHasChildren(true);
		}
		
		// for dispatched trees, update the server tree structure
		if (isDispatchEnabled(source)) {	
			addNodeInfo(channel, statefulClientId, source, fullPath == null, true, treeContext);
		}
		
		// create structure for current tree node or create structure for entire tree
		boolean entireStructure = false;
		if (treeContext.get(WHOLE_TREE_KEY) != null) {
			entireStructure = ((Boolean) context.get(WHOLE_TREE_KEY)).booleanValue();
		}
		// populate node with children data
		populateChildren(channel, statefulClientId, source, treeNode, treeContext, entireStructure);
		
		return treeNode;
	}

	private TreeNode openNodeInternalFromExistingNodes(CommunicationChannel channel, String statefulClientId, List<PathFragment> fullPath, Map<Object, Object> context, Set<String> existingNodes) {
		TreeNode node = openNodeInternal(channel, statefulClientId, fullPath, context);
		List<TreeNode> children = new ArrayList<TreeNode>();
		for (TreeNode child : node.getChildren()) {
			// get child path as list of pathFragment
			List<PathFragment> childFullPath = new ArrayList<PathFragment>();
			if (fullPath != null) {
				childFullPath.addAll(fullPath);
			}
			childFullPath.add(child.getPathFragment());		
			// get child path as string
			String path = "";
			for (PathFragment pathFragment : childFullPath) {
				if (path != "") {
					path += "/";
				}
				path += pathFragment.getName();
			}
			if (existingNodes.contains(path)) {
				children.add(openNodeInternalFromExistingNodes(channel, statefulClientId, childFullPath, context, existingNodes));
			} else {
				children.add(child);
			}
		}
		node.setChildren(children);
		return node;
	}

	///////////////////////////////////////////////////////////////
	// @RemoteInvocation methods
	///////////////////////////////////////////////////////////////
	
	/**
	 * 
	 */
	@RemoteInvocation
	public void subscribe(StatefulServiceInvocationContext context, IStatefulClientLocalState statefulClientLocalState) {
		logger.info("Subscribing to {} with {}", context.getStatefulClientId(), context.getCommunicationChannel());
		
		GenericTreeStatefulClientLocalState localState = (GenericTreeStatefulClientLocalState) statefulClientLocalState;
			
		Set<String> existingNodes = new HashSet<String>();
		
		for (List<PathFragment> path : localState.getOpenNodes()) {
			String fullPath = "";
			for (PathFragment pathFragment : path) {
				if (fullPath != "") {
					fullPath += "/";
				}
				fullPath += pathFragment.getName();
			}			
			existingNodes.add(fullPath);
		}
	
		// set tree context
		GenericTreeContext treeContext = getTreeContext(context.getCommunicationChannel(), context.getStatefulClientId());
		treeContext.setStatefulContext(localState.getStatefulContext());
		treeContext.setClientContext(localState.getClientContext());
		
		if (existingNodes.size() > 0) {			
			TreeNode node = openNodeInternalFromExistingNodes(
					context.getCommunicationChannel(), 
					context.getStatefulClientId(), 
					null, 
					treeContext.getClientContext(), 
					existingNodes);		
			updateNode(context.getCommunicationChannel(), context.getStatefulClientId(), null, node, false, false, false, true);
		}
	}

	/**
	 * 
	 */
	@RemoteInvocation
	public void unsubscribe(StatefulServiceInvocationContext context, IStatefulClientLocalState statefulClientLocalState) {
		logger.info("Unsubscribing from {} with {}", context.getStatefulClientId(), context.getCommunicationChannel());
		if (isDispatchEnabled(null)) {
			cleanupAfterNodeClosed(rootNodeInfo.getNode(), context.getStatefulClientId(), context.getCommunicationChannel(), null);
		}
	}

	/**
	 * 
	 */
	@RemoteInvocation
	public abstract String getInplaceEditorText(StatefulServiceInvocationContext context, List<PathFragment> fullPath);

	/**
	 * 	 
	 * 
	 */
	@RemoteInvocation
	public abstract boolean setInplaceEditorText(StatefulServiceInvocationContext context, List<PathFragment> path, String text);

	/**
	 * 
	 */
	@RemoteInvocation
	public boolean performDrop(StatefulServiceInvocationContext context, List<PathFragment> target, List<List<PathFragment>> selectedResources) { 
		return false;
	}

	/**
	 * Called from client when a node is opened.
	 * Used for both types of trees.
	 * 
	 * <p>
	 * Creates the corresponding {@link TreeNode} and its children and sends
	 * an update command to client.
	 * 
	 * <p>
	 * For dispatched trees, registers the object in {@link #openNodes} map
	 * and adds it in the list of parent's children (the parent must be already in map).
	 * 
	 * @param siContext
	 * @param fullPath - path of the node that must be opened. 
	 * 				If <code>null</code>, the node is considered to be the root.
	 * @param context - context used to customize the method (e.g. filter for the children list)
	 * 
	 * @see #openNodeInternal()
	 * 
	 * 
	 */
	@RemoteInvocation
	public void openNode(StatefulServiceInvocationContext context, List<PathFragment> path, Map<Object, Object> clientContext) {
		AuditDetails auditDetails = new AuditDetails(logger, "OPEN_NODE", path, context.getStatefulClientId());
		
		// create structure
		TreeNode treeNode = openNodeInternal(context.getCommunicationChannel(), context.getStatefulClientId(), path, clientContext);		
		
		updateNode(
				context.getCommunicationChannel(), 
				context.getStatefulClientId(), 
				path, 
				treeNode, 
				clientContext != null && clientContext.get(EXPAND_NODE_KEY) != null, 
				false, 
				clientContext != null && clientContext.get(SELECT_NODE_KEY) != null, 
				true);	
		
		LogUtil.audit(auditDetails);
	}

	public void dispatchContentUpdate(Object node) {
		dispatchContentUpdate(node, null);
	}
	
	protected void updateNode(CommunicationChannel channel, String statefulClientId, List<PathFragment> path, TreeNode treeNode, boolean expandNode, boolean colapseNode, boolean selectNode, boolean isContentUpdate) {
		updateNode(channel, statefulClientId, path, treeNode, expandNode, colapseNode, selectNode, isContentUpdate, null);
	}
	
	/**
	 * Called from client when a node is closed.
	 * Used only for dispatched trees.
	 * 
	 * <p>
	 * Cleans the {@link #openNodes} map.
	 * 
	 * @param context	
	 * @param path - path of the node that must be closed. 
	 * 				If <code>null</code>, the node is considered to be the root.
	 * 
	 * 
	 */
	@RemoteInvocation
	public void closeNode(StatefulServiceInvocationContext context, List<PathFragment> path, Map<Object, Object> clientContext) {
		GenericTreeContext treeContext = getTreeContext(context.getCommunicationChannel(), context.getStatefulClientId());
		treeContext.setClientContext(clientContext);
		Object source = getNodeByPath(path, treeContext);
		if (source == null) { // something happened
			throw new RuntimeException("Source node not found for path " + path);
		}
		if (isDispatchEnabled(source)) {
			if (logger.isTraceEnabled()) {
				logger.trace("Closing node with path {} for client [{} with statefulClientId={}]", new Object[] { path, context.getCommunicationChannel(), context.getStatefulClientId() });				
			}
			cleanupAfterNodeClosed(source, context.getStatefulClientId(), context.getCommunicationChannel(), null);
		}
	}

	@RemoteInvocation	
	public boolean updateTreeStatefulContext(StatefulServiceInvocationContext context, String key, Object value) {
		TreeInfoClient treeInfo = new TreeInfoClient(context.getCommunicationChannel(), context.getStatefulClientId());
		GenericTreeContext treeContext = treeContexts.get(treeInfo);
					
		if (treeContext == null) {
			treeContext = new GenericTreeContext(null);			
		}		
		treeContext.getStatefulContext().put(key, value);
		
		treeContexts.put(treeInfo, treeContext);
		
		return true;
	}
	
}