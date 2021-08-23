import { SignupService } from './../services/signup.service';
import { CustomValidators } from '../helpers/custom-validators';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { User } from '../models/User';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})

export class SignupComponent implements OnInit {

  emailRegx = /^(([^<>+()\[\]\\.,;:\s@"-#$%&=]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,3}))$/;
  form;
  user: User;
  
  // message to be display if Issue added or not
  message = '';
  
  constructor(private fb: FormBuilder, private signupService: SignupService) {
    this.user = new User;
  }

  //TODO: Fix the Validation in the UI......
  ngOnInit() {
    this.form = this.fb.group({
      fullName: [null, Validators.required],
      email: [null, [Validators.required, Validators.pattern(this.emailRegx)]],
      password: [null, Validators.compose([
        Validators.required,
        CustomValidators.patternValidator(/\d/, {hasNumber: true}),
        CustomValidators.patternValidator(/[A-Z]/, { hasCapitalCase: true }),
        CustomValidators.patternValidator(/[a-z]/, { hasSmallCase: true }),
        Validators.minLength(6)])
      ],
      confirmPassword: [null, Validators.compose([Validators.required])]
    },
    {
      validator: CustomValidators.passwordMatchValidator
    })
  }

  submit() {
    if(this.form.invalid){
      this.message = "Fields should not be empty!!! Please verify details";
    }
    else{
      this.user.admin = false
      this.user.name = this.form.get('fullName').value
      this.user.email = this.form.get('email').value
      this.user.password = this.form.get('password').value
      this.signupService.saveUser(this.user).subscribe();
    } 
  }

}