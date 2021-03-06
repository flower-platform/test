package org.flowerplatform.editor.model.changes_processor;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;

/**
 * @author Cristi
 */
public interface IChangesProcessor {

	public void processChanges(Map<String, Object> context, EObject object, Changes changes);
	
}
