import { initializeApp } from 'firebase/app'; // Nowy sposób importu Firebase
import { getAuth, GoogleAuthProvider } from 'firebase/auth';  // Zaktualizowane importy dla autentykacji

// Konfiguracja Firebase, używając danych z Firebase Console
const firebaseConfig = {
  apiKey: "AIzaSyBTd7raWBP-cMvbo8Ce2EH9pj_5d1zwSs4",  // API Key
  authDomain: "test-fd946.firebaseapp.com",         // authDomain
  projectId: "test-fd946",                         // projectId
  storageBucket: "test-fd946.appspot.com",          // storageBucket
  messagingSenderId: "254525692175",               // messagingSenderId
  appId: "1:254525692175:android:c9f4530d11524087ca067a", // appId (z Androida)
  measurementId: "G-XXXXXX",                        // measurementId (jeśli używasz Analytics)
};

// Inicjalizacja Firebase
const app = initializeApp(firebaseConfig);

// Uzyskanie instancji autentykacji
const auth = getAuth(app);

// Możliwość użycia GoogleAuthProvider do logowania przez Google
const googleAuthProvider = new GoogleAuthProvider();

export { auth, GoogleAuthProvider };
