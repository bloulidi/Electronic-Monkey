import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { AuthenticationService } from '../services/authentication.service';
import { User } from '../models/User';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form: FormGroup;
  user: User;

  // message to be display if user logged in or not
  message = '';
  isRememberMe = false;

  constructor(private fb: FormBuilder, private route: ActivatedRoute, private router: Router, private authenticationService: AuthenticationService) {
    this.user = new User;
    // redirect to home if already logged in
    if (this.authenticationService.currentUserValue) {
      this.router.navigate(['/']);
    }
  }

  ngOnInit() {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    })
  }

  onSubmit() {
    if (this.form.value.email === '' && this.form.value.password === '') {
      this.message = 'All fields are required';
    } else if (this.form.value.email === '') {
      this.message = 'Email is required';
    } else if (this.form.value.password === '') {
      this.message = 'Password is required';
    } else if (this.form.invalid) {
      this.message = "Invalid email and/or password!";
    } else {
      this.user.email = this.form.value.email;
      this.user.password = this.form.value.password;
      this.authenticationService.login(this.user).subscribe({
        next: data => {
          this.router.navigate(['']);
        },
        error: error => {
          if (error.status == '404') {
            this.message = error.error;
            console.error(error);
          } else {
            this.message = "Failed to login!";
            console.error("Failed to login!", error);
          }
        }
      });
    }
  }

  clearForm() {
    this.form.reset();
  }

  onRememberMeChanged(value: boolean) {
    this.isRememberMe = value;
  }

  onClickSignUp() {
    this.router.navigate(['signup']);
  }
}