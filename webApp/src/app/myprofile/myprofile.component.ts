import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CustomValidators } from '../helpers/custom-validators';
import { User } from '../models/User';
import { AuthenticationService } from '../services/authentication.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-myprofile',
  templateUrl: './myprofile.component.html',
  styleUrls: ['./myprofile.component.css'],
})
export class MyprofileComponent implements OnInit {
  form: FormGroup;
  user: User;
  message = '';
  email = '';
  password = '';
  fullName = '';
  isEmailOrPasswordChanged = false;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private userService: UserService,
    private authenticationService: AuthenticationService
  ) {
    this.form = this.fb.group({
      fullName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: [
        '',
        Validators.compose([
          Validators.required,
          CustomValidators.patternValidator(/\d/, { hasNumber: true }),
          CustomValidators.patternValidator(/[A-Z]/, { hasCapitalCase: true }),
          CustomValidators.patternValidator(/[a-z]/, { hasSmallCase: true }),
          Validators.minLength(6),
        ]),
      ],
    });

    this.user = new User();
  }

  ngOnInit(): void {
    this.retreiveUserInfo();
  }

  retreiveUserInfo() {
    this.userService
      .getUserByEmail(this.authenticationService.currentUserValue.email)
      .subscribe({
        next: (data: any) => {
          this.user.id = data.id;
          this.email = data.email;
          this.fullName = data.name;
          this.password = data.password;
          this.user.admin = data.admin;
          this.form.get('fullName').setValue(this.fullName);
          this.form.get('email').setValue(this.email);
          this.form.get('password').setValue(this.password);
        },
        error: (error) => {
          console.error(error);
        },
      });
  }

  onCancel() {
    this.form.get('fullName').setValue(this.fullName);
    this.form.get('email').setValue(this.email);
    this.form.get('password').setValue(this.password);
    this.message = '';
  }

  checkSave() {
    if (
      this.email === this.form.get('email').value &&
      this.password === this.form.get('password').value &&
      this.fullName == this.form.get('fullName').value
    ) {
      return true;
    } else {
      return false;
    }
  }

  onSubmit() {
    if (
      this.form.value.email === '' &&
      this.form.value.password === '' &&
      this.form.value.fullName === ''
    ) {
      this.message = 'All fields are required';
    } else if (this.form.value.email === '') {
      this.message = 'Email is required';
    } else if (this.form.value.password === '') {
      this.message = 'Password is required';
    } else if (this.form.value.fullName === '') {
      this.message = 'Full name is required';
    } else if (this.form.invalid) {
      this.message = 'Invalid field(s)!';
    } else {
      if (
        this.email != this.form.get('email').value ||
        this.password != this.form.get('password').value
      ) {
        this.isEmailOrPasswordChanged = true;
      }
      this.user.name = this.form.get('fullName').value;
      this.user.email = this.form.get('email').value;
      this.user.password = this.form.get('password').value;
      this.userService.updateUser(this.user).subscribe({
        next: (data: any) => {
          if (this.isEmailOrPasswordChanged) {
            this.message = 'Email or password update successful. Logging out!';
            setTimeout(() => this.router.navigate(['logout']), 1000);
          } else {
            this.message = 'User updated successfully!';
            setTimeout(() => this.router.navigate(['']), 1000);
          }
        },
        error: (error) => {
          if (error.status == '409' || error.status == '404') {
            this.message = error.error;
            console.error(error);
          } else {
            this.message = 'Failed to update!';
            console.error('Failed to update!', error);
          }
        },
      });
    }
  }
}
