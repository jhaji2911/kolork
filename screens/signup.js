import * as React from 'react';
import { Button, View, Text } from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import User from '../components/User';

const Signup = ({navigation}) => {
  //...

  return (
    <View style={styles.container}>
    
      
      <User 
        buttonTitle="Sign Up with Google"
        btnType="google"
        color="#de4d41"
        backgroundColor="#f5e7ea"
        onPress={() => {}}
      />

     
    </View>
  );
};

export default Signup;

//...