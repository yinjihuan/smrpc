package org.smrpc.core.rpc;

import java.io.Serializable;

public class DefaultRpcRequest implements RpcRequest, Serializable {

	private static final long serialVersionUID = 8195602469121904869L;

	private String interfaceName;
	private String methodName;
	private Object[] arguments;
	private long requestId;
	private Class<?>[] parameterTypes;
	
	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}

	public void setParameterTypes(Class<?>[] parameterTypes) {
		this.parameterTypes = parameterTypes;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Object[] getArguments() {
		return arguments;
	}

	public void setArguments(Object[] arguments) {
		this.arguments = arguments;
	}

	public long getRequestId() {
		return requestId;
	}

	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}

}
