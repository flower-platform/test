package org.flowerplatform.editor.model.java;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.internal.core.CompilationUnit;
import org.flowerplatform.communication.channel.CommunicationChannel;
import org.flowerplatform.communication.command.AbstractServerCommand;
import org.flowerplatform.communication.tree.remote.PathFragment;
import org.flowerplatform.editor.model.IDragOnDiagramHandler;
import org.flowerplatform.editor.model.java.remote.JavaClassDiagramOperationsService;
import org.flowerplatform.emf_model.notation.Bounds;
import org.flowerplatform.emf_model.notation.Diagram;
import org.flowerplatform.emf_model.notation.Node;
import org.flowerplatform.emf_model.notation.NotationFactory;
import org.flowerplatform.emf_model.notation.View;
import org.flowerplatform.web.projects.remote.ProjectsService;

import com.crispico.flower.mp.codesync.code.CodeSyncCodePlugin;
import com.crispico.flower.mp.codesync.code.java.CodeSyncCodeJavaPlugin;
import com.crispico.flower.mp.model.codesync.CodeSyncElement;

/**
 * @author Mariana
 */
public class JavaDragOnDiagramHandler extends AbstractServerCommand implements IDragOnDiagramHandler {

	public List<PathFragment> pathWithRoot;
	
	@Override
	public boolean handleDragOnDiagram(Collection<?> draggedObjects, Diagram diagram, View viewUnderMouse, Object layoutHint, CommunicationChannel communicationChannel) {
//		for (Object object : draggedObjects) {
//			if (!acceptDraggedObject(object)) {
//				return false;
//			}
//		}
//		
//		for (Object object : draggedObjects) {
//			String fqName = CodeSyncCodePlugin.getInstance().getFullyQualifiedNameProvider().getFullyQualifiedName(object);
//			
//			// TODO Mariana : create views
//			
//			CodeSyncCodeJavaPlugin.getInstance().getFolderModelAdapter().setLimitedPath(fqName);
//			CodeSyncCodePlugin.getInstance().getCodeSyncElement(fqName, CodeSyncCodeJavaPlugin.TECHNOLOGY, communicationChannel);
//		}
		
		for (Object object : draggedObjects) {
			IResource resource = ProjectsService.getInstance().getProjectWrapperResourceFromFile((List<PathFragment>) object);
			CodeSyncElement cse = CodeSyncCodePlugin.getInstance().getCodeSyncElement(resource.getProject(), resource, CodeSyncCodeJavaPlugin.TECHNOLOGY, communicationChannel, false);
			
			Node node = NotationFactory.eINSTANCE.createNode();
			node.setViewType("class");
			node.setDiagrammableElement(cse.getChildren().get(0)); // get the class from the file
			
			Node classTitle = NotationFactory.eINSTANCE.createNode();
			classTitle.setViewType("classTitle");
			classTitle.setDiagrammableElement(cse.getChildren().get(0));
			node.getPersistentChildren().add(classTitle);
			
			Node classAttrSeparator = NotationFactory.eINSTANCE.createNode();
			classAttrSeparator.setViewType(JavaClassDiagramOperationsService.ATTRIBUTE_SEPARATOR);
			node.getPersistentChildren().add(classAttrSeparator);
			
			Bounds bounds = NotationFactory.eINSTANCE.createBounds();
			bounds.setX(200);
			bounds.setHeight(100);
			bounds.setWidth(100);
			node.setLayoutConstraint(bounds);
			diagram.getPersistentChildren().add(node);
		}
		
		return true;
	}

	private boolean acceptDraggedObject(Object object) {
		// TODO Mariana : add support for JE
		return (object instanceof String || object instanceof IFile && ((IFile) object).getFileExtension().equals(CodeSyncCodeJavaPlugin.TECHNOLOGY)) || object instanceof CompilationUnit;
	}

	@Override
	public void executeCommand() {
		IResource resource = ProjectsService.getInstance().getProjectWrapperResourceFromFile(pathWithRoot);
		CodeSyncCodePlugin.getInstance().getCodeSyncElement(resource.getProject(), resource, CodeSyncCodeJavaPlugin.TECHNOLOGY, communicationChannel, true);
	}
}