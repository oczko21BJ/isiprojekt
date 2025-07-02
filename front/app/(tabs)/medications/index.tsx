import { useState, useEffect } from 'react';
import { View, StyleSheet, FlatList } from 'react-native';
import { Text, Searchbar, Chip, FAB } from 'react-native-paper';
import { SafeAreaView } from 'react-native-safe-area-context';
import { useRouter } from 'expo-router';
import { MedicationCard } from '@/components/MedicationCard';
import { useApi } from '@/contexts/ApiContext';
import { Medicine } from '@/types';

export default function MedicationsScreen() {
  const router = useRouter();
  const { medicineService } = useApi();
  const [medications, setMedications] = useState<Medicine[]>([]);
  const [searchQuery, setSearchQuery] = useState('');
  const [selectedCategory, setSelectedCategory] = useState<string | null>(null);
  const [loading, setLoading] = useState(true);

  const categories = ['All', 'Pain Relief', 'Antibiotics', 'Vitamins', 'Supplements'];

  useEffect(() => {
    loadMedications();
  }, []);

  const loadMedications = async () => {
    try {
      setLoading(true);
      const data = await medicineService.getAll();
      console.log('medicineService:', medicineService);

      setMedications(data);
    } catch (error) {
      console.error('Error loading medications:', error);
    } finally {
      setLoading(false);
    }
  };

  const filteredMedications = medications.filter(med => {
    const matchesSearch = med.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
                         med.description?.toLowerCase().includes(searchQuery.toLowerCase());
    const matchesCategory = !selectedCategory || selectedCategory === 'All' || 
                           med.category === selectedCategory;
    return matchesSearch && matchesCategory;
  });

  const renderMedication = ({ item }: { item: Medicine }) => (
    <MedicationCard
      medication={item}
      onPress={() => router.push(`/medications/${item.id}`)}
    />
  );

  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.header}>
        <Text variant="headlineMedium" style={styles.title}>
          Medications
        </Text>
        <Searchbar
          placeholder="Search medications..."
          onChangeText={setSearchQuery}
          value={searchQuery}
          style={styles.searchbar}
        />
        
        <FlatList
          horizontal
          showsHorizontalScrollIndicator={false}
          data={categories}
          keyExtractor={(item) => item}
          renderItem={({ item }) => (
            <Chip
              selected={selectedCategory === item}
              onPress={() => setSelectedCategory(item === 'All' ? null : item)}
              style={styles.chip}
            >
              {item}
            </Chip>
          )}
          contentContainerStyle={styles.categoriesContainer}
        />
      </View>

      <FlatList
        data={filteredMedications}
        renderItem={renderMedication}
        keyExtractor={(item) => item.id.toString()}
        contentContainerStyle={styles.listContainer}
        refreshing={loading}
        onRefresh={loadMedications}
        numColumns={2}
        columnWrapperStyle={styles.row}
      />

      <FAB
        icon="plus"
        style={styles.fab}
        //onPress={() => router.push('/medications/add')}
      />
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#f5f5f5',
  },
  header: {
    padding: 16,
    backgroundColor: 'white',
    elevation: 2,
  },
  title: {
    fontWeight: 'bold',
    marginBottom: 16,
  },
  searchbar: {
    marginBottom: 16,
  },
  categoriesContainer: {
    paddingHorizontal: 4,
  },
  chip: {
    marginRight: 8,
  },
  listContainer: {
    padding: 16,
  },
  row: {
    justifyContent: 'space-between',
  },
  fab: {
    position: 'absolute',
    margin: 16,
    right: 0,
    bottom: 0,
  },
});