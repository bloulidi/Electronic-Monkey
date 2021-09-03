import { UserService } from '../services/user.service';
import { CustomValidators } from '../helpers/custom-validators';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { User } from '../models/User';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})

export class SignupComponent implements OnInit {

  form;
  user: User;

  // message to be display if Issue added or not
  message = '';

  constructor(private fb: FormBuilder, private router: Router, private userService: UserService) {
    this.user = new User;
  }

  ngOnInit() {
    this.form = this.fb.group({
      fullName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.compose([
        Validators.required,
        CustomValidators.patternValidator(/\d/, { hasNumber: true }),
        CustomValidators.patternValidator(/[A-Z]/, { hasCapitalCase: true }),
        CustomValidators.patternValidator(/[a-z]/, { hasSmallCase: true }),
        Validators.minLength(6)])
      ],
      confirmPassword: ['', Validators.compose([Validators.required])]
    },
      {
        validator: CustomValidators.passwordMatchValidator
      })
  }

  submit() {
    if (this.form.value.fullName === '' && this.form.value.email === '' && this.form.value.password === '' && this.form.value.confirmPassword == '') {
      this.message = 'All fields are required';
    } else if (this.form.value.fullName === '') {
      this.message = 'Full Name is required';
    } else if (this.form.value.email === '') {
      this.message = 'Email is required';
    } else if (this.form.value.password === '') {
      this.message = 'Password is required';
    } else if (this.form.value.confirmPassword === '') {
      this.message = 'Confirm Password is required';
    } else if (this.form.invalid) {
      this.message = "Invalid email and/or password!";
    } else {
      this.user.name = this.form.value.fullName
      this.user.email = this.form.value.email
      this.user.password = this.form.value.password
      this.userService.saveUser(this.user).subscribe({
        next: res => {
          this.router.navigate(['login']);
        },
        error: error => {
          if (error.status == '409') {
            this.message = error.error;
            console.error(error);
          } else {
            this.message = "Failed to signup!";
            console.error("Failed to signup!", error);
          }
        }
      });
    }
  }
}