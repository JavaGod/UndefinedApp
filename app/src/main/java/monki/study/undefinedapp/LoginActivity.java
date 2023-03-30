package monki.study.undefinedapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import monki.study.undefinedapp.database.UserDBHelper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_user;
    private EditText et_password;
    private ActivityResultLauncher<Intent> register;
    private String user;
    private String password;
    private SharedPreferences preferences;
    private CheckBox ck_remember;
    private UserDBHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LinearLayout ll_tv_user= findViewById(R.id.ll_tv_user);
        LinearLayout ll_tv_password= findViewById(R.id.ll_tv_password);
        Button btn_login= findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        Button btn_signup= findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(this);
        et_user = findViewById(R.id.et_user);
        et_password = findViewById(R.id.et_password);
        ll_tv_user.setBackgroundResource(R.drawable.shape_rect_gold);
        ll_tv_password.setBackgroundResource(R.drawable.shape_rect_gold);
        ck_remember = findViewById(R.id.ck_remember);
        /*register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if(result != null){
                Intent intent =result.getData();
                if(result!=null&&result.getResultCode()== Activity.RESULT_OK){
                    Bundle bundle = intent.getExtras();
                    user=bundle.getString("user_signed");
                    password= bundle.getString("password_signed");
                }
            }

        });*/
        preferences = getSharedPreferences("config", Context.MODE_PRIVATE);
        reload();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mHelper = UserDBHelper.getInstance(this);
        mHelper.createReadLink();
        mHelper.createWriteLink();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHelper.closeRWLink();
    }

    private void reload() {
        Boolean isRemember = preferences.getBoolean("isRemember",false);
        if (isRemember){
            //设置注册界面传过来的密码
            //this.user=preferences.getString("user",null);
            //this.password = preferences.getString("password",null);
            //设置当前输入框的密码
            String user = preferences.getString("user",null);
            et_user.setText(user);
            String password = preferences.getString("password",null);
            et_password.setText(password);

        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        if(v.getId()==R.id.btn_login){
            String user = this.user;
            String password=this.password;
            Toast loginWrong = Toast.makeText(LoginActivity.this,"账号名或密码错误，请重新输入",Toast.LENGTH_SHORT);
            Toast loginSuccess = Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT);
            Toast noneInput = Toast.makeText(LoginActivity.this,"请输入账号或密码",Toast.LENGTH_SHORT);
            Toast noneUser = Toast.makeText(LoginActivity.this,"请先注册账号",Toast.LENGTH_SHORT);
            Log.d("monki","user"+user);
            Log.d("monki","password"+password);

            intent.setClass(this,HomeActivity.class);


            if(/*user==null&&password==null*/!mHelper.login(et_user.getText().toString(),et_password.getText().toString())){
            //数据库为空
                noneUser.show();
            }else if(et_user.length()==0||et_password.length()==0){
            noneInput.show();
            }else if(/*et_user.getText().toString().equals(user)&&et_password.getText().toString().equals(password)*/mHelper.login(et_user.getText().toString(),et_password.getText().toString())){
                //设置启动标志：跳转到新页面时，栈中原有的实例都被清空，同时开辟新的活动栈
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                loginSuccess.show();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("user",et_user.getText().toString());
                editor.putString("password",et_password.getText().toString());
                editor.putBoolean("isRemember",ck_remember.isChecked());
                editor.commit();
                startActivity(new Intent(intent));

            }else{
                loginWrong.show();
            }
        }
        if(v.getId()==R.id.btn_signup){
            //register.launch(new Intent(this,SignUpActivity.class));
            startActivity(new Intent(this,SignUpActivity.class));
            //欢迎界面
        }
    }
}