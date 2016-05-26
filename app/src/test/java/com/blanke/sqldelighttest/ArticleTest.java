package com.blanke.sqldelighttest;


import com.blanke.sqldelighttest.api.GanKAPI;
import com.blanke.sqldelighttest.config.C;
import com.blanke.sqldelighttest.core.article.model.api.ArticleApiImpl;
import com.blanke.sqldelighttest.core.article.model.dao.ArticleDaoImpl;
import com.blanke.sqldelighttest.database.adapter.GankDateTimeForMatter;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.ryanharter.auto.value.gson.AutoValueGsonTypeAdapterFactory;

import org.junit.Test;
import org.threeten.bp.ZonedDateTime;

import java.util.logging.Logger;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by blanke on 16-5-24.
 */
public class ArticleTest {
    private ArticleApiImpl articleApi;

    @Test
    public void testGetArticle() {
//        RESTMockServer.whenGET(pathcon)
//                .thenReturnString(200, "{}");
        initConfig();
        getData();
    }

    public void initConfig() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(C.BASE_API_URL)
                .addConverterFactory(
                        GsonConverterFactory.create(
                                new GsonBuilder()
//                                        .setDateFormat(C.DATE_FORMAT)
                                        .registerTypeAdapter(ZonedDateTime.class,
                                                (JsonDeserializer<ZonedDateTime>) (json, typeOfT, context) -> {
                                                    return ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()
                                                            , GankDateTimeForMatter.getmDateTimeFormatter());
                                                })
                                        .registerTypeAdapterFactory(new AutoValueGsonTypeAdapterFactory())
                                        .create()
                        )
                )
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        GanKAPI gankApi = retrofit.create(GanKAPI.class);
        articleApi = new ArticleApiImpl(
                new ArticleDaoImpl(null), gankApi);
    }

    public void getData() {
        articleApi.getArticles("Android", 20, 1)
                .subscribe(articles -> {
                    Logger.getLogger("mainactivity")
                            .info(articles.toString());
                }, throwable -> {
                    throwable.printStackTrace();
                    Logger.getLogger("mainactivity")
                            .info(throwable.toString());
                });
    }
}
