# Mnist_tf_android
A useful hello world introducing the whole CNN networks and tensorflow, including android integration. 

## Samples Mnist dataset images
![alt text](https://camo.githubusercontent.com/d440ac2eee1cb3ea33340a2c5f6f15a0878e9275/687474703a2f2f692e7974696d672e636f6d2f76692f3051493378675875422d512f687164656661756c742e6a7067)


# Directories & Files:
mnist_cnn/mnist_CNN.py ---> Example of training done using Tensorflow(1.4.1, ubuntu)

mnist_cnn/mnist_Keras.py ---> Example of training done using Tensorflow(1.4.1, ubuntu) as backend for Keras

mnist_cnn/out_k ---> directory containg output files from keras training, during android development I used the frozen_mnist_convnet.pb file. (opt_mnist_convnet.pb causes errors)

mnist_cnn/out_k/chk_graph.py ---> few lines to check pb file's integrity

/Android ---> containes Mnist, android studio project

# Tensorflow Details:
As native comunication between android studio code and Tensorflow model (pb file), it's necessary to include some already built libraries (.so files), it is possible to build them manually, however in this project I used some already compiled.
Including these files currently(25/02/2018) is compulsory as android devices need specific (based on device chipset) libraries to run and build tensorflow graphs. 

Under /Android/Mnist/app/libs there are all variants (x86/ARM/ARM64...), I used the latest (at this time) stable version #362 from http://ci.tensorflow.org/view/Nightly/job/nightly-android/

Some changes are required in the gradle, to compile libandroid_tensorflow_inference_java.jar, which provides an interface to the tensorflow model(required to feed input, run model, fetch output data)

# Notation & assumptions:
Using tensorflow as backend requires the input to be in format [width, height, depth], while if you use Theano is [depth, width, height].

In tensorflow (mnist_CNN.py) the notation is a little bit different [batch_size, w, h, d]; Batch size rappresents the number of inputs(images) used for each epoch(cycle(feedforward + backpropagation))

For keras I used AdaDelta as as optimizer, while for tensorflow version uses Adam optimizer

# Model Layers (mnist_CNN.py):
1) Input image shape = [-1, 28, 28, 1] ---> -1 stands for arbitrary number of images, and our images are in grayscale hence depth=1

2) Convolution: receptive field = 3 (filters = 32), strides = 1 [Padding = SAME ---> input shape 	will be maintained] 

3) RELU

4) Maxpool

5) Convolution: receptive field = 3 (filters = 62), strides = 1 [Padding = SAME]

6) RELU

7) Maxpool

8) Reshape the 7x7x64 tensor to an array of 7*7*64 elements

9) Fully Connected (Dense layer) --> neurons 1024

10) RELU

11) Softmax (cross_entroy)
