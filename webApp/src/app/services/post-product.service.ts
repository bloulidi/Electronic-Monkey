import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { baseUrl } from '../global-variables';
import { Product } from '../models/Product';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

const httpOptionsImage = {
  headers: new HttpHeaders({ 'Accept': "multipart/form-data" })
};

@Injectable({
  providedIn: 'root'
})
export class PostProductService {

  constructor(private httpClient: HttpClient) { }

  localhost = baseUrl + 'catalog/api/v1/products';

  //TODO: add the product object and the file to the backend
  postProduct(product: Product, fileToUpload: File): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('imageFile', fileToUpload, fileToUpload.name);
    formData.append('product', new Blob([JSON.stringify(product)], {type: 'application/json'}));
    console.log(formData.get('product'))
    return this.httpClient.post(this.localhost, formData, httpOptionsImage);
  }
  saveProduct(product)  {
    return this.httpClient.post(this.localhost, product, httpOptions);
  }
  getProductById(id)  {
    return this.httpClient.get(this.localhost + '/' + id);
  }
  getProductByCode(code)  {
    return this.httpClient.get(this.localhost + '/code/' + code);
  }
  deleteProduct(id)  {
    return this.httpClient.delete(this.localhost + '/' + id);
  }
  getAllProducts()  {
    return this.httpClient.get(this.localhost);
  }
  updateProduct(product)  {
    return this.httpClient.patch(this.localhost, product, httpOptions);
  }
}