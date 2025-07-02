import { View, StyleSheet, ScrollView } from 'react-native';
import { Text, Card, Button, Surface, FAB } from 'react-native-paper';
import { SafeAreaView } from 'react-native-safe-area-context';
import { useRouter } from 'expo-router';
import { useAuth } from '@/contexts/AuthContext';
import { Heart, ShoppingBag, Clock, Award } from 'lucide-react-native';

export default function HomeScreen() {
  const router = useRouter();
  const { user } = useAuth();

  const features = [
    {
      title: 'Browse Medications',
      description: 'Find the medicines you need',
      icon: <ShoppingBag color="#6200ee" size={24} />,
      onPress: () => router.push('/medications'),
    },
    {
      title: 'Order History',
      description: 'Track your past orders',
      icon: <Clock color="#6200ee" size={24} />,
      onPress: () => router.push('/orders' as any),
    },
    {
      title: 'Health Services',
      description: 'Consultations and advice',
      icon: <Heart color="#6200ee" size={24} />,
      onPress: () => {},
    },
    {
      title: 'Loyalty Program',
      description: 'Earn points and rewards',
      icon: <Award color="#6200ee" size={24} />,
      onPress: () => {},
    },
  ];

  return (
    <SafeAreaView style={styles.container}>
      <ScrollView style={styles.scrollView}>
        <Surface style={styles.header}>
          <Text variant="headlineMedium" style={styles.headerText}>
            Welcome{user ? `, ${user.firstName}` : ''}!
          </Text>
          <Text variant="bodyLarge" style={styles.subtitle}>
            Your trusted pharmacy partner
          </Text>
        </Surface>

        <View style={styles.content}>
          <Text variant="titleLarge" style={styles.sectionTitle}>
            Quick Actions
          </Text>
          
          <View style={styles.featuresGrid}>
            {features.map((feature, index) => (
              <Card key={index} style={styles.featureCard} onPress={feature.onPress}>
                <Card.Content style={styles.cardContent}>
                  {feature.icon}
                  <Text variant="titleMedium" style={styles.featureTitle}>
                    {feature.title}
                  </Text>
                  <Text variant="bodyMedium" style={styles.featureDescription}>
                    {feature.description}
                  </Text>
                </Card.Content>
              </Card>
            ))}
          </View>

          <Card style={styles.promoCard}>
            <Card.Cover source={{ uri: 'https://images.pexels.com/photos/5726794/pexels-photo-5726794.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260' }} />
            <Card.Content>
              <Text variant="titleLarge">Health & Wellness</Text>
              <Text variant="bodyMedium">
                Discover our range of health products and services
              </Text>
            </Card.Content>
            <Card.Actions>
              <Button mode="contained">Learn More</Button>
            </Card.Actions>
          </Card>
        </View>
      </ScrollView>

      <FAB
        icon="plus"
        style={styles.fab}
        onPress={() => router.push('/medications')}
      />
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
    padding: 20,
    margin: 16,
    borderRadius: 12,
    elevation: 2,
  },
  headerText: {
    fontWeight: 'bold',
    color: '#6200ee',
  },
  subtitle: {
    marginTop: 4,
    opacity: 0.7,
  },
  content: {
    padding: 16,
  },
  sectionTitle: {
    marginBottom: 16,
    fontWeight: 'bold',
  },
  featuresGrid: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    justifyContent: 'space-between',
    marginBottom: 20,
  },
  featureCard: {
    width: '48%',
    marginBottom: 12,
    elevation: 2,
  },
  cardContent: {
    alignItems: 'center',
    padding: 16,
  },
  featureTitle: {
    marginTop: 8,
    textAlign: 'center',
    fontWeight: 'bold',
  },
  featureDescription: {
    marginTop: 4,
    textAlign: 'center',
    opacity: 0.7,
  },
  promoCard: {
    marginTop: 20,
    elevation: 3,
  },
  fab: {
    position: 'absolute',
    margin: 16,
    right: 0,
    bottom: 0,
  },
});