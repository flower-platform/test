package com.crispico.flower.mp.codesync.code.java;

import java.util.Arrays;

import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IElementChangedListener;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaModelException;
import org.flowerplatform.communication.CommunicationPlugin;
import org.flowerplatform.communication.service.ServiceRegistry;
import org.flowerplatform.editor.remote.EditableResource;
import org.flowerplatform.editor.remote.EditableResourceClient;
import org.flowerplatform.editor.remote.EditorStatefulService;

import com.crispico.flower.mp.codesync.base.communication.CodeSyncEditorStatefulService;
import com.crispico.flower.mp.codesync.code.CodeSyncCodePlugin;

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
					try {
						CodeSyncCodePlugin.getInstance().getDragOnDiagramHandler().handleDragOnDiagram(Arrays.asList(deltaToProcess.getElement().getCorrespondingResource()), null, null, null, client.getCommunicationChannel());
					} catch (JavaModelException e) {
//						e.printStackTrace();
					}
				}
			}
		}
	}

}
