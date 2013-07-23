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
package org.flowerplatform.common.regex;

import java.util.regex.Matcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Cristi
 * @flowerModelElementId _HxNM4DLuEeKrMsw303uxEA
 */
public class RegexProcessingSession {

	private static final Logger logger = LoggerFactory.getLogger(RegexProcessingSession.class); 
	
	protected Matcher matcher;
	
	protected RegexConfiguration configuration;

	// attributes holding results of the last match
	
	protected int currentMatchGroupIndex;
	
	protected RegexWithAction currentRegex;
	
	protected String[] currentSubMatchesForCurrentRegex;
	
	public boolean ignoreMatches;
	
	public int currentNestingLevel;
	
	protected String lastMatchCategory;
	
	public void reset(boolean resetMatcher) {
		currentMatchGroupIndex = -1;
		currentRegex = null;		
		currentSubMatchesForCurrentRegex = null;		
		ignoreMatches = !configuration.useUntilFoundThisIgnoreAll ? false : true;
		currentNestingLevel = 0;
		lastMatchCategory = null;
		
		if (resetMatcher) {
			matcher.reset();
		}
	}
	
	public Matcher getMatcher() {
		return matcher;
	}
	
	public RegexConfiguration getConfiguration() {
		return configuration;
	}

	public int getCurrentMatchGroupIndex() {
		return currentMatchGroupIndex;
	}

	public RegexWithAction getCurrentRegex() {
		return currentRegex;
	}

	public String[] getCurrentSubMatchesForCurrentRegex() {
		return currentSubMatchesForCurrentRegex;
	}

	public boolean find() {
		boolean result = matcher.find();
		
		if (logger.isTraceEnabled()) {
			if (!result) {
				logger.trace("Not found");
			} else {
				StringBuilder sb = new StringBuilder();
				sb.append(String.format("Found '%s' from %d to %d having %d groups: ", getMatcher().group(), getMatcher().start(), getMatcher().end(), getMatcher().groupCount()));
				for (int i = 1; i <= getMatcher().groupCount(); i++) {
					sb.append(String.format("Group %d = %s, ", i, getMatcher().group(i)));
				}
				logger.trace(sb.toString());
			}
		}
		
		if (!result) {
			return false;
		}
		
		// find the index for the regexp that matched
		for (currentMatchGroupIndex = 1; currentMatchGroupIndex <= matcher.groupCount(); currentMatchGroupIndex++) {
			if (matcher.group(currentMatchGroupIndex) != null) {
				// found the first not null group
				break;
			}
		}
		
		// find the current regex
		if (currentMatchGroupIndex > matcher.groupCount()) {
			// i.e. there was not at least one not null group;  
			logger.error("currentMatchGroupIndex > matcher.groupCount. This shouldn't happen. Please see the audit logs to try to reproduce the input, while enabling trace for this package.");
		}
		
		if (configuration.captureGroupToRegexMapping[currentMatchGroupIndex] == null) {
			// i.e. the captured group corresponds to a sub-match of a regex; not to the original match itself
			logger.error("Captured group corresponds to a sub-match, and not with the top-level group. This shouldn't happen. Please see the audit logs to try to reproduce the input, while enabling trace for this package.");
		} 

		currentRegex = configuration.captureGroupToRegexMapping[currentMatchGroupIndex];
		if (logger.isTraceEnabled()) {
			logger.trace("[{}:{}] corresponds to group #{}. Invoking Action...", new Object[] { currentRegex.humanReadableRegexMeaning, currentRegex.getClass().getSimpleName(), currentMatchGroupIndex});
		}
		
		// for the the current regex, populate the submatches
		if (currentRegex.numberOfCaptureGroups == 0) {
			currentSubMatchesForCurrentRegex = null;
		} else {
			currentSubMatchesForCurrentRegex = new String[currentRegex.numberOfCaptureGroups];
			for (int i = 0; i < currentRegex.numberOfCaptureGroups; i++) {
				if (currentMatchGroupIndex + i + 1 > matcher.groupCount()) {
					logger.error("Not enough match groups left, to fully populate the expected submatches. This shouldn't happen. Please see the audit logs to try to reproduce the input, while enabling trace for this package.");
				}
				
				currentSubMatchesForCurrentRegex[i] = matcher.group(currentMatchGroupIndex + i + 1); 
			}
		}
		
		currentRegex.executeAction(this);
		
		return true;
	}
	
	public void candidateAnnounced(String category) {
		if (logger.isTraceEnabled()) {
			StringBuilder subMatchesAsString = new StringBuilder();
			if (currentSubMatchesForCurrentRegex != null) {
				for (String subMatch : currentSubMatchesForCurrentRegex) {
					subMatchesAsString.append(subMatch);
					subMatchesAsString.append(',');
				}
			}
			
			// debug to show with other color
			logger.debug("Match candidate for category = {} and submatches = {}", category, subMatchesAsString);
		}
		lastMatchCategory = category;
	}
	
	/**
	 * @flowerModelElementId _HxQ3QTLuEeKrMsw303uxEA
	 */
	public int[] findRangeFor(String category, String searchString) {
		while (find()) {
			if (category.equals(lastMatchCategory)) {
				if (searchString.equals(currentSubMatchesForCurrentRegex[0])) {
					return new int[] {
						matcher.start(currentMatchGroupIndex + 1), // currentMatchGroupIndex is the "big group", i.e. in (big_group1|big_group2); so we add + 1 for the 1st submatch
						matcher.end(currentMatchGroupIndex + 1),
					};
				} else {
					// it's possible that next iteration of find doesn't go through candidateAnnounced,
					// so we cancel the result
					lastMatchCategory = null;
				}
			}
		}
		return null;
	}

	/**
	 * @flowerModelElementId _ODo3cDLyEeKrMsw303uxEA
	 */
	public String findLastMatchBeforeIndex(String category, int index) {
		// TODO implement
		return null;
	}

}


