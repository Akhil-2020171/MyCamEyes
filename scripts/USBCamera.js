import { NativeModules } from 'react-native';

const { USBCameraModule } = NativeModules;

const UsbCamera = {
  startUsbCameraActivity: () => {
    return USBCameraModule.startUsbCameraActivity();
  },

  stopUsbCameraActivity: () => {
    return USBCameraModule.stopUsbCameraActivity();
  }
};

export default UsbCamera;