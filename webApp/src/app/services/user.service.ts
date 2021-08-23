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
    return this.httpClient.post(this.localhost + "user", user, httpOptions);
  }
  getAllUsers()  {
    return this.httpClient.get(this.localhost + 'users');
  }
  getUserById(id)  {
    return this.httpClient.get(this.localhost + 'user/' + id);
  }
  getUserByEmail(email)  {
    return this.httpClient.get(this.localhost + 'user/email/' + email);
  }
  getUsersByName(name)  {
    return this.httpClient.get(this.localhost + 'users/' + name);
  }
  getUsersByAdmin(admin)  {
    return this.httpClient.get(this.localhost + 'users/admin/' + admin);
  }
  deleteUser(id)  {
    return this.httpClient.delete(this.localhost + 'user/' + id);
  }
  updateUser(user)  {
    return this.httpClient.patch(this.localhost + 'user', user, httpOptions);
  }
}
