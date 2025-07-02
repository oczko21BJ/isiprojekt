import { useState, useEffect } from 'react';
import { View, StyleSheet, ScrollView, Alert } from 'react-native';
import { Text, Button, Card, Surface, IconButton } from 'react-native-paper';
import { SafeAreaView } from 'react-native-safe-area-context';
import { useLocalSearchParams, useRouter } from 'expo-router';
import { useApi } from '@/contexts/ApiContext';
import { useCart } from '@/contexts/CartContext';
import { Medicine } from '@/types';
import { ArrowLeft, Plus, Minus } from 'lucide-react-native';

export default function MedicationDetailScreen() {
  const { id } = useLocalSearchParams<{ id: string }>();
  const router = useRouter();
  const { medicineService } = useApi();
  const { addItem } = useCart();
  const [medication, setMedication] = useState<Medicine | null>(null);
  const [quantity, setQuantity] = useState(1);
  const [loading, setLoading] = useState(true);

  //useEffect(() => {
  //  if (id) {
   //   loadMedication();
  //  }
  //}, [id]);
useEffect(() => {
  if (!id || isNaN(Number(id))) {
    console.warn('❌ Invalid ID param:', id);
    Alert.alert('Invalid ID', 'Could not load medicine. Invalid or missing ID.');
    return;
  }

  loadMedication();
}, [id]);

//const loadMedication = async () => {
//  try {
//    setLoading(true);
//    const data = await medicineService.getById(parseInt(id!));
//    setMedication(data);
//  } catch (error) {
//    console.error('Error loading medication:', error);
//    Alert.alert('Error', 'Failed to load medication details');
//  } finally {
//    setLoading(false);
//  }
//};
//
const loadMedication = async () => {
  try {
    setLoading(true);
    const numericId = parseInt(id!);
    const data = await medicineService.getById(numericId);
    setMedication(data);
  } catch (error) {
    console.error('Error loading medication:', error);
    Alert.alert('Error', 'Failed to load medication details');
  } finally {
    setLoading(false);
  }
};

  const handleAddToCart = () => {
    if (medication) {
      addItem({
        id: medication.id,
        name: medication.name,
        price: medication.price,
        quantity: quantity,
        image: medication.imageUrl,
      });
      Alert.alert('Success', `${medication.name} added to cart`);
    }
  };

  if (loading || !medication) {
    return (
      <SafeAreaView style={styles.container}>
        <Text>Loading...</Text>
      </SafeAreaView>
    );
  }

  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.header}>
        <IconButton
          icon={({ size, color }) => <ArrowLeft size={size} color={color} />}
          onPress={() => router.back()}
        />
        <Text variant="titleLarge" style={styles.headerTitle}>
          Medication Details
        </Text>
      </View>

      <ScrollView style={styles.scrollView}>
        <Card style={styles.imageCard}>
          <Card.Cover 
            source={{ 
              uri: medication.imageUrl || 'https://images.pexels.com/photos/159211/headache-pain-pills-medication-159211.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260'
            }} 
            style={styles.image}
          />
        </Card>

        <Surface style={styles.detailsCard}>
          <Text variant="headlineMedium" style={styles.name}>
            {medication.name}
          </Text>
          
          <Text variant="titleLarge" style={styles.price}>
            ${medication.price.toFixed(2)}
          </Text>

          <View style={styles.infoRow}>
            <Text variant="bodyLarge" style={styles.label}>Category:</Text>
            <Text variant="bodyLarge">{medication.category}</Text>
          </View>

          <View style={styles.infoRow}>
            <Text variant="bodyLarge" style={styles.label}>Manufacturer:</Text>
            <Text variant="bodyLarge">{medication.manufacturer}</Text>
          </View>

          <View style={styles.infoRow}>
            <Text variant="bodyLarge" style={styles.label}>Stock:</Text>
            <Text variant="bodyLarge">{medication.stockQuantity} units</Text>
          </View>

          {medication.description && (
            <View style={styles.descriptionSection}>
              <Text variant="titleMedium" style={styles.sectionTitle}>
                Description
              </Text>
              <Text variant="bodyMedium">{medication.description}</Text>
            </View>
          )}

         {/* <View style={styles.quantitySection}>
            <Text variant="titleMedium" style={styles.sectionTitle}>
              Quantity
            </Text>
            <View style={styles.quantityControls}>
              <IconButton
                icon={({ size, color }) => <Minus size={size} color={color} />}
                onPress={() => setQuantity(Math.max(1, quantity - 1))}
                mode="contained-tonal"
              />
              <Text variant="titleLarge" style={styles.quantityText}>
                {quantity}
              </Text>
              <IconButton
                icon={({ size, color }) => <Plus size={size} color={color} />}
                onPress={() => setQuantity(Math.min(medication.stockQuantity, quantity + 1))}
                mode="contained-tonal"
              />
            </View>
          </View>*/}
        </Surface>
      </ScrollView>

      <Surface style={styles.footer}>
        <View style={styles.totalSection}>
          <Text variant="titleMedium">Total</Text>
          <Text variant="titleLarge" style={styles.totalPrice}>
            ${(medication.price * quantity).toFixed(2)}
          </Text>
        </View>
        <Button
          mode="contained"
          onPress={handleAddToCart}
          style={styles.addButton}
          disabled={medication.stockQuantity === 0}
        >
          {medication.stockQuantity === 0 ? 'Out of Stock' : 'Add to Cart'}
        </Button>
      </Surface>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#f5f5f5',
  },
  header: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: 'white',
    elevation: 2,
    paddingRight: 16,
  },
  headerTitle: {
    flex: 1,
    fontWeight: 'bold',
  },
  scrollView: {
    flex: 1,
  },
  imageCard: {
    margin: 16,
    elevation: 3,
  },
  image: {
    height: 200,
  },
  detailsCard: {
    margin: 16,
    padding: 20,
    borderRadius: 12,
    elevation: 2,
  },
  name: {
    fontWeight: 'bold',
    marginBottom: 8,
  },
  price: {
    color: '#6200ee',
    fontWeight: 'bold',
    marginBottom: 20,
  },
  infoRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginBottom: 12,
    paddingBottom: 8,
    borderBottomWidth: 1,
    borderBottomColor: '#e0e0e0',
  },
  label: {
    fontWeight: 'bold',
  },
  descriptionSection: {
    marginTop: 20,
  },
  sectionTitle: {
    fontWeight: 'bold',
    marginBottom: 12,
  },
  quantitySection: {
    marginTop: 20,
  },
  quantityControls: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
  },
  quantityText: {
    marginHorizontal: 20,
    fontWeight: 'bold',
  },
  footer: {
    padding: 20,
    elevation: 8,
  },
  totalSection: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: 16,
  },
  totalPrice: {
    fontWeight: 'bold',
    color: '#6200ee',
  },
  addButton: {
    paddingVertical: 8,
  },
});