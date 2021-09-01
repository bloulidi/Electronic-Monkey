import { Photo } from "./Photo";

// Data model for Product
export class Product {
    title?: string;
    description?: string;
    category?: string;
    price?: number;
    photo?: Photo;
    userId?: number
    id?: string;
}