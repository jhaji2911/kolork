import React from 'react'
import { View, Text,Button , NativeModules} from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createMaterialBottomTabNavigator } from '@react-navigation/material-bottom-tabs';
import 
  MaterialCommunityIcons
  from 'react-native-vector-icons/MaterialCommunityIcons';
 
import FaceEmotion  from './tabs/FaceEmotion';
// import {App} from './App';
const {NavigationModule} = NativeModules;
console.log(NavigationModule);

function colorDetect() {

return(<View  style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
  <Text>Go to the Native View</Text>
  <Button title="Color Detect" onPress={()=> NavigationModule.navigateToNative()}/>
  </View>);
  }

  
  function MyColors() {
    return (
      <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
        <Text>Saved Colors  </Text>
      </View>
    );
  }
  

  const Tab = createMaterialBottomTabNavigator();

function MyTabs() {
  return (
    <NavigationContainer>
    <Tab.Navigator
      initialRouteName="ColorDetect"
      activeColor="#42c7ae"
      labelStyle={{ fontSize: 12 }}
      style={{ backgroundColor: 'green' }}
    >
      <Tab.Screen
        name="Color Detection"
        component={colorDetect}
        options={{
          tabBarLabel: 'Home',
          tabBarIcon: ({ color }) => (
            <MaterialCommunityIcons name="home" color={color} size={26} />
          ),
        }}
      />
      <Tab.Screen
        name="Emotions"
        component= {FaceEmotion}
        options={{
          tabBarLabel: 'Emotions',
          tabBarIcon: ({ color }) => (
            <MaterialCommunityIcons name="bell" color={color} size={26} />
          ),
        }}
      />
      <Tab.Screen
        name="MyColors"
        component={MyColors}
        options={{
          tabBarLabel: 'MyColors',
          tabBarIcon: ({ color }) => (
            <MaterialCommunityIcons name="account" color={color} size={26} />
          ),
        }}
    />
    </Tab.Navigator></NavigationContainer>
  );
}

export default function kolorkNative() {
  return(
    <MyTabs/>
  );
}
















