/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.flowerplatform.model.astcache.wiki.impl;

import com.crispico.flower.mp.model.codesync.impl.AstCacheElementImpl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.flowerplatform.model.astcache.wiki.AstCacheWikiPackage;
import org.flowerplatform.model.astcache.wiki.FlowerBlock;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Flower Block</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.flowerplatform.model.astcache.wiki.impl.FlowerBlockImpl#getContent <em>Content</em>}</li>
 *   <li>{@link org.flowerplatform.model.astcache.wiki.impl.FlowerBlockImpl#getLineStart <em>Line Start</em>}</li>
 *   <li>{@link org.flowerplatform.model.astcache.wiki.impl.FlowerBlockImpl#getLineEnd <em>Line End</em>}</li>
 *   <li>{@link org.flowerplatform.model.astcache.wiki.impl.FlowerBlockImpl#isConflict <em>Conflict</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FlowerBlockImpl extends AstCacheElementImpl implements FlowerBlock {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The default value of the '{@link #getContent() <em>Content</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContent()
	 * @generated
	 * @ordered
	 */
	protected static final String CONTENT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getContent() <em>Content</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContent()
	 * @generated
	 * @ordered
	 */
	protected String content = CONTENT_EDEFAULT;

	/**
	 * The default value of the '{@link #getLineStart() <em>Line Start</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLineStart()
	 * @generated
	 * @ordered
	 */
	protected static final int LINE_START_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getLineStart() <em>Line Start</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLineStart()
	 * @generated
	 * @ordered
	 */
	protected int lineStart = LINE_START_EDEFAULT;

	/**
	 * The default value of the '{@link #getLineEnd() <em>Line End</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLineEnd()
	 * @generated
	 * @ordered
	 */
	protected static final int LINE_END_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getLineEnd() <em>Line End</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLineEnd()
	 * @generated
	 * @ordered
	 */
	protected int lineEnd = LINE_END_EDEFAULT;

	/**
	 * The default value of the '{@link #isConflict() <em>Conflict</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isConflict()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CONFLICT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isConflict() <em>Conflict</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isConflict()
	 * @generated
	 * @ordered
	 */
	protected boolean conflict = CONFLICT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FlowerBlockImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AstCacheWikiPackage.Literals.FLOWER_BLOCK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getContent() {
		return content;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContent(String newContent) {
		String oldContent = content;
		content = newContent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AstCacheWikiPackage.FLOWER_BLOCK__CONTENT, oldContent, content));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getLineStart() {
		return lineStart;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLineStart(int newLineStart) {
		int oldLineStart = lineStart;
		lineStart = newLineStart;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AstCacheWikiPackage.FLOWER_BLOCK__LINE_START, oldLineStart, lineStart));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getLineEnd() {
		return lineEnd;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLineEnd(int newLineEnd) {
		int oldLineEnd = lineEnd;
		lineEnd = newLineEnd;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AstCacheWikiPackage.FLOWER_BLOCK__LINE_END, oldLineEnd, lineEnd));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isConflict() {
		return conflict;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConflict(boolean newConflict) {
		boolean oldConflict = conflict;
		conflict = newConflict;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AstCacheWikiPackage.FLOWER_BLOCK__CONFLICT, oldConflict, conflict));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AstCacheWikiPackage.FLOWER_BLOCK__CONTENT:
				return getContent();
			case AstCacheWikiPackage.FLOWER_BLOCK__LINE_START:
				return getLineStart();
			case AstCacheWikiPackage.FLOWER_BLOCK__LINE_END:
				return getLineEnd();
			case AstCacheWikiPackage.FLOWER_BLOCK__CONFLICT:
				return isConflict();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case AstCacheWikiPackage.FLOWER_BLOCK__CONTENT:
				setContent((String)newValue);
				return;
			case AstCacheWikiPackage.FLOWER_BLOCK__LINE_START:
				setLineStart((Integer)newValue);
				return;
			case AstCacheWikiPackage.FLOWER_BLOCK__LINE_END:
				setLineEnd((Integer)newValue);
				return;
			case AstCacheWikiPackage.FLOWER_BLOCK__CONFLICT:
				setConflict((Boolean)newValue);
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
			case AstCacheWikiPackage.FLOWER_BLOCK__CONTENT:
				setContent(CONTENT_EDEFAULT);
				return;
			case AstCacheWikiPackage.FLOWER_BLOCK__LINE_START:
				setLineStart(LINE_START_EDEFAULT);
				return;
			case AstCacheWikiPackage.FLOWER_BLOCK__LINE_END:
				setLineEnd(LINE_END_EDEFAULT);
				return;
			case AstCacheWikiPackage.FLOWER_BLOCK__CONFLICT:
				setConflict(CONFLICT_EDEFAULT);
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
			case AstCacheWikiPackage.FLOWER_BLOCK__CONTENT:
				return CONTENT_EDEFAULT == null ? content != null : !CONTENT_EDEFAULT.equals(content);
			case AstCacheWikiPackage.FLOWER_BLOCK__LINE_START:
				return lineStart != LINE_START_EDEFAULT;
			case AstCacheWikiPackage.FLOWER_BLOCK__LINE_END:
				return lineEnd != LINE_END_EDEFAULT;
			case AstCacheWikiPackage.FLOWER_BLOCK__CONFLICT:
				return conflict != CONFLICT_EDEFAULT;
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
		result.append(" (content: ");
		result.append(content);
		result.append(", lineStart: ");
		result.append(lineStart);
		result.append(", lineEnd: ");
		result.append(lineEnd);
		result.append(", conflict: ");
		result.append(conflict);
		result.append(')');
		return result.toString();
	}

} //FlowerBlockImpl
