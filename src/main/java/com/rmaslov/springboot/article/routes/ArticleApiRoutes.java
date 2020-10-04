package com.rmaslov.springboot.article.routes;

import com.rmaslov.springboot.base.routes.BaseApiRoutes;

public class ArticleApiRoutes {
    public static final String ROOT = BaseApiRoutes.V1 + "/article";
    public static final String BY_ID = ROOT + "/{id}";
}
