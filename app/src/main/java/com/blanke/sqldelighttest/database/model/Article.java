package com.blanke.sqldelighttest.database.model;

import com.blanke.sqldelighttest.database.adapter.DateAdapter;
import com.google.auto.value.AutoValue;
import com.squareup.sqldelight.ColumnAdapter;

/**
 * Created by blanke on 16-5-18.
 */
@AutoValue
public abstract class Article implements ArticleModel {
    private static final DateAdapter DATE_ADAPTER = new DateAdapter();

    public static final ArticleModel.Mapper<Article> MAPPER =
            new Mapper<>(AutoValue_Article::new,
                    DATE_ADAPTER, DATE_ADAPTER);

    public static final class Marshal extends ArticleModel.ArticleMarshal {

        public Marshal(ColumnAdapter publishedAtAdapter) {
            super(publishedAtAdapter, DATE_ADAPTER);
        }

        public Marshal(ArticleModel copy) {
            super(copy, DATE_ADAPTER, DATE_ADAPTER);
        }
    }

}
