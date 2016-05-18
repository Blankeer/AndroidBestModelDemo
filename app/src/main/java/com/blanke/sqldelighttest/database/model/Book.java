package com.blanke.sqldelighttest.database.model;

import com.blanke.sqldelighttest.database.adapter.DateAdapter;
import com.google.auto.value.AutoValue;
import com.squareup.sqldelight.ColumnAdapter;

import java.time.ZonedDateTime;

/**
 * Created by blanke on 16-5-16.
 */
@AutoValue
public abstract class Book implements BookModel {
    private static final DateAdapter DATE_ADAPTER = new DateAdapter();
    public static final Mapper<Book> MAPPER = new Mapper<>(new Mapper.Creator<Book>() {
        @Override
        public Book create(long id, String isbn, String title, ZonedDateTime release_year) {
            return new AutoValue_Book(id, isbn, title, release_year);
        }
    }, DATE_ADAPTER);

    public static final class Marshal extends BookMarshal<Marshal> {

        public Marshal() {
            super(DATE_ADAPTER);
        }

        public Marshal(BookModel copy) {
            super(copy, DATE_ADAPTER);
        }
    }
}
