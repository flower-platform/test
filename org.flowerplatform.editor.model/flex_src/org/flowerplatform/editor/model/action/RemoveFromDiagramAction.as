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
package org.flowerplatform.editor.model.action {
	
	import org.flowerplatform.editor.model.EditorModelPlugin;
	import org.flowerplatform.emf_model.notation.Diagram;
	import org.flowerplatform.emf_model.notation.View;

	/**
	 * @author Mariana Gheorghe
	 */
	public class RemoveFromDiagramAction extends DiagramShellAwareActionBase {
		
		public function RemoveFromDiagramAction() {
			super();
			
			label = EditorModelPlugin.getInstance().getMessage("action.removeFromDiagram");
			icon = EditorModelPlugin.getInstance().getResourceUrl("images/action/eraser.png");
			preferShowOnActionBar = true;
			orderIndex = 950;
		}
		
		override public function get visible():Boolean {
			if (selection != null && selection.length == 1) {
				var selectedItem:Object = selection.getItemAt(0);
				if (selectedItem is View && !(selectedItem is Diagram)) {
					return true;
				}
			}
			return false;
		}
		
		override public function run():void {
			var node:View = View(selection.getItemAt(0));
			notationDiagramEditorStatefulClient.service_removeView(node.id);
		}
	}
}