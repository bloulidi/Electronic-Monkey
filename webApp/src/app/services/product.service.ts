import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';;

import { Product } from '../models/Product';
import { environment } from '../../environments/environment'
import { Observable } from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private httpClient: HttpClient) { }

  localhost = environment.apiUrl + 'catalog/api/v1/products';

  saveProduct(product: Product, fileToUpload: File) {
    const formData: FormData = new FormData();
    const productBlob = new Blob([JSON.stringify(product)], { type: "application/json" })
    formData.append('product', productBlob);
    formData.append('image', fileToUpload, fileToUpload.name);
    return this.httpClient.post(this.localhost, formData);
  }
  getProductById(id: string) {
    return this.httpClient.get(this.localhost + '/' + id);
  }
  getProductsByUserId(userId: number): Observable<Product[]> {
    return this.httpClient.get<Product[]>(this.localhost + '/user/' + userId);
  }
  getProductsByCategory(category: string): Observable<Product[]> {
    return this.httpClient.get<Product[]>(this.localhost + '/category/' + category);
  }
  deleteProduct(id: any): Observable<any> {
    return this.httpClient.delete(this.localhost + '/' + id);
  }
  getAllProducts() {
    return this.httpClient.get(this.localhost);
  }
  updateProductWithoutImage(product: Product) {
    return this.httpClient.patch(this.localhost, product);
  }
  updateProductWithImage(product: Product,  fileToUpload: File) {
    const formData: FormData = new FormData();
    const productBlob = new Blob([JSON.stringify(product)], { type: "application/json" })
    formData.append('product', productBlob);
    formData.append('image', fileToUpload, fileToUpload.name);
    return this.httpClient.patch(this.localhost + '/image', formData);
  }
}