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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.flowerplatform.codesync.code.javascript.processor.JavascriptElementProcessor;
import org.flowerplatform.codesync.code.javascript.processor.JavascriptFileElementProcessor;
import org.flowerplatform.codesync.code.javascript.remote.JavascriptClassDiagramOperationsService;
import org.flowerplatform.common.plugin.AbstractFlowerJavaPlugin;
import org.flowerplatform.communication.CommunicationPlugin;
import org.flowerplatform.editor.model.EditorModelPlugin;
import org.osgi.framework.BundleContext;

import com.crispico.flower.mp.codesync.code.CodeSyncCodePlugin;

/**
 * @author Mariana Gheorghe
 */
public class CodeSyncCodeJavascriptPlugin extends AbstractFlowerJavaPlugin {

	protected static CodeSyncCodeJavascriptPlugin INSTANCE;
	
	public static String TECHNOLOGY = "js";
	
	public static CodeSyncCodeJavascriptPlugin getInstance() {
		return INSTANCE;
	}

	private Map<String, List<String>> availableTemplates = new HashMap<String, List<String>>();

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		INSTANCE = this;
		
		CodeSyncCodePlugin.getInstance().addSrcDir("js");
		
		CommunicationPlugin.getInstance().getServiceRegistry().registerService(JavascriptClassDiagramOperationsService.SERVICE_ID, new JavascriptClassDiagramOperationsService());
		
		EditorModelPlugin.getInstance().getDiagramUpdaterChangeProcessor().addDiagrammableElementFeatureChangeProcessor("file", new JavascriptElementProcessor());
		EditorModelPlugin.getInstance().getDiagramUpdaterChangeProcessor().addDiagrammableElementFeatureChangeProcessor("fileElementContainer", new JavascriptFileElementProcessor());
		EditorModelPlugin.getInstance().getDiagramUpdaterChangeProcessor().addDiagrammableElementFeatureChangeProcessor("fileElementContainer", new JavascriptElementProcessor());
	
		// TODO this should be initialized from rules
		availableTemplates.put("", Arrays.asList("BackboneClass", "Table", "TableItem", "Form"));
		availableTemplates.put("BackboneClass", Arrays.asList("RequireEntry", "Attribute", "Operation", "EventsAttribute", "RoutesAttribute"));
		availableTemplates.put("Table", Arrays.asList("TableHeaderEntry"));
		availableTemplates.put("TableItem", Arrays.asList("TableItemEntry"));
		availableTemplates.put("Form", Arrays.asList("FormItem"));
		availableTemplates.put("EventsAttribute", Arrays.asList("EventsAttributeEntry"));
		availableTemplates.put("RoutesAttribute", Arrays.asList("RoutesAttributeEntry"));
	}
	
	public Map<String, List<String>> getAvailableTemplates() {
		return availableTemplates;
	}
	
}
