package monki.study.undefinedapp;

import android.app.Application;
import android.content.Context;

import monki.study.undefinedapp.database.UserDBHelper;

public class MyApplication extends Application {

    private static MyApplication mApp;

    private UserDBHelper mDBHelper;
    public static MyApplication getInstance(){
        return mApp;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mApp=this;
    }

    public UserDBHelper getmDBHelper() {
        mDBHelper=UserDBHelper.getInstance(this);
        return mDBHelper;
    }

}
