import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form;
  
  // message to be display if user logged in or not
  message = '';

  isLogged: boolean;
  isRememberMe: boolean;

  constructor(private fb : FormBuilder) {
   
   }

  ngOnInit() {
    this.form = this.fb.group({
      email : ['', Validators.email],
      password : ['']
    })
    this.isLogged = false;
    this.isRememberMe = false;
  }

  onSubmit() {
    if(this.form.value.email === '' || this.form.value.password === '') {
      this.message = 'Email and Password should not be empty!!! Please verify details';
    }
   else if(this.form.invalid){
     this.message = "Email is not valid !";
   }
    else {
      const email: string = this.form.value.email;
      console.log(email);
      const password : string = this.form.value.password;
      console.log(password);

      this.message = "Login succesful";
      this.isLogged = true;
    }
  }
  clearForm() {
    this.form.reset();
  }
  onRememberMeChanged(value:boolean){
    this.isRememberMe = value;
    console.log(value);
  }
}
