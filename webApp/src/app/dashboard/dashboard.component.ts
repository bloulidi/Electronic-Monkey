import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  ngOnInit(): void {
  }

  constructor(private router:Router) { }
  
  onClickPhones(){
    this.router.navigate(['phones']);
  }
  onClickComputers(){
    this.router.navigate(['computers']);
  }
  onClickAccessories(){
    this.router.navigate(['accessories']);
  }
}
