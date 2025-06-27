import React, { createContext, useContext, useEffect, useState } from 'react';
import { ReactNode } from 'react';
import OrderType from '@/app/checkout';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { ApiService } from '../lib/api'; 

type Props = {
  children: ReactNode;
};

export const ApiContext = createContext<ApiService | null>(null);

export const ApiProvider = ({ children }: Props) => {
  // Przykładowa funkcja tworzenia zamówienia z tokenem Google
  const getToken = async () => {
    return await AsyncStorage.getItem('@accessToken');
  };
  
  const apiService = new ApiService(getToken);

  return (
     <ApiContext.Provider value={apiService}>
      {children}
    </ApiContext.Provider>
  );
};

export const useApi = () => {
  const context = useContext(ApiContext);
  if (!context) {
    throw new Error('useApi must be used within an ApiProvider');
  }
  return context;
};

