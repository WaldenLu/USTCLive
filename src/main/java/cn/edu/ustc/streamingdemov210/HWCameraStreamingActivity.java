package cn.edu.ustc.streamingdemov210;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;

import android.view.MotionEvent;
import android.view.WindowManager;

import com.qiniu.pili.droid.streaming.AVCodecType;
import com.qiniu.pili.droid.streaming.CameraStreamingSetting;
import com.qiniu.pili.droid.streaming.MediaStreamingManager;
import com.qiniu.pili.droid.streaming.MicrophoneStreamingSetting;
import com.qiniu.pili.droid.streaming.StreamingProfile;
import com.qiniu.pili.droid.streaming.StreamingState;
import com.qiniu.pili.droid.streaming.StreamingStateChangedListener;
import com.qiniu.pili.droid.streaming.widget.AspectFrameLayout;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;



/**
 * Created by 97267 on 2016/12/27.
 * 创建名为 SWCameraStreamingActivity 的 Empty Activity，SWCameraStreamingActivity 的主要工作包括：
 *
 * SWCameraStreamingActivity 获取 MainActivity 从 app server 获取到的 stream json
 * 在 onCreate 通过 stream json 初始化推流 SDK 的核心类 MediaStreamingManager
 * 在 onResume 中调用 mMediaStreamingManager.resume();
 * 在接收到 READY 之后，开始推流 mMediaStreamingManager.startStreaming();，startStreaming 需要在非 UI 线程中进行操作。
 */

public class HWCameraStreamingActivity extends Activity implements StreamingStateChangedListener, CameraPreviewFrameView.Listener
{
    private MediaStreamingManager streamingManager;
    private StreamingProfile streamingProfile;
    private MicrophoneStreamingSetting mMicrophoneStreamingSetting;
//    private final String PUBLISH_URL = "rtmp://pili-publish.chinadota.cn/ustclive/helloUSTC?e=1483088335&token=J0s1LhSEbaBgHaLzvtziUruNCD5d6GQjzYs2IgmN:z2qsRsnF3AThPliZMI_H5QA5TCk=" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_hwcamera_streaming);
        AspectFrameLayout afl = (AspectFrameLayout) findViewById(R.id.cameraPreview_afl);
        afl.setShowMode(AspectFrameLayout.SHOW_MODE.REAL);
        CameraPreviewFrameView cameraPreviewFrameView =
                (CameraPreviewFrameView) findViewById(R.id.cameraPreview_surfaceView);
        cameraPreviewFrameView.setListener(this);
        Bundle bundle = this.getIntent().getExtras();
        String publishurl = bundle.getString("publishurl");
        streamingProfile = new StreamingProfile();

        try{
            streamingProfile.setVideoQuality(StreamingProfile.VIDEO_QUALITY_MEDIUM2)
                    .setAudioQuality(StreamingProfile.AUDIO_QUALITY_MEDIUM2)
                    .setEncodingSizeLevel(StreamingProfile.VIDEO_ENCODING_HEIGHT_480)
                    .setEncoderRCMode(StreamingProfile.EncoderRCModes.BITRATE_PRIORITY)
                    .setDnsManager(null).setAdaptiveBitrateEnable(true)
                    .setFpsControllerEnable(true).setStreamStatusConfig(new StreamingProfile.StreamStatusConfig(3))
                    .setPublishUrl(publishurl)
                    .setSendingBufferProfile(new StreamingProfile.SendingBufferProfile(0.2f, 0.8f, 3.0f, 20 * 1000));
            CameraStreamingSetting setting = new CameraStreamingSetting();
            setting.setCameraId(Camera.CameraInfo.CAMERA_FACING_BACK)
                    .setContinuousFocusModeEnabled(true)
                    .setCameraPrvSizeLevel(CameraStreamingSetting.PREVIEW_SIZE_LEVEL.MEDIUM)
                    .setCameraPrvSizeRatio(CameraStreamingSetting.PREVIEW_SIZE_RATIO.RATIO_16_9);
            streamingManager = new MediaStreamingManager(this,afl,cameraPreviewFrameView,
                    AVCodecType.HW_VIDEO_WITH_HW_AUDIO_CODEC);//hw 硬件解码 sw 软解
            mMicrophoneStreamingSetting = new MicrophoneStreamingSetting();
            mMicrophoneStreamingSetting.setBluetoothSCOEnabled(false);
            streamingManager.prepare(setting,mMicrophoneStreamingSetting,null,streamingProfile);
            streamingManager.setStreamingStateListener(this);

        }catch (URISyntaxException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        streamingManager.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //you must invoke pause here.
        streamingManager.pause();
    }

    @Override
    public void onStateChanged(StreamingState streamingState, Object o) {
        switch (streamingState){
            case PREPARING:
                break;
            case READY:
                //start streaming whwn READY
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if(streamingManager != null){
                            streamingManager.startStreaming();
                        }
                    }
                }).start();
                break;
            case CONNECTING:
                break;
            case STREAMING:
                //The av packet had been sent.
            case SHUTDOWN:
                //the streaming had been finished.
                break;
            case IOERROR:
                //Network connect error.
                break;
            case SENDING_BUFFER_EMPTY:
                break;
            case SENDING_BUFFER_FULL:
                break;
            case AUDIO_RECORDING_FAIL:
                //Failed to record audio.
                break;
            case OPEN_CAMERA_FAIL:
                //Failed to open camera.
                break;
            case DISCONNECTED:
                //The socket is broken while streaing
                break;
        }
    }

    public boolean onSingleTapUp(MotionEvent e){
        return false;
    }

    public boolean onZoomValueChanged(float factor){
        return false;
    }
}
