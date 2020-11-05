package com.ums.models.services;

import com.ums.models.entity.User;

public interface UserService {

	public User findByUsername(String username);
}
