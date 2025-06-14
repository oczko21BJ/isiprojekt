import { MD3LightTheme as DefaultTheme } from 'react-native-paper';

export const theme = {
  ...DefaultTheme,
  colors: {
    ...DefaultTheme.colors,
    primary: '#6200ee',
    primaryContainer: '#bb86fc',
    secondary: '#03dac6',
    secondaryContainer: '#018786',
    tertiary: '#ff6b6b',
    error: '#b00020',
    errorContainer: '#fcd7d7',
    surface: '#ffffff',
    surfaceVariant: '#f5f5f5',
    background: '#fafafa',
    onBackground: '#1c1b1f',
    onSurface: '#1c1b1f',
    onSurfaceVariant: '#49454f',
  },
  roundness: 12,
};