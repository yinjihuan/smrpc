package org.smrpc.core.rpc;

import java.io.Serializable;

public class DefaultRpcResponse implements RpcResponse, Serializable {
	
	private static final long serialVersionUID = 4281186647291615871L;

	private Object value;
	private Exception exception;
	private long requestId;

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public long getRequestId() {
		return requestId;
	}

	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}

}