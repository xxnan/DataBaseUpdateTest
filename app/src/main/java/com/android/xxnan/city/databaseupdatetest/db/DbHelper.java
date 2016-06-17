package com.android.xxnan.city.databaseupdatetest.db;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {  
    private final static int VERSION = 1;  
    public final static String DB_NAME = "picc.db";
    public final static String TABLE_NAME_PIC = "local_picture"; 
    public final static String TABLE_NAME_TASK = "local_task";
    public final static String TABLE_TASK_PIC_UPLAOD = "local_task_pic_upload";
    public final static String TABLE_PICCTALK = "local_picc_talk";//聊天表
    private SQLiteDatabase db;  
         private static final String CREATE_TABLE_NAME_PIC =
             "CREATE TABLE if not exists " + TABLE_NAME_PIC + " (" +
          DBpicture.ID + " integer PRIMARY KEY autoincrement," +
          DBpicture.CASE_NO + " varchar(60)," +
          DBpicture.USER_NO + " varchar(60)," +
          DBpicture.PIC_PATH + " varchar(60)," +
          DBpicture.PIC_NAME + " varchar(60)," +
            " UNIQUE (" + DBpicture.ID +"));";//暂时规定不能重名
        private static final String CREATE_TABLE_TASK =
              "CREATE TABLE if not exists " + TABLE_NAME_TASK + " (" +
                      DBTask.ID + " integer PRIMARY KEY autoincrement," +
                      DBTask.CASE_NO + " varchar(60)," +
                      DBTask.USER_NO + " varchar(60)," +
                      DBTask.USER_NAME + " varchar(60)," +
                      DBTask.CAR_NO + " varchar(60)," +
                      DBTask.DEAL_NO + " varchar(60)," +
                      DBTask.TASK_WORK_ID + " varchar(60)," +
                      DBTask.CONTACT_NO + " varchar(60)," +
                      DBTask.BEI_ZHU + " varchar(1000)," +
                      DBTask.CASE_TIME + " varchar(60)," +
                      DBTask.CASE_STATUS + " varchar(60)," +
              " UNIQUE (" + DBTask.ID +"));";
    private static final String CREATE_TABLE_PICCTALK =
            "CREATE TABLE if not exists " +TABLE_PICCTALK + " (" +
                    DBTalk.ID + " integer PRIMARY KEY autoincrement," +
                    DBTalk.CASE_NO + " varchar(60)," +
                    DBTalk.USER_NO + " varchar(60)," +
                    DBTalk.USER_NAME + " varchar(60)," +
                    DBTalk.CAR_NO + " varchar(60)," +
                    DBTalk.TASK_WORK_ID + " varchar(60)," +
                    DBTalk.TALK_CONTENT + " varchar(1000)," +
                    DBTalk.TIME + " varchar(60)," +
                    DBTalk.WITH_TALK_NO + " varchar(60)," +
                    DBTalk.WITH_TALK_NAME + " varchar(60)," +
                    " UNIQUE (" + DBTalk.ID +"));";
    //SQLiteOpenHelper子类必须要的一个构造函数
    public DbHelper(Context context, String name, CursorFactory factory,int version) {  
        //必须通过super 调用父类的构造函数
        super(context, name, factory, version);  
    }

    //数据库的构造函数，传递三个参数的
    public DbHelper(Context context, String name, int version){  
        this(context, name, null, version);  
    }

    //数据库的构造函数，传递一个参数的， 数据库名字和版本号都写死了
    public DbHelper(Context context){  
        this(context, DB_NAME, null, VERSION);  
    }

    // 回调函数，第一次创建时才会调用此函数，创建一个数据库
    @Override  
    public void onCreate(SQLiteDatabase db) {  
        this.db = db;  
        db.execSQL(CREATE_TABLE_NAME_PIC); 
        db.execSQL(CREATE_TABLE_TASK);
        db.execSQL(CREATE_TABLE_PICCTALK);
    }

    //回调函数，当你构造DBHelper的传递的Version与之前的Version调用此函数
    @Override  
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {  
        if(newVersion>oldVersion)
        {
            db.execSQL("ALTER TABLE local_picc_talk ADD  talknumber varchar(20);");
        }

    }  
      
    public void insert(ContentValues values, String tableName){
        db = getWritableDatabase();
        db.insert(tableName, null, values);
        db.close();  
    }  
   
    public Cursor query(String tableName,String[] whereClause,String whereArgs,String [] args){
        db = getReadableDatabase();  
//        Cursor c=db.query(tableName, whereClause,whereClause[0]+"=?", whereArgs, null,null, null, null);
        Cursor cursor = db.query(tableName, null, whereArgs+"= ?", args, null, null, null);  
        return cursor;  
          
    }  
    public Cursor queryPic(String tableName,String[] whereClause,String whereArgs,String whereArgs1,String [] args){
        db = getReadableDatabase();  
//        Cursor c=db.query(tableName, whereClause,whereClause[0]+"=?", whereArgs, null,null, null, null);
        Cursor cursor = db.query(tableName, null, whereArgs+"=? and "+whereArgs1+"=?", args, null, null, null);  
        return cursor;  
          
    }  
    public void delete(String tableName,String id){
        db = getWritableDatabase();  
        db.delete(tableName, DBTask.CASE_NO+"=?", new String[]{id});  
    }  
    public void deletePic(String tableName,String id){  
        db = getWritableDatabase();  
        db.delete(tableName, DBpicture.CASE_NO+"=?", new String[]{id});  
    } 

    //�������ݿ������  
    public void update(ContentValues values, String whereClause, String[]whereArgs,String tableName){  
        db = getWritableDatabase();  
        db.update(tableName, values, whereClause, whereArgs);  
    }  
    public void updatePic(ContentValues values, String whereClause, String[]whereArgs,String tableName){  
        db = getWritableDatabase();  
        db.update(tableName, values, whereClause, whereArgs);  
    }   
    public void updatePicFile(ContentValues values, String whereClause, String[]whereArgs,String tableName){  
        db = getWritableDatabase();  
        db.update(tableName, values, whereClause+"=?", whereArgs);  
    } 
    public void close(){
        if(db != null){  
            db.close();  
        }  
    }  
    public Cursor TalkQuery(String tableName,String[] whereClause,String whereArgs,String [] args){
        db = getReadableDatabase();  
//        Cursor cursor = db.query(tableName, null, whereArgs, args, null, null, null);
        Cursor cursor= db.rawQuery("select * from "+ tableName+" where "+whereArgs, args);
        return cursor;  
          
    }  
}  