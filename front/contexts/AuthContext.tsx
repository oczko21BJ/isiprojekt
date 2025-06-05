import React, { createContext, useContext, useState, useEffect } from 'react';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { useApi } from './ApiContext'; // popraw ścieżkę jeśli inna
import type { User } from '@/types';
import axios from "axios";
import { Alert, Platform } from 'react-native';

export interface AuthContextType {
  user: User | null;
  isAuthenticated: boolean;
  login: (email: string, password: string) => Promise<void>;
  register: (userData: any) => Promise<void>;
  logout: () => Promise<void>;
  updateUser: (userData: Partial<User>) => Promise<void>;
  accessToken: string | null;  // Nowy stan dla accessToken
  updateAccessToken: (token: string) => void;  // Funkcja do ustawiania tokenu

}

const AuthContext = createContext<AuthContextType | undefined>(undefined);


export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [user, setUser] = useState<User | null>(null);
  const { userService } = useApi();
  const [storedAccessToken, setStoredAccessToken] = useState<string | null>(null); // Stan dla accessToken
  const [accessToken, setAccessToken] = useState<string | null>(null);

  useEffect(() => {
    loadStoredUser();
  }, []);
useEffect(() => {
  if (typeof window !== 'undefined' && Platform.OS === 'web') {
    const hash = window.location.hash;

    if (hash.includes("id_token") && hash.includes("access_token")) {
      const params = new URLSearchParams(hash.replace('#', ''));
      const idToken = params.get("id_token");
      const accessToken = params.get("access_token");

      if (idToken && accessToken) {
        console.log("Znaleziono idToken:", idToken);
        console.log("Znaleziono accessToken:", accessToken);

        // Wyslij id_token do backendu do uwierzytelnienia
        axios.post("http://192.168.56.1:8080/api/users/authenticate/google", {
          id_token: idToken,
        })

        .then(async (res) => {
           console.log("Odpowiedź z backendu:", res.data);
          // Backend zwraca swój token JWT, zapisz go i ustaw w stanie
          const backendJwt = res.data.accessToken;
          const backendUser = res.data.user;
          
          await AsyncStorage.setItem("@accessToken", backendJwt);
          updateAccessToken(backendJwt);
           console.log("updateaccesstoken:", updateAccessToken);

            // Zapisz i ustaw usera w stanie i AsyncStorage
             if (backendUser) {
               await AsyncStorage.setItem("@user", JSON.stringify(backendUser));
               setUser(backendUser);
             }
  
          // Opcjonalnie mozna tez zapisac oryginalny access_token Google,
          // jeśli jest potrzebny do zapytań do Google API
          await AsyncStorage.setItem("@googleAccessToken", accessToken);

          // Czyscimy hash z URL, żeby nie trzymac tokenow w URL
          window.history.replaceState(null, '', window.location.pathname);

          //Alert.alert("Sukces", "Zalogowano przez Google!");
          
            if (Platform.OS === 'web') {
            window.alert("Sukces, Zalogowano przez Google!");
              if (window.opener) {
              window.opener.location.reload(); // Odswiez strone, ktora otworzyla popup
              }
            window.close();
               } else {
                   Alert.alert("Sukces", "Zalogowano przez Google!");
              }
        })
        .catch(err => {
          console.error("Błąd logowania przez Google:", err);
          Alert.alert("Błąd", "Nie udało się przetworzyć tokena.");
        });
      }
    }
  }
}, []);

  // Function to load the stored user data from AsyncStorage
  const loadStoredUser = async () => {
    try {
      const storedUser = await AsyncStorage.getItem('@user');
      if (storedUser) {
        const parsedUser = JSON.parse(storedUser);
        setUser(parsedUser);
        console.log('Loaded user from AsyncStorage:', parsedUser);
      }
      const storedToken = await AsyncStorage.getItem('@accessToken');
      if (storedToken) {
        setStoredAccessToken(storedToken);
        console.log('Loaded storedToken from AsyncStorage:', storedToken);

      }
    } catch (error) {
      console.error('Error loading stored user:', error);
    }
  };

  // Login function
  const login = async (email: string, password: string) => {
    try {
     const userData = await userService.authenticate(email, password);
      
      // Log the user data before saving to AsyncStorage
      console.log('User data after authentication:', userData);
      console.log('Response from backend:', Response);
      console.log('Response from backend:', storedAccessToken);
      if (userData) {
        const response = await axios.post('http://192.168.56.1:8080/api/users/authenticate', {
        email,
        password,
      });

      console.log('Response from backend:', response.data);
       const { accessToken, user } = response.data;
       await AsyncStorage.setItem('@accessToken', JSON.stringify(accessToken));
        await AsyncStorage.setItem('@user', JSON.stringify(userData));
        setUser(userData);
        updateAccessToken(accessToken);
      } else {
        console.log('Authentication failed, no user data received.');
      }
    } catch (error) {
      console.error('Login failed:', error);
      throw new Error('Login failed, please check your credentials');
    }
  };

  // Register function
  const register = async (userData: any) => {
    try {
      const newUser = await userService.create(userData);
      
      // Log the new user data before saving to AsyncStorage
      console.log('New user after registration:', newUser);

      if (newUser) {
        setUser(newUser);
        await AsyncStorage.setItem('@user', JSON.stringify(newUser));
      } else {
        console.log('Registration failed, no user data received.');
      }
    } catch (error) {
      console.error('Registration failed:', error);
      throw new Error('Registration failed, please try again');
    }
  };
  // Funkcja aktualizująca accessToken
  const updateAccessToken = (token: string) => {
    console.log("Ustawiam accessToken w stanie:", token);
    setStoredAccessToken(token); // Aktualizuje stan przechowujący token
    AsyncStorage.setItem('@accessToken', token);  // Zapisuje token w AsyncStorage
  };
  // Logout function
  const logout = async () => {
    try {
      setUser(null);
      setStoredAccessToken(null);
      await AsyncStorage.removeItem('@user');
      await AsyncStorage.removeItem('@accessToken');
      await AsyncStorage.removeItem('@googleAccessToken');
      await AsyncStorage.removeItem('@cart');
          delete axios.defaults.headers.common['Authorization'];

      console.log('User logged out and removed from AsyncStorage');
    } catch (error) {
      console.error('Error logging out:', error);
    }
  };

  // Update user function
  const updateUser = async (userData: Partial<User>) => {
    try {
      if (user) {
        const updatedUser = await userService.update(user.id, userData);

        // Log the updated user data before saving to AsyncStorage
        console.log('Updated user data:', updatedUser);

        if (updatedUser) {
          setUser(updatedUser);
          await AsyncStorage.setItem('@user', JSON.stringify(updatedUser));
        } else {
          console.log('Failed to update user.');
        }
      }
    } catch (error) {
      console.error('Error updating user:', error);
      throw new Error('Failed to update user');
    }
  };

  const value = {
    user,
    isAuthenticated: !!user,
    login,
    register,
    logout,
    updateUser,
    accessToken: storedAccessToken, // Przechowuje token w kontekscie
    updateAccessToken, // Udostepnia funkcje do ustawiania tokenu
  };

  return (
    <AuthContext.Provider value={value}>
      {children}
    </AuthContext.Provider>
  );
}
export const useAuth = () => {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error('useAuth must be used within AuthProvider');
  return ctx;
};

