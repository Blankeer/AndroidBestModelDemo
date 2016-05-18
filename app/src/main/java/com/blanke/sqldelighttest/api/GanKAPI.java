package com.blanke.sqldelighttest.api;

import com.blanke.sqldelighttest.core.article.model.api.ArticleJsonResult;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by blanke on 16-5-18.
 */
public interface GanKAPI {
    @GET("{type}/{size}/{page}")
    Observable<ArticleJsonResult> getArticles(
            @Path("type") String type,
            @Path("size") int size,
            @Path("page") int page);
}
