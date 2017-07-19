# An Android application project for speech recognition based on TensorFlow  library. 

see Tensorflow : An open-source software library for Machine Intelligence. 

https://www.tensorflow.org

# requirement

Android Studio 2.3 + (Build tools API >= 23 is required to build the TF Android
demo (though it will run on API >= 21 devices).)

https://developer.android.com/studio/

# tensorflow inference library

https://ci.tensorflow.org/view/Nightly/job/nightly-android/

download files:

*libandroid_tensorflow_inference_java.jar

see  https://github.com/tensorflow/tensorflow/tree/master/tensorflow/contrib/android

*libtensorflow_inference.so includes : tensorflow-inference-debug.aar and tensorflow-inference-release.aar;

see https://github.com/tensorflow/tensorflow/tree/master/tensorflow/contrib/android/cmake

# Model  training
using audioNet to foster a *.h5 file and frozen as *.pb file

# frozen graph
put ‘asrModel.pb’ in assets folder


