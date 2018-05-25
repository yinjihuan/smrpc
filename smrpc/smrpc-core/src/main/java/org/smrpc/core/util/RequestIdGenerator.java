package org.smrpc.core.util;

import java.util.concurrent.atomic.AtomicLong;

public class RequestIdGenerator {
	
	private static final AtomicLong INVOKE_ID = new AtomicLong(0);
	
	public static long getRequestId() {
		return INVOKE_ID.getAndIncrement();
	}
	
}
