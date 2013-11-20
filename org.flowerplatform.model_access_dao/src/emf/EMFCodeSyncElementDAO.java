package emf;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.flowerplatform.model_access_dao.CodeSyncElementDAO;
import org.flowerplatform.model_access_dao.DAOFactory;
import org.flowerplatform.model_access_dao.UUID;
import org.flowerplatform.model_access_dao.model.CodeSyncElement1;
import org.flowerplatform.model_access_dao.model.ModelFactory;
import org.flowerplatform.model_access_dao.model.Relation1;

public class EMFCodeSyncElementDAO implements CodeSyncElementDAO {

	@Override
	public String createCodeSyncElement(String repoId, String discussableDesignId, String resourceId, String id, String parentId) {
		CodeSyncElement1 element = ModelFactory.eINSTANCE.createCodeSyncElement1();
		if (id == null) {
			id = UUID.newUUID();
		}
		element.setId(id);
		
		System.out.println("> created CSE " + element.getId());
		
		if (parentId == null) {
			Resource resource = DAOFactory.registryDAO.loadResource(repoId, discussableDesignId, resourceId);
			resource.getContents().add(element);
		} else {
			CodeSyncElement1 parent = getCodeSyncElement(repoId, discussableDesignId, resourceId, parentId);
			setParent(parent, element);
		}
		
		return element.getId();
	}

	@Override
	public CodeSyncElement1 getCodeSyncElement(String repoId, String discussableDesignId, String resourceId, String uid) {
		Resource resource = DAOFactory.registryDAO.loadResource(repoId, discussableDesignId, resourceId);

		// search for the referenced object in this resource
		CodeSyncElement1 referencedObject = null;
		if (resource != null) {
			referencedObject = (CodeSyncElement1) resource.getEObject(uid);
			if (referencedObject != null) {
				return (CodeSyncElement1) referencedObject;
			}
		}
		
		if (discussableDesignId == null) {
			return null;
		}

		// no local resource, or the referenced object is not in this resource
		// => search in the global resource
		Resource globalResource = DAOFactory.registryDAO.loadResource(repoId, null, resourceId);
		
		if (globalResource == null) {
			throw new RuntimeException("No global resource for repo " + repoId);
		}
		
		referencedObject = (CodeSyncElement1) globalResource.getEObject(uid);

		// duplicate the object from the global resource
		if (resource != null) {
			referencedObject = duplicateCodeSyncElement(repoId, discussableDesignId, resourceId, referencedObject);
		}
		return (CodeSyncElement1) referencedObject;
	}

	private CodeSyncElement1 duplicateCodeSyncElement(String repoId, String discussableDesignId, String resourceId, CodeSyncElement1 referencedObject) {
		CodeSyncElement1 parent = getParent(referencedObject, repoId, discussableDesignId, resourceId);
		String parentId = parent == null ? null : parent.getId();
		CodeSyncElement1 duplicate = getCodeSyncElement(repoId, discussableDesignId, resourceId, 
				createCodeSyncElement(repoId, discussableDesignId, resourceId, referencedObject.getId(), parentId));
		copyProperties(referencedObject, duplicate);
		return duplicate;
	}
	
	protected void copyProperties(CodeSyncElement1 source, CodeSyncElement1 target) {
		target.setName(source.getName());
	}
	
	public List<CodeSyncElement1> getCodeSyncElements(String repoId, String discussableDesignId, String resourceId) {
		// use LinkedHashMap to preserve insertion-order
		Map<String, CodeSyncElement1> codeSyncElements = new LinkedHashMap<String, CodeSyncElement1>();
		
		// merge with global resource if we're in a discussable design
		if (discussableDesignId != null) {
			for (CodeSyncElement1 codeSyncElement : getCodeSyncElements(repoId, null, resourceId)) {
				codeSyncElements.put(codeSyncElement.getId(), codeSyncElement);
			}
		}
		
		Resource resource = DAOFactory.registryDAO.loadResource(repoId, discussableDesignId, resourceId);
		for (EObject eObject : resource.getContents()) {
			if (eObject instanceof CodeSyncElement1) {
				addToMap((CodeSyncElement1) eObject, codeSyncElements);
			}
		}
		
		return Arrays.asList(codeSyncElements.values().toArray(new CodeSyncElement1[0]));
	}
	
	private void addToMap(CodeSyncElement1 codeSyncElement, Map<String, CodeSyncElement1> codeSyncElements) {
		codeSyncElements.put(codeSyncElement.getId(), codeSyncElement);
		for (CodeSyncElement1 child : codeSyncElement.getChildren()) {
			addToMap(child, codeSyncElements);
		}
	}
	
	@Override
	public List<CodeSyncElement1> getChildren(CodeSyncElement1 element, String repoId, String discussableDesignId, String resourceId) {
		// use LinkedHashMap to preserve insertion-order
		Map<String , CodeSyncElement1> children = new LinkedHashMap<String, CodeSyncElement1>();
		
		// merge with global resource if we're in a discussable design
		if (discussableDesignId != null) {
			CodeSyncElement1 globalElement = getCodeSyncElement(repoId, null, resourceId, element.getId());
			if (globalElement != null) {
				for (CodeSyncElement1 child : getChildren(globalElement, repoId, null, resourceId)) {
					children.put(child.getId(), child);
				}
			}
		}
		
		for (CodeSyncElement1 child : element.getChildren()) {
			children.put(child.getId(), child);
		}
		
		return Arrays.asList(children.values().toArray(new CodeSyncElement1[0]));
	}

	@Override
	public CodeSyncElement1 getParent(CodeSyncElement1 element, String repoId, String discussableDesignId, String resourceId) {
		CodeSyncElement1 parent = (CodeSyncElement1) element.eContainer();
		return parent == null ? null : getCodeSyncElement(repoId, discussableDesignId, resourceId, parent.getId());
	}

	@Override
	public void setParent(CodeSyncElement1 parent, CodeSyncElement1 element) {
		if (parent != null) {
			parent.getChildren().add(element);
		} else {
			parent = (CodeSyncElement1) element.eContainer();
			if (parent == null) {
				// remove from resource TODO
			} else {
				parent.getChildren().remove(element);
			}
		}
	}
	
	@Override
	public void deleteCodeSyncElement(CodeSyncElement1 element) {
		ECrossReferenceAdapter adapter = ECrossReferenceAdapter.getCrossReferenceAdapter(element);
		if (adapter.getNonNavigableInverseReferences(element).size() == 0) {
			setParent(null, element);
		}
	}
	
	@Override
	public void addRelation(CodeSyncElement1 source, CodeSyncElement1 target, 
			String repoId, String discussableDesignId, String resourceId) {
		Relation1 relation = ModelFactory.eINSTANCE.createRelation1();
		relation.setSource(source);
		relation.setTarget(target);
		source.getRelations().add(relation);
	}

	@Override
	public List<CodeSyncElement1> getReferencedElements(CodeSyncElement1 source, String repoId, String discussableDesignId, String resourceId) {
		Map<String, CodeSyncElement1> referencedElements = new LinkedHashMap<String, CodeSyncElement1>();

		if (discussableDesignId != null) {
			CodeSyncElement1 globalSource = getCodeSyncElement(repoId, null, resourceId, source.getId());
			for (CodeSyncElement1 refElt : getReferencedElements(globalSource, repoId, null, resourceId)) {
				referencedElements.put(refElt.getId(), refElt);
			}
		}

		for (Relation1 relation : source.getRelations()) {
			referencedElements.put(relation.getTarget().getId(), relation.getTarget());
		}

		return Arrays.asList(referencedElements.values().toArray(new CodeSyncElement1[0]));
	}

	@Override
	public CodeSyncElement1 getSource(Relation1 relation, String repoId, String discussableDesignId) {
		return resolveProxy(relation.getSource(), repoId, discussableDesignId);
	}

	@Override
	public CodeSyncElement1 getTarget(Relation1 relation, String repoId, String discussableDesignId) {
		return resolveProxy(relation.getTarget(), repoId, discussableDesignId);
	}
	
	protected CodeSyncElement1 resolveProxy(CodeSyncElement1 element, String repoId, String discussableDesignId) {
		if (element.eIsProxy()) {
			URI uri = ((InternalEObject) element).eProxyURI();
			String uid = uri.fragment();
			String resourceId = uri.opaquePart();
			element = getCodeSyncElement(repoId, discussableDesignId, resourceId, uid);
		}
		return element;
	}
	
}
