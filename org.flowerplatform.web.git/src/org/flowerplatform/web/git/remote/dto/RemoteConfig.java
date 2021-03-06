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
package org.flowerplatform.web.git.remote.dto;

import java.util.List;

/**
 *	@author Cristina Constantinescu
 */
public class RemoteConfig {
	
	private String name;
	
	private String uri;
	
	private List<String> fetchMappings;
		
	private List<String> pushMappings;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getFetchMappings() {
		return fetchMappings;
	}

	public void setFetchMappings(List<String> fetchMappings) {
		this.fetchMappings = fetchMappings;
	}

	public List<String> getPushMappings() {
		return pushMappings;
	}

	public void setPushMappings(List<String> pushMappings) {
		this.pushMappings = pushMappings;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
}