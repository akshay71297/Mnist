package com.akwares.mnist;

import android.content.res.AssetManager;
import android.util.Log;

import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

/**
 * Created by ak on 22/02/18.
 */

public class TensorFlowClassifier {

    static {
        System.loadLibrary("tensorflow_inference");
    }

    private TensorFlowInferenceInterface inferenceInterface;

    private static final String MODEL_FILE = "file:///android_asset/frozen_mnist_convnet.pb";
    private static final String INPUT_NODE = "conv2d_1_input";
    private static final String[] OUTPUT_NODE = new String[]{"dense_2/Softmax"};
    private static final int nClasses = 10;

    public TensorFlowClassifier(AssetManager assetManager) {
        inferenceInterface = new TensorFlowInferenceInterface(assetManager, MODEL_FILE);
        Log.d("DEBUGGGGGGGGG", "model loaded successfully");

    }

    public static int argmax(float[] elems) {
        int bstIdx = 0;
        float bestV = elems[0];

        for(int i = 1; i < elems.length; i++){
            if(elems[i] > bestV){
                bestV = elems[i];
                bstIdx = i;
            }
        }

        return bstIdx;
    }

    public float[] recognize(float[] in){
        float[] result = new float[nClasses];

        inferenceInterface.feed(INPUT_NODE, in, 1, 28, 28, 1);
        inferenceInterface.run(OUTPUT_NODE);
        inferenceInterface.fetch(OUTPUT_NODE[0], result);

        return result;
    }


}
