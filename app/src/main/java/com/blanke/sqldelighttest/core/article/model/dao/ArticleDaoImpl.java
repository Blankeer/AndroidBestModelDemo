package com.blanke.sqldelighttest.core.article.model.dao;

import com.blanke.sqldelighttest.database.bean.Article;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;

import rx.Observable;

/**
 * Created by blanke on 16-5-18.
 */
public class ArticleDaoImpl implements ArticleDao {
    private BriteDatabase mBriteDatabase;

    public ArticleDaoImpl(BriteDatabase mBriteDatabase) {
        this.mBriteDatabase = mBriteDatabase;
    }

    @Override
    public Observable<List<Article>> getAllArticle(String type, int size, int page) {
        return mBriteDatabase.createQuery
                (Article.TABLE_NAME, Article.SELECT_PAGE
                        , type, size + "", size * page + "")
                .mapToList(Article.MAPPER::map);
    }

    @Override
    public void insertArticles(List<Article> list) {
        BriteDatabase.Transaction trans = mBriteDatabase.newTransaction();
        try {
            for (Article item : list) {
                mBriteDatabase.insert(Article.TABLE_NAME,
                        new Article.Marshal(item).asContentValues());
            }
            trans.markSuccessful();
        } finally {
            trans.end();
        }
    }
}
