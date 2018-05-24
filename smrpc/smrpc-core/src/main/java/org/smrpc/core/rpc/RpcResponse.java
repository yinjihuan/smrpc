package org.smrpc.core.rpc;

public interface RpcResponse {
	
    Object getValue();

    Exception getException();
    
    long getRequestId();

}
