import { Component, OnInit } from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import { PostComponent } from '../Post/Post.component';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  ngOnInit(): void {
  }

  constructor(public dialog: MatDialog) {}

  openDialog(): void {
    const dialogRef = this.dialog.open(PostComponent, {
      width: '350px',
    });
  }

}
