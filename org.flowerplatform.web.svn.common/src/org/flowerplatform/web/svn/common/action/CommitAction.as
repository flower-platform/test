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

package org.flowerplatform.web.svn.common.action {
	
	import mx.collections.*;
	import mx.collections.ArrayCollection;
	import mx.collections.ArrayList;
	
	import org.flowerplatform.communication.tree.remote.PathFragment;
	import org.flowerplatform.communication.tree.remote.TreeNode;
	import org.flowerplatform.flexutil.FlexUtilGlobals;
	import org.flowerplatform.flexutil.popup.ActionBase;
	import org.flowerplatform.web.svn.common.SvnCommonPlugin;
	import org.flowerplatform.web.svn.common.ui.CommitView;
	
	/**
	 * @author Victor Badila
	 */	
	public class CommitAction extends ActionBase {
		
		public function CommitAction() {
			label = SvnCommonPlugin.getInstance().getMessage("svn.action.commit.label");
			icon = null;
		}
		
		//get visible may be the same for commit, update and possibly other actions	
		public override function get visible():Boolean {				
			if (selection.length == 0)
				return false;
			if (!selection.getItemAt(0) is TreeNode)
				return false;
			var node_type:String = TreeNode(selection.getItemAt(0)).pathFragment.type;
			if(node_type != SvnCommonPlugin.NODE_TYPE_REPOSITORY && node_type != SvnCommonPlugin.NODE_TYPE_FILE)
				return false;
			for (var i:int = 0; i<selection.length; i++) {
				var currentNode:TreeNode = TreeNode(selection.getItemAt(i)); 
				if (currentNode.pathFragment.type != node_type || (currentNode.pathFragment.type == SvnCommonPlugin.NODE_TYPE_FILE && currentNode.customData.isFolder == false)) {
					return false;
				}			
			}			
			var organizationName:String = PathFragment(TreeNode(selection.getItemAt(0)).getPathForNode(false).getItemAt(0)).name;
			for (var i:int=0; i<selection.length; i++) {
				var treeNode:TreeNode = TreeNode(selection.getItemAt(i));
				if (organizationName != PathFragment(treeNode.getPathForNode(false).getItemAt(0)).name) {
					return false;
				}
			}
			return true;
		}
			
		public override function run():void {			
			var view:CommitView = new CommitView();
			view.selection = ArrayList(selection);
			FlexUtilGlobals.getInstance().popupHandlerFactory.createPopupHandler()
				.setPopupContent(view)
				.show();
		}		
		
	}
}