/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.flowerplatform.codesync.code.javascript.regex_ast.util;

import com.crispico.flower.mp.model.codesync.AstCacheElement;
import com.crispico.flower.mp.model.codesync.CodeSyncElement;
import java.io.Serializable;
import java.util.Map;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.flowerplatform.codesync.code.javascript.regex_ast.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.flowerplatform.codesync.code.javascript.regex_ast.RegExAstPackage
 * @generated
 */
public class RegExAstAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static RegExAstPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RegExAstAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = RegExAstPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RegExAstSwitch<Adapter> modelSwitch =
		new RegExAstSwitch<Adapter>() {
			@Override
			public Adapter caseRegExAstNode(RegExAstNode object) {
				return createRegExAstNodeAdapter();
			}
			@Override
			public Adapter caseStringToIntegerEntry(Map.Entry<String, Integer> object) {
				return createStringToIntegerEntryAdapter();
			}
			@Override
			public Adapter caseRegExAstNodeParameter(RegExAstNodeParameter object) {
				return createRegExAstNodeParameterAdapter();
			}
			@Override
			public Adapter caseRegExAstCacheElement(RegExAstCacheElement object) {
				return createRegExAstCacheElementAdapter();
			}
			@Override
			public Adapter caseSerializable(Serializable object) {
				return createSerializableAdapter();
			}
			@Override
			public Adapter caseAstCacheElement(AstCacheElement object) {
				return createAstCacheElementAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link org.flowerplatform.codesync.code.javascript.regex_ast.RegExAstNode <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.flowerplatform.codesync.code.javascript.regex_ast.RegExAstNode
	 * @generated
	 */
	public Adapter createRegExAstNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link java.util.Map.Entry <em>String To Integer Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see java.util.Map.Entry
	 * @generated
	 */
	public Adapter createStringToIntegerEntryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.flowerplatform.codesync.code.javascript.regex_ast.RegExAstNodeParameter <em>Node Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.flowerplatform.codesync.code.javascript.regex_ast.RegExAstNodeParameter
	 * @generated
	 */
	public Adapter createRegExAstNodeParameterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.flowerplatform.codesync.code.javascript.regex_ast.RegExAstCacheElement <em>Cache Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.flowerplatform.codesync.code.javascript.regex_ast.RegExAstCacheElement
	 * @generated
	 */
	public Adapter createRegExAstCacheElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link java.io.Serializable <em>Serializable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see java.io.Serializable
	 * @generated
	 */
	public Adapter createSerializableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.crispico.flower.mp.model.codesync.AstCacheElement <em>Ast Cache Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.crispico.flower.mp.model.codesync.AstCacheElement
	 * @generated
	 */
	public Adapter createAstCacheElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //RegExAstAdapterFactory
