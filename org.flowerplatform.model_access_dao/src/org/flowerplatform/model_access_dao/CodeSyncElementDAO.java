package org.flowerplatform.model_access_dao;

import java.util.List;

import org.flowerplatform.model_access_dao.model.CodeSyncElement1;
import org.flowerplatform.model_access_dao.model.Relation1;

public interface CodeSyncElementDAO {
	
	String createCodeSyncElement(String repoId, String discussableDesignId, String resourceId, String id, String parentId);
	CodeSyncElement1 getCodeSyncElement(String repoId, String discussableDesignId, String resourceId, String id);
	
	/**
	 * Merge with global resource, if this is a local resource.
	 */
	List<CodeSyncElement1> getCodeSyncElements(String repoId, String discussableDesignId, String resourceId);
	
	/**
	 * Merge with global resource, if this is a local resource.
	 */
	List<CodeSyncElement1> getChildren(CodeSyncElement1 element, String repoId, String discussableDesignId, String resourceId);
	
	CodeSyncElement1 getParent(CodeSyncElement1 element, String repoId, String discussableDesignId, String resourceId);
	void setParent(CodeSyncElement1 parent, CodeSyncElement1 element);
	
	/**
	 * Delete element only if there are no references towards it.
	 */
	void deleteCodeSyncElement(CodeSyncElement1 element);
	
	void addRelation(CodeSyncElement1 source, CodeSyncElement1 target, String repoId, String discussableDesignId, String resourceId);

	List<CodeSyncElement1> getReferencedElements(CodeSyncElement1 source, String repoId, String discussableDesignId, String resourceId);
	
	CodeSyncElement1 getSource(Relation1 relation, String repoId, String discussableDesignId);
	
	CodeSyncElement1 getTarget(Relation1 relation, String repoId, String discussableDesignId);
	
}
