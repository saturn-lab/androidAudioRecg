package net.saturn.audiorec;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button record = (Button)findViewById(R.id.recording);
        record.setOnTouchListener(new ButtonListener(this));

        askForPermission();
        TensorFlowInferenceInterface tfInterface = new TensorFlowInferenceInterface(getAssets(),
                "file:///android_asset/asrModel.pb");

        AudioSpeechRecognition.tif = tfInterface;
    }

    private void askForPermission(){
        int magicCode=152;
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED) {
             TextView text = (TextView) findViewById(R.id.result);
             text.setText("按住录音键开始录音");
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
                text.setText("录音权限get√\n按住录音键开始录音");
            }
            else {
                TextView text = (TextView) findViewById(R.id.result);
                text.setText("不给权限没法干活");
            }
        }
    }
}
