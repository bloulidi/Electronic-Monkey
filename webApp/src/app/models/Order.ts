import { OrderProduct } from './OrderProduct';
export class Order {
  orderProducts?: OrderProduct[];
  totalOrderPrice?: number;
  userId?: number;
}
