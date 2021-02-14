import React from 'react'
import { View, Text,StyleSheet , Button} from 'react-native'
import * as tf from '@tensorflow/tfjs';
import '@tensorflow/tfjs-react-native';

import CameraFront from '../Components/CameraFront';


 class FaceEmotion extends React.Component {
   
 state = {
   isTfReady :false
 }

async componentDidMount() {
  await tf.ready()
  this.setState({
    isTfReady: true
  })
}


 

  render(){

    const {isTfReady} = this.state
   return(
    <>
        <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' ,alignSelf:"center",borderColor:"red"}}>
         <View style={{height:300,width:300,borderRadius:300/2,}}><CameraFront/></View>
        </View>
        <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center'}}>
        <Button title="watch my emotion"></Button>
        <Text style={styles.text}>
            TFJS ready? {isTfReady ? <Text>✅</Text> : ''}
          </Text>
        </View></>
   );
}
 
 }

 export default FaceEmotion;
