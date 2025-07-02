import { Link, Stack } from 'expo-router';
import { StyleSheet, View } from 'react-native';
import { Text, Button } from 'react-native-paper';

export default function NotFoundScreen() {
  return (
    <>
      <Stack.Screen options={{ title: 'Oops!' }} />
      <View style={styles.container}>
        <Text variant="headlineMedium" style={styles.text}>
          This screen doesn't exist.
        </Text>
        <Link href="/" asChild>
          <Button mode="contained" style={styles.button}>
            Go to home screen
          </Button>
        </Link>
      </View>
    </>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    padding: 20,
  },
  text: {
    textAlign: 'center',
    marginBottom: 20,
  },
  button: {
    marginTop: 15,
  },
});