import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

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
  onClickMyPosts(){
    this.router.navigate(['myposts']);
  }

}
