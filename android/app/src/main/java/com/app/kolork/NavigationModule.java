package com.app.kolork;

import android.content.Intent;

import androidx.annotation.NonNull;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import com.facebook.react.bridge.ReactContext;
import java.util.Map;
import java.util.HashMap;


public class NavigationModule  extends ReactContextBaseJavaModule {
    private static ReactApplicationContext reactContext;
    private Intent intent;

    NavigationModule(ReactApplicationContext context) {
        super(context);
        reactContext = context;
    }

    @NonNull
    @Override
    public String getName() {
        return "NavigationModule";
    } //The name of the component when it is called in the RN code

    @ReactMethod
    public void navigateToNative(){
        ReactApplicationContext context = getReactApplicationContext();
        intent = new Intent(context,Dashboard.class);

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            intent.setFlags((Intent.FLAG_ACTIVITY_NEW_TASK));
            context.startActivity(intent);
        }
    }
}