package com.izneus.bonfire.module.system.service;

import com.izneus.bonfire.module.system.controller.v1.query.LoginQuery;
import com.izneus.bonfire.module.system.service.dto.LoginDTO;

public interface LoginService {
    LoginDTO login(LoginQuery loginQuery);
}
