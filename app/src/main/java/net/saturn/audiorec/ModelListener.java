package net.saturn.audiorec;

import android.app.AlertDialog;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

import java.io.File;

/**
 * Created by Administrator on 2017/7/8.
 */

public class ModelListener implements View.OnClickListener {
    public MainActivity activity;
    public ModelListener(MainActivity activity) {
        this.activity = activity;
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.model){
            TensorFlowInferenceInterface tfInterface;
            TextView text = (TextView) activity.findViewById(R.id.result);
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/myModel.pb");
            if(file.exists()) {
                text.setText("找到了用户训练的模型！已载入。");
                tfInterface = new TensorFlowInferenceInterface(activity.getAssets(),
                        Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/myModel.pb");
            }
            else
            {
                text.setText("未在Android文件夹里找到用户训练的模型...载入默认模型");
                tfInterface = new TensorFlowInferenceInterface(activity.getAssets(),
                        "file:///android_asset/asrModel.pb");
            }
        }
    }
}
