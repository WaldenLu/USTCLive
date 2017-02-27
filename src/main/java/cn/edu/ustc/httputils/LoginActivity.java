package cn.edu.ustc.httputils;


import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import cn.edu.ustc.streamingdemov210.MainActivity;
import cn.edu.ustc.streamingdemov210.R;


/**
 * 数据上传
 * 结果返回
 */
public class LoginActivity extends AppCompatActivity {

    private EditText name_input;
    private EditText pwd_input;
    private Button btn_submit;
    private  String RESULT;
//    private TextView result_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //强制启动，不推荐 需要在新的线程中启动
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
//        result_view = (TextView)findViewById(R.id.result_view) ;
        name_input = (EditText) findViewById(R.id.user_name_input);
        pwd_input = (EditText) findViewById(R.id.user_password_input);
        btn_submit = (Button) findViewById(R.id.login_button);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = name_input.getText().toString();
                String password = pwd_input.getText().toString();
                Map<String, String> params = new HashMap<String, String>();
                params.put("streamkey", username);
                params.put("password", password);
                //这个rusult可以作为一个返回值给StreamingDemo调用
                RESULT = HttpUtils.submitPostData(params, "utf-8");
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("publishurl",RESULT);
                intent.putExtras(bundle);
                startActivity(intent);
//                Toast.makeText(getApplicationContext(),"++!", Toast.LENGTH_SHORT).show();
            }
        });
    }
//    public void OpenNew(View v){
//        //新建一个显式意图，第一个参数为当前Activity类对象，第二个参数为你要打开的Activity类
//        Intent intent =new Intent(LoginActivity.this,MainActivity.class);
//
//        //用Bundle携带数据
//        Bundle bundle=new Bundle();
//        //传递name参数为tinyphp
//        bundle.putString("publishurl",RESULT);
//        intent.putExtras(bundle);
//        startActivity(intent);
//    }
}






    /*private void initView() {
        name_input = (AutoCompleteTextView) findViewById(R.id.username_input);
        pwd_input = (TextView) findViewById(R.id.password_input);
        btn_submit = (Button) findViewById(R.id.sign_button);
        result_text = (TextView) findViewById(R.id.result_text);



    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_button:
                String username = name_input.getText().toString();
                String password = pwd_input.getText().toString();
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);
                //这个rusult可以作为一个返回值给StreamingDemo调用
                result_text.setText(HttpUtils.submitPostData(params, "utf-8"));
                break;
        }
    }*/



