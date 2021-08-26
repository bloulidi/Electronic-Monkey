import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  numProductCart:number = 0;
  message:string ="";
  constructor(private router:Router) { }

  ngOnInit(): void {
    this.numProductCart = 5;
  }
  onClickPost(){
    this.message ="Add a Product";
    console.log(this.message);
  }

  onClickLogOut(){
    this.router.navigate(['logout']);
  }

  onClickMyProfile(){
    this.router.navigate(['myprofile']);
  }

}
