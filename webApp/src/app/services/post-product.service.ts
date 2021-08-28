import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { StringifyOptions } from 'querystring';
import { Observable } from 'rxjs';
import { baseUrl } from '../global-variables';
import { Product } from '../models/Product';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class PostProductService {

  constructor(private httpClient: HttpClient) { }

  localhost = baseUrl + 'catalog/api/v1/products';

  saveProductWithImage(product: Product, fileToUpload: File)  {
    const formData: FormData = new FormData();
    const productBlob = new Blob([JSON.stringify(product)],{ type: "application/json"})
    formData.append('product', productBlob);
    formData.append('image', fileToUpload, fileToUpload.name);
    return this.httpClient.post(this.localhost, formData);
  }
  getProductById(id: string)  {
    return this.httpClient.get(this.localhost + '/' + id);
  }
  getProductByCode(code: string)  {
    return this.httpClient.get(this.localhost + '/code/' + code);
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