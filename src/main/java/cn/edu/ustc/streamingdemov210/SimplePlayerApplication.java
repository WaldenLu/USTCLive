package cn.edu.ustc.streamingdemov210;

import android.app.Application;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.qiniu.pili.droid.streaming.StreamingEnv;

/**
 * Created by 97267 on 2016/12/27.
 */


public class SimplePlayerApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        StreamingEnv.init(getApplicationContext());
    }
}
