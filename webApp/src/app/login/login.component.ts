import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from '../models/User';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form;
  user: User;
  
  // message to be display if user logged in or not
  message = '';

  isLoggedIn: boolean;
  isRememberMe: boolean;

  constructor(private fb : FormBuilder, private router:Router, private userService: UserService) {
    this.user = new User;
  }

  ngOnInit() {
    this.form = this.fb.group({
      email : ['', [Validators.required, Validators.email]],
      password : ['', [Validators.required, Validators.minLength(6)]]
    })
    this.isLoggedIn = false;
    this.isRememberMe = false;
  }

  onSubmit() {
    if(this.form.value.email === '' || this.form.value.password === '') {
      this.message = 'Email and Password should not be empty!!! Please verify details';
    }
   else if(this.form.invalid){
     this.message = "Invalid email and/or password!";
   }
    else {
      const email: string = this.form.value.email;
      console.log(email);
      const password : string = this.form.value.password;
      console.log(password);
      this.user.email = email
      this.user.password = password
      this.userService.loginUser(this.user).subscribe({
        next: (data:any) => {
          console.log(data);
          if(data.message == "Login Successful"){
            this.message = data.message;
            this.isLoggedIn = true;
          }
          this.router.navigate(['']);
      },
        error: error => {
          this.isLoggedIn = false;
          this.message = error.error;
          console.error('There was an error!', error);
        }
      });;
    }
  }
  clearForm() {
    this.form.reset();
  }
  onRememberMeChanged(value:boolean){
    this.isRememberMe = value;
    console.log(value);
  }
  onClickSignUp(){
    this.router.navigate(['signup']);
  }
}
