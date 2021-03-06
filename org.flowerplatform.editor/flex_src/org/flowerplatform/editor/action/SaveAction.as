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
package org.flowerplatform.editor.action {

	
	import org.flowerplatform.editor.remote.EditorStatefulClient;
	import org.flowerplatform.flexutil.action.ActionBase;
	
	//use namespace mx_internal;
	/**
	 * @author Sebastian Solomon
	 */
	public class SaveAction extends ActionBase {
		
		public var currentEditorStatefulClient:EditorStatefulClient;
		
		public override function run():void {
			if (currentEditorStatefulClient != null) {
				currentEditorStatefulClient.save();		
			}
		}
		
	}
}