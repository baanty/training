package com.qpark.eip.core.spring;

/**
 * Provides the context name and version of the bus.app.authority.
 * @author bhausen
 */
public class ContextNameProvider {
	/** The context name of the eip core authority. */
	private String contextName;
	/** The context version of the eip core authority. */
	private String contextVersion;

	/**
	 * Get the context name of the eip core authority.
	 * @return the context name of the eip core authority.
	 */
	public String getContextName() {
		return this.contextName;
	}

	/**
	 * Get the context version of the eip core authority.
	 * @return the context version of the eip core authority.
	 */
	public String getContextVersion() {
		return this.contextVersion;
	}

	/**
	 * Set the context name of the eip core authority.
	 * @param contextName the context name of the eip core authority.
	 */
	public void setContextName(final String context) {
		this.contextName = context;
	}

	/**
	 * Set the context version of the eip core authority.
	 * @param contextVersion the context version of the eip core authority.
	 */
	public void setContextVersion(final String contextVersion) {
		this.contextVersion = contextVersion;
	}

}
