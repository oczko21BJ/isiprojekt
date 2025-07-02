import { useState } from 'react';
import { View, StyleSheet, ScrollView, Alert, Platform } from 'react-native';
import { Text, Button, Surface, TextInput, RadioButton, Divider } from 'react-native-paper';
import { SafeAreaView } from 'react-native-safe-area-context';
import { useRouter } from 'expo-router';
import { useCart } from '@/contexts/CartContext';
import { useAuth } from '@/contexts/AuthContext';
import { useApi } from '@/contexts/ApiContext';

export default function CheckoutScreen() {
  const router = useRouter();
  const { items, getTotal, clearCart } = useCart();
  const { user } = useAuth();
  const { orderService, paymentService } = useApi();
  const [loading, setLoading] = useState(false);
  const [paymentMethod, setPaymentMethod] = useState('card');
  const [deliveryAddress, setDeliveryAddress] = useState(user?.address || '');
  const [specialInstructions, setSpecialInstructions] = useState('');
  const [cardNumber, setCardNumber] = useState('');
  const [cardHolderName, setCardHolderName] = useState('');
  const [expiryDate, setExpiryDate] = useState('');
  const [cvv, setCvv] = useState('');
  const totalStringOrNumber = getTotal();
  const subtotal = typeof totalStringOrNumber === 'number'
  ? parseFloat(totalStringOrNumber.toFixed(2))
  : parseFloat(parseFloat(totalStringOrNumber).toFixed(2));
  const deliveryFee = 5.99;
  const tax = parseFloat((subtotal * 0.08).toFixed(2));
  const total = parseFloat((subtotal + deliveryFee + tax).toFixed(2));
  const roundedSubtotal = parseFloat(subtotal.toFixed(2));

  const handlePlaceOrder = async () => {
    if (!deliveryAddress.trim()) {
      Alert.alert('Error', 'Please provide a delivery address');
      return;
    }

    try {
      setLoading(true);
      
      // Create order
      const orderData = {
        customerId: user?.id,
        items: items.map(item => ({
          medicineId: item.id,
          quantity: item.quantity,
          price: item.price,
        })),
        deliveryAddress,
        specialInstructions,
        subtotal: roundedSubtotal,
        deliveryFee,
        tax,
        total,
      };
      console.log('Order Data:', orderData);

      const order = await orderService.create(orderData);
   

      // Process payment
      const paymentData = {
        orderId: order.id,
        amount: total,
        cardNumber, 
        cardHolderName, 
        expiryDate, 
        cvv,
        paymentMethod,
      };

      await paymentService.processPayment(paymentData);
      // Clear cart and navigate
      clearCart();
      if (Platform.OS === 'web') {
    window.alert(`Your order #${order.id} has been confirmed and will be delivered soon.`);
    router.replace('/(tabs)');
  } else {
    Alert.alert(
      'Order Placed Successfully!',
      `Your order #${order.id} has been confirmed and will be delivered soon.`,
      [{ text: 'OK', onPress: () => router.replace('/(tabs)') }]
    );
  }

} catch (error) {
  console.error('Payment error:', error);
  if (Platform.OS === 'web') {
    window.alert('Failed to place order. Please try again.');
  } else {
    Alert.alert('Error', 'Failed to place order. Please try again.');
  }
} finally {
  setLoading(false);
}
  };

  return (
    <SafeAreaView style={styles.container}>
      <ScrollView style={styles.scrollView}>
        <Text variant="headlineMedium" style={styles.title}>
          Checkout
        </Text>

        {/* Order Summary */}
        <Surface style={styles.section}>
          <Text variant="titleMedium" style={styles.sectionTitle}>
            Order Summary
          </Text>
          {items.map(item => (
            <View key={item.id} style={styles.orderItem}>
              <Text variant="bodyMedium">{item.name} x {item.quantity}</Text>
              <Text variant="bodyMedium">${(item.price * item.quantity).toFixed(2)}</Text>
            </View>
          ))}
          <Divider style={styles.divider} />
          <View style={styles.totalRow}>
            <Text variant="bodyMedium">Subtotal</Text>
            <Text variant="bodyMedium">${subtotal.toFixed(2)}</Text>
          </View>
          <View style={styles.totalRow}>
            <Text variant="bodyMedium">Delivery Fee</Text>
            <Text variant="bodyMedium">${deliveryFee.toFixed(2)}</Text>
          </View>
          <View style={styles.totalRow}>
            <Text variant="bodyMedium">Tax</Text>
            <Text variant="bodyMedium">${tax.toFixed(2)}</Text>
          </View>
          <Divider style={styles.divider} />
          <View style={styles.totalRow}>
            <Text variant="titleMedium" style={styles.boldText}>Total</Text>
            <Text variant="titleMedium" style={styles.boldText}>${total.toFixed(2)}</Text>
          </View>
        </Surface>

        {/* Delivery Address */}
        <Surface style={styles.section}>
          <Text variant="titleMedium" style={styles.sectionTitle}>
            Delivery Address
          </Text>
          <TextInput
            label="Address"
            value={deliveryAddress}
            onChangeText={setDeliveryAddress}
            mode="outlined"
            multiline
            numberOfLines={3}
            style={styles.input}
          />
          <TextInput
            label="Special Instructions (Optional)"
            value={specialInstructions}
            onChangeText={setSpecialInstructions}
            mode="outlined"
            multiline
            numberOfLines={2}
            style={styles.input}
          />
        </Surface>

        {/* Payment Method */}
        <Surface style={styles.section}>
          <Text variant="titleMedium" style={styles.sectionTitle}>
            Payment Method
          </Text>
          <RadioButton.Group onValueChange={setPaymentMethod} value={paymentMethod}>
            <View style={styles.radioItem}>
              <RadioButton value="card" />
              <Text variant="bodyMedium">Credit/Debit Card</Text>
            </View>
            <View style={styles.radioItem}>
              <RadioButton value="cash" />
              <Text variant="bodyMedium">Cash on Delivery</Text>
            </View>
            <View style={styles.radioItem}>
              <RadioButton value="digital" />
              <Text variant="bodyMedium">Digital Wallet</Text>
            </View>
          </RadioButton.Group>
        </Surface>
        {paymentMethod === 'card' && (
  <Surface style={styles.section}>
    <Text variant="titleMedium" style={styles.sectionTitle}>Card Details</Text>
    <TextInput
      label="Card Number"
      value={cardNumber}
      onChangeText={setCardNumber}
      keyboardType="number-pad"
      maxLength={16}
      mode="outlined"
      style={styles.input}
    />
    <TextInput
      label="Card Holder Name"
      value={cardHolderName}
      onChangeText={setCardHolderName}
      mode="outlined"
      style={styles.input}
    />
    <TextInput
      label="Expiry Date (MM/YY)"
      value={expiryDate}
      onChangeText={setExpiryDate}
      mode="outlined"
      style={styles.input}
    />
    <TextInput
      label="CVV"
      value={cvv}
      onChangeText={setCvv}
      keyboardType="number-pad"
      maxLength={4}
      mode="outlined"
      style={styles.input}
      secureTextEntry
    />
  </Surface>
)}

      </ScrollView>


      <Surface style={styles.footer}>
        <Text variant="titleLarge" style={styles.footerTotal}>
          Total: ${total.toFixed(2)}
        </Text>
        <Button
          mode="contained"
          onPress={handlePlaceOrder}
          loading={loading}
          disabled={loading || items.length === 0}
          style={styles.placeOrderButton}
        >
          Place Order
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
  scrollView: {
    flex: 1,
  },
  title: {
    fontWeight: 'bold',
    textAlign: 'center',
    margin: 16,
  },
  section: {
    margin: 16,
    padding: 16,
    borderRadius: 12,
    elevation: 2,
  },
  sectionTitle: {
    fontWeight: 'bold',
    marginBottom: 16,
  },
  orderItem: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginBottom: 8,
  },
  totalRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginVertical: 4,
  },
  boldText: {
    fontWeight: 'bold',
  },
  divider: {
    marginVertical: 12,
  },
  input: {
    marginBottom: 12,
  },
  radioItem: {
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: 8,
  },
  footer: {
    padding: 20,
    elevation: 8,
  },
  footerTotal: {
    textAlign: 'center',
    fontWeight: 'bold',
    marginBottom: 16,
  },
  placeOrderButton: {
    paddingVertical: 8,
  },
});