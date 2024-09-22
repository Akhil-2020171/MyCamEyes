package com.akhilsharma.USBCameraApp;

import android.content.Intent;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

// import com.akhilsharma.USBCameraApp.UsbCameraActivity;

public class UsbCameraModule extends ReactContextBaseJavaModule {

    private static ReactApplicationContext reactContext;
    
    // private static final String TAG = "UsbCameraModule";
    // private UsbCameraActivity usbCameraActivity;

    UsbCameraModule(ReactApplicationContext context) {
        super(context);
        reactContext = context;
        // usbCameraActivity = new UsbCameraActivity();
    }

    @Override
    public String getName() {
        return "UsbCameraModule";
    }

    @ReactMethod
    public void startUsbCameraActivity() {
        Intent intent = new Intent(reactContext, UsbCameraActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        reactContext.startActivity(intent);
    }

    @ReactMethod
    public void stopUsbCameraActivity() {
        Intent intent = new Intent(reactContext, UsbCameraActivity.class);
        reactContext.stopService(intent);
    }

    // @ReactMethod
    // public void startUsbCameraActivity(){
    //     usbCameraActivity.onStart();
    // }

    // @ReactMethod
    // public void stopUsbCameraActivity(){
    //     usbCameraActivity.onStop();
    // }

}
