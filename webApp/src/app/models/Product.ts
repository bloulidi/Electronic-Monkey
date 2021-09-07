import { Photo } from './Photo';

// Data model for Product
export class Product {
  id?: string;
  title?: string;
  description?: string;
  category?: string;
  price?: number;
  photo?: Photo;
  userId?: number;
  hidden?: boolean;
}
