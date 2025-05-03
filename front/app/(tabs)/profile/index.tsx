import { View, StyleSheet, ScrollView } from 'react-native';
import { Text, Card, Button, List, Avatar, Surface } from 'react-native-paper';
import { SafeAreaView } from 'react-native-safe-area-context';
import { useRouter } from 'expo-router';
import { useAuth } from '@/contexts/AuthContext';
import { User, ShoppingBag, Settings, LogOut, Heart } from 'lucide-react-native';

export default function ProfileScreen() {
  const router = useRouter();
  const { user, logout } = useAuth();

  const handleLogout = () => {
    logout();
    router.push('/auth/login');
  };

  if (!user) {
    return (
      <SafeAreaView style={styles.container}>
        <View style={styles.loginPrompt}>
          <Text variant="titleLarge" style={styles.loginText}>
            Please log in to view your profile
          </Text>
          <Button
            mode="contained"
            onPress={() => router.push('/auth/login')}
            style={styles.loginButton}
          >
            Log In
          </Button>
        </View>
      </SafeAreaView>
    );
  }

  return (
    <SafeAreaView style={styles.container}>
      <ScrollView style={styles.scrollView}>
        <Surface style={styles.header}>
          <Avatar.Icon size={80} icon="account" style={styles.avatar} />
          <Text variant="headlineSmall" style={styles.name}>
            {user.firstName} {user.lastName}
          </Text>
          <Text variant="bodyLarge" style={styles.email}>
            {user.email}
          </Text>
        </Surface>

        <Card style={styles.menuCard}>
          <List.Section>
            <List.Item
              title="Order History"
              description="View your past orders"
              left={(props) => <List.Icon {...props} icon={({ size, color }) => <ShoppingBag size={size} color={color} />} />}
              right={(props) => <List.Icon {...props} icon="chevron-right" />}
              onPress={() => router.push('/profile/orders' as any)}
            />
            <List.Item
              title="Wishlist"
              description="Saved medications"
              left={(props) => <List.Icon {...props} icon={({ size, color }) => <Heart size={size} color={color} />} />}
              right={(props) => <List.Icon {...props} icon="chevron-right" />}
              onPress={() => {}}
            />
            <List.Item
              title="Settings"
              description="Account and app settings"
              left={(props) => <List.Icon {...props} icon={({ size, color }) => <Settings size={size} color={color} />} />}
              right={(props) => <List.Icon {...props} icon="chevron-right" />}
              onPress={() => router.push('/profile/settings' as any)}
            />
          </List.Section>
        </Card>

        <Card style={styles.infoCard}>
          <Card.Content>
            <Text variant="titleMedium" style={styles.cardTitle}>
              Account Information
            </Text>
            <View style={styles.infoRow}>
              <Text variant="bodyMedium" style={styles.label}>Phone:</Text>
              <Text variant="bodyMedium">{user.phoneNumber || 'Not provided'}</Text>
            </View>
            <View style={styles.infoRow}>
              <Text variant="bodyMedium" style={styles.label}>Address:</Text>
              <Text variant="bodyMedium">{user.address || 'Not provided'}</Text>
            </View>
            <View style={styles.infoRow}>
              <Text variant="bodyMedium" style={styles.label}>Member since:</Text>
              <Text variant="bodyMedium">
                {user.createdAt ? new Date(user.createdAt).toLocaleDateString() : 'Unknown'}
              </Text>
            </View>
          </Card.Content>
        </Card>

        <Button
          mode="outlined"
          onPress={handleLogout}
          style={styles.logoutButton}
          icon={({ size, color }) => <LogOut size={size} color={color} />}
        >
          Log Out
        </Button>
      </ScrollView>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#f5f5f5',
  },
  scrollView: {
    flex: 1,
  },
  header: {
    alignItems: 'center',
    padding: 24,
    margin: 16,
    borderRadius: 12,
    elevation: 2,
  },
  avatar: {
    backgroundColor: '#6200ee',
    marginBottom: 12,
  },
  name: {
    fontWeight: 'bold',
    marginBottom: 4,
  },
  email: {
    opacity: 0.7,
  },
  menuCard: {
    margin: 16,
    elevation: 2,
  },
  infoCard: {
    margin: 16,
    elevation: 2,
  },
  cardTitle: {
    fontWeight: 'bold',
    marginBottom: 16,
  },
  infoRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginBottom: 8,
  },
  label: {
    fontWeight: 'bold',
  },
  logoutButton: {
    margin: 16,
    paddingVertical: 8,
  },
  loginPrompt: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 32,
  },
  loginText: {
    textAlign: 'center',
    marginBottom: 24,
  },
  loginButton: {
    paddingHorizontal: 24,
  },
});