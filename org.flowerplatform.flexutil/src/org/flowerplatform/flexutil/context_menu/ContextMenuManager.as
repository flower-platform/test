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
package org.flowerplatform.flexutil.context_menu {
	import flash.display.DisplayObject;
	import flash.events.MouseEvent;
	
	import mx.collections.ArrayList;
	import mx.collections.IList;
	import mx.core.FlexGlobals;
	import mx.managers.PopUpManager;
	
	import org.flowerplatform.flexutil.action.ActionUtil;
	import org.flowerplatform.flexutil.action.IAction;

	/**
	 * Listens to the right click menu, dispatches FILL_CONTEXT_MENU in order to get actions
	 * and selection and then opens the context menu.
	 * 
	 * <p>
	 * Holds the actions and selection for the current "right click session".
	 * 
	 * @author Cristian Spiescu
	 */
	public class ContextMenuManager	{
		
		public var contextMenuStack:Vector.<ContextMenu> = new Vector.<ContextMenu>();
		
		public var allActions:Vector.<IAction>;
		
		public var selection:IList;
		
		public function ContextMenuManager() {
			FlexGlobals.topLevelApplication.stage.addEventListener(MouseEvent.RIGHT_CLICK, rightClickHandler);
		}
		
		public function dispose():void {
			FlexGlobals.topLevelApplication.stage.removeEventListener(MouseEvent.RIGHT_CLICK, rightClickHandler);
		}
		
		/**
		 * Opens the menu (first level), if someone has items to provide to the menu.
		 */
		protected function rightClickHandler(event:MouseEvent):void {
			// if there are open menus => close them
			if (contextMenuStack.length > 0) {
				contextMenuStack[0].closeContextMenuStack(0);
			}
			
			// find a provider for actions (i.e. someone listening to FILL_CONTEXT_MENU) and
			// open the menu
			var currentElementUnderMouse:DisplayObject = DisplayObject(event.target);
			while (currentElementUnderMouse != null) {
				if (currentElementUnderMouse.hasEventListener(FillContextMenuEvent.FILL_CONTEXT_MENU)) {
					dispatchSimulatedMouseDownEvent(DisplayObject(event.target), currentElementUnderMouse, event);
					var cmEvent:FillContextMenuEvent = new FillContextMenuEvent();
					currentElementUnderMouse.dispatchEvent(cmEvent);

					if (cmEvent.allActions != null && cmEvent.allActions.length > 0) {
						var cm:org.flowerplatform.flexutil.context_menu.ContextMenu = new org.flowerplatform.flexutil.context_menu.ContextMenu();
						allActions = cmEvent.allActions;
						selection = cmEvent.selection;
						cm.openContextMenu(this, event.stageX, event.stageY, cmEvent.rootActionsAlreadyCalculated, null);		
					}
					return;
				}
				currentElementUnderMouse = currentElementUnderMouse.parent;
			}
		}
		
		/**
		 * When a right click has been caught (and the object has a listener for FILL_CONTEXT_MENU event), 
		 * we simulate a left click on the component hierarchy, so that they can do the logic associated with
		 * a click (e.g. select the item).
		 */ 
		protected function dispatchSimulatedMouseDownEvent(startingWith:DisplayObject, endingWidth:DisplayObject, event:MouseEvent):void {
			var simulatedLeftClickEvent:MouseEvent = new MouseEvent(
				MouseEvent.MOUSE_DOWN, event.bubbles, event.cancelable, event.localX, event.localY,
				event.relatedObject, event.ctrlKey, event.altKey, event.shiftKey, 
				event.buttonDown, event.delta);
			var currentElementUnderMouse:DisplayObject = DisplayObject(event.target);
			while (currentElementUnderMouse != null && currentElementUnderMouse != endingWidth) {
				currentElementUnderMouse.dispatchEvent(simulatedLeftClickEvent);
				currentElementUnderMouse = currentElementUnderMouse.parent;
			}

		}
		
		/**
		 * Closes any open menus, and opens a menu for the current parentActionId (which
		 * should belong to a composed action).
		 */
		public function openContextMenu(x:Number, y:Number, allActions:Vector.<IAction>, actionsForCurrentLevelAreadyCalculated:IList, parentActionId:String, selection:IList):void {
			if (contextMenuStack.length > 0) {
				contextMenuStack[0].closeContextMenuStack(0);
			}
			this.allActions = allActions;
			this.selection = selection;
			new ContextMenu().openContextMenu(this, x, y, actionsForCurrentLevelAreadyCalculated, parentActionId);
		}
	}
}