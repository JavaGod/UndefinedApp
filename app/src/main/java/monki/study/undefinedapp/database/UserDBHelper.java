package monki.study.undefinedapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import monki.study.undefinedapp.entity.User;

public class UserDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME ="user.db";
    private static final String TABLE_NAME ="user";
    private static final int DB_VERSION =1;
    private static UserDBHelper mHelper = null;
    private SQLiteDatabase mRDB;
    private SQLiteDatabase mWDB;

    //单例模式
    private UserDBHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }
    
    public static synchronized UserDBHelper getInstance(Context context){

        if(mHelper == null){
            mHelper = new UserDBHelper(context);
        }
        return mHelper;
    }

    //创建数据库读连接
    public SQLiteDatabase createReadLink(){
        if(mRDB == null || !mRDB.isOpen()){
            mRDB = mHelper.getReadableDatabase();
        }
        return mRDB;
    }

    //创建数据库写连接
    public SQLiteDatabase createWriteLink(){
        if(mWDB == null || !mWDB.isOpen()){
            mWDB = mHelper.getWritableDatabase();
        }
        return mWDB;
    }

    //关闭数据库连接
    public void closeRWLink(){
        if(mRDB != null && mRDB.isOpen())
        {
            mRDB.close();
            mRDB = null;
        }
        if(mWDB != null && mWDB.isOpen())
        {
            mWDB.close();
            mWDB = null;
        }
    }

    //创建数据库，执行建表语句
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists "+TABLE_NAME+"("+
                "       id integer primary key autoincrement not null," +
                "       account varchar not null," +
                "       password varchar not null," +
                "       remember integer not null);" ;
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void save(User user){
        try{
            mWDB.beginTransaction();
            delete(user);
            insert(user);
            mWDB.setTransactionSuccessful();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            mWDB.endTransaction();
        }
    }
    //Returns:
    //the row ID of the newly inserted row, or -1 if an error occurred
    public long insert(User user){
        ContentValues values = new ContentValues();
        values.put("account",user.account);
        values.put("password",user.password);
        values.put("remember",user.isRemember);
        return mWDB.insert(TABLE_NAME,null,values);
    }

    //return the number of rows affected if a whereClause is passed in, 0
    //     *         otherwise. To remove all rows and get a count pass "1" as the
    //     *         whereClause.
    public long delete(User user){
        return mWDB.delete(TABLE_NAME,"account = ?",new String[]{user.account});
    }

    public boolean login(String account, String password) {
        //DBHelper dbHelper = new DBHelper(context);
        //SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = mRDB.query(TABLE_NAME, null,
                 "account=? and password =?",
                new String[]{account, password}, null, null, null);
        boolean result = cursor.moveToFirst();
        return result;
    }
}


