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
package org.flowerplatform.codesync.code.javascript;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;

import org.flowerplatform.codesync.remote.CodeSyncDiagramOperationsService1;
import org.flowerplatform.communication.CommunicationPlugin;
import org.flowerplatform.communication.channel.CommunicationChannel;
import org.flowerplatform.communication.service.ServiceInvocationContext;
import org.flowerplatform.editor.EditorPlugin;
import org.flowerplatform.editor.model.IDragOnDiagramHandler;
import org.flowerplatform.emf_model.notation.Diagram;
import org.flowerplatform.emf_model.notation.View;

import com.crispico.flower.mp.codesync.base.CodeSyncPlugin;
import com.crispico.flower.mp.codesync.code.CodeSyncCodePlugin;
import com.crispico.flower.mp.model.codesync.CodeSyncElement;

/**
 * @author Mariana Gheorghe
 */
public class JavascriptDragOnDiagramHandler implements IDragOnDiagramHandler {

	@Override
	public boolean handleDragOnDiagram(ServiceInvocationContext context, Collection<?> draggedObjects, Diagram diagram, View viewUnderMouse, Object layoutHint, CommunicationChannel communicationChannel) {
		for (Object object : draggedObjects) {
			File resource = (File) EditorPlugin.getInstance().getFileAccessController().getFile((String) object);
			File project = CodeSyncPlugin.getInstance().getProjectsProvider().getContainingProjectForFile(resource);
			
			if (!acceptDraggedObject(resource)) {
				return false;
			}
			
			CodeSyncElement codeSyncElement = CodeSyncCodePlugin.getInstance().getCodeSyncElement(
					project, resource, CodeSyncCodeJavascriptPlugin.TECHNOLOGY, communicationChannel, false);
			codeSyncElement = codeSyncElement.getChildren().get(0);
			
			CodeSyncDiagramOperationsService1 service = (CodeSyncDiagramOperationsService1) 
					CommunicationPlugin.getInstance().getServiceRegistry().getService("codeSyncDiagramOperationsService");
			service.addOnDiagram(context, diagram.eResource().getURIFragment(diagram), null, codeSyncElement, new HashMap<String, Object>());
		}
		return true;
	}
	
	private boolean acceptDraggedObject(Object object) {
		if (!(object instanceof File)) {
			return false;
		}
		String extension = CodeSyncPlugin.getInstance().getFileExtension((File) object);
		return (CodeSyncCodeJavascriptPlugin.TECHNOLOGY.equals(extension) || "html".equals(extension));
	}

}
