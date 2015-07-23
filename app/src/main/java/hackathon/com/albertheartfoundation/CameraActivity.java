package hackathon.com.albertheartfoundation;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import java.io.IOException;


public class CameraActivity extends Activity implements SurfaceHolder.Callback {

    SurfaceView mSurfaceView;
    SurfaceHolder mSurfaceHolder;
    Camera mCamera;
    boolean mPreviewRunning;

    boolean tookPicture = false;
    Button captureButton;
    Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        mSurfaceView = (SurfaceView) findViewById(R.id.surface_camera);

        mSurfaceHolder = mSurfaceView.getHolder();

        mSurfaceHolder.addCallback(this);

        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        captureButton = (Button) findViewById(R.id.btnCapture);
        cancelButton = (Button) findViewById(R.id.btnClear);

        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!tookPicture) {
                    captureButton.setText("Confirm!");
                    mCamera.takePicture(null, null, mPictureCallback);
                } else {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    //i.putExtra("img", backgroundBitmap);
                    startActivity(i);
                    finish();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.stopPreview();
                mPreviewRunning = false;
                mCamera.release();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_signup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mCamera = Camera.open();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {

        if (mPreviewRunning) {

            mCamera.stopPreview();

        }

        Camera.Parameters p = mCamera.getParameters();

        p.setPreviewSize(w, h);

        mCamera.setParameters(p);

        try {

            mCamera.setPreviewDisplay(holder);

        } catch (IOException e) {

            e.printStackTrace();

        }

        mCamera.startPreview();

        mPreviewRunning = true;

    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.stopPreview();
        mPreviewRunning = false;
        mCamera.release();
    }

    Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] imageData, Camera c) {
            Bitmap backgroundBitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);

            if (!tookPicture) {
                captureButton.setText("Confirm!");
            } else {
                Intent i = new Intent(getApplicationContext(), Share.class);
                //i.putExtra("img", backgroundBitmap);
                startActivity(i);
                finish();
            }


        }
    };
}
