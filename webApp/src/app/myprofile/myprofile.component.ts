import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CustomValidators } from '../helpers/custom-validators';
import { User } from '../models/User';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-myprofile',
  templateUrl: './myprofile.component.html',
  styleUrls: ['./myprofile.component.css']
})
export class MyprofileComponent implements OnInit {
  form;
  user: User;
  message = '';
  id;
  email;
  password;
  fullName;
  isEmailOrPasswordChanged:boolean = false;

  constructor(private fb: FormBuilder, private router: Router, private userService: UserService) {
    
    this.form = this.fb.group({
      fullName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.compose([
        Validators.required,
        CustomValidators.patternValidator(/\d/, {hasNumber: true}),
        CustomValidators.patternValidator(/[A-Z]/, { hasCapitalCase: true }),
        CustomValidators.patternValidator(/[a-z]/, { hasSmallCase: true }),
        Validators.minLength(6)])
      ]
    });

    this.user = new User;
  }

  ngOnInit(): void {   
    this.retreiveUserInfo();
  }
  
  retreiveUserInfo() {
    let email = localStorage.getItem("username");
     this.userService.getUserByEmail(email).subscribe({
        next:(data:any) => {
            console.log(data);
            this.id = data.id;
            this.email = data.email;
            this.fullName = data.name;
            this.password = data.password;
            console.log(this.id);
            console.log(this.email);
            console.log(this.fullName);
            console.log(this.password);
            this.form.get('fullName').setValue(this.fullName);
            this.form.get('email').setValue(this.email);
            this.form.get('password').setValue(this.password);
        },
        error:error => {
          console.log(error);
        }
      });;
  }
  
  onCancel(){
    this.form.get('fullName').setValue(this.fullName);
    this.form.get('email').setValue(this.email);
    this.form.get('password').setValue(this.password);
    this.message='';
  }

  onSubmit() {
    if(this.form.value.email === '' 
      || this.form.value.password === '' 
      || this.form.value.fullName === '') {
        this.message = 'Fields should not be empty!!! Please verify details.';
    }
    else if(this.form.invalid){
      this.message = "Invalid email and/or password!";
    }
    else{

      if(this.email != this.form.get('email').value 
        || this.password != this.form.get('password').value){
          this.isEmailOrPasswordChanged = true;
        }
      this.user.id = this.id;
      this.user.name = this.form.get('fullName').value;
      this.user.email = this.form.get('email').value;
      this.user.password = this.form.get('password').value;
      this.userService.updateUser(this.user).subscribe({
        next:(data:any) => {
          console.log(data);
          if(this.isEmailOrPasswordChanged){
            this.router.navigate(['logout']);
          }
          else{this.router.navigate(['']);}
        },
        error: error => {
          console.log(this.user)
          this.message = "This email already exists.";
          console.log(error.error);
        }
      }
      );
    } 
  }

}
