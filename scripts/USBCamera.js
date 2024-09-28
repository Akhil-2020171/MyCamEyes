import { NativeModules } from 'react-native';

const { UsbCameraModule } = NativeModules;

const UsbCamera = {
  startUsbCameraActivity: () => {
    return UsbCameraModule.startUsbCameraActivity();
  },

  stopUsbCameraActivity: () => {
    return UsbCameraModule.stopUsbCameraActivity();
  }
};

export default UsbCamera;