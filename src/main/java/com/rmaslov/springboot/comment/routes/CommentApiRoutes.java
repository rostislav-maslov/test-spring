package com.rmaslov.springboot.comment.routes;

import com.rmaslov.springboot.base.routes.BaseApiRoutes;

public class CommentApiRoutes {
    public static final String ROOT = BaseApiRoutes.V1 + "/comment";
    public static final String BY_ID = ROOT + "/{id}";
}
