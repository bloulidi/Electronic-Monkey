import { OrderProduct } from './OrderProduct';
export class Order {
    orderProducts?: OrderProduct[];
    totalPrice?: number;
    userId?: number;
}