import { View, StyleSheet } from 'react-native';
import { Card, Text, Button } from 'react-native-paper';
import { Medicine } from '@/types';

interface MedicationCardProps {
  medication: Medicine;
  onPress: () => void;
}

export function MedicationCard({ medication, onPress }: MedicationCardProps) {
  return (
    <Card style={styles.card} onPress={onPress}>
      <Card.Cover 
        source={{ 
          uri: medication.imageUrl || 'https://images.pexels.com/photos/159211/headache-pain-pills-medication-159211.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260'
        }} 
        style={styles.image}
      />
      <Card.Content style={styles.content}>
        <Text variant="titleMedium" style={styles.name} numberOfLines={2}>
          {medication.name}
        </Text>
        <Text variant="bodySmall" style={styles.category}>
          {medication.category}
        </Text>
        <Text variant="titleMedium" style={styles.price}>
          ${medication.price.toFixed(2)}
        </Text>
        <Text variant="bodySmall" style={styles.stock}>
          Stock: {medication.stockQuantity}
        </Text>
      </Card.Content>
      <Card.Actions>
        <Button mode="outlined" onPress={onPress} compact>
          View Details
        </Button>
      </Card.Actions>
    </Card>
  );
}

const styles = StyleSheet.create({
  card: {
    flex: 1,
    margin: 8,
    elevation: 3,
  },
  image: {
    height: 120,
  },
  content: {
    padding: 12,
  },
  name: {
    fontWeight: 'bold',
    marginBottom: 4,
  },
  category: {
    opacity: 0.7,
    marginBottom: 8,
  },
  price: {
    color: '#6200ee',
    fontWeight: 'bold',
    marginBottom: 4,
  },
  stock: {
    opacity: 0.8,
  },
});