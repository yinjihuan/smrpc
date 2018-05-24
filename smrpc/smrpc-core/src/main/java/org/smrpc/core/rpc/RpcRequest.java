package org.smrpc.core.rpc;

public interface RpcRequest {
	/**
     * 接口名称
     * 
     * @return
     */
    String getInterfaceName();

    /**
     * 方法名称
     * 
     * @return
     */
    String getMethodName();

    /**
     * 方法的参数
     * 
     * @return
     */
    Object[] getArguments();

    /**
     * 请求编号
     * 
     * @return
     */
    long getRequestId();
}
