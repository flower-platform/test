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
package org.flowerplatform.codesync.processor;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.change.FeatureChange;
import org.flowerplatform.codesync.remote.RelationDescriptor;
import org.flowerplatform.editor.model.change_processor.AbstractDiagramProcessor;
import org.flowerplatform.emf_model.notation.View;

import com.crispico.flower.mp.codesync.base.CodeSyncPlugin;
import com.crispico.flower.mp.model.codesync.Relation;

/**
 * Sets the view details (e.g. label) for an edge.
 * 
 * @author Mariana Gheorghe
 * @author Cristina Constantinescu
 */
public class RelationDiagramProcessor extends AbstractDiagramProcessor {

	@Override
	protected void processFeatureChange(EObject object, 
			FeatureChange featureChange, View associatedViewOnOpenDiagram,
			Map<String, Object> viewDetails) {
		if (featureChange != null) {
			// the name is a constant; so don't react to change deltas
			return;
		}
		
		RelationDescriptor descriptor = CodeSyncPlugin.getInstance().getRelationDescriptor(((Relation) object).getType());
		viewDetails.put("label", descriptor.getLabel());
		viewDetails.put("sourceEndFigureType", descriptor.getSourceEndFigureType());
		viewDetails.put("targetEndFigureType", descriptor.getTargetEndFigureType());
	}

}
