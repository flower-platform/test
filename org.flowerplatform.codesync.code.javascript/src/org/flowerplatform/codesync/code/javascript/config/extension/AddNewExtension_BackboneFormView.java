package org.flowerplatform.codesync.code.javascript.config.extension;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.flowerplatform.codesync.code.javascript.config.JavaScriptDescriptors;
import org.flowerplatform.codesync.remote.CodeSyncOperationsService;
import org.flowerplatform.emf_model.notation.View;

import com.crispico.flower.mp.model.codesync.CodeSyncElement;

/**
 * @author Cristian Spiescu
 */
public class AddNewExtension_BackboneFormView extends AddNewExtension_BackboneView {

	protected Collection<String> getInitializationTypes() {
		return Collections.singleton(JavaScriptDescriptors.INIT_TYPE_BACKBONE_FORM_VIEW);
	}
	
	@Override
	protected void doAddNew(CodeSyncElement codeSyncElement, Resource codeSyncMappingResource, Map<String, Object> parameters) {
		super.doAddNew(codeSyncElement, codeSyncMappingResource, parameters);
		
		CodeSyncOperationsService.getInstance().setFeatureValue(codeSyncElement, JavaScriptDescriptors.FEATURE_NAME, "FormView");	
		
		{
			CodeSyncElement child = CodeSyncOperationsService.getInstance().create(JavaScriptDescriptors.TYPE_JAVASCRIPT_ATTRIBUTE);
			CodeSyncOperationsService.getInstance().setFeatureValue(child, JavaScriptDescriptors.FEATURE_NAME, "htmlIdSuffix");
			CodeSyncOperationsService.getInstance().add(codeSyncElement, child);		
		}
	}

}
