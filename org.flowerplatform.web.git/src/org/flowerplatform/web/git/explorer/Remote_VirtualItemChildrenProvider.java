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
package org.flowerplatform.web.git.explorer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Repository;
import org.flowerplatform.common.util.Pair;
import org.flowerplatform.communication.tree.GenericTreeContext;
import org.flowerplatform.communication.tree.IChildrenProvider;
import org.flowerplatform.communication.tree.remote.TreeNode;
import org.flowerplatform.web.git.GitNodeType;
import org.flowerplatform.web.git.explorer.entity.RemoteNode;

/**
 * Parent node = Virtual node (Remotes) (i.e. Pair<Repository, nodeType>).<br/>
 * Child node = remote, i.e. Pair<remoteName, nodeType>.
 * 
 * @author Cristina Constantinescu
 */
public class Remote_VirtualItemChildrenProvider implements IChildrenProvider {
	
	@Override
	public Collection<Pair<Object, String>> getChildrenForNode(Object node, TreeNode treeNode, GenericTreeContext context) {
		@SuppressWarnings("unchecked")
		Repository repository = ((Pair<Repository, String>) node).a;		
		Collection<Pair<Object, String>> result = new ArrayList<Pair<Object, String>>();
		
		Set<String> configNames = repository.getConfig().getSubsections(ConfigConstants.CONFIG_KEY_REMOTE);

		Pair<Object, String> child;	
		RemoteNode realChild;
		for (String configName : configNames) {
			realChild = new RemoteNode(repository, configName);
			child = new Pair<Object, String>(realChild, GitNodeType.NODE_TYPE_REMOTE);
			result.add(child);
		}
		return result;
	}

	@Override
	public Boolean nodeHasChildren(Object node, TreeNode treeNode, GenericTreeContext context) {
		@SuppressWarnings("unchecked")
		Repository repository = ((Pair<Repository, String>) node).a;
		return repository.getConfig().getSubsections(ConfigConstants.CONFIG_KEY_REMOTE).size() > 0;
	}

}