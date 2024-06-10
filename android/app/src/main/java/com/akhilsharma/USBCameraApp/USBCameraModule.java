package com.akhilsharma.USBCameraApp;

import android.hardware.usb.UsbDevice;
import android.os.Handler;
import android.util.Log;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.serenegiant.usb.USBMonitor;
import com.serenegiant.usb.UVCCamera;
import com.serenegiant.usb.DeviceFilter;

import java.util.List;

public class USBCameraModule extends ReactContextBaseJavaModule {
    private static final String TAG = "USBCameraModule";
    private USBMonitor mUSBMonitor;
    private UVCCamera mUVCCamera;
    private List<UsbDevice> list_devices;

    public USBCameraModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mUSBMonitor = new USBMonitor(reactContext, mOnDeviceConnectListener);
    }

    @Override
    public String getName() {
        return TAG;
    }

    @ReactMethod
    public void initialize(Promise promise) {
        mUSBMonitor.register();
        updateDevices();
        promise.resolve(null);
    }

    @ReactMethod
    public void startPreview(Promise promise) {
        if (mUVCCamera != null) {
            mUVCCamera.startPreview();
            promise.resolve(null);
        } else {
            promise.reject("USBCamera not initialized");
        }
    }

    @ReactMethod
    public void stopPreview(Promise promise) {
        if (mUVCCamera != null) {
            mUVCCamera.stopPreview();
            promise.resolve(null);
        } else {
            promise.reject("USBCamera not initialized");
        }
    }

    @ReactMethod
    public void requestPermission(Promise promise) {
        if (list_devices != null && list_devices.size() > 0) {
            UsbDevice usbDevice = list_devices.get(0);
            new Handler().postDelayed(() -> {
                if (usbDevice instanceof UsbDevice) {
                    mUSBMonitor.requestPermission(usbDevice);
                    promise.resolve(null);
                }
            }, 300);
        } else {
            promise.reject("No USB devices found");
        }
    }

    @ReactMethod
    public void checkPermissions(Promise promise) {
        if (ContextCompat.checkSelfPermission(getCurrentActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getCurrentActivity(), new String[]{Manifest.permission.CAMERA}, 1);
        } else {
            promise.resolve(true);
        }
    }

    private void updateDevices() {
        final List<DeviceFilter> filter = DeviceFilter.getDeviceFilters(getReactApplicationContext(), com.serenegiant.uvccamera.R.xml.device_filter);
        list_devices = mUSBMonitor.getDeviceList(filter.get(0));
    }

    private final USBMonitor.OnDeviceConnectListener mOnDeviceConnectListener = new USBMonitor.OnDeviceConnectListener() {
        @Override
        public void onAttach(final UsbDevice device) {
            Log.d(TAG, "USB_DEVICE_ATTACHED");
        }

        @Override
        public void onConnect(final UsbDevice device, final USBMonitor.UsbControlBlock ctrlBlock, final boolean createNew) {
            if (mUVCCamera != null) {
                mUVCCamera.destroy();
            }
            mUVCCamera = new UVCCamera();
            mUVCCamera.open(ctrlBlock);
        }

        @Override
        public void onDisconnect(final UsbDevice device, final USBMonitor.UsbControlBlock ctrlBlock) {
            if (mUVCCamera != null) {
                mUVCCamera.close();
            }
        }

        @Override
        public void onDettach(final UsbDevice device) {
            Log.d(TAG, "USB_DEVICE_DETACHED");
        }

        @Override
        public void onCancel(final UsbDevice device) {
            Log.d(TAG, "USB_DEVICE_CANCELLED");
        }
    };
}
