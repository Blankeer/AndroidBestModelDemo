package com.blanke.sqldelighttest.database.adapter;

import android.content.ContentValues;
import android.database.Cursor;

import com.squareup.sqldelight.ColumnAdapter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by blanke on 16-5-16.
 */
public class DateAdapter implements ColumnAdapter<ZonedDateTime> {
    private final DateTimeFormatter mDateTimeFormatter
            = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public ZonedDateTime map(Cursor cursor, int columnIndex) {
        return mDateTimeFormatter.parse
                (cursor.getString(columnIndex), ZonedDateTime::from);
    }

    @Override
    public void marshal(ContentValues values, String key, ZonedDateTime value) {
        values.put(key, mDateTimeFormatter.format(value));
    }

}
