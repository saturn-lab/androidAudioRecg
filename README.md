An Android application for speech recogniztion in ITIS and BDMI. 

# requirement

Android Studio 1.3 + (Build tools API >= 23 is required to build the TF Android
demo (though it will run on API >= 21 devices).)

# tensorflow inference library

https://ci.tensorflow.org/view/Nightly/job/nightly-android/

download files:

libandroid_tensorflow_inference_java.jar

tensorflow.aar : 

libandroid_tensorflow_lib.lo
libtensorflow_inference.so

# Model  training
using audioNet to foster a *.h5 file and frozen as *.pb file

# frozen graph
put ‘asrModel.pb’ in assets folder


