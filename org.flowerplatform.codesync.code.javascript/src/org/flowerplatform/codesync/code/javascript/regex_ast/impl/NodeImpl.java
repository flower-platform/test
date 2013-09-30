/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.flowerplatform.codesync.code.javascript.regex_ast.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.flowerplatform.codesync.code.javascript.regex_ast.Node;
import org.flowerplatform.codesync.code.javascript.regex_ast.Parameter;
import org.flowerplatform.codesync.code.javascript.regex_ast.RegExAstPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Node</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.flowerplatform.codesync.code.javascript.regex_ast.impl.NodeImpl#getChildren <em>Children</em>}</li>
 *   <li>{@link org.flowerplatform.codesync.code.javascript.regex_ast.impl.NodeImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link org.flowerplatform.codesync.code.javascript.regex_ast.impl.NodeImpl#getKeyParameter <em>Key Parameter</em>}</li>
 *   <li>{@link org.flowerplatform.codesync.code.javascript.regex_ast.impl.NodeImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.flowerplatform.codesync.code.javascript.regex_ast.impl.NodeImpl#isCategoryNode <em>Category Node</em>}</li>
 *   <li>{@link org.flowerplatform.codesync.code.javascript.regex_ast.impl.NodeImpl#getOffset <em>Offset</em>}</li>
 *   <li>{@link org.flowerplatform.codesync.code.javascript.regex_ast.impl.NodeImpl#getLength <em>Length</em>}</li>
 *   <li>{@link org.flowerplatform.codesync.code.javascript.regex_ast.impl.NodeImpl#isAdded <em>Added</em>}</li>
 *   <li>{@link org.flowerplatform.codesync.code.javascript.regex_ast.impl.NodeImpl#isDeleted <em>Deleted</em>}</li>
 *   <li>{@link org.flowerplatform.codesync.code.javascript.regex_ast.impl.NodeImpl#getTemplate <em>Template</em>}</li>
 *   <li>{@link org.flowerplatform.codesync.code.javascript.regex_ast.impl.NodeImpl#getChildrenInsertPoint <em>Children Insert Point</em>}</li>
 *   <li>{@link org.flowerplatform.codesync.code.javascript.regex_ast.impl.NodeImpl#getNextSiblingInsertPoint <em>Next Sibling Insert Point</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NodeImpl extends EObjectImpl implements Node {
	/**
	 * The cached value of the '{@link #getChildren() <em>Children</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildren()
	 * @generated
	 * @ordered
	 */
	protected EList<Node> children;

	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<Parameter> parameters;

	/**
	 * The default value of the '{@link #getKeyParameter() <em>Key Parameter</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKeyParameter()
	 * @generated
	 * @ordered
	 */
	protected static final String KEY_PARAMETER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getKeyParameter() <em>Key Parameter</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKeyParameter()
	 * @generated
	 * @ordered
	 */
	protected String keyParameter = KEY_PARAMETER_EDEFAULT;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected String type = TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #isCategoryNode() <em>Category Node</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCategoryNode()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CATEGORY_NODE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isCategoryNode() <em>Category Node</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCategoryNode()
	 * @generated
	 * @ordered
	 */
	protected boolean categoryNode = CATEGORY_NODE_EDEFAULT;

	/**
	 * The default value of the '{@link #getOffset() <em>Offset</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOffset()
	 * @generated
	 * @ordered
	 */
	protected static final int OFFSET_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getOffset() <em>Offset</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOffset()
	 * @generated
	 * @ordered
	 */
	protected int offset = OFFSET_EDEFAULT;

	/**
	 * The default value of the '{@link #getLength() <em>Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLength()
	 * @generated
	 * @ordered
	 */
	protected static final int LENGTH_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getLength() <em>Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLength()
	 * @generated
	 * @ordered
	 */
	protected int length = LENGTH_EDEFAULT;

	/**
	 * The default value of the '{@link #isAdded() <em>Added</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAdded()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ADDED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAdded() <em>Added</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAdded()
	 * @generated
	 * @ordered
	 */
	protected boolean added = ADDED_EDEFAULT;

	/**
	 * The default value of the '{@link #isDeleted() <em>Deleted</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeleted()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DELETED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDeleted() <em>Deleted</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeleted()
	 * @generated
	 * @ordered
	 */
	protected boolean deleted = DELETED_EDEFAULT;

	/**
	 * The default value of the '{@link #getTemplate() <em>Template</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTemplate()
	 * @generated
	 * @ordered
	 */
	protected static final String TEMPLATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTemplate() <em>Template</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTemplate()
	 * @generated
	 * @ordered
	 */
	protected String template = TEMPLATE_EDEFAULT;

	/**
	 * The default value of the '{@link #getChildrenInsertPoint() <em>Children Insert Point</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildrenInsertPoint()
	 * @generated
	 * @ordered
	 */
	protected static final int CHILDREN_INSERT_POINT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getChildrenInsertPoint() <em>Children Insert Point</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildrenInsertPoint()
	 * @generated
	 * @ordered
	 */
	protected int childrenInsertPoint = CHILDREN_INSERT_POINT_EDEFAULT;

	/**
	 * The default value of the '{@link #getNextSiblingInsertPoint() <em>Next Sibling Insert Point</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNextSiblingInsertPoint()
	 * @generated
	 * @ordered
	 */
	protected static final int NEXT_SIBLING_INSERT_POINT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getNextSiblingInsertPoint() <em>Next Sibling Insert Point</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNextSiblingInsertPoint()
	 * @generated
	 * @ordered
	 */
	protected int nextSiblingInsertPoint = NEXT_SIBLING_INSERT_POINT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RegExAstPackage.Literals.NODE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Node> getChildren() {
		if (children == null) {
			children = new EObjectContainmentEList<Node>(Node.class, this, RegExAstPackage.NODE__CHILDREN);
		}
		return children;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Parameter> getParameters() {
		if (parameters == null) {
			parameters = new EObjectContainmentEList<Parameter>(Parameter.class, this, RegExAstPackage.NODE__PARAMETERS);
		}
		return parameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getKeyParameter() {
		return keyParameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKeyParameter(String newKeyParameter) {
		String oldKeyParameter = keyParameter;
		keyParameter = newKeyParameter;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RegExAstPackage.NODE__KEY_PARAMETER, oldKeyParameter, keyParameter));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(String newType) {
		String oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RegExAstPackage.NODE__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isCategoryNode() {
		return categoryNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCategoryNode(boolean newCategoryNode) {
		boolean oldCategoryNode = categoryNode;
		categoryNode = newCategoryNode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RegExAstPackage.NODE__CATEGORY_NODE, oldCategoryNode, categoryNode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOffset(int newOffset) {
		int oldOffset = offset;
		offset = newOffset;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RegExAstPackage.NODE__OFFSET, oldOffset, offset));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getLength() {
		return length;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLength(int newLength) {
		int oldLength = length;
		length = newLength;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RegExAstPackage.NODE__LENGTH, oldLength, length));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAdded() {
		return added;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAdded(boolean newAdded) {
		boolean oldAdded = added;
		added = newAdded;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RegExAstPackage.NODE__ADDED, oldAdded, added));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isDeleted() {
		return deleted;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeleted(boolean newDeleted) {
		boolean oldDeleted = deleted;
		deleted = newDeleted;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RegExAstPackage.NODE__DELETED, oldDeleted, deleted));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTemplate() {
		return template;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTemplate(String newTemplate) {
		String oldTemplate = template;
		template = newTemplate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RegExAstPackage.NODE__TEMPLATE, oldTemplate, template));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getChildrenInsertPoint() {
		return childrenInsertPoint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setChildrenInsertPoint(int newChildrenInsertPoint) {
		int oldChildrenInsertPoint = childrenInsertPoint;
		childrenInsertPoint = newChildrenInsertPoint;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RegExAstPackage.NODE__CHILDREN_INSERT_POINT, oldChildrenInsertPoint, childrenInsertPoint));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getNextSiblingInsertPoint() {
		return nextSiblingInsertPoint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNextSiblingInsertPoint(int newNextSiblingInsertPoint) {
		int oldNextSiblingInsertPoint = nextSiblingInsertPoint;
		nextSiblingInsertPoint = newNextSiblingInsertPoint;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RegExAstPackage.NODE__NEXT_SIBLING_INSERT_POINT, oldNextSiblingInsertPoint, nextSiblingInsertPoint));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RegExAstPackage.NODE__CHILDREN:
				return ((InternalEList<?>)getChildren()).basicRemove(otherEnd, msgs);
			case RegExAstPackage.NODE__PARAMETERS:
				return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RegExAstPackage.NODE__CHILDREN:
				return getChildren();
			case RegExAstPackage.NODE__PARAMETERS:
				return getParameters();
			case RegExAstPackage.NODE__KEY_PARAMETER:
				return getKeyParameter();
			case RegExAstPackage.NODE__TYPE:
				return getType();
			case RegExAstPackage.NODE__CATEGORY_NODE:
				return isCategoryNode();
			case RegExAstPackage.NODE__OFFSET:
				return getOffset();
			case RegExAstPackage.NODE__LENGTH:
				return getLength();
			case RegExAstPackage.NODE__ADDED:
				return isAdded();
			case RegExAstPackage.NODE__DELETED:
				return isDeleted();
			case RegExAstPackage.NODE__TEMPLATE:
				return getTemplate();
			case RegExAstPackage.NODE__CHILDREN_INSERT_POINT:
				return getChildrenInsertPoint();
			case RegExAstPackage.NODE__NEXT_SIBLING_INSERT_POINT:
				return getNextSiblingInsertPoint();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case RegExAstPackage.NODE__CHILDREN:
				getChildren().clear();
				getChildren().addAll((Collection<? extends Node>)newValue);
				return;
			case RegExAstPackage.NODE__PARAMETERS:
				getParameters().clear();
				getParameters().addAll((Collection<? extends Parameter>)newValue);
				return;
			case RegExAstPackage.NODE__KEY_PARAMETER:
				setKeyParameter((String)newValue);
				return;
			case RegExAstPackage.NODE__TYPE:
				setType((String)newValue);
				return;
			case RegExAstPackage.NODE__CATEGORY_NODE:
				setCategoryNode((Boolean)newValue);
				return;
			case RegExAstPackage.NODE__OFFSET:
				setOffset((Integer)newValue);
				return;
			case RegExAstPackage.NODE__LENGTH:
				setLength((Integer)newValue);
				return;
			case RegExAstPackage.NODE__ADDED:
				setAdded((Boolean)newValue);
				return;
			case RegExAstPackage.NODE__DELETED:
				setDeleted((Boolean)newValue);
				return;
			case RegExAstPackage.NODE__TEMPLATE:
				setTemplate((String)newValue);
				return;
			case RegExAstPackage.NODE__CHILDREN_INSERT_POINT:
				setChildrenInsertPoint((Integer)newValue);
				return;
			case RegExAstPackage.NODE__NEXT_SIBLING_INSERT_POINT:
				setNextSiblingInsertPoint((Integer)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case RegExAstPackage.NODE__CHILDREN:
				getChildren().clear();
				return;
			case RegExAstPackage.NODE__PARAMETERS:
				getParameters().clear();
				return;
			case RegExAstPackage.NODE__KEY_PARAMETER:
				setKeyParameter(KEY_PARAMETER_EDEFAULT);
				return;
			case RegExAstPackage.NODE__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case RegExAstPackage.NODE__CATEGORY_NODE:
				setCategoryNode(CATEGORY_NODE_EDEFAULT);
				return;
			case RegExAstPackage.NODE__OFFSET:
				setOffset(OFFSET_EDEFAULT);
				return;
			case RegExAstPackage.NODE__LENGTH:
				setLength(LENGTH_EDEFAULT);
				return;
			case RegExAstPackage.NODE__ADDED:
				setAdded(ADDED_EDEFAULT);
				return;
			case RegExAstPackage.NODE__DELETED:
				setDeleted(DELETED_EDEFAULT);
				return;
			case RegExAstPackage.NODE__TEMPLATE:
				setTemplate(TEMPLATE_EDEFAULT);
				return;
			case RegExAstPackage.NODE__CHILDREN_INSERT_POINT:
				setChildrenInsertPoint(CHILDREN_INSERT_POINT_EDEFAULT);
				return;
			case RegExAstPackage.NODE__NEXT_SIBLING_INSERT_POINT:
				setNextSiblingInsertPoint(NEXT_SIBLING_INSERT_POINT_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case RegExAstPackage.NODE__CHILDREN:
				return children != null && !children.isEmpty();
			case RegExAstPackage.NODE__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
			case RegExAstPackage.NODE__KEY_PARAMETER:
				return KEY_PARAMETER_EDEFAULT == null ? keyParameter != null : !KEY_PARAMETER_EDEFAULT.equals(keyParameter);
			case RegExAstPackage.NODE__TYPE:
				return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
			case RegExAstPackage.NODE__CATEGORY_NODE:
				return categoryNode != CATEGORY_NODE_EDEFAULT;
			case RegExAstPackage.NODE__OFFSET:
				return offset != OFFSET_EDEFAULT;
			case RegExAstPackage.NODE__LENGTH:
				return length != LENGTH_EDEFAULT;
			case RegExAstPackage.NODE__ADDED:
				return added != ADDED_EDEFAULT;
			case RegExAstPackage.NODE__DELETED:
				return deleted != DELETED_EDEFAULT;
			case RegExAstPackage.NODE__TEMPLATE:
				return TEMPLATE_EDEFAULT == null ? template != null : !TEMPLATE_EDEFAULT.equals(template);
			case RegExAstPackage.NODE__CHILDREN_INSERT_POINT:
				return childrenInsertPoint != CHILDREN_INSERT_POINT_EDEFAULT;
			case RegExAstPackage.NODE__NEXT_SIBLING_INSERT_POINT:
				return nextSiblingInsertPoint != NEXT_SIBLING_INSERT_POINT_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (keyParameter: ");
		result.append(keyParameter);
		result.append(", type: ");
		result.append(type);
		result.append(", categoryNode: ");
		result.append(categoryNode);
		result.append(", offset: ");
		result.append(offset);
		result.append(", length: ");
		result.append(length);
		result.append(", added: ");
		result.append(added);
		result.append(", deleted: ");
		result.append(deleted);
		result.append(", template: ");
		result.append(template);
		result.append(", childrenInsertPoint: ");
		result.append(childrenInsertPoint);
		result.append(", nextSiblingInsertPoint: ");
		result.append(nextSiblingInsertPoint);
		result.append(')');
		return result.toString();
	}

} //NodeImpl
