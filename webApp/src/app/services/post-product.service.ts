import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
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

  localhost = baseUrl + 'user/api/v1/users';

  //TODO: add the product object and the file to the backend
  postProduct(product: Product, fileToUpload: File): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('fileKey', fileToUpload, fileToUpload.name);
    return this.httpClient.post(this.localhost, formData, httpOptions);
  }
}
