package com.blanke.sqldelighttest.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.blanke.sqldelighttest.database.bean.Article;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import rx.schedulers.Schedulers;

/**
 * DataBaseManager管理类，主要是对BriteDatabase的管理
 * Created by blanke on 16-5-18.
 */
public class DataBaseManager {
    private static SQLiteOpenHelper mSqLiteOpenHelper;
    private static DataBaseManager mDataBaseManager;
    private static final String DB_NAME = "gank";
    private static SqlBrite mSqlBrite;
    private static BriteDatabase mBriteDatabase;

    public static BriteDatabase getBriteDatabase(Context context) {
        if (mBriteDatabase == null) {
            synchronized (DataBaseManager.class) {
                if (mDataBaseManager == null) {
                    mDataBaseManager = new DataBaseManager();
                    mDataBaseManager.mSqLiteOpenHelper = new SQLiteOpenHelper(context, DB_NAME, null, 1) {
                        @Override
                        public void onCreate(SQLiteDatabase db) {
                            init(db);
                        }

                        @Override
                        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                        }
                    };
                    mSqlBrite = SqlBrite.create();
                    mBriteDatabase = mSqlBrite.wrapDatabaseHelper(mSqLiteOpenHelper, Schedulers.io());
                    mBriteDatabase.setLoggingEnabled(true);
                }
            }
        }
        return mBriteDatabase;
    }

    private static void init(SQLiteDatabase db) {
        db.execSQL(Article.CREATE_TABLE);
    }
}
