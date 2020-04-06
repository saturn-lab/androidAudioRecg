package net.saturn.audiorec;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;
import android.view.View;
import java.io.File;
import android.os.Environment;


import android.media.SoundPool;
import android.media.AudioAttributes;

import java.io.IOException;
import java.util.Date;
import android.util.Log;
import android.content.Context;

import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

public class MainActivity extends AppCompatActivity {
//    Context context = this;
//    public MediaPlayer mp = MediaPlayer.create(this, 1);

    AssetFileDescriptor descriptor;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mp = new MediaPlayer();
//        try {
//            AssetFileDescriptor descriptor = getAssets().openFd("test.mp3");
//            mp.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
//            descriptor.close();
//            mp.prepare();
//        }
//        catch (IOException e)
//        {
//            Log.v("no file", "no file");
//            mp = null;
//        }

        Button record = (Button)findViewById(R.id.recording);
//        record.setOnClickListener(new View.OnClickListener()
//                                {
//                                      public void onClick(View v)
//                                      {
//                                          try
//                                          {
//                                              descriptor = getAssets().openFd("lemon.mp3");
//                                              mp.setDataSource(descriptor.getFileDescriptor(),descriptor.getStartOffset(),descriptor.getLength());
//                                              descriptor.close();
//                                              mp.prepare();
//                                              mp.start();
//                                          }
//                                          catch (Exception e) {
//                                              e.printStackTrace();
//                                          }
//                                      }
//                                  });

        record.setOnClickListener(new ButtonListenerNew(this, mp));
//        record.setOnTouchListener(new ButtonListener(this));

        Button about = (Button) findViewById(R.id.about);
        about.setOnClickListener(new AboutListener(this));

        Button orders = (Button) findViewById(R.id.orders);
        orders.setOnClickListener(new OrdersListener(this));

        Button model = (Button) findViewById(R.id.model);
        model.setOnClickListener(new ModelListener(this));


        askForPermission();
        TensorFlowInferenceInterface tfInterface;
        TextView text = (TextView) findViewById(R.id.result);
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/myModel.pb");
        if(file.exists()) {
            text.setText("已载入用户训练的模型");
            tfInterface = new TensorFlowInferenceInterface(getAssets(),
                    Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/myModel.pb");
        }
        else
        {
            text.setText("未找到用户训练的模型，已载入默认模型");
            tfInterface = new TensorFlowInferenceInterface(getAssets(),
                    "file:///android_asset/asrModel.pb");
        }
//        ButtonListenerNew.mediaPlayer = new MediaPlayer();
//        ButtonListenerNew.mediaPlayer.reset();
//        try {
//        AssetFileDescriptor descriptor = getAssets().openFd("test.mp3");
//        ButtonListenerNew.mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
//        descriptor.close();
//        }
//        catch (IOException e)
//        {
//            Log.v("no file", "no file");
//            ButtonListenerNew.mediaPlayer = null;
//        }

        AudioSpeechRecognition.tif = tfInterface;
//        try {
//            AssetFileDescriptor descriptor = getAssets().openFd("file:///android_asset/test.mp3");
//            ButtonListenerNew.mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
//            descriptor.close();
//        }
//        catch (IOException e)
//        {
////            Log.v("no file", "no file");
//            ButtonListenerNew.mediaPlayer = null;
//        }
////
//

    }

    private void askForPermission(){
        int magicCode=152;
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED) {
             TextView text = (TextView) findViewById(R.id.result);
             Date date = new Date();
            text.setText("点击与我对话吧");
//             text.setText("当前版本:"+date.toString());
//             text.setText("按住录音键开始录音");
        }else{
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    magicCode);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 152) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                TextView text = (TextView) findViewById(R.id.result);
                text.setText("麦克风权限已获取\n点击与我对话吧");
//                text.setText("麦克风权限get√\n点击对话");
            }
            else {
                TextView text = (TextView) findViewById(R.id.result);
                text.setText("不给权限没法干活");
            }
        }
    }
}
