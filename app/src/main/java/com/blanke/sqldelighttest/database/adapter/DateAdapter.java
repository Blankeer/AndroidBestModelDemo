package com.blanke.sqldelighttest.database.adapter;

import android.content.ContentValues;
import android.database.Cursor;

import com.squareup.sqldelight.ColumnAdapter;

import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.chrono.IsoChronology;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.DateTimeFormatterBuilder;
import org.threeten.bp.format.ResolverStyle;
import org.threeten.bp.temporal.ChronoField;


/**
 * Created by blanke on 16-5-16.
 */
public class DateAdapter implements ColumnAdapter<ZonedDateTime> {
    private DateTimeFormatter mDateTimeFormatter
            = DateTimeFormatter.ISO_LOCAL_DATE;

    public DateAdapter() {
        initDateFormat();
    }

    private void initDateFormat() {
        mDateTimeFormatter = new DateTimeFormatterBuilder().parseCaseInsensitive()
                .append(DateTimeFormatter.ISO_LOCAL_DATE)
                .appendLiteral('T')
                .appendValue(ChronoField.HOUR_OF_DAY, 2)
                .appendLiteral(':')
                .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
//                .optionalStart()
                .appendLiteral(':')
                .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
                .appendLiteral('Z')
                .toFormatter()
                .withResolverStyle(ResolverStyle.STRICT)
                .withChronology(IsoChronology.INSTANCE)
                .withZone(ZoneId.systemDefault());
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
