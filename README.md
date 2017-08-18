# An Android app for speech recognition based on TensorFlow library. 

# Step 1. Software requirement

# Step 1.1 Install Android Studio 2.3 + 

Android Studio (Build tools API >= 23 is required to build the TF Android
demo (though it will run on API >= 21 devices).)

https://developer.android.com/studio/

# Step 1.2 Download TensorFlow inference library

https://ci.tensorflow.org/view/Nightly/job/nightly-android/

download two files:

*`libandroid_tensorflow_inference_java.jar`

refer to: https://github.com/tensorflow/tensorflow/tree/master/tensorflow/contrib/android

*`libtensorflow_inference.so` 

includes : tensorflow-inference-debug.aar and tensorflow-inference-release.aar;

refer to: https://github.com/tensorflow/tensorflow/tree/master/tensorflow/contrib/android/cmake

# Step 2. Training Model and Frozen graph

Using audioNet to foster a *.h5 file and frozen as *.pb file

# Step 3. Put model file in android project

put ‘asrModel.pb’ in assets folder

#  Step 4. Run Tensorflow inference interface

done!

# Reference

see Tensorflow : An open-source software library for Machine Intelligence. 

https://www.tensorflow.org
