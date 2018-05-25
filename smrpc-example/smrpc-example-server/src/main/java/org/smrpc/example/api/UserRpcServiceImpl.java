package org.smrpc.example.api;

import org.smrpc.example.api.UserRpcService;

public class UserRpcServiceImpl implements UserRpcService {

	public String getName(Long id) {
		return "猿天地";
	}

}
