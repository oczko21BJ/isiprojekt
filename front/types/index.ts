export interface Medicine {
  id: number;
  name: string;
  description?: string;
  price: number;
  category: string;
  manufacturer: string;
  stockQuantity: number;
  imageUrl?: string;
  createdAt?: string;
  updatedAt?: string;
}

export interface User {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber?: string;
  address?: string;
  role: string;
  createdAt?: string;
  updatedAt?: string;
}

export interface CartItem {
  id: number;
  name: string;
  price: number;
  quantity: number;
  image?: string;
}

export interface Order {
  id: number;
  customerId: number;
  items: OrderItem[];
  deliveryAddress: string;
  specialInstructions?: string;
  subtotal: number;
  deliveryFee: number;
  tax: number;
  total: number;
  status: OrderStatus;
  createdAt: string;
  updatedAt?: string;
}

export interface OrderItem {
  id: number;
  medicineId: number;
  quantity: number;
  price: number;
}

export enum OrderStatus {
  PENDING = 'PENDING',
  CONFIRMED = 'CONFIRMED',
  PREPARING = 'PREPARING',
  DELIVERED = 'DELIVERED',
  CANCELLED = 'CANCELLED',
}

export interface Payment {
  id: number;
  orderId: number;
  amount: number;
  paymentMethod: string;
  status: PaymentStatus;
  createdAt: string;
  cardNumber: String, 
  cardHolderName: String, 
  expiryDate: String, 
  cvv: String,
}

export enum PaymentStatus {
  PENDING = 'PENDING',
  COMPLETED = 'COMPLETED',
  FAILED = 'FAILED',
  REFUNDED = 'REFUNDED',
}

export interface Customer {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber?: string;
  address?: string;
  createdAt: string;
}

export interface Complaint {
  id: number;
  customerId: number;
  title: string;
  description: string;
  status: ComplaintStatus;
  createdAt: string;
  resolvedAt?: string;
}

export enum ComplaintStatus {
  OPEN = 'OPEN',
  IN_PROGRESS = 'IN_PROGRESS',
  RESOLVED = 'RESOLVED',
  CLOSED = 'CLOSED',
}

export interface Notification {
  id: number;
  title: string;
  message: string;
  type: NotificationType;
  targetUserId?: number;
  createdAt: string;
  isRead: boolean;
}

export enum NotificationType {
  INFO = 'INFO',
  WARNING = 'WARNING',
  ERROR = 'ERROR',
  SUCCESS = 'SUCCESS',
}

export interface Promotion {
  id: number;
  title: string;
  description: string;
  discountPercentage: number;
  startDate: string;
  endDate: string;
  isActive: boolean;
}