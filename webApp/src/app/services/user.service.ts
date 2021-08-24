import { User } from '../models/User';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { baseUrl } from '../global-variables';
import { map } from "rxjs/operators";

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})

export class UserService {
  constructor(private httpClient : HttpClient) { }

  localhost = baseUrl + 'user/api/v1/users';

  saveUser(user)  {
    return this.httpClient.post(this.localhost, user, httpOptions);
  }
  loginUser(user)  {
    return this.httpClient.post(this.localhost + '/login', user, httpOptions).pipe(
      map((data:any) => {
        sessionStorage.setItem("username", user.email);
        let tokenStr = "Bearer " + data.token;
        sessionStorage.setItem("token", tokenStr);
        return data;
      })
    );
  }
  isUserLoggedIn() {
    let user = sessionStorage.getItem("username");
    let isLoggedIn:boolean = !(user === null);
    if(isLoggedIn) console.log(user + " is logged in")
    else console.log("Not logged in")
    return isLoggedIn;
  }
  logOut() {
    sessionStorage.removeItem("username");
    console.log("logout")
  }
  getAllUsers()  {
    return this.httpClient.get(this.localhost);
  }
  getUserById(id)  {
    return this.httpClient.get(this.localhost + '/' + id);
  }
  getUserByEmail(email)  {
    return this.httpClient.get(this.localhost + '/email/' + email);
  }
  getUsersByName(name)  {
    return this.httpClient.get(this.localhost + '/name/' + name);
  }
  getUsersByAdmin(admin)  {
    return this.httpClient.get(this.localhost + '/admin/' + admin);
  }
  deleteUser(id)  {
    return this.httpClient.delete(this.localhost + '/' + id);
  }
  updateUser(user)  {
    return this.httpClient.patch(this.localhost, user, httpOptions);
  }
}
