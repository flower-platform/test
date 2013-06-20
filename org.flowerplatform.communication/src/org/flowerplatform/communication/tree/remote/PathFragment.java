package org.flowerplatform.communication.tree.remote;

import org.flowerplatform.communication.tree.NodeInfo;


/**
 * Holds information for a tree node like its name and its type.
 * 
 * <p>
 * Used to communicate with the client side.
 * 
 * @see NodeInfo
 * 
 * @author Cristi
 * @author Cristina
 * 
 * @flowerModelElementId _zqqNoKKbEeGYz6sIcvSzpg
 */
public class PathFragment {
	
	/**
	 * @flowerModelElementId _jcBw8KKdEeGYz6sIcvSzpg
	 */
	private String name;
	
	/**
	 * @flowerModelElementId _m1DiQKKdEeGYz6sIcvSzpg
	 */
	private String type;

	/**
	 * @flowerModelElementId _vFGCUKP8EeGeHqktJlHXmA
	 */
	public String getName() {
		return name;
	}

	/**
	 * @flowerModelElementId _vFJssKP8EeGeHqktJlHXmA
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @flowerModelElementId _vFTdsqP8EeGeHqktJlHXmA
	 */
	public String getType() {
		return type;
	}

	/**
	 * @flowerModelElementId _vFec0aP8EeGeHqktJlHXmA
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @flowerModelElementId _vFr4MaP8EeGeHqktJlHXmA
	 */
	public PathFragment() {		
	}
	
	/**
	 * @flowerModelElementId _vF4FcKP8EeGeHqktJlHXmA
	 */
	public PathFragment(String name, String type) {	
		this.name = name;
		this.type = type;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof PathFragment))
			return false;
		PathFragment other = (PathFragment) obj;
		
		if (this.name == null && other.name != null)
			return false;
		if (!this.name.equals(other.name))
			return false;
		
		if (this.type == null && other.type != null)
			return false;
		if (!this.type.equals(other.type))
			return false;
		
		return true;
	}
	
	/**
	 * @flowerModelElementId _vGHWAKP8EeGeHqktJlHXmA
	 */
	@Override
	public String toString() {		
		return "[name=" + getName() + ",type=" + getType() + "]";
	}
			
}