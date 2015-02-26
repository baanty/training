/*******************************************************************************
 * Copyright (c) 2013 QPark Consulting  S.a r.l.
 * 
 * This program and the accompanying materials are made available under the 
 * terms of the Eclipse Public License v1.0. 
 * The Eclipse Public License is available at 
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Bernhard Hausen - Initial API and implementation
 *
 ******************************************************************************/
package com.qpark.maven.plugin;

/**
 * @author bhausen
 */
public interface EipGeneratorDefaults {
	/** The default value of the spring framework xsd version. */
	public static final String DEFAULT_SPRING_FRAMEWORK_XSD_VERSION = "3.2";
	/** The default value of the spring integration xsd version. */
	public static final String DEFAULT_SPRING_INTEGRATION_XSD_VERSION = "2.2";
	/** The default value of the spring ws xsd version. */
	public static final String DEFAULT_SPRING_WS_XSD_VERSION = "2.0";
	/** The default value of the spring security xsd version. */
	public static final String DEFAULT_SPRING_SECURITY_XSD_VERSION = "3.1";
}
