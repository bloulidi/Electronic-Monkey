import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';;

import { Product } from '../models/Product';
import { environment } from '../../environments/environment'

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private httpClient: HttpClient) { }

  localhost = environment.apiUrl + 'catalog/api/v1/products';

  saveProduct(product: Product, fileToUpload: File)  {
    const formData: FormData = new FormData();
    const productBlob = new Blob([JSON.stringify(product)],{ type: "application/json"})
    formData.append('product', productBlob);
    formData.append('image', fileToUpload, fileToUpload.name);
    return this.httpClient.post(this.localhost, formData);
  }
  getProductById(id: string)  {
    return this.httpClient.get(this.localhost + '/' + id);
  }
  getProductsByUserId(userId: number)  {
    return this.httpClient.get(this.localhost + '/user/' + userId);
  }
  deleteProduct(id: string)  {
    return this.httpClient.delete(this.localhost + '/' + id);
  }
  getAllProducts()  {
    return this.httpClient.get(this.localhost);
  }
  updateProduct(product: Product)  {
    return this.httpClient.patch(this.localhost, product, httpOptions);
  }
}