import React, { useState, useEffect } from 'react';
import { View, Text } from 'react-native';
import { Avatar, Button, Card, Title, Paragraph, Colors } from 'react-native-paper';
import auth from '@react-native-firebase/auth';
import { GoogleSignin } from '@react-native-google-signin/google-signin';

const LeftContent = props => <Avatar.Icon {...props} icon="account" />

function User() {
  // Set an initializing state whilst Firebase connects
  const [initializing, setInitializing] = useState(true);
  const [user, setUser] = useState();
  GoogleSignin.configure({
    webClientId: '662355918769-gdfpgjd4mvip8gj853vdkvg10cqo4rrb.apps.googleusercontent.com',
  });

  async function onGoogleButtonPress() {
    // Get the users ID token
    const { idToken } = await GoogleSignin.signIn();

    // Create a Google credential with the token
    const googleCredential = auth.GoogleAuthProvider.credential(idToken);

    // Sign-in the user with the credential
    return auth().signInWithCredential(googleCredential);
  }
  const Logout = () => {
    auth()
      .signOut()
      .then(() => console.log('User signed out!'));
  }

  // Handle user state changes
  function onAuthStateChanged(user) {
    setUser(user);
    if (initializing) setInitializing(false);
  }
  useEffect(() => {
    const subscriber = auth().onAuthStateChanged(onAuthStateChanged);
    return subscriber; // unsubscribe on unmount
  }, []);

  if (initializing) return null;




  return (
    <View >
    <Card style={ {paddingHorizontal:20,paddingVertical:10}}>
    <Card.Title title="Your Kolork"  subtitle="SignIn and recieve future updates" left={LeftContent} />
    <Card.Content>
      {user ? <Title>welcome 😁 {user.email} </Title> : <Text>SignIn for best experience</Text>}
    </Card.Content>
    <Card.Cover style={{height:380}} source={ require('../assets/login.jpg')} />
    <Paragraph>Dear user this is the start of our journey , this app will  be getting many more updates and for being a part of such incredile transition please signIn</Paragraph>
    <Card.Actions>
    {!user ?
         <Button 
         icon="google"
         color={Colors.green900}
         size={20}
         onPress={() => onGoogleButtonPress()
        .then(() => console.log('Signed in with Google!'))}>SignIn with Google</Button>
        :
        <>
        <Button icon="logout" size={10}  style={{paddingTop:10}} color={Colors.red900} onPress={Logout}>Logout </Button>
          </> }
    </Card.Actions>
  </Card>

     
    </View>
  )
}
export default User;