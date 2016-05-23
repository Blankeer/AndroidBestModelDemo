package com.blanke.sqldelighttest.database.adapter;

import org.threeten.bp.chrono.IsoChronology;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.DateTimeFormatterBuilder;
import org.threeten.bp.format.ResolverStyle;
import org.threeten.bp.format.SignStyle;
import org.threeten.bp.temporal.ChronoField;

/**
 * Created by blanke on 16-5-23.
 */
public class GankDateTimeForMatter {
    private static DateTimeFormatter mDateTimeFormatter;

    public static DateTimeFormatter getmDateTimeFormatter() {
        if (mDateTimeFormatter == null) {
            initDateFormat();
        }
        return mDateTimeFormatter;
    }

    private static void initDateFormat() {
        mDateTimeFormatter = new DateTimeFormatterBuilder().parseCaseInsensitive()
                .append(DateTimeFormatter.ISO_LOCAL_DATE)
                .appendLiteral('T')
                .appendValue(ChronoField.HOUR_OF_DAY, 2)
                .appendLiteral(':')
                .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
                .appendLiteral(':')
                .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
                .appendLiteral('.')
                .appendValue(ChronoField.OFFSET_SECONDS, 1, 3, SignStyle.NORMAL)
                .appendLiteral('Z')
                .toFormatter()
                .withResolverStyle(ResolverStyle.STRICT)
                .withChronology(IsoChronology.INSTANCE);
    }
}
