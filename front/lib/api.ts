// Base API configuration
const API_BASE_URL = 'http://192.168.56.1:8080/api'; // Replace with your backend URL

interface ApiResponse<T> {
  data: T;
  message?: string;
  success: boolean;
  user?: any; 
}

class BaseApiService {
  private baseUrl: string;
  private getToken: () => Promise<string | null>;

  constructor(baseUrl: string = API_BASE_URL,
              getToken: () => Promise<string | null>
  ) {
    this.baseUrl = baseUrl;
    this.getToken = getToken;

  }

  private async request<T>(
    endpoint: string,
    options: RequestInit = {}
  ): Promise<T> {
    const url = `${this.baseUrl}${endpoint}`;
    const token = await this.getToken();
    console.log('Token wysyłany w zapytaniu:', token);

    const headers = {
      'Content-Type': 'application/json',
      ...(token ? { 'Authorization': `Bearer ${token}` } : {}),
      ...options.headers,
    };
    //  if (token) {
      //headers['Authorization'] = `Bearer ${token}`;
    //}

    try {
      const response = await fetch(url, {
        ...options,
          headers
      });
if (!response.ok) {
  const errorData = await response.json();
  throw new Error(errorData.message || `HTTP error! status: ${response.status}`);
}

      const data = await response.json();
      return data;
    } catch (error) {
      console.error(`API request failed: ${endpoint}`, error);
      throw error;
    }
  }

  protected async get<T>(endpoint: string): Promise<T> {
    return this.request<T>(endpoint, { method: 'GET' });
  }

  protected async post<T>(endpoint: string, data?: any): Promise<T> {
    return this.request<T>(endpoint, {
      method: 'POST',
      body: data ? JSON.stringify(data) : undefined,
    });
  }

  protected async put<T>(endpoint: string, data: any): Promise<T> {
    return this.request<T>(endpoint, {
      method: 'PUT',
      body: JSON.stringify(data),
    });
  }

  protected async delete<T>(endpoint: string): Promise<T> {
    return this.request<T>(endpoint, { method: 'DELETE' });
  }
}

// Cart Service
export class CartService extends BaseApiService {
  async getCart(): Promise<any[]> {
    return this.get('/cart');
  }

  async addToCart(productId: number, quantity: number): Promise<any> {
    return this.post(`/cart/add?productId=${productId}&quantity=${quantity}`);
  }

  async clearCart(): Promise<void> {
    return this.delete('/cart');
  }
}

// Medicine Service
export class MedicineService extends BaseApiService {
  async getAll(): Promise<any[]> {
    return this.get('/medicines');
  }

  async getById(id: number): Promise<any> {
    return this.get(`/medicines/${id}`);
  }

  async create(data: any): Promise<any> {
    return this.post('/medicines', data);
  }

  async update(id: number, data: any): Promise<any> {
    return this.put(`/medicines/${id}`, data);
  }

  async deleteById(id: number): Promise<void> {
    return this.delete(`/medicines/${id}`);
  }
}

// User Service
export class UserService extends BaseApiService {
  async getAll(): Promise<any[]> {
    return this.get('/users');
  }

  async getById(id: number): Promise<any> {
    return this.get(`/users/${id}`);
  }

  async create(data: any): Promise<any> {
    return this.post('/users/register', data);
  }

  async update(id: number, data: any): Promise<any> {
    return this.put(`/users/${id}`, data);
  }

  async deleteById(id: number): Promise<void> {
    return this.delete(`/users/${id}`);
  }

  async authenticate(email: string, password: string): Promise<any> {
  const response = await this.post<ApiResponse<any>>('/users/authenticate', { email, password });
    console.log('Response from authenticate:', response); // Logowanie odpowiedzi

  if (!response.success) {
    throw new Error(response.message || 'Authentication failed');
  }
  return response.user; // data - tylko dane użytkownika
}

}

// Order Service
export class OrderService extends BaseApiService {
  async getAll(): Promise<any[]> {
    return this.get('/orders');
  }

  async getById(id: number): Promise<any> {
    return this.get(`/orders/${id}`);
  }

  async create(data: any): Promise<any> {
    return this.post('/orders', data);
  }

  async update(id: number, data: any): Promise<any> {
    return this.put(`/orders/${id}`, data);
  }

  async deleteById(id: number): Promise<void> {
    return this.delete(`/orders/${id}`);
  }

  async getUserOrders(userId: number): Promise<any[]> {
    return this.get(`/orders?userId=${userId}`);
  }
}

// Payment Service
export class PaymentService extends BaseApiService {
  async getAll(): Promise<any[]> {
    return this.get('/payments');
  }

  async getById(id: number): Promise<any> {
    return this.get(`/payments/${id}`);
  }

  async processPayment(data: any): Promise<any> {
    return this.post('/payments', data);
  }

  async update(id: number, data: any): Promise<any> {
    return this.put(`/payments/${id}`, data);
  }

  async deleteById(id: number): Promise<void> {
    return this.delete(`/payments/${id}`);
  }
  async confirmOffline(id: number, data: any): Promise<any> {
    return this.put(`/payments/${id}/confirm-offline`, data);
  }
}

// Customer Service
export class CustomerService extends BaseApiService {
  async getAll(): Promise<any[]> {
    return this.get('/customers');
  }

  async getById(id: number): Promise<any> {
    return this.get(`/customers/${id}`);
  }

  async create(data: any): Promise<any> {
    return this.post('/customers', data);
  }

  async update(id: number, data: any): Promise<any> {
    return this.put(`/customers/${id}`, data);
  }

  async deleteById(id: number): Promise<void> {
    return this.delete(`/customers/${id}`);
  }
}

// Complaint Service
export class ComplaintService extends BaseApiService {
  async getAll(): Promise<any[]> {
    return this.get('/complaints');
  }

  async getById(id: number): Promise<any> {
    return this.get(`/complaints/${id}`);
  }

  async create(data: any): Promise<any> {
    return this.post('/complaints', data);
  }

  async update(id: number, data: any): Promise<any> {
    return this.put(`/complaints/${id}`, data);
  }

  async deleteById(id: number): Promise<void> {
    return this.delete(`/complaints/${id}`);
  }
}

// Notification Service
export class NotificationService extends BaseApiService {
  async getAll(): Promise<any[]> {
    return this.get('/notifications');
  }

  async create(data: any): Promise<any> {
    return this.post('/notifications', data);
  }

  async update(id: number, data: any): Promise<any> {
    return this.put(`/notifications/${id}`, data);
  }

  async deleteById(id: number): Promise<void> {
    return this.delete(`/notifications/${id}`);
  }
}

// Promotion Service
export class PromotionService extends BaseApiService {
  async getAll(): Promise<any[]> {
    return this.get('/promotions');
  }

  async getById(id: number): Promise<any> {
    return this.get(`/promotions/${id}`);
  }

  async create(data: any): Promise<any> {
    return this.post('/promotions', data);
  }

  async update(id: number, data: any): Promise<any> {
    return this.put(`/promotions/${id}`, data);
  }

  async deleteById(id: number): Promise<void> {
    return this.delete(`/promotions/${id}`);
  }
}

// Report Service
export class ReportService extends BaseApiService {
  async getAll(): Promise<any[]> {
    return this.get('/reports');
  }

  async generate(data: any): Promise<any> {
    return this.post('/reports', data);
  }
}

// Database Service
export class DatabaseService extends BaseApiService {
  async getTables(): Promise<string[]> {
    return this.get('/tables');
  }
}

// Main API Service that combines all services
export class ApiService {
  public cartService: CartService;
  public medicineService: MedicineService;
  public userService: UserService;
  public orderService: OrderService;
  public paymentService: PaymentService;
  public customerService: CustomerService;
  public complaintService: ComplaintService;
  public notificationService: NotificationService;
  public promotionService: PromotionService;
  public reportService: ReportService;
  public databaseService: DatabaseService;

  constructor(getToken: () => Promise<string | null>) {

    this.cartService = new CartService(API_BASE_URL, getToken);
    this.medicineService = new MedicineService(API_BASE_URL, getToken);
    this.userService = new UserService(API_BASE_URL, getToken);
    this.orderService = new OrderService(API_BASE_URL, getToken);
    this.paymentService = new PaymentService(API_BASE_URL, getToken);
    this.customerService = new CustomerService(API_BASE_URL, getToken);
    this.complaintService = new ComplaintService(API_BASE_URL, getToken);
    this.notificationService = new NotificationService(API_BASE_URL, getToken);
    this.promotionService = new PromotionService(API_BASE_URL, getToken);
    this.reportService = new ReportService(API_BASE_URL, getToken);
    this.databaseService = new DatabaseService(API_BASE_URL, getToken);
  }
}