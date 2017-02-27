package cn.edu.ustc.streamingdemov210;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
/*
 *   由于是快速开发demo，所以主界面很简单，只有两个按钮，
 *   一个推流，一个观看，这里只讲推流的。
 *   mainactivity非常简单，只是实现了按钮的点击事件，
 *   当然还有些判断权限、添加权限的代码，
 *   然后异步请求stream的代码我也省去了。
 *   所以最简单的代码如下：
 */

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private android.widget.Button btnpili;
    private android.widget.Button btnplay;
    private boolean mPermissionEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //接收传来的数据
        final Bundle bundle = this.getIntent().getExtras();
        final String publishurl = bundle.getString("publishurl") ;

        this.btnplay = (Button) findViewById(R.id.btn_play);
        this.btnpili = (Button) findViewById(R.id.btn_pili);
//    public void OpenNew(View v){
//        //新建一个显式意图，第一个参数为当前Activity类对象，第二个参数为你要打开的Activity类
//        Intent intent =new Intent(LoginActivity.this,MainActivity.class);
//
//        //用Bundle携带数据
//        Bundle bundle=new Bundle();
//        //传递name参数为tinyphp
//        bundle.putString("publishurl",RESULT);
//        intent.putExtras(bundle);
//        startActivity(intent)/(new Intent(MainActivity.this,HWCameraStreamingActivity.class))
//    }
        //开始直播
        btnpili.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,HWCameraStreamingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("publishurl",publishurl);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //观看直播
        btnplay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });
    }
}
