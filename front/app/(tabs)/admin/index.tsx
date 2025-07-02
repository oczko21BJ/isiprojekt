import { View, StyleSheet, ScrollView } from 'react-native';
import { Text, Card, Surface } from 'react-native-paper';
import { SafeAreaView } from 'react-native-safe-area-context';
import { useRouter } from 'expo-router';
import { Package, Users, ShoppingCart, BarChart3, Bell, FileText } from 'lucide-react-native';

export default function AdminDashboardScreen() {
  const router = useRouter();

  const stats = [
    { title: 'Total Medications', value: '234', icon: <Package color="#6200ee" size={24} /> },
    { title: 'Active Users', value: '1,456', icon: <Users color="#6200ee" size={24} /> },
    { title: 'Orders Today', value: '89', icon: <ShoppingCart color="#6200ee" size={24} /> },
    { title: 'Revenue', value: '$12,345', icon: <BarChart3 color="#6200ee" size={24} /> },
  ];

  const quickActions = [
    {
      title: 'Manage Medications',
      description: 'Add, edit, or remove medications',
      icon: <Package color="#6200ee" size={24} />,
      onPress: () => router.push('/admin/medications'),
    },
    {
      title: 'View Orders',
      description: 'Monitor and process orders',
      icon: <ShoppingCart color="#6200ee" size={24} />,
      onPress: () => router.push('/admin/orders'),
    },
    {
      title: 'User Management',
      description: 'Manage customer accounts',
      icon: <Users color="#6200ee" size={24} />,
      onPress: () => router.push('/admin/users'),
    },
    {
      title: 'Reports',
      description: 'View analytics and reports',
      icon: <FileText color="#6200ee" size={24} />,
      onPress: () => router.push('/admin/reports'),
    },
    {
      title: 'Notifications',
      description: 'Send alerts to users',
      icon: <Bell color="#6200ee" size={24} />,
      onPress: () => router.push('/admin/notifications'),
    },
  ];

  return (
    <SafeAreaView style={styles.container}>
      <ScrollView style={styles.scrollView}>
        <Surface style={styles.header}>
          <Text variant="headlineMedium" style={styles.title}>
            Admin Dashboard
          </Text>
          <Text variant="bodyLarge" style={styles.subtitle}>
            Pharmacy Management System
          </Text>
        </Surface>

        <View style={styles.content}>
          <Text variant="titleLarge" style={styles.sectionTitle}>
            Overview
          </Text>
          
          <View style={styles.statsGrid}>
            {stats.map((stat, index) => (
              <Card key={index} style={styles.statCard}>
                <Card.Content style={styles.statContent}>
                  {stat.icon}
                  <Text variant="headlineSmall" style={styles.statValue}>
                    {stat.value}
                  </Text>
                  <Text variant="bodyMedium" style={styles.statTitle}>
                    {stat.title}
                  </Text>
                </Card.Content>
              </Card>
            ))}
          </View>

          <Text variant="titleLarge" style={styles.sectionTitle}>
            Quick Actions
          </Text>
          
          <View style={styles.actionsGrid}>
            {quickActions.map((action, index) => (
              <Card key={index} style={styles.actionCard} onPress={action.onPress}>
                <Card.Content style={styles.actionContent}>
                  {action.icon}
                  <Text variant="titleMedium" style={styles.actionTitle}>
                    {action.title}
                  </Text>
                  <Text variant="bodyMedium" style={styles.actionDescription}>
                    {action.description}
                  </Text>
                </Card.Content>
              </Card>
            ))}
          </View>
        </View>
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
    padding: 20,
    margin: 16,
    borderRadius: 12,
    elevation: 2,
  },
  title: {
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
  statsGrid: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    justifyContent: 'space-between',
    marginBottom: 32,
  },
  statCard: {
    width: '48%',
    marginBottom: 12,
    elevation: 2,
  },
  statContent: {
    alignItems: 'center',
    padding: 16,
  },
  statValue: {
    fontWeight: 'bold',
    marginTop: 8,
    color: '#6200ee',
  },
  statTitle: {
    textAlign: 'center',
    marginTop: 4,
    opacity: 0.7,
  },
  actionsGrid: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    justifyContent: 'space-between',
  },
  actionCard: {
    width: '48%',
    marginBottom: 12,
    elevation: 2,
  },
  actionContent: {
    alignItems: 'center',
    padding: 16,
  },
  actionTitle: {
    marginTop: 8,
    textAlign: 'center',
    fontWeight: 'bold',
  },
  actionDescription: {
    marginTop: 4,
    textAlign: 'center',
    opacity: 0.7,
  },
});