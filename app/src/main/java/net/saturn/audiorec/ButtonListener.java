package net.saturn.audiorec;

import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.media.AudioFormat;


/**
 * Created by Administrator on 2017/3/27.
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

        @Override
        protected Integer doInBackground(Void... params) {
            int freq = 44100;
            int channel = AudioFormat.CHANNEL_IN_MONO;
            int encoding =  AudioFormat.ENCODING_PCM_16BIT;
            int minSize = AudioRecord.getMinBufferSize(freq, channel, encoding);

            int maxSecond = 10;
            int maxSize = maxSecond * freq;

            buffer = new short[maxSize];

            AudioRecord rec = new AudioRecord(MediaRecorder.AudioSource.MIC, freq, channel, encoding, maxSize);
            rec.startRecording();

            int progress = 0;

            while(isRecording && progress < maxSize - minSize) {
                int readCount = rec.read(buffer, progress, minSize);
                progress += readCount;
            }
            rec.stop();
            rec.release();
            
            return progress;
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
