package monki.study.undefinedapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button bt_jump = findViewById(R.id.bt_jump);
        Button bt_login = findViewById(R.id.bt_Login);
        bt_jump.setOnClickListener(this);
        bt_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");

        if (v.getId() == R.id.bt_jump) {
            Uri content_url = Uri.parse("http://10.255.0.19");
            intent.setData(content_url);
            startActivity(intent);

        } else if (v.getId() == R.id.bt_Login) {
            Uri content_url = Uri.parse("http://10.255.0.19/drcom/login?callback=dr1003&DDDDD=2021304400@cmcc&upass=0128501&0MKKey=123456&R1=0&R3=0&R6=0&para=00&v6ip=&v=7759");
            //Uri content_url = Uri.parse("http://10.255.0.19");
            intent.setData(content_url);
            startActivity(intent);
        }
    }
}