package com.blanke.sqldelighttest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.blanke.sqldelighttest.api.GanKAPI;
import com.blanke.sqldelighttest.config.ProjectConfig;
import com.blanke.sqldelighttest.core.article.model.api.ArticleApi;
import com.blanke.sqldelighttest.core.article.model.api.ArticleApiImpl;
import com.blanke.sqldelighttest.core.article.model.dao.ArticleDaoImpl;
import com.blanke.sqldelighttest.database.DataBaseManager;
import com.blanke.sqldelighttest.database.adapter.GankDateTimeForMatter;
import com.blanke.sqldelighttest.database.bean.Article;
import com.blanke.sqldelighttest.utils.RxJavaUtils;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.ryanharter.auto.value.gson.AutoValueGsonTypeAdapterFactory;

import org.threeten.bp.ZonedDateTime;

import java.util.List;
import java.util.logging.Logger;

import io.appflate.restmock.MatchableCall;
import io.appflate.restmock.RESTMockServer;
import io.appflate.restmock.RESTMockServerStarter;
import io.appflate.restmock.android.AndroidAssetsFileParser;
import io.appflate.restmock.android.AndroidLogger;
import io.appflate.restmock.utils.RequestMatchers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class MainActivity extends AppCompatActivity {

    private ArticleApi articleApi;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.main_text);
        initTest();
        initConfig();
        getData();
    }

    private void initTest() {
//        ProjectConfig.setIsTestApp(true);
        if (ProjectConfig.isTest()) {//初始化http mock
            initRESTMock();
        }
    }

    private void initRESTMock() {
        RESTMockServerStarter.startSync(
                new AndroidAssetsFileParser(this),
                new AndroidLogger());//mock rest
        MatchableCall mockServer = RESTMockServer.whenGET(
                RequestMatchers.pathContains("Android"));
        mockServer.thenReturnFile(200, "article.json");
        ProjectConfig.setTestBaseApiUrl(RESTMockServer.getUrl());
    }

    public void initConfig() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ProjectConfig.getBaseApiUrl())
                .addConverterFactory(
                        GsonConverterFactory.create(
                                new GsonBuilder()
//                                        .setDateFormat(ProjectConfig.DATE_FORMAT)
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
                new ArticleDaoImpl(DataBaseManager.getBriteDatabase(this))
                , gankApi);
    }

    public void getData() {
        Observable<List<Article>> dataObservable = articleApi.getArticles("Android", 20, 1);
        if (!ProjectConfig.isTestJunit()) {//如果不是test则异步，否则同步测试
            dataObservable = RxJavaUtils.schedulerOnAndroid(dataObservable);
        }
        dataObservable.subscribe(articles -> {
            Logger.getLogger("mainactivity")
                    .info(articles.toString());
            mTextView.setText(articles.toString());
        }, throwable -> {
            throwable.printStackTrace();
            Logger.getLogger("mainactivity")
                    .info(throwable.toString());
        });
    }
}
