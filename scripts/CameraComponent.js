import React, { useEffect } from 'react';
import { Button, View, NativeModules } from 'react-native';
import { requestPermissions } from './handlePermission';

const UVCCamera = NativeModules.UVCCameraModule;

const CameraComponent = () => {
  useEffect(() => {
    requestPermissions(true, UVCCamera, 'Camera Permission', 'We need camera permission to access the USB camera').then(isAuthorized => {
      if (isAuthorized) {
        UVCCamera.initialize().then(() => {
          console.log('Camera initialized');
        }).catch(error => {
          console.error(error);
        });
      } else {
        console.log('Camera permission denied');
      }
    }).catch(error => {
      console.error('Permission error:', error);
    });
  }, []);

  const startPreview = () => {
    UVCCamera.startPreview().then(() => {
      console.log('Preview started');
    }).catch(error => {
      console.error(error);
    });
  };

  const stopPreview = () => {
    UVCCamera.stopPreview().then(() => {
      console.log('Preview stopped');
    }).catch(error => {
      console.error(error);
    });
  };

  return (
    <View>
      <Button title="Start Preview" onPress={startPreview} />
      <Button title="Stop Preview" onPress={stopPreview} />
    </View>
  );
};

export default CameraComponent;
