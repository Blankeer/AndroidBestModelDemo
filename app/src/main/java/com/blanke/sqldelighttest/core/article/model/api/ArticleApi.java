package com.blanke.sqldelighttest.core.article.model.api;

import com.blanke.sqldelighttest.database.bean.Article;

import java.util.List;

import rx.Observable;

/**
 * Created by blanke on 16-5-18.
 */
public interface ArticleApi {
    Observable<List<Article>> getArticles(
            String type,
            int size,
            int page);
}
