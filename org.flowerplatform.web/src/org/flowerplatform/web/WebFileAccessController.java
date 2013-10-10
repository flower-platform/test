package org.flowerplatform.web;

import java.io.File;

import org.flowerplatform.common.CommonPlugin;
import org.flowerplatform.editor.file.PlainFileAccessController;

/**
 * @author Cristina Constantinescu
 */
public class WebFileAccessController extends PlainFileAccessController {

	@Override
	public String getPath(Object file) {		
		return CommonPlugin.getInstance().getPathRelativeToWorkspaceRoot((File) file);
	}

	@Override
	public Object getFile(String path) {
		// TODO: implement
		return null;
	}

}
