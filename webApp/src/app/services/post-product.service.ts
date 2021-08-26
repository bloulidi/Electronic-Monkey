import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { baseUrl } from '../global-variables';
import { Product } from '../models/Product';

const httpOptions = {
  headers: new HttpHeaders({ 'Accept': "multipart/form-data" })
};

@Injectable({
  providedIn: 'root'
})
export class PostProductService {

  constructor(private httpClient: HttpClient) { }

  localhost = baseUrl + 'catalog/api/v1/products';

  //TODO: Ask how to send multiple arguments with POST method.
  //? Is formData still a file or is it still a file ????

  postProduct(product: Product, fileToUpload: File): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('imageFile', fileToUpload, fileToUpload.name);
    formData.append('product', new Blob([JSON.stringify(product)], {type: 'application/json'}));
    console.log(formData.get('product'))
    return this.httpClient.post(this.localhost, formData, httpOptions);
  }
}
