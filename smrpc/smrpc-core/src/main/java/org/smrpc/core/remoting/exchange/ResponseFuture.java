package org.smrpc.core.remoting.exchange;

public interface ResponseFuture {

    /**
     * 获取结果
     *
     * @return result
     */
    Object get() throws Exception;

    /**
     * 获取结果指定超时时间
     *
     * @param timeoutInMillis timeout
     * @return result
     */
    Object get(int timeoutInMillis) throws Exception;

    /**
     * set callback.
     *
     * @param callback
     */
    void setCallback(ResponseCallback callback);

    /**
     * check is done.
     *
     * @return done or not.
     */
    boolean isDone();
}
