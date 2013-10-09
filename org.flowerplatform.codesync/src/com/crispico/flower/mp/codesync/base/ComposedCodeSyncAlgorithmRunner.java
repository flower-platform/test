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
package com.crispico.flower.mp.codesync.base;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.flowerplatform.communication.channel.CommunicationChannel;

/**
 * @author Mariana
 */
public class ComposedCodeSyncAlgorithmRunner implements ICodeSyncAlgorithmRunner {

	protected Map<String, ICodeSyncAlgorithmRunner> runners = new HashMap<String, ICodeSyncAlgorithmRunner>();
	
	public void addRunner(String technology, ICodeSyncAlgorithmRunner runner) {
		runners.put(technology, runner);
	}
	
	@Override
	public void runCodeSyncAlgorithm(File project, File resource, String technology, CommunicationChannel communicationChannel, boolean showDialog) {
		runners.get(technology).runCodeSyncAlgorithm(project, resource, technology, communicationChannel, showDialog);
	}

}