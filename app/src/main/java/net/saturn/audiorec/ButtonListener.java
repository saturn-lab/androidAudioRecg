package net.saturn.audiorec;

import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.media.AudioFormat;
import java.io.File;
import java.io.IOException;
import android.os.*;
import java.io.*;
import java.io.FileOutputStream;


/**
 * Created by Administrator on 2017/3/27.
 */

/**
 * 更新语音识别APP，让它成为可以对话（conversation）（伪）。
 *
 * 首先，设计对话的自动机，包括起始状态、录音状态、等待用户输入状态、识别结果显示的状态，状态中跃迁。
 */

public class ButtonListener implements View.OnTouchListener {
    public MainActivity activity;
    public Boolean isRecording;

    public ButtonListener(MainActivity activity) {
        this.activity = activity;
        isRecording = false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v.getId() == R.id.recording){
            TextView result = (TextView) this.activity.findViewById(R.id.result);
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                result.setText("正在录音...");
                RecordTask r_task = new RecordTask();
                r_task.execute();
                isRecording = true;
            }
            else if(event.getAction() == MotionEvent.ACTION_UP) {
                isRecording = false;
            }
        }
        return false;
    }

    class RecordTask extends AsyncTask<Void,Void, Integer> {
        short[] buffer;
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/test_record.pcm");


        @Override
        protected Integer doInBackground(Void... params) {
            int freq = 44100;
//            int freq = 8000;
            int channel = AudioFormat.CHANNEL_IN_MONO;
//            int channel = 16;
            int encoding =  AudioFormat.ENCODING_PCM_16BIT;
            int minSize = AudioRecord.getMinBufferSize(freq, channel, encoding);

            int maxSecond = 10;
            int maxSize = maxSecond * freq;

            buffer = new short[maxSize*10];

            AudioRecord rec = new AudioRecord(MediaRecorder.AudioSource.MIC, freq, channel, encoding, maxSize);
            rec.startRecording();


            if (file.exists()){
                file.delete();
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new IllegalStateException("Failed to create " + file.toString());
            }
            try {
                OutputStream os = new FileOutputStream(file);
                BufferedOutputStream bos = new BufferedOutputStream(os);
                DataOutputStream dos = new DataOutputStream(bos);
                int progress = 0;

                while(isRecording && progress < maxSize - minSize) {
                    int readCount = rec.read(buffer, progress, minSize);
                    progress += readCount;
                    for (int i = 0; i < readCount; i++)
                        dos.writeShort(buffer[i]);
                }
                rec.stop();
                rec.release();
                dos.close();
                return progress;
            } catch (FileNotFoundException e){
                throw new IllegalStateException("File not exist!");
            } catch (IOException e){
                throw new IllegalStateException("IO Error!");
            }

        }

        @Override
        protected void onPostExecute(Integer length) {
            TextView result = (TextView) activity.findViewById(R.id.result);


            short[] trueBuffer = new short[length];
            System.arraycopy(buffer,0,trueBuffer,0,length);

            if( length < 20000 ) result.setText("说的太快没听清楚");
            else result.setText("识别结果：" + AudioSpeechRecognition.recognize(trueBuffer));
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
