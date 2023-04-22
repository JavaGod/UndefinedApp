package monki.study.undefinedapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import monki.study.undefinedapp.database.UserDBHelper;
import monki.study.undefinedapp.entity.User;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_set_user;
    private EditText et_set_password;
    private EditText et_verify_password;
    private UserDBHelper mHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        findViewById(R.id.btn_verify_signup).setOnClickListener(this);
        findViewById(R.id.btn_showdatabase_signup).setOnClickListener(this);
        et_set_user = findViewById(R.id.et_set_user);
        et_set_password = findViewById(R.id.et_set_password);
        et_verify_password = findViewById(R.id.et_verify_password);

        mHelper = MyApplication.getInstance().getmDBHelper();
        mHelper.createWriteLink();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_verify_signup:
                Toast disMatch=Toast.makeText(SignUpActivity.this,"两次输入的密码不匹配，请重新输入密码",Toast.LENGTH_LONG);
                Toast success=Toast.makeText(SignUpActivity.this,"注册成功,请登录",Toast.LENGTH_LONG);
                Toast noneInput = Toast.makeText(SignUpActivity.this,"请输入账号或密码",Toast.LENGTH_SHORT);

                if(et_set_user.length()==0||et_set_password.length()==0){
                    //账号或密码未输入
                    noneInput.show();

                } else if(et_set_password.getText().toString().equals(et_verify_password.getText().toString())){
                    //两次输入的密码匹配，即注册成功
            /*Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("user_signed",et_set_user.getText().toString());
            bundle.putString("password_signed",et_set_password.getText().toString());
            intent.putExtras(bundle);
            setResult(RESULT_OK,intent);*/
                    User user = new User();
                    user.account =et_set_user.getText().toString();
                    user.password =et_set_password.getText().toString();

                    mHelper.save(user);
                    success.show();
                    finish();
                }else{//两次输入的密码不匹配
                    disMatch.show();
                    et_set_password.setText("");
                    et_verify_password.setText("");

                }
                break;
            case R.id.btn_showdatabase_signup:

                showDatabaseInformation();
                break;
        }
    }

    private void showDatabaseInformation() {
        Intent intent = new Intent();
        intent.setClass(this,DatabaseInformationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mHelper.closeRWLink();
    }
}