package com.blanke.sqldelighttest.core.article.model.api;

import com.blanke.sqldelighttest.api.GanKAPI;
import com.blanke.sqldelighttest.core.article.model.dao.ArticleDao;
import com.blanke.sqldelighttest.database.bean.Article;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by blanke on 16-5-18.
 */
public class ArticleApiImpl implements ArticleApi {
    private GanKAPI mGanKAPI;
    private ArticleDao mArticleDao;

    public ArticleApiImpl(ArticleDao mArticleDao, GanKAPI mGanKAPI) {
        this.mArticleDao = mArticleDao;
        this.mGanKAPI = mGanKAPI;
    }

    @Override
    public Observable<List<Article>> getArticles(String type, int size, int page) {
        return mGanKAPI.getArticles(type, size, page)
                .map(ArticleJsonResult::results)//map转换为List<Article>
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(mArticleDao::insertArticles);//保存到数据库
    }
}
