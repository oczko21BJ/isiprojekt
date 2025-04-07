import { View, StyleSheet, FlatList, Alert } from 'react-native';
import { Text, Card, Button, IconButton, Surface, Divider } from 'react-native-paper';
import { SafeAreaView } from 'react-native-safe-area-context';
import { useRouter } from 'expo-router';
import { useCart } from '@/contexts/CartContext';
import { Trash2, Plus, Minus } from 'lucide-react-native';

export default function CartScreen() {
  const router = useRouter();
  const { items, updateQuantity, removeItem, getTotal, clearCart } = useCart();

  const handleQuantityChange = (id: number, change: number) => {
    const item = items.find(item => item.id === id);
    if (item) {
      const newQuantity = item.quantity + change;
      if (newQuantity > 0) {
        updateQuantity(id, newQuantity);
      } else {
        removeItem(id);
      }
    }
  };

  const handleRemoveItem = (id: number, name: string) => {
    Alert.alert(
      'Remove Item',
      `Are you sure you want to remove ${name} from your cart?`,
      [
        { text: 'Cancel', style: 'cancel' },
        { text: 'Remove', style: 'destructive', onPress: () => removeItem(id) },
      ]
    );
  };

  const handleClearCart = () => {
    Alert.alert(
      'Clear Cart',
      'Are you sure you want to remove all items from your cart?',
      [
        { text: 'Cancel', style: 'cancel' },
        { text: 'Clear', style: 'destructive', onPress: clearCart },
      ]
    );
  };

  const renderCartItem = ({ item }: { item: any }) => (
    <Card style={styles.itemCard}>
      <Card.Content>
        <View style={styles.itemHeader}>
          <View style={styles.itemInfo}>
            <Text variant="titleMedium" style={styles.itemName}>
              {item.name}
            </Text>
            <Text variant="bodyLarge" style={styles.itemPrice}>
              ${item.price.toFixed(2)}
            </Text>
          </View>
          <IconButton
            icon={({ size, color }) => <Trash2 size={size} color={color} />}
            onPress={() => handleRemoveItem(item.id, item.name)}
          />
        </View>
        
        <View style={styles.quantitySection}>
          <Text variant="bodyMedium">Quantity:</Text>
          <View style={styles.quantityControls}>
            <IconButton
              icon={({ size, color }) => <Minus size={size} color={color} />}
              onPress={() => handleQuantityChange(item.id, -1)}
              size={20}
            />
            <Text variant="titleMedium" style={styles.quantityText}>
              {item.quantity}
            </Text>
            <IconButton
              icon={({ size, color }) => <Plus size={size} color={color} />}
              onPress={() => handleQuantityChange(item.id, 1)}
              size={20}
            />
          </View>
        </View>
        
        <Divider style={styles.divider} />
        <Text variant="titleMedium" style={styles.subtotal}>
          Subtotal: ${(item.price * item.quantity).toFixed(2)}
        </Text>
      </Card.Content>
    </Card>
  );

  if (items.length === 0) {
    return (
      <SafeAreaView style={styles.container}>
        <View style={styles.header}>
          <Text variant="headlineMedium" style={styles.title}>
            Shopping Cart
          </Text>
        </View>
        <View style={styles.emptyContainer}>
          <Text variant="titleLarge" style={styles.emptyText}>
            Your cart is empty
          </Text>
          <Text variant="bodyMedium" style={styles.emptySubtext}>
            Add some medications to get started
          </Text>
          <Button
            mode="contained"
            onPress={() => router.push('/medications')}
            style={styles.shopButton}
          >
            Browse Medications
          </Button>
        </View>
      </SafeAreaView>
    );
  }

  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.header}>
        <Text variant="headlineMedium" style={styles.title}>
          Shopping Cart
        </Text>
        <Button mode="text" onPress={handleClearCart}>
          Clear All
        </Button>
      </View>

      <FlatList
        data={items}
        renderItem={renderCartItem}
        keyExtractor={(item) => item.id.toString()}
        contentContainerStyle={styles.listContainer}
      />

      <Surface style={styles.footer}>
        <View style={styles.totalSection}>
          <Text variant="titleLarge">Total Items: {items.length}</Text>
          <Text variant="headlineSmall" style={styles.totalPrice}>
            ${getTotal().toFixed(2)}
          </Text>
        </View>
        <Button
          mode="contained"
          onPress={() => router.push('/checkout')}
          style={styles.checkoutButton}
        >
          Proceed to Checkout
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
    justifyContent: 'space-between',
    alignItems: 'center',
    padding: 16,
    backgroundColor: 'white',
    elevation: 2,
  },
  title: {
    fontWeight: 'bold',
  },
  listContainer: {
    padding: 16,
  },
  itemCard: {
    marginBottom: 12,
    elevation: 2,
  },
  itemHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'flex-start',
  },
  itemInfo: {
    flex: 1,
  },
  itemName: {
    fontWeight: 'bold',
    marginBottom: 4,
  },
  itemPrice: {
    color: '#6200ee',
  },
  quantitySection: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginTop: 12,
  },
  quantityControls: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  quantityText: {
    marginHorizontal: 12,
    fontWeight: 'bold',
  },
  divider: {
    marginVertical: 12,
  },
  subtotal: {
    textAlign: 'right',
    fontWeight: 'bold',
  },
  emptyContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 32,
  },
  emptyText: {
    marginBottom: 8,
    textAlign: 'center',
  },
  emptySubtext: {
    marginBottom: 24,
    textAlign: 'center',
    opacity: 0.7,
  },
  shopButton: {
    paddingHorizontal: 24,
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
  checkoutButton: {
    paddingVertical: 8,
  },
});