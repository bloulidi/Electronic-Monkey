import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { User } from '../models/User';
import { environment } from '../../environments/environment'

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({ providedIn: 'root' })
export class UserService {
  constructor(private httpClient: HttpClient) { }

  localhost = environment.apiUrl + 'user/api/v1/users';

  saveUser(user: any) {
    return this.httpClient.post(this.localhost + "/signup", user, httpOptions);
  }
  getAllUsers() {
    return this.httpClient.get(this.localhost);
  }
  getUserById(id: number) {
    return this.httpClient.get(this.localhost + '/' + id);
  }
  getUserByEmail(email: string) {
    return this.httpClient.get(this.localhost + '/email/' + email);
  }
  getUsersByName(name: string) {
    return this.httpClient.get(this.localhost + '/name/' + name);
  }
  getUsersByAdmin(admin: boolean) {
    return this.httpClient.get(this.localhost + '/admin/' + admin);
  }
  deleteUser(id: number) {
    return this.httpClient.delete(this.localhost + '/' + id);
  }
  updateUser(user: User) {
    return this.httpClient.patch(this.localhost, user, httpOptions);
  }
}
