# An Android app for speech recognition based on TensorFlow library. 

# Step 1. Software requirement

# Step 1.1 Install Android Studio 2.3 + 

Android Studio (Build tools API >= 23 is required to build the TF Android
demo (though it will run on API >= 21 devices).)

https://developer.android.com/studio/

##  '''(Skip to Step2.)''' Step 1.2 Download TensorFlow inference library

https://ci.tensorflow.org/view/Nightly/job/nightly-android/

download two files:

*`libandroid_tensorflow_inference_java.jar`

refer to: https://github.com/tensorflow/tensorflow/tree/master/tensorflow/contrib/android

*`libtensorflow_inference.so` 

includes : tensorflow-inference-debug.aar and tensorflow-inference-release.aar;

refer to: https://github.com/tensorflow/tensorflow/tree/master/tensorflow/contrib/android/cmake

# (optinal) Step 2. Training Model and Frozen graph (Work done on Project AudioNet )

Using audioNet to foster a *.h5 file and frozen as *.pb file

# (optinal) Step 3. Put model file in android project (already)

put ‘asrModel.pb’ in assets folder

#  (optinal) Step 4. Run Tensorflow inference interface (already intergated into project)

# Step 5. Run Android Studio, open this project: androidAudioRecg

---build apk file,  Install apk file in your mobile phone. 

done!

# Reference

see Tensorflow : An open-source software library for Machine Intelligence. 

https://www.tensorflow.org
