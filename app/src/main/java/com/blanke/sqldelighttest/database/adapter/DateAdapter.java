package com.blanke.sqldelighttest.database.adapter;

import android.content.ContentValues;
import android.database.Cursor;

import com.squareup.sqldelight.ColumnAdapter;

import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;


/**
 * Created by blanke on 16-5-16.
 */
public class DateAdapter implements ColumnAdapter<ZonedDateTime> {
    private DateTimeFormatter mDateTimeFormatter;

    public DateAdapter() {
        mDateTimeFormatter = GankDateTimeForMatter.getmDateTimeFormatter();
    }

    @Override
    public ZonedDateTime map(Cursor cursor, int columnIndex) {
        //        createdAt: "2016-05-23T10:05:09.526Z",
        return mDateTimeFormatter.parse
                (cursor.getString(columnIndex), ZonedDateTime::from);
    }

    @Override
    public void marshal(ContentValues values, String key, ZonedDateTime value) {
        values.put(key, mDateTimeFormatter.format(value));
    }

}
