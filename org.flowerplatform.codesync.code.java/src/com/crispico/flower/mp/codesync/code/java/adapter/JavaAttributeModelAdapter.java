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
package com.crispico.flower.mp.codesync.code.java.adapter;

import java.util.Collections;
import java.util.List;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclaration;

import com.crispico.flower.mp.model.astcache.code.AstCacheCodePackage;
import com.crispico.flower.mp.model.codesync.CodeSyncPackage;

/**
 * Mapped to {@link FieldDeclaration}. Does not return any children.
 * 
 * @author Mariana
 */
public class JavaAttributeModelAdapter extends JavaAbstractAstNodeModelAdapter {

	public static final String ATTRIBUTE = "javaAttribute";
	
	@Override
	public List<?> getChildren(Object modelElement) {
		return Collections.emptyList();
	}

	@Override
	public Object getMatchKey(Object modelElement) {
		VariableDeclaration var = (VariableDeclaration) getFieldDeclaration(modelElement).fragments().get(0);
		return var.getName().getIdentifier();
	}

	@Override
	public Object getValueFeatureValue(Object element, Object feature, Object correspondingValue) {
		if (CodeSyncPackage.eINSTANCE.getCodeSyncElement_Name().equals(feature)) {
			return getLabel(element);
		}
		if (CodeSyncPackage.eINSTANCE.getCodeSyncElement_Type().equals(feature)) {
			return ATTRIBUTE;
		}
		if (AstCacheCodePackage.eINSTANCE.getTypedElement_Type().equals(feature)) {
			return getStringFromType(getFieldDeclaration(element).getType());
		}
		if (AstCacheCodePackage.eINSTANCE.getAttribute_Initializer().equals(feature)) {
			VariableDeclaration var = (VariableDeclaration) getFieldDeclaration(element).fragments().get(0);
			return getStringFromExpression(var.getInitializer());
		}
		return super.getValueFeatureValue(element, feature, correspondingValue);
	}
	
	@Override
	public void setValueFeatureValue(Object element, Object feature, Object value) {
		if (CodeSyncPackage.eINSTANCE.getCodeSyncElement_Name().equals(feature)) {
			FieldDeclaration field = getFieldDeclaration(element);
			String name = (String) value;
			VariableDeclaration var = (VariableDeclaration) field.fragments().get(0);
			var.setName(field.getAST().newSimpleName(name));
		}
		if (AstCacheCodePackage.eINSTANCE.getTypedElement_Type().equals(feature)) {
			FieldDeclaration field = getFieldDeclaration(element);
			Type type = getTypeFromString(field.getAST(), (String) value);
			field.setType(type);
		}
		if (AstCacheCodePackage.eINSTANCE.getAttribute_Initializer().equals(feature)) {
			VariableDeclaration var = (VariableDeclaration) getFieldDeclaration(element).fragments().get(0);
			var.setInitializer(getExpressionFromString(var.getAST(), (String) value));
		}
		super.setValueFeatureValue(element, feature, value);
	}

	private FieldDeclaration getFieldDeclaration(Object element) {
		return (FieldDeclaration) element;
	}

	@Override
	public Object createCorrespondingModelElement(Object element) {
		return AstCacheCodePackage.eINSTANCE.getAstCacheCodeFactory().createAttribute();
	}
	
}