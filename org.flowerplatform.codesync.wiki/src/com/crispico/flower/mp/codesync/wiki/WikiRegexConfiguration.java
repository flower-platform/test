package com.crispico.flower.mp.codesync.wiki;

import org.flowerplatform.common.regex.RegexConfiguration;
import org.flowerplatform.common.regex.RegexWithAction;

/**
 * @author Mariana
 */
public class WikiRegexConfiguration extends RegexConfiguration {
	
	public static final String LINE_TERMINATOR = "(?:\r\n|[\r\n]|\\z)";

	public static final String HEADLINE_LEVEL_1_CATEGORY = "Headline 1";
	public static final String HEADLINE_LEVEL_2_CATEGORY = "Headline 2";
	public static final String HEADLINE_LEVEL_3_CATEGORY = "Headline 3";
	public static final String HEADLINE_LEVEL_4_CATEGORY = "Headline 4";
	public static final String HEADLINE_LEVEL_5_CATEGORY = "Headline 5";
	public static final String HEADLINE_LEVEL_6_CATEGORY = "Headline 6";
	
	public static final String PARAGRAPH_CATEGORY = "Paragraph";
	
	public static final String PARAGRAPH_REGEX = "(.*?)" + LINE_TERMINATOR;
	
	public static final String FLOWER_BLOCK_CATEGORY = "Flower Block";
	
	public static final String FLOWER_BLOCK_START = "@flower-platform-type";
	
	public static final String FLOWER_BLOCK_END = "flower-platform-type-end";
	
	private final String FLOWER_BLOCK = FLOWER_BLOCK_START + "\\s+?(\\S*?)\\s+?(.*?)\\s+?" + FLOWER_BLOCK_END;
	
	private Class<? extends WikiTreeBuilder> sessionClass;
	
	public WikiRegexConfiguration() {
		add(new RegexWithAction.IfFindThisAnnounceMatchCandidate(FLOWER_BLOCK_CATEGORY, FLOWER_BLOCK, FLOWER_BLOCK_CATEGORY));
	}
	
	public WikiRegexConfiguration setSessionClass(Class<? extends WikiTreeBuilder> sessionClass) {
		this.sessionClass = sessionClass;
		return this;
	}

	@Override
	protected WikiTreeBuilder createSessionInstance() {
		try {
			return sessionClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("The session class cannot be instantiated!", e);
		} 
	}

	@Override
	public WikiTreeBuilder startSession(CharSequence input) {
		WikiTreeBuilder session = (WikiTreeBuilder) super.startSession(input);
		session.setInput(input.toString());
		return session;
	}
	
}