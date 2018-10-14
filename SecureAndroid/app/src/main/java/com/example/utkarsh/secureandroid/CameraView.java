package com.example.utkarsh.secureandroid;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.hardware.Camera;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by utkarsh on 28/5/18.
 */

public class CameraView extends Activity implements SurfaceHolder.Callback,View.OnClickListener {
    Camera mCamera;
    private static final String TAG = "CameraTest";
    private static final String WAKELOCK_TAG = "com.example.utkarsh.selfieintruder.CameraView";
    private static final long WAKELOCK_TIME_OUT = 10 * 1000;
    private PowerManager.WakeLock mWakeLock;

    boolean mPreviewRunning = false;
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Log.e(TAG, "onCreate");

        setContentView(R.layout.activity_camera_view);

        mSurfaceView = (SurfaceView) findViewById(R.id.surface_camera);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);

        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mSurfaceHolder.setKeepScreenOn(true);

        // mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    protected void onStart() {
        super.onStart();

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, WAKELOCK_TAG);
        mWakeLock.acquire(WAKELOCK_TIME_OUT);
    }

    protected void onResume() {
        Log.e(TAG, "onResume");
        super.onResume();
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    protected void onStop() {
        Log.e(TAG, "onStop");
        mWakeLock.release();
        super.onStop();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        Log.e(TAG, "surfaceChanged");
        if (mPreviewRunning) {
            mCamera.stopPreview();
        }

        Camera.Parameters p = mCamera.getParameters();
        mCamera.setParameters(p);
        mCamera.startPreview();
        mPreviewRunning = true;
        mCamera.takePicture(null, null, mPictureCallback);


    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.e(TAG, "surfaceDestroyed");
        stopCamera();
    }

    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;

    public void onClick(View v) {
        mCamera.takePicture(null, mPictureCallback, mPictureCallback);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        Log.e(TAG, "surfaceCreated");

        int i = findFrontFacingCamera();

        if (i > 0);
        while (true) {
            try {
                this.mCamera = Camera.open(i);
                try {
                    this.mCamera.setPreviewDisplay(holder);
                    return;
                } catch (IOException localIOException2) {
                    stopCamera();
                    return;
                }
            } catch (RuntimeException localRuntimeException) {
                localRuntimeException.printStackTrace();
                if (this.mCamera == null)
                    continue;
                stopCamera();
                this.mCamera = Camera.open(i);
                try {
                    this.mCamera.setPreviewDisplay(holder);
                    Log.d("HiddenEye Plus", "Camera open RE");
                    return;
                } catch (IOException localIOException1) {
                    stopCamera();
                    localIOException1.printStackTrace();
                    return;
                }

            } catch (Exception localException) {
                if (this.mCamera != null)
                    stopCamera();
                localException.printStackTrace();
                return;
            }
        }
    }

    private void stopCamera() {
        if (this.mCamera != null) {

            this.mPreviewRunning = false;
        }
    }

    private int findFrontFacingCamera() {
        int i = Camera.getNumberOfCameras();
        for (int j = 0;; j++) {
            if (j >= i)
                return -1;
            Camera.CameraInfo localCameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(j, localCameraInfo);
            if (localCameraInfo.facing == 1)
                return j;
        }
    }

    Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {

        public void onPictureTaken(byte[] data, Camera camera) {
            if (data != null) {

                mCamera.stopPreview();
                mPreviewRunning = false;
                mCamera.release();

                try {
                    BitmapFactory.Options opts = new BitmapFactory.Options();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0,
                            data.length, opts);
                    bitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, false);
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    int newWidth = 300;
                    int newHeight = 300;


                    float scaleWidth = ((float) newWidth) / width;
                    float scaleHeight = ((float) newHeight) / height;


                    Matrix matrix = new Matrix();
                    matrix.postScale(scaleWidth, scaleHeight);
                    matrix.postRotate(-90);
                    Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                            width, height, matrix, true);

                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 40,
                            bytes);

                    //**********This Part is to save the file******

                    Calendar cal = Calendar.getInstance();
                    File f = new File(Environment.getExternalStorageDirectory() ,(cal.getTimeInMillis()+".jpg"));
                    f.createNewFile();
                    FileOutputStream fo = new FileOutputStream(f);
                    fo.write(bytes.toByteArray());
                    fo.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                setResult(585);
                finish();
            }
        }
    };}
