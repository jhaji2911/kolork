import * as React from 'react';
import { Button, View, Text } from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import User from '../tabs/User';
import { AuthContext } from '../navigation/AuthProvider';

const Login = ({navigation}) => {
  //...
  const {login, googleLogin} = useContext(AuthContext);
  return (
    <View style={styles.container}>
    
      
      <User
        buttonTitle="Sign In with Google"
        btnType="google"
        color="#de4d41"
        backgroundColor="#f5e7ea"
        onPress={() =>  googleLogin()}
      />

    
    </View>
  );
};

export default Login;

