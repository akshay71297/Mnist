package com.akwares.mnist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import safety.com.br.android_shake_detector.core.ShakeCallback;
import safety.com.br.android_shake_detector.core.ShakeDetector;
import safety.com.br.android_shake_detector.core.ShakeOptions;

public class MainActivity extends AppCompatActivity {

    TensorFlowClassifier tensorFlowClassifier;
    Drawing myDrawingSpace;

    Button detect;
    Button showEx;
    private ShakeDetector shakeDetector;

    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDrawingSpace = (Drawing)findViewById(R.id.drawingSpace);

        myDrawingSpace.setDrawingCacheEnabled(true);

        tensorFlowClassifier = new TensorFlowClassifier(getAssets());

        showEx = findViewById(R.id.examples);
        detect = findViewById(R.id.detect);
        result = findViewById(R.id.textView2);

        ShakeOptions options = new ShakeOptions()
                .background(true)
                .interval(1000)
                .shakeCount(1)
                .sensibility(5.0f);

        this.shakeDetector = new ShakeDetector(options).start(this, new ShakeCallback() {
            @Override
            public void onShake() {
                myDrawingSpace.clear();
                myDrawingSpace.setDrawingCacheEnabled(false);
                myDrawingSpace.setDrawingCacheEnabled(true);
                result.setText("");
            }
        });

        showEx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, Examples.class);
                startActivity(myIntent);
            }
        });

        detect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap b;
                b = myDrawingSpace.getDrawingCache();
                b = Bitmap.createScaledBitmap(b, 28, 28, false);

                float[] inp = new float[784];
                int k=0;

                for (int i=0; i<b.getHeight(); i++){
                    for(int j=0; j<b.getWidth(); j++){
                        int colour = b.getPixel(j, i);

                        int red = Color.red(colour);
                        int blue = Color.blue(colour);
                        int green = Color.green(colour);


                        if(red != 0 || green != 0 || blue !=0){
                            inp[k] = 1.f;
                        } else {
                            inp[k] = 0.f;
                        }

                        k++;
                    }
                }


                float[] results = tensorFlowClassifier.recognize(inp);

                float max = -1;
                int idx = 0;

                for(int i=0; i<results.length; i++){
                    Log.d("TRRRRRRRRRRRRRRRR", ""+results[i]);
                    if(results[i] > max){
                        max = results[i];
                        idx = i;
                    }
                }

                result.setText(""+idx);
            }
        });





    }
}
