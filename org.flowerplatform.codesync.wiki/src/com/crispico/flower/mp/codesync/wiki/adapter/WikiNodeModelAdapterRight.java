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
package com.crispico.flower.mp.codesync.wiki.adapter;

import org.flowerplatform.model.astcache.wiki.AstCacheWikiPackage;
import org.flowerplatform.model.astcache.wiki.Page;

import com.crispico.flower.mp.codesync.base.CodeSyncPlugin;
import com.crispico.flower.mp.codesync.wiki.WikiDiff;
import com.crispico.flower.mp.codesync.wiki.WikiPlugin;
import com.crispico.flower.mp.model.codesync.CodeSyncElement;

/**
 * @author Mariana
 */
public class WikiNodeModelAdapterRight extends WikiNodeModelAdapter {

	@Override
	public Object getValueFeatureValue(Object element, Object feature, Object correspondingValue) {
		if (AstCacheWikiPackage.eINSTANCE.getPage_InitialContent().equals(feature)) {
			return WikiPlugin.getInstance().getWikiText(getWikiNode(element), technology);
		}
		return super.getValueFeatureValue(element, feature, correspondingValue);
	}

	/**
	 * Creates the page tree from the {@link WikiDiff} and resets
	 * the initial content of the page.
	 */
	@Override
	protected void doSave(CodeSyncElement cse, WikiDiff diff) {
		diff.getRight(cse);
		Page page = (Page) cse.getAstCacheElement();
		page.setInitialContent(diff.getLeft());
		CodeSyncPlugin.getInstance().saveResource(cse.eResource());
	}
	
}