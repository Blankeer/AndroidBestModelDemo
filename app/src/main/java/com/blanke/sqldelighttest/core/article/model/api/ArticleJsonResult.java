package com.blanke.sqldelighttest.core.article.model.api;

import android.os.Parcelable;

import com.blanke.sqldelighttest.database.bean.Article;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.List;

/**
 * 接口访问的json，包含 error results
 * Created by blanke on 16-5-18.
 */
@AutoValue
public abstract class ArticleJsonResult implements Parcelable {
    public abstract boolean error();

    public abstract List<Article> results();

    //auto-value-gson扩展
    public static TypeAdapter<ArticleJsonResult> typeAdapter(Gson gson) {
        return new AutoValue_ArticleJsonResult.GsonTypeAdapter(gson);
    }
}
