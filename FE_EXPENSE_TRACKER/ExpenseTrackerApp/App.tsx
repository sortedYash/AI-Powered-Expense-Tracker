import React from "react";
import { Text } from "react-native";
import { SafeAreaView , SafeAreaProvider } from "react-native-safe-area-context";
import {NavigationContainer} from '@react-navigation/native';
import {createNativeStackNavigator} from '@react-navigation/native-stack';
import { GluestackUIProvider } from '@gluestack-ui/themed';
import Login from './src/app/pages/Login';
import SignUp from "./src/app/pages/SignUp";

const Stack = createNativeStackNavigator();
function App(): React .JSX.Element{
  return (
     <SafeAreaProvider>
        <NavigationContainer>
          <Stack.Navigator>
            <Stack.Screen name="Login" component={Login} />
            <Stack.Screen name="SignUp" component={SignUp} />
            </Stack.Navigator>
        </NavigationContainer>
      </SafeAreaProvider>
  );
}

export default App;