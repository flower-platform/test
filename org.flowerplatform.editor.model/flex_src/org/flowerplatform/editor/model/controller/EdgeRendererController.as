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
package org.flowerplatform.editor.model.controller {
	
	import org.flowerplatform.editor.model.renderer.EdgeRenderer;
	import org.flowerplatform.emf_model.notation.Edge;
	import org.flowerplatform.flexdiagram.DiagramShell;
	import org.flowerplatform.flexdiagram.controller.renderer.ConnectionRendererController;
	import org.flowerplatform.flexdiagram.renderer.connection.ConnectionEnd;
	
	/**
	 * @author Mariana Gheorghe
	 * @author Cristina Constantinescu
	 */
	public class EdgeRendererController extends ConnectionRendererController {
		public function EdgeRendererController(diagramShell:DiagramShell) {
			super(diagramShell, EdgeRenderer);
		}
		
		/**
		 * @author Mariana Gheorghe
		 * @author Cristina Constantinescu
		 * @author Cristian Spiescu
		 */
		override public function getSourceModel(connectionModel:Object):Object {
			if (connectionModel == null || connectionModel.source_RH == null) {
				// this may happen if there is an edge which doesn't have a model associated. E.g. the source/target
				// view doesn't exist any more, but the edge still exists. Normally the server should make sure that
				// this doesn't happen (e.g. delete logic). But if for some reason this does happens, we return null
				// and the diagram continues to work. The edge knows how to handle null source/target.
				return null;
			}
			return Edge(connectionModel).source_RH.referencedObject;
		}
		
		/**
		 * @author Mariana Gheorghe
		 * @author Cristina Constantinescu
		 * @author Cristian Spiescu
		 */
		override public function getTargetModel(connectionModel:Object):Object {
			if (connectionModel == null || connectionModel.target_RH == null) {
				// this may happen if there is an edge which doesn't have a model associated. E.g. the source/target
				// view doesn't exist any more, but the edge still exists. Normally the server should make sure that
				// this doesn't happen (e.g. delete logic). But if for some reason this does happens, we return null
				// and the diagram continues to work. The edge knows how to handle null source/target.
				return null;
			}
			return Edge(connectionModel).target_RH.referencedObject;
		}
		
		override public function getSourceEndFigureType(connectionModel:Object):String {
			if (connectionModel.viewDetails.sourceEndFigureType) {
				return connectionModel.viewDetails.sourceEndFigureType;
			}
			return super.getSourceEndFigureType(connectionModel);
		}
		
		override public function getTargetEndFigureType(connectionModel:Object):String {
			if (connectionModel.viewDetails.targetEndFigureType) {
				return connectionModel.viewDetails.targetEndFigureType;
			}
			return super.getTargetEndFigureType(connectionModel);
		}
		
		override public function hasMiddleLabel(connectionModel:Object):Boolean {
			return true;
		}
		
	}
}