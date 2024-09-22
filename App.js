import React from 'react';
import { Button, View } from 'react-native';
import UsbCamera from './scripts/USBCamera';  // Correct the import path if necessary

export default function App() {
  const startUsbCameraActivity = () => {
    UsbCamera.startUsbCameraActivity();
  };

  const stopUsbCameraActivity = () => {
    UsbCamera.stopUsbCameraActivity();
  };

  return (
    <View>
      <Button title="Start USB Camera Activity" onPress={startUsbCameraActivity} />
      <Button title="Stop USB Camera Activity" onPress={stopUsbCameraActivity} />
    </View>
  );
}