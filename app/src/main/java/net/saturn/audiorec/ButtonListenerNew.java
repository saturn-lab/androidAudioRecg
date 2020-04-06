package net.saturn.audiorec;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.media.MediaPlayer;
import android.content.res.AssetFileDescriptor;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.util.Log;
import android.media.PlaybackParams;


import android.media.SoundPool;
import android.media.AudioAttributes;
import android.media.AudioManager;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;




/**
 * Created by Administrator on 2017/3/27.
 */

/**
 * 更新语音识别APP，让它成为可以对话（conversation）（伪）。
 *
 * 首先，设计对话的自动机，包括起始状态、录音状态、等待用户输入状态、识别结果显示的状态，状态中跃迁。
 */

public class ButtonListenerNew implements View.OnClickListener {
    public MainActivity activity;
    public Boolean isWaiting;
    public Boolean isRecording;
//    public MediaPlayer mediaPlayer = new MediaPlayer();

    MediaPlayer mediaPlayer;


    public ButtonListenerNew(MainActivity activity, MediaPlayer mp) {
        this.activity = activity;
        isRecording = false;
        isWaiting = false;
        mediaPlayer = mp;
    }

//    {
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.recording) {
//            TextView result = (TextView) this.activity.findViewById(R.id.result);
//            result.setText("正在播放音乐...");
////            try {
////                Thread.sleep(10000);
////            } catch (InterruptedException e) {
////                return;
////            }
//
////            this.playVoice(this.activity);
//            mediaPlayer.start();
//
//            result.setText("音乐播放结束...");
            try
              {
                  AssetFileDescriptor descriptor = activity.getAssets().openFd("test_g.mp3");
                  mediaPlayer.reset();
                  mediaPlayer.setDataSource(descriptor.getFileDescriptor(),descriptor.getStartOffset(),descriptor.getLength());
                  descriptor.close();
                  try{
                  PlaybackParams params = mediaPlayer.getPlaybackParams();
                  params.setSpeed(1.5f);
                  mediaPlayer.setPlaybackParams(params);}
                  catch (Exception e){
                      e.printStackTrace();
                  }
                  mediaPlayer.prepare();
                  mediaPlayer.start();
                  mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                      @Override
                      public void onCompletion(MediaPlayer mp) {
                                      TextView result = (TextView) activity.findViewById(R.id.result);
                        result.setText("我正在听...");
                          RecordTask r_task = new RecordTask();
                          isRecording = true;
                          r_task.execute();
//                                      try {
//                            Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                return;
//            }
//                          isRecording = false;
                      }
                  });
//                  RecordTask r_task = new RecordTask();
//                  r_task.execute();
//                  isRecording = true;
              }
              catch (Exception e) {
                  e.printStackTrace();
              }
//            SoundPool.Builder builder = new SoundPool.Builder();
//            builder.setMaxStreams(1);
//            AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
//            attrBuilder.setLegacyStreamType(AudioManager.STREAM_ALARM);
//            builder.setAudioAttributes(attrBuilder.build());
//            SoundPool soundPool = builder.build();
//            try {
//                AssetFileDescriptor afd = activity.getAssets().openFd("test_g.mp3");
//                final int soundId = soundPool.load(afd, 1);
//                soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
//                    @Override
//                    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
//                        if (status == 0) {
//                            //第一个参数soundID
//                            //第二个参数leftVolume为左侧音量值（范围= 0.0到1.0）
//                            //第三个参数rightVolume为右的音量值（范围= 0.0到1.0）
//                            //第四个参数priority 为流的优先级，值越大优先级高，影响当同时播放数量超出了最大支持数时SoundPool对该流的处理
//                            //第五个参数loop 为音频重复播放次数，0为值播放一次，-1为无限循环，其他值为播放loop+1次
//                            //第六个参数 rate为播放的速率，范围0.5-2.0(0.5为一半速率，1.0为正常速率，2.0为两倍速率)
//                            soundPool.play(soundId, 1, 1, 1, 0, 1);
//                        }
//                    }
//                });
////                soundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
//            }
//            catch (Exception e){
//                e.printStackTrace();
//            }


        }
    }

    class RecordTask extends AsyncTask<Void,Void, Integer> {
        short[] buffer;
//        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/test_record.pcm");


        @Override
        protected Integer doInBackground(Void... params) {
//            TextView result = (TextView) activity.findViewById(R.id.result);
//            result.setText("正在听");
            int freq = 44100;
//            int freq = 8000;
            int channel = AudioFormat.CHANNEL_IN_MONO;
//            int channel = 16;
            int encoding =  AudioFormat.ENCODING_PCM_16BIT;
            int minSize = AudioRecord.getMinBufferSize(freq, channel, encoding);

//            int maxSecond = 10;
            int maxSecond = 3;
            int maxSize = maxSecond * freq;

            buffer = new short[maxSize*10];

            AudioRecord rec = new AudioRecord(MediaRecorder.AudioSource.MIC, freq, channel, encoding, maxSize);
            rec.startRecording();


//            if (file.exists()){
//                file.delete();
//            }
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                throw new IllegalStateException("Failed to create " + file.toString());
//            }
//            try {
//                OutputStream os = new FileOutputStream(file);
//                BufferedOutputStream bos = new BufferedOutputStream(os);
//                DataOutputStream dos = new DataOutputStream(bos);
                int progress = 0;

                while(isRecording && progress < maxSize - minSize) {
                    int readCount = rec.read(buffer, progress, minSize);
                    progress += readCount;
//                    for (int i = 0; i < readCount; i++)
//                        dos.writeShort(buffer[i]);
                }
                rec.stop();
                rec.release();
//                dos.close();
                return progress;
//            } catch (FileNotFoundException e){
//                throw new IllegalStateException("File not exist!");
//            } catch (IOException e){
//                throw new IllegalStateException("IO Error!");
//            }

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

    public void playVoice(MainActivity activity) {
        Log.v("Playing voice","Playing voice");
        try {
//            if (mediaPlayer.isPlaying()) {
//                mediaPlayer.stop();
//                mediaPlayer.release();
//                mediaPlayer = new MediaPlayer();
//            }


//
//            mediaPlayer.reset();
//            AssetFileDescriptor descriptor = activity.getResources().getAssets().openFd("lemon.mp3");
//            mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
//            descriptor.close();
//            mediaPlayer.prepare();
////            mediaPlayer.prepareAsync();
//            mediaPlayer.setVolume(1f, 1f);
//            mediaPlayer.setLooping(false);
////            m.setLooping(true);
//            mediaPlayer.reset();
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

/*
public class ButtonListenerNew1 implements View.OnTouchListener {
    public MainActivity activity;
    public Boolean isRecording;

    public ButtonListenerNew(MainActivity activity) {
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
*/