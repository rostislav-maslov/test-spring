package com.rmaslov.springboot.user.routes;

import com.rmaslov.springboot.base.routes.BaseApiRoutes;

public class UserApiRoutes {
    public static final String ROOT = BaseApiRoutes.V1 + "/user";
    public static final String BY_ID = ROOT + "/{id}";
}
