package net.saturn.audiorec;

import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

/**
 * Created by saturn on 16-12-29.
 */

public class AudioSpeechRecognition {
    static public TensorFlowInferenceInterface tif=null;

    static public final int CLASS_NUM = 24;
    static public final String INPUT_NAME = "input_1";
    static public final String[] OUTPUT_NAME = new String[] {"dense_2/Softmax"};
    static public final float MIN_PROB = 0.7f;
    
    static public final String[] sentences = {
            "蓝牙开机", "蓝牙拨打电话", "蓝牙打电话", "蓝牙接听电话",
            "蓝牙接电话", "蓝牙拒接", "蓝牙播放音乐", "蓝牙开始音乐",
            "蓝牙暂停音乐", "蓝牙停止音乐", "蓝牙上一首", "蓝牙上一曲",
            "蓝牙下一首", "蓝牙下一曲", "蓝牙音量增大", "蓝牙声音增大",
            "蓝牙音量增加", "蓝牙声音增加", "蓝牙音量减小", "蓝牙声音减小 ",
            "蓝牙关机", "蓝牙电量提醒", "蓝牙还剩多少电", "蓝牙还剩多少电量"
    };

    public static String recognize(short[] sample) {
        int result = recognizeInt(sample);
        if (result == -1) return "听不懂。";
        return sentences[result];
    }

    public static int recognizeInt(short[] sample) {
        float[] fltSample = short2float(sample);
        float[] result = new float[CLASS_NUM];
        tif.feed(INPUT_NAME, fltSample,1,fltSample.length,1,1);
        tif.run(OUTPUT_NAME);
        tif.fetch(OUTPUT_NAME[0], result);

        int argmax = 0;
        for(int i = 1; i < result.length; i++) {
            if (result[i] > result[argmax]) {
                argmax = i;
            }
        }
        
        if (result[argmax] < MIN_PROB) {
            return -1;
        }
        return argmax;
    }

    private static float[] short2float(short[] sample) {
        float[] flt = new float[sample.length];
        int mini = 0;
        int maxi = 0;
        for(int i:sample) {
            if(i > maxi) {
                maxi = i;
            }
            if(i < mini) {
                mini = i;
            }
        }
        mini = - mini;

        maxi = maxi > mini? maxi: mini;
        float MAX = maxi;
        for(int i = 0; i < sample.length; i++) {
            flt[i] = sample[i] / MAX;
        }

        return flt;
    }

}
