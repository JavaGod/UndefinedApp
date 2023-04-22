package monki.study.undefinedapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import monki.study.undefinedapp.database.UserDBHelper;

public class DatabaseInformationActivity extends AppCompatActivity {

    UserDBHelper mDBHelper =MyApplication.getInstance().getmDBHelper();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_information);
        TextView tv_inf_db = findViewById(R.id.tv_inf_db);
        mDBHelper.getReadableDatabase();
        tv_inf_db.setText(showDBInformation());
    }

    private String showDBInformation() {
        return mDBHelper.showAllInformation();
    }

}