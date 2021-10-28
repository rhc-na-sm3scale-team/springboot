package com.redhat.runtimes.api;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

@RestController
public class UserApiImpl implements UserApi {
	@Override
	public Optional<NativeWebRequest> getRequest() {
		return UserApi.super.getRequest();
	}
}
