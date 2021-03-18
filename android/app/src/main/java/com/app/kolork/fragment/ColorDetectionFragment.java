package com.app.kolork.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.app.kolork.Dashboard;
import com.app.kolork.R;
import com.app.kolork.databinding.FragmentColorDetectionBinding;
import com.app.kolork.util.CameraColorPickerPreview;
import com.app.kolork.util.Cameras;
import com.app.kolork.util.ColorNameBuilder;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

public class ColorDetectionFragment extends Fragment implements
        CameraColorPickerPreview.OnColorSelectedListener, View.OnClickListener{

    private static String TAG="ApkZube";
    private static Context ctx;

    private FragmentColorDetectionBinding mBinding;
    private Camera mCamera;
    private boolean mIsPortrait;
    private CameraColorPickerPreview mCameraPreview;
    private CameraAsyncTask mCameraAsyncTask;
    private int mSelectedColor;
    private String action = null;
    private FrameLayout mPreviewContainer;

    public ColorDetectionFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new ColorDetectionFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mBinding = DataBindingUtil.
                inflate(inflater, R.layout.fragment_color_detection, container, false);

        Intent intent = getActivity().getIntent();
        if (intent != null)
            action = intent.getAction();

        allocation();
        setEvent();

        return mBinding.getRoot();
    }

    private void allocation() {
        ctx=getContext();
        mIsPortrait = getResources().getBoolean(R.bool.is_portrait);
        mPreviewContainer=mBinding.activityColorPickerPreviewContainer;
    }

    private void setEvent() {

    }

    private static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return c;
    }


    @Override
    public void onClick(View view) {
        if (view == mCameraPreview) {
            animatePickedColor(mSelectedColor);
        }
    }



    private void animatePickedColor(int pickedColor) {

        final String colorHex = Integer.toHexString(pickedColor);

        mBinding.txtHexCode.setBackgroundColor(pickedColor);


        try {

            String hexcode = "#" + colorHex.substring(2);
            ColorNameBuilder colorNameBuilder=new ColorNameBuilder();
            String colorName=colorNameBuilder.getColorName(
                    colorNameBuilder.initColor(getActivity().getAssets().open("ntc.json")),hexcode);

            Dashboard.speechNow(colorName);
            mBinding.txtColorName.setText(colorName+" : "+hexcode);

            Log.d("BlindVision", "setEvent: "+colorName);
        } catch (JSONException e) {
            Log.d("BlindVision", "setEvent: "+e.getMessage());
        } catch (IOException e) {
            Log.d("BlindVision", "setEvent: "+e.getMessage());
        }

        //Dashboard.speechNow(setColorName(colorHex, txtColor));
        //Toast.makeText(ctx, "Color : "+pickedColor+" : "+colorHex , Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onColorSelected(int color) {

        mSelectedColor = color;
        mBinding.activityColorPickerPointerRing.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }


    @Override
    public void onResume() {
        super.onResume();

        // Setup the camera asynchronously.
        mCameraAsyncTask = new CameraAsyncTask();
        mCameraAsyncTask.execute();
    }

    @Override
    public void onPause() {
        super.onPause();

        // Cancel the Camera AsyncTask.
        mCameraAsyncTask.cancel(true);

        // Release the camera.
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }

        // Remove the camera preview
        if (mCameraPreview != null) {
            mPreviewContainer.removeView(mCameraPreview);
        }
    }


    // class CameraAsyncTask
    private class CameraAsyncTask extends AsyncTask<Void, Void, Camera> {

        /**
         * The {@link ViewGroup.LayoutParams} used for adding the preview to its container.
         */
        protected FrameLayout.LayoutParams mPreviewParams;

        @Override
        protected Camera doInBackground(Void... params) {
            Camera camera = getCameraInstance();
            if (camera == null) {
                getActivity().finish();
            } else {

                //configure Camera parameters
                Camera.Parameters cameraParameters = camera.getParameters();

                //get optimal camera preview size according to the layout used to display it
                Camera.Size bestSize = Cameras.getBestPreviewSize(
                        cameraParameters.getSupportedPreviewSizes()
                        , mPreviewContainer.getWidth()
                        , mPreviewContainer.getHeight()
                        , mIsPortrait);
                //set optimal camera preview
                cameraParameters.setPreviewSize(bestSize.width, bestSize.height);
                camera.setParameters(cameraParameters);

                //set camera orientation to match with current device orientation
                Cameras.setCameraDisplayOrientation(ctx, camera);

                //get proportional dimension for the layout used to display preview according to the preview size used
                int[] adaptedDimension = Cameras.getProportionalDimension(
                        bestSize
                        , mPreviewContainer.getWidth()
                        , mPreviewContainer.getHeight()
                        , mIsPortrait);

                //set up params for the layout used to display the preview
                mPreviewParams = new FrameLayout.LayoutParams(adaptedDimension[0], adaptedDimension[1]);
                mPreviewParams.gravity = Gravity.CENTER;
            }
            return camera;
        }

        @Override
        protected void onPostExecute(Camera camera) {
            super.onPostExecute(camera);

            // Check if the task is cancelled before trying to use the camera.
            if (!isCancelled()) {
                mCamera = camera;
                if (mCamera == null) {
                    getActivity().finish();
                } else {
                    //set up camera preview
                    mCameraPreview = new CameraColorPickerPreview(ctx, mCamera);
                    mCameraPreview.setOnColorSelectedListener(ColorDetectionFragment.this);
                    mCameraPreview.setOnClickListener(ColorDetectionFragment.this);

                    //add camera preview
                    mPreviewContainer.addView(mCameraPreview, 0, mPreviewParams);
                }
            }
        }

        public void injectScriptFile(WebView view, String scriptFile) {
            InputStream input;
            scriptFile = "file:///android_asset/ntc.js";
            try {
                input = getActivity().getAssets().open(scriptFile);
                byte[] buffer = new byte[input.available()];
                input.read(buffer);
                input.close();

                // String-ify the script byte-array using BASE64 encoding !!!
                String encoded = Base64.encodeToString(buffer, Base64.NO_WRAP);
                view.loadUrl("javascript:(function() {" +
                        "var parent = document.getElementsByTagName('head').item(0);" +
                        "var script = document.createElement('script');" +
                        "script.type = 'text/javascript';" +
                        // Tell the browser to BASE64-decode the string into your script !!!
                        "script.innerHTML = window.atob('" + encoded + "');" +
                        "parent.appendChild(script)" +
                        "})()");
            } catch (IOException e) {
                //TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        @Override
        protected void onCancelled(Camera camera) {
            super.onCancelled(camera);
            if (camera != null) {
                camera.release();
            }
        }
    }

}
