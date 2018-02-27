package com.geely.mars.sqldemo;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    final String  tag =this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    private void intSql() {
        db = openOrCreateDatabase("demo.db", MODE_PRIVATE, null);
        db.execSQL("DROP TABLE IF EXISTS person");
        db.execSQL("create table person (id INTEGER PRIMARY KEY autoincrement,name VARCHAR,age SAMLLINT)");
        Person person = new Person();
        person.name="abc";
        person.age=20;
        db.execSQL("insert into person values(null,?,?)",new Object[]{person.name,person.age});
        person.name="lakdanmf";
        person.age=30;
        ContentValues contentValues = new ContentValues();
        contentValues.put("age",person.age);
        contentValues.put("name",person.name);
        db.insert("person",null,contentValues);
    }

    public void onClick(View view) {

        switch (view.getId()){
            case R.id.bt1:
                intSql();
                break;
            case R.id.bt2:
                queryDb();
                break;
            case R.id.bt3:
                DbHelper dbHelper = new DbHelper(this);
                SQLiteDatabase readableDatabase = dbHelper.getWritableDatabase();
                readableDatabase.beginTransaction();
                break;
            case R.id.bt4:
                Toast.makeText(MainActivity.this,"bt4点击",Toast.LENGTH_SHORT).show();
                Intent it = new Intent(this,TimeActivity.class);
                startActivity(it);
                break;
            case R.id.bt5:
                startActivity(new Intent(MainActivity.this,ZxingActivity.class));
                break;
            default:
                break;
        }
    }

    private void queryDb() {
        Cursor cursor = db.rawQuery("select * from person", null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            short age = cursor.getShort(cursor.getColumnIndex("age"));
            Log.e(tag,"id--->"+id+"\n"+"name--->"+name+"\n"+"age--->"+age);
        }
        cursor.close();
        db.close();
    }
}
