package com.blanke.sqldelighttest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.blanke.sqldelighttest.api.GanKAPI;
import com.blanke.sqldelighttest.core.article.model.api.ArticleApi;
import com.blanke.sqldelighttest.core.article.model.api.ArticleApiImpl;
import com.blanke.sqldelighttest.core.article.model.dao.ArticleDaoImpl;
import com.blanke.sqldelighttest.database.DataBaseManager;
import com.blanke.sqldelighttest.database.adapter.GankDateTimeForMatter;
import com.blanke.sqldelighttest.database.bean.Article;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.ryanharter.auto.value.gson.AutoValueGsonTypeAdapterFactory;

import org.threeten.bp.ZonedDateTime;

import java.util.List;
import java.util.logging.Logger;

import io.appflate.restmock.RESTMockServer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private ArticleApi articleApi;
    private TextView mTextView;
    private boolean isTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.main_text);
        initConfig();
        getData();
    }

    public void setTest(Boolean isTest) {
        this.isTest = isTest;
    }

    public void initConfig() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RESTMockServer.getUrl())
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
                new ArticleDaoImpl(DataBaseManager.getBriteDatabase(this))
                , gankApi);
    }

    public void getData() {
        Observable<List<Article>> dataObservable = articleApi.getArticles("Android", 20, 1);
        if (!isTest) {
            dataObservable=dataObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
        dataObservable.subscribe(articles -> {
            Logger.getLogger("mainactivity")
                    .info(articles.toString());
            System.out.println(articles.toString());
            mTextView.setText(articles.toString());
        }, throwable -> {
            throwable.printStackTrace();
            Logger.getLogger("mainactivity")
                    .info(throwable.toString());
        });
    }
}
