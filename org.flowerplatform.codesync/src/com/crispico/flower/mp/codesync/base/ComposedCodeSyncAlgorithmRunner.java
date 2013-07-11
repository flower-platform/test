package com.crispico.flower.mp.codesync.base;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.flowerplatform.communication.channel.CommunicationChannel;


/**
 * @author Mariana
 */
public class ComposedCodeSyncAlgorithmRunner implements ICodeSyncAlgorithmRunner {

	protected List<ICodeSyncAlgorithmRunner> runners = new ArrayList<ICodeSyncAlgorithmRunner>();
	
	public void addRunner(ICodeSyncAlgorithmRunner runner) {
		runners.add(runner);
	}
	
	@Override
	public void runCodeSyncAlgorithm(IProject project,
			IFile file, String technology, CommunicationChannel communicationChannel) {
		for (ICodeSyncAlgorithmRunner runner : runners) {
			runner.runCodeSyncAlgorithm(project, file, technology, communicationChannel);
		}
	}

}