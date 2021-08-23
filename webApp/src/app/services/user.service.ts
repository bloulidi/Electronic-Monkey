import { User } from '../models/User';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})

export class UserService {
  constructor(private httpClient : HttpClient) { }

  localhost = 'http://localhost:8080/api/v1/';

  saveUser(user)  {
    return this.httpClient.post(this.localhost + "users", user, httpOptions);
  }
  getAllUsers()  {
    return this.httpClient.get(this.localhost + 'users');
  }
  getUserById(id)  {
    return this.httpClient.get(this.localhost + 'users/' + id);
  }
  getUserByEmail(email)  {
    return this.httpClient.get(this.localhost + 'users/email/' + email);
  }
  getUsersByName(name)  {
    return this.httpClient.get(this.localhost + 'users/name/' + name);
  }
  getUsersByAdmin(admin)  {
    return this.httpClient.get(this.localhost + 'users/admin/' + admin);
  }
  deleteUser(id)  {
    return this.httpClient.delete(this.localhost + 'users/' + id);
  }
  updateUser(user)  {
    return this.httpClient.patch(this.localhost + 'users', user, httpOptions);
  }
}
