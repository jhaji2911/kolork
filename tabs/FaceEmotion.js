import React, { Component } from 'react';
import { StyleSheet, Text, View } from 'react-native';
import { WebView } from 'react-native-webview';

// ...
class FaceEmotion extends Component {
  render() {
    
    return <WebView source={{ uri: 'https://emotionappbynishant.netlify.app/' }} />;
    
  }
}
export default FaceEmotion