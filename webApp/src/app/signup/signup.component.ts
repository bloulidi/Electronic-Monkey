import { CustomValidators } from '../Helpers/custom-validators';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})

export class SignupComponent implements OnInit {

  emailRegx = /^(([^<>+()\[\]\\.,;:\s@"-#$%&=]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,3}))$/;
  form;
  
  // message to be display if Issue added or not
  message = '';
  
  constructor(private fb: FormBuilder) {
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
      this.message = ""
    } 
  }

}