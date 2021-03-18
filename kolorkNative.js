import React from 'react'
import { View, Text,  NativeModules,Image} from 'react-native';
import {  NavigationContainer } from '@react-navigation/native';
//import * as SplashScreen from 'expo-splash-screen';
import { createMaterialBottomTabNavigator } from '@react-navigation/material-bottom-tabs';
import 
  MaterialCommunityIcons
  from 'react-native-vector-icons/MaterialCommunityIcons';
//import LottieView from 'lottie-react-native';
import FaceEmotion  from './tabs/FaceEmotion';
import { Avatar, Button, Card, Title, Paragraph } from 'react-native-paper';
import User from './tabs/User';
// import {App} from './App';
const {NavigationModule} = NativeModules;
console.log(NavigationModule);
const LeftContent = props => <Avatar.Icon {...props} icon="invert-colors" />
function colorDetect() {

return(
<Card style={{paddingHorizontal:20,paddingVertical:10}}>
  <Card.Title title="Welcome to kolork" subtitle="Detect Colors and Listen their names!" left={LeftContent} />
  <Card.Content>
    <Title>Kolork</Title>
    <Paragraph>Start Detecting</Paragraph>
  </Card.Content>
  <Card.Cover   style={{height:400}} source={ require('./assets/main-theme.jpg')} />
  <Paragraph>By Clicking on the Detect Button you will go in the world of Color detection </Paragraph>
  <Card.Actions>
   <Button style={{elevation:50}}  onPress={()=> NavigationModule.navigateToNative()} > Detect</Button>
  </Card.Actions>
</Card>);
  }


  

  const Tab = createMaterialBottomTabNavigator();

function MyTabs() {
  return (
    <NavigationContainer>
    <Tab.Navigator
      initialRouteName="ColorDetect"
      activeColor="#93ff73"
      labelStyle={{ fontSize: 12 }}
      style={{ backgroundColor: 'green' }}
    >
      <Tab.Screen
        name="Color Detection"
        component={colorDetect}
        options={{
          tabBarLabel: 'Kolork',
          tabBarIcon: ({ color }) => (
            <MaterialCommunityIcons name="format-color-fill" color={color} size={26} />
          ),
        }}
      />
      <Tab.Screen
        name="Emotions"
        component= {FaceEmotion}
        options={{
          tabBarLabel: 'Emotions',
          tabBarIcon: ({ color }) => (
            <MaterialCommunityIcons name="face-recognition" color={color} size={26} />
          ),
        }}
      />
      <Tab.Screen
        name="User"
        component={User}
        options={{
          tabBarLabel: 'Login',
          tabBarIcon: ({ color }) => (
            <MaterialCommunityIcons name="account-circle" color={color} size={26} />
          ),
        }}
    />
    </Tab.Navigator></NavigationContainer>
  );
}

export default class kolorkNative extends React.Component {
  // state = {
  //   isReady: false,
  // };

  // componentDidMount() {
  //   SplashScreen.preventAutoHideAsync();
  // }

  render() 
  {
  //   if (!this.state.isReady) {
  //   return (
  //     <View style={{ flex: 1 }}>
  //       <Image
  //         source={require('./assets/Kolork.gif')}
  //         onLoad={this._cacheResourcesAsync}
  //       />
  //     </View>
  //   );
  // }
    return( 
    <MyTabs/>
    )
  }

  // _cacheSplashResourcesAsync = async () => {
  //   const gif = require('./assets/Kolork.gif');
  //   return Asset.fromModule(gif).downloadAsync();
  // };




}
















