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
package org.flowerplatform.editor.mindmap.remote;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.flowerplatform.editor.model.remote.NewDiagramAction;
import org.flowerplatform.emf_model.notation.Diagram;
import org.flowerplatform.emf_model.notation.MindMapNode;
import org.flowerplatform.emf_model.notation.NotationFactory;

import com.crispico.flower.mp.model.codesync.CodeSyncFactory;
import com.crispico.flower.mp.model.codesync.MindMapRoot;

/**
 * @author Mariana Gheorghe
 */
public class NewMindMapDiagramAction extends NewDiagramAction {

	@Override
	protected Diagram createDiagram(Object file, ResourceSet resourceSet) {
		Diagram diagram = NotationFactory.eINSTANCE.createDiagram();
		MindMapNode root = NotationFactory.eINSTANCE.createMindMapNode();
		root.setViewType("default");
				
		MindMapRoot mindmapCse = CodeSyncFactory.eINSTANCE.createMindMapRoot();				
		mindmapCse.setName("Root");
		mindmapCse.setType("default");
		
		root.setDiagrammableElement(mindmapCse);
		diagram.getPersistentChildren().add(root);
		return diagram;
	}

	@Override
	protected String getServiceId() {
		return "mindmapEditorStatefulService";
	}

}