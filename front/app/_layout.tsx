import { useEffect, useState } from 'react';
import { Platform } from 'react-native';
import { Stack } from 'expo-router';
import { StatusBar } from 'expo-status-bar';
import { PaperProvider } from 'react-native-paper';
import { AuthProvider } from '@/contexts/AuthContext';
import { CartProvider } from '@/contexts/CartContext';
import { ApiProvider } from '@/contexts/ApiContext';
import { theme } from '@/theme/theme';
import { useFrameworkReady } from '@/hooks/useFrameworkReady';
import React, { ReactNode, ReactElement } from 'react';
import { Elements } from '@stripe/react-stripe-js';

type Props = {
  children: ReactNode;
};

const STRIPE_PUBLIC_KEY = 'pk_test_51RecBHRxJWaeWLFMo5GD1XUUpnxtfqetfh9DI0l1J0fkBxGkYTDMJrOCq1lpKTnsX9lr2MoaVR1cUzGkTmqgrhvR00veLsJCAm';  // Zamień na swoją publiczną klucz Stripe
export default function RootLayout({ children }: Props) {
  const [StripeWrapper, setStripeWrapper] = useState<React.FC<{ children: ReactNode }> | null>(null);
  const [stripePromise, setStripePromise] = useState<any>(null);

  useFrameworkReady();

  useEffect(() => {
    if (Platform.OS === 'web') {
      import('@stripe/stripe-js').then(({ loadStripe }) => {
        const stripe = loadStripe(STRIPE_PUBLIC_KEY);
        setStripePromise(stripe);
        setStripeWrapper(() => ({ children }: { children: ReactNode }) => (
          <Elements stripe={stripe}>{children}</Elements>
        ));
      });
    } else {
      import('@stripe/stripe-react-native').then(({ StripeProvider }) => {
        setStripeWrapper(() => ({ children }: { children: ReactNode }) => (
          <StripeProvider publishableKey={STRIPE_PUBLIC_KEY}>
            {children as ReactElement | ReactElement[]}
          </StripeProvider>
        ));
      });
    }
  }, []);

  if (!StripeWrapper) {
    return null; // lub spinner/loading
  }

  return (
    <PaperProvider theme={theme}>
      <ApiProvider>
        <AuthProvider>
          <CartProvider>
            <StripeWrapper>
            <Stack screenOptions={{ headerShown: false }}>
              <Stack.Screen name='(tabs)/index.tsx' />
            </Stack>
            </StripeWrapper>
            <StatusBar style="auto" />
          </CartProvider>
        </AuthProvider>
      </ApiProvider>
    </PaperProvider>
  );
}