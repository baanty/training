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
package com.samples.platform.core.failure;

import org.slf4j.Logger;

import com.qpark.eip.core.failure.BaseFailureHandler;
import com.qpark.eip.core.failure.FailureDescription;
import com.qpark.eip.service.common.msg.ErrorCode;
import com.qpark.eip.service.common.msg.FailureType;
import com.samples.platform.RequestProperties;

/**
 * @author bhausen
 */
public class FailureHandler extends BaseFailureHandler {
	/** The default {@link ErrorCode} if non presented. */
	private static final String DEFAULT = "E_ALL_NOT_KNOWN_ERROR";
	/** The default database {@link ErrorCode} if non presented. */
	private static final String DEFAULT_DATABASE = "E_DATABASE_ERROR";

	/**
	 * @return the unknown {@link FailureType}.
	 */
	public static FailureType getUnknownFailure() {
		return getFailureTypeDefault((Object[]) null);
	}

	/**
	 * @param data
	 * @return the default {@link FailureType}.
	 */
	private static FailureType getFailureTypeDefault(final Object... data) {
		FailureDescription fd = getFailure(null, null, DEFAULT, data);
		return FailureTypeMapper.getFailureType(fd);
	}

	/**
	 * @param data
	 * @return the unknow {@link FailureType}.
	 */
	public static FailureType getUnknownFailure(final Object... data) {
		return getFailureTypeDefault(data);
	}

	/**
	 * defaults to error code E_ALL_NOT_KNOWN_ERROR.
	 * @param e
	 * @param rp
	 * @param log
	 */
	public static FailureType handleException(final Throwable e,
			final RequestProperties<?> rp, final Logger log) {
		return handleException(e, rp, null, log);
	}

	/**
	 * @param e
	 * @param rp
	 * @param code
	 * @param log
	 * @return
	 */
	public static FailureType handleException(final Throwable e,
			final RequestProperties<?> rp, final String code, final Logger log) {
		FailureDescription fd = handleException(e, code, log, (Object[]) null);
		FailureType ft = FailureTypeMapper.getFailureType(fd);
		rp.getFailure().add(ft);
		return ft;
	}

	/**
	 * @param code
	 * @return the {@link FailureType}.
	 */
	public static FailureType getFailureType(final String code) {
		return getFailureType(code, (Throwable) null, (Object[]) null);
	}

	/**
	 * @param code
	 * @param data
	 * @return the {@link FailureType}.
	 */
	public static FailureType getFailureType(final String code,
			final Object... data) {
		return getFailureType(code, (Throwable) null, data);
	}

	/**
	 * @param code
	 * @param data
	 * @return the {@link FailureType}.
	 */
	public static FailureType getFailureType(final String code,
			final Throwable t) {
		return getFailureType(code, t, (Object[]) null);
	}

	/**
	 * @param o
	 * @param ft
	 * @param data
	 * @return the {@link FailureType}.
	 */
	public static FailureType getFailureType(final String code,
			final Throwable t, final Object... data) {
		FailureDescription fd = getFailure(code, t, data);
		return FailureTypeMapper.getFailureType(fd);
	}
}
