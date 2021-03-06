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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.Expression;

import com.crispico.flower.mp.model.astcache.code.AstCacheCodePackage;
import com.crispico.flower.mp.model.codesync.CodeSyncPackage;

/**
 * Mapped to {@link EnumConstantDeclaration}.
 * 
 * @author Mariana
 */
public class JavaEnumConstantDeclarationModelAdapter extends JavaAbstractAstNodeModelAdapter {

	public static final String ENUM_CONSTANT = "javaEnumConstant";
	
	@Override
	public Object getMatchKey(Object element) {
		return getEnumConstant(element).getName().getIdentifier();
	}

	@Override
	public Object getValueFeatureValue(Object element, Object feature, Object correspondingValue) {
		if (CodeSyncPackage.eINSTANCE.getCodeSyncElement_Name().equals(feature)) {
			return getMatchKey(element);
		}
		if (CodeSyncPackage.eINSTANCE.getCodeSyncElement_Type().equals(feature)) {
			return ENUM_CONSTANT;
		}
		if (AstCacheCodePackage.eINSTANCE.getEnumConstant_Arguments().equals(feature)) {
			return Collections.emptyList();
		}
		return super.getValueFeatureValue(element, feature, correspondingValue);
	}



	@Override
	public void setValueFeatureValue(Object element, Object feature, Object value) {
		if (CodeSyncPackage.eINSTANCE.getCodeSyncElement_Name().equals(feature)) {
			EnumConstantDeclaration enumConstant = getEnumConstant(element);
			enumConstant.setName(enumConstant.getAST().newSimpleName((String) value));
		}
		super.setValueFeatureValue(element, feature, value);
	}



	@Override
	public Iterable<?> getContainmentFeatureIterable(Object element, Object feature, Iterable<?> correspondingIterable) {
		if (AstCacheCodePackage.eINSTANCE.getEnumConstant_Arguments().equals(feature)) {
			return getExpressionStrings(getEnumConstant(element).arguments());
		}
		return super.getContainmentFeatureIterable(element, feature, correspondingIterable);
	}

	@Override
	public Object createChildOnContainmentFeature(Object element, Object feature, Object correspondingChild) {
		if (AstCacheCodePackage.eINSTANCE.getEnumConstant_Arguments().equals(feature)) {
			EnumConstantDeclaration parent = (EnumConstantDeclaration) element;
			AST ast = parent.getAST();
			Expression arg = getExpressionFromString(ast, (String) correspondingChild);
			parent.arguments().add(arg);
			return arg;
		}
		return super.createChildOnContainmentFeature(element, feature, correspondingChild);
	}

	@Override
	public Object createCorrespondingModelElement(Object element) {
		return AstCacheCodePackage.eINSTANCE.getAstCacheCodeFactory().createEnumConstant();
	}

	@Override
	public List<?> getChildren(Object modelElement) {
		return Collections.emptyList();
	}
	
	private EnumConstantDeclaration getEnumConstant(Object element) {
		return (EnumConstantDeclaration) element;
	}
	
	private List<String> getExpressionStrings(List expressions) {
		List<String> rslt = new ArrayList<String>();
		for (Object expression : expressions) {
			rslt.add(getStringFromExpression((Expression) expression));
		}
		return rslt;
	}

}