package com.blanke.sqldelighttest.core.article.model.dao;

import com.blanke.sqldelighttest.database.bean.Article;

import java.util.List;

import rx.Observable;

/**
 * Created by blanke on 16-5-18.
 */
public interface ArticleDao {
    Observable<List<Article>> getAllArticle(String type,
                                            int size,
                                            int page);

    void insertArticles(List<Article> list);
}
