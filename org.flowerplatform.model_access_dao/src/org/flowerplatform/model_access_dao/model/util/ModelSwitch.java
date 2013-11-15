/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.flowerplatform.model_access_dao.model.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.flowerplatform.model_access_dao.model.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.flowerplatform.model_access_dao.model.ModelPackage
 * @generated
 */
public class ModelSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ModelPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelSwitch() {
		if (modelPackage == null) {
			modelPackage = ModelPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case ModelPackage.CODE_SYNC_ELEMENT1: {
				CodeSyncElement1 codeSyncElement1 = (CodeSyncElement1)theEObject;
				T result = caseCodeSyncElement1(codeSyncElement1);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.CODE_SYNC_ELEMENT1_EMF: {
				CodeSyncElement1EMF codeSyncElement1EMF = (CodeSyncElement1EMF)theEObject;
				T result = caseCodeSyncElement1EMF(codeSyncElement1EMF);
				if (result == null) result = caseCodeSyncElement1(codeSyncElement1EMF);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.ENTITY_EMF: {
				EntityEMF entityEMF = (EntityEMF)theEObject;
				T result = caseEntityEMF(entityEMF);
				if (result == null) result = caseCodeSyncElement1EMF(entityEMF);
				if (result == null) result = caseCodeSyncElement1(entityEMF);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.NODE1: {
				Node1 node1 = (Node1)theEObject;
				T result = caseNode1(node1);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.NODE1_EMF: {
				Node1EMF node1EMF = (Node1EMF)theEObject;
				T result = caseNode1EMF(node1EMF);
				if (result == null) result = caseNode1(node1EMF);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.DIAGRAM1: {
				Diagram1 diagram1 = (Diagram1)theEObject;
				T result = caseDiagram1(diagram1);
				if (result == null) result = caseNode1EMF(diagram1);
				if (result == null) result = caseNode1(diagram1);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.RESOURCE_INFO: {
				ResourceInfo resourceInfo = (ResourceInfo)theEObject;
				T result = caseResourceInfo(resourceInfo);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Code Sync Element1</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Code Sync Element1</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCodeSyncElement1(CodeSyncElement1 object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Code Sync Element1 EMF</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Code Sync Element1 EMF</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCodeSyncElement1EMF(CodeSyncElement1EMF object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Entity EMF</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Entity EMF</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEntityEMF(EntityEMF object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Node1</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Node1</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNode1(Node1 object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Node1 EMF</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Node1 EMF</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNode1EMF(Node1EMF object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Diagram1</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Diagram1</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDiagram1(Diagram1 object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Resource Info</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Resource Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseResourceInfo(ResourceInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //ModelSwitch
