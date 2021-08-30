import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { PostComponent } from '../post/post.component';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  numProductCart:number = 0;
  message:string ="";
  constructor(private router:Router, public dialog: MatDialog) { }

  ngOnInit(): void {
    this.numProductCart = 5;
  }
  onClickPost(){
    const dialogRef = this.dialog.open(PostComponent, {
      width: '350px',
    });
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
