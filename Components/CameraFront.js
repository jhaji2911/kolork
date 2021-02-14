import React, { useState, useEffect } from 'react';
import { StyleSheet, Text, View, TouchableOpacity } from 'react-native';
import { Camera } from 'expo-camera';
import {useIsFocused} from '@react-navigation/native';
import {withNavigationFocus} from '@react-navigation/compat';

 function CameraFront() {
  const [hasPermission, setHasPermission] = useState(null);
  const [type, setType] = useState(Camera.Constants.Type.back);
  const isFocused = useIsFocused(); 
  useEffect(() => {
    (async () => {
      const { status } = await Camera.requestPermissionsAsync();
      setHasPermission(status === 'granted');
    })();
  }, []);

  if (hasPermission === null) {
    return <View />;
  }
  else if (hasPermission === false) {
    return <Text>No access to camera</Text>;
  }
  else if(isFocused){
      console.log('iski bhen ki lund');
      return  <View style={styles.container}>
      <Camera style={{height:300,width:300,borderRadius:300/2,}} type={type}>
        <View style={styles.buttonContainer}>
          <TouchableOpacity
            style={styles.button}
            onPress={() => {
              setType(
                type === Camera.Constants.Type.back
                  ? Camera.Constants.Type.front
                  : Camera.Constants.Type.back
              );
            }}>
            <Text style={styles.text}>Flip</Text>
          </TouchableOpacity>
        </View>
      </Camera>
    </View>
  }
else {
    return <View />;
}
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
   
  },
  buttonContainer: {
    flex: 1,
    backgroundColor: 'transparent',
    flexDirection: 'column',
    margin: 20,
    justifyContent:'flex-end'
  },
  button: {
    flex: 0.1,
    alignSelf: 'center',
    alignItems: 'flex-end',
  },
  text: {
    fontSize: 18,
    color: 'white',
  },
});

export default withNavigationFocus(CameraFront);