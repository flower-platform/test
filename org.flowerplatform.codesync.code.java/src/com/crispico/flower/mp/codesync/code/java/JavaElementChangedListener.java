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
package com.crispico.flower.mp.codesync.code.java;

import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IElementChangedListener;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.IJavaProject;
import org.flowerplatform.communication.CommunicationPlugin;
import org.flowerplatform.editor.remote.EditableResource;
import org.flowerplatform.editor.remote.EditableResourceClient;
import org.flowerplatform.editor.remote.EditorStatefulService;

import com.crispico.flower.mp.codesync.base.communication.CodeSyncEditorStatefulService;

/**
 * @author Mariana
 */
public class JavaElementChangedListener implements IElementChangedListener {

	@Override
	public void elementChanged(ElementChangedEvent event) {
		processDelta(event.getDelta());
	}
	
	private void processDelta(IJavaElementDelta deltaToProcess) {
		for (IJavaElementDelta delta : deltaToProcess.getAffectedChildren()) {
			processDelta(delta);
		}
		
		IJavaProject project = deltaToProcess.getElement().getJavaProject();
		if (project != null) {
			String path = project.getPath().toString();
			EditorStatefulService service = (EditorStatefulService) CommunicationPlugin.getInstance().getServiceRegistry().getService(CodeSyncEditorStatefulService.SERVICE_ID);
			EditableResource editableResource = service.getEditableResource(path);
			if (editableResource != null) {
				for (EditableResourceClient client : editableResource.getClients()) {
//					try {
//						CodeSyncPlugin.getInstance().getDragOnDiagramHandler().handleDragOnDiagram(Arrays.asList(deltaToProcess.getElement().getCorrespondingResource()), null, null, null, client.getCommunicationChannel());
//					} catch (JavaModelException e) {
//						e.printStackTrace();
//					}
				}
			}
		}
	}

}