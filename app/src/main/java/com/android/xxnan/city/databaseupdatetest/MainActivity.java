package com.android.xxnan.city.databaseupdatetest;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.android.xxnan.city.databaseupdatetest.db.DBTalk;
import com.android.xxnan.city.databaseupdatetest.db.DbHelper;
import com.android.xxnan.city.databaseupdatetest.db.TalkBean;

public class MainActivity extends AppCompatActivity {
    private DbHelper  myDbHelper;
    private Button btn;
    SQLiteDatabase db;
    int ver=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            ver=getVersionName(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        myDbHelper = new DbHelper(this,DbHelper.DB_NAME,ver);
        db= myDbHelper.getWritableDatabase();
        btn=(Button)findViewById(R.id.insert);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert(null);
            }
        });
    }
    public int getVersionName(Context context) throws Exception
    {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
        int version = packInfo.versionCode;
        return version;
    }
    public void insert(TalkBean talkBean)
    {
        //插入数据到聊天表
        ContentValues values = new ContentValues();
        values.put(DBTalk.CASE_NO, "158");
        values.put(DBTalk.USER_NO, "18620197974");
        values.put(DBTalk.USER_NAME, "xxnan");
        values.put(DBTalk.TASK_WORK_ID, "11111111");
        values.put(DBTalk.TIME, "12345678");
        values.put(DBTalk.TALK_CONTENT, "haha!");
        values.put(DBTalk.CAR_NO, "yue Sf0789");
        values.put(DBTalk.WITH_TALK_NAME, "123");
        values.put(DBTalk.WITH_TALK_NO, "123");
        values.put("talknumber", "1000");
        myDbHelper.insert(values, DbHelper.TABLE_PICCTALK);
    }
}
