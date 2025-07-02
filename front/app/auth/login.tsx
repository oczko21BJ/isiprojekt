import { useState, useEffect } from 'react';
import { View, StyleSheet, Alert, Platform } from 'react-native';
import { Text, TextInput, Button, Surface } from 'react-native-paper';
import { SafeAreaView } from 'react-native-safe-area-context';
import { useRouter, Link } from 'expo-router';
import { useAuth } from '@/contexts/AuthContext';
import axios from "axios";
import * as Google from "expo-auth-session/providers/google";
import * as AuthSession from 'expo-auth-session';
import AsyncStorage from '@react-native-async-storage/async-storage';

export default function LoginScreen() {
  const router = useRouter();
  const { login, updateAccessToken, user} = useAuth();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);
 // const [googleLoading, setGoogleLoading] = useState(false);
 

const redirectUri = AuthSession.makeRedirectUri({
  scheme: 'pharmacy',  // taki jak w app.json
});
console.log("Redirect URI:", redirectUri);

const [request, response, promptAsync] = Google.useAuthRequest({
  androidClientId: "254525692175-6oh5tf6ugbvfgdmid0fvlamu0068hb8t.apps.googleusercontent.com",
  webClientId: "182032682556-fglp9iq1dr51c1uavr7lls07fkmeeuou.apps.googleusercontent.com",
  clientId: "182032682556-fglp9iq1dr51c1uavr7lls07fkmeeuou.apps.googleusercontent.com",
  redirectUri,
  responseType: "id_token token",
  usePKCE: false, 
  scopes: ["openid", "profile", "email"],
  extraParams: {
    nonce: "random_nonce_12345", // ważne: nonce musi być unikalny i losowy, można wygenerować losowo
  },

});

  useEffect(() => {
  const authenticate = async () => {
    if (response?.type === "success" && response.authentication) {
      const { accessToken, idToken } = response.authentication;

      if (accessToken) {
        try {
          console.log(accessToken)
          const backendResponse = await axios.post("http://192.168.56.1:8080/api/users/authenticate/google", {
            id_token: idToken,
          });
           const { accessToken: backendJwt } = backendResponse.data;
          await AsyncStorage.setItem('@accessToken', backendJwt);
          updateAccessToken(backendJwt);
          
           //updateAccessToken(accessToken);

   //     // Pobieramy dane użytkownika z Google API
      const userInfoResponse = await fetch('https://www.googleapis.com/oauth2/v3/userinfo', {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      });

      if (!userInfoResponse.ok) {
        throw new Error('Failed to fetch user info from Google');
      }

      const userInfo = await userInfoResponse.json();
      console.log('User info from Google:', userInfo);

       Alert.alert("Zalogowano jako", userInfo.name || userInfo.email);

      
      await axios.post("http://192.168.56.1:8080/api/users/authenticate/firebase", {
        id_token: response.authentication.idToken,  //  idToken
        access_token: accessToken, 
      });
                  console.log(accessToken, response.authentication.idToken)                   // lub accessToken
                     
        } catch (error: any) {
          Alert.alert("Błąd logowania", error.message);
        }
      } else {
        Alert.alert("Błąd logowania", "Access token not received.");
      }
    }
  };
  authenticate();
}, [response]);

useEffect(() => {
  if (user) {
    console.log('Current user after login:', user);
            router.replace('/(tabs)');
  }
}, [user]);
  const handleLogin = async () => {
    if (!email.trim() || !password.trim()) {
      Alert.alert('Error', 'Please fill in all fields');
      return;
    }

    try {
      setLoading(true);
      await login(email, password);
      console.log('Access Token after login:', updateAccessToken);  // <-- tu dodaj log
      console.log('Response from backend:', response);  // <-- tu dodaj log
          const token = await AsyncStorage.getItem('@accessToken');
      console.log('AccessToken from AsyncStorage:', token);
      const allKeys = await AsyncStorage.getAllKeys();
const allData = await AsyncStorage.multiGet(allKeys);
console.log('Zawartość AsyncStorage:', allData);

      router.replace('/(tabs)');
  
    } catch (error) {
        console.error("Login Error: ", error); // Zaloguj szczegóły błędu
      Alert.alert('Login Failed', 'Invalid email or password');
    } finally {
      setLoading(false);
    }
  };

  return (
    <SafeAreaView style={styles.container}>
      <Surface style={styles.surface}>
        <Text variant="headlineMedium" style={styles.title}>
          Welcome Back
        </Text>
        <Text variant="bodyLarge" style={styles.subtitle}>
          Sign in to your account
        </Text>

        <TextInput
          label="Email"
          value={email}
          onChangeText={setEmail}
          mode="outlined"
          keyboardType="email-address"
          autoCapitalize="none"
          style={styles.input}
        />

        <TextInput
          label="Password"
          value={password}
          onChangeText={setPassword}
          mode="outlined"
          secureTextEntry
          style={styles.input}
        />
        {Platform.OS === 'web' && (

      <Button
      disabled={!request}
         mode="outlined"
        icon="google"
  onPress={() => promptAsync()}
         style={{ marginTop: 16 }
        }
        >
        Sign in with Google
        </Button>)}

        <Button
          mode="contained"
          onPress={handleLogin}
          loading={loading}
          disabled={loading}
          style={styles.button}
        >
          Sign In
        </Button>

        <View style={styles.footer}>
          <Text variant="bodyMedium">Don't have an account? </Text>
          <Link href="/auth/register" asChild>
            <Text variant="bodyMedium" style={styles.link}>
              Sign up
            </Text>
          </Link>
        </View>
      </Surface>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#f5f5f5',
    justifyContent: 'center',
    padding: 16,
  },
  surface: {
    padding: 24,
    borderRadius: 12,
    elevation: 4,
  },
  title: {
    textAlign: 'center',
    marginBottom: 8,
    fontWeight: 'bold',
  },
  subtitle: {
    textAlign: 'center',
    marginBottom: 32,
    opacity: 0.7,
  },
  input: {
    marginBottom: 16,
  },
  button: {
    marginTop: 16,
    paddingVertical: 8,
  },
  footer: {
    flexDirection: 'row',
    justifyContent: 'center',
    marginTop: 24,
  },
  link: {
    color: '#6200ee',
    fontWeight: 'bold',
  },
});