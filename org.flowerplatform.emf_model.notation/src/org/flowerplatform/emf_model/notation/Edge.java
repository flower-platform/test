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
/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.flowerplatform.emf_model.notation;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Edge</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.flowerplatform.emf_model.notation.Edge#getSource <em>Source</em>}</li>
 *   <li>{@link org.flowerplatform.emf_model.notation.Edge#getTarget <em>Target</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.flowerplatform.emf_model.notation.NotationPackage#getEdge()
 * @model
 * @generated
 */
public interface Edge extends View {
	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.flowerplatform.emf_model.notation.View#getSourceEdges <em>Source Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(View)
	 * @see org.flowerplatform.emf_model.notation.NotationPackage#getEdge_Source()
	 * @see org.flowerplatform.emf_model.notation.View#getSourceEdges
	 * @model opposite="sourceEdges"
	 * @generated
	 */
	View getSource();

	/**
	 * Sets the value of the '{@link org.flowerplatform.emf_model.notation.Edge#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(View value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.flowerplatform.emf_model.notation.View#getTargetEdges <em>Target Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(View)
	 * @see org.flowerplatform.emf_model.notation.NotationPackage#getEdge_Target()
	 * @see org.flowerplatform.emf_model.notation.View#getTargetEdges
	 * @model opposite="targetEdges"
	 * @generated
	 */
	View getTarget();

	/**
	 * Sets the value of the '{@link org.flowerplatform.emf_model.notation.Edge#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(View value);

} // Edge