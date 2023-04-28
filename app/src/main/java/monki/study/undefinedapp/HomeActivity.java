package monki.study.undefinedapp;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.NoRouteToHostException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            /*Uri content_url = Uri.parse("http://10.255.0.19/drcom/login?callback=dr1003&DDDDD=2021304400@cmcc&upass=0128501&0MKKey=123456&R1=0&R3=0&R6=0&para=00&v6ip=&v=7759");
            //Uri content_url = Uri.parse("http://10.255.0.19");
            intent.setData(content_url);
            startActivity(intent);*/
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Looper.prepare();
                        Toast.makeText(HomeActivity.this,"开始建立连接...",Toast.LENGTH_LONG).show();
                        URL url = new URL("http://10.255.0.19/drcom/login?callback=dr1003&DDDDD=2021304400@cmcc&upass=0128501&0MKKey=123456&R1=0&R3=0&R6=0&para=00&v6ip=&v=7759"); // 替换成要打开的网址
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        /*conn.setDoOutput(true);
                        conn.setUseCaches(false);
                        conn.setInstanceFollowRedirects(true);*/
                        InputStream inputStream = conn.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        Toast.makeText(HomeActivity.this,"连接建立成功...开始读取数据",Toast.LENGTH_SHORT).show();
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        reader.close();
                        conn.disconnect();
                        JSONObject jo =new JSONObject(response.toString().replaceAll("dr1003\\u0028","").replaceAll("\\u0029","").trim());
                        /*for (int i = 0; i< jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            jsonObject
                        }*/


                        Log.d("Monki","json:"+response.toString().replaceAll("dr1003\\u0028","").replaceAll("\\u0029","").trim());
                        Log.d("Monki",jo.getString("result"));

                        Toast.makeText(HomeActivity.this,"读取数据成功",Toast.LENGTH_SHORT).show();
                        Log.d("Monki","读取数据成功");
                        /*String content = response.toString();
                        Pattern pattern = Pattern.compile("\"result\":(\\d+)");
                        Matcher matcher = pattern.matcher(content);*/
                        Toast.makeText(HomeActivity.this,"开始检测连通状态..." ,Toast.LENGTH_SHORT).show();
                        Log.d("Monki","开始检测连通状态...");
                        if (response!=null) {
                            //String result = matcher.group(0);
                            //Log.d("Monki","result="+result+"\"");
                            //runOnUiThread(new Runnable() {
                            //@Override
                            // public void run() {
                            if(jo.getString("result").equals("1"))
                            {
                                Toast.makeText(HomeActivity.this,"自动连接成功",Toast.LENGTH_SHORT).show();
                                Log.d("Monki","自动连接成功");
                            }
                            else if(jo.getString("result").equals("0")){
                                Toast.makeText(HomeActivity.this,"自动连接失败，请重新连接",Toast.LENGTH_SHORT).show();
                                Log.d("Monki","自动连接失败，请重新连接");
                            }
                            // }
                            //});

                        }else{
                            Toast.makeText(HomeActivity.this,"未获取到信息，请稍后重试",Toast.LENGTH_SHORT).show();
                        }
                    }catch (ConnectException ce){
                        Toast.makeText(HomeActivity.this,"连接出错，请检查你的网络",Toast.LENGTH_SHORT).show();
                    }catch (NoRouteToHostException ne){
                        Toast.makeText(HomeActivity.this,"连接出错，请检查你的网络",Toast.LENGTH_SHORT).show();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                       e.printStackTrace();
                    }

                    Looper.loop();
                }
            }).start();
        }
    }
}