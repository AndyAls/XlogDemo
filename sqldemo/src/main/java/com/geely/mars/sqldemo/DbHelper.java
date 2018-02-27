package com.geely.mars.sqldemo;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Shuai.Li13 on 2017/12/1.
 */

public class DbHelper extends SQLiteOpenHelper {
    private final String TAG="---";
    public DbHelper(Context context) {
        super(context, "Woo.db", null, 1, null);
        Log.e(TAG," super(context, \"Woo.db\", null, 1, null);");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists lishuai (id INTEGER primary key autoincrement,name VARCHAR(20),age int)");
        Log.e(TAG, "onCreate:=======");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.e(TAG, "onUpgrade: ===");
    }

}
