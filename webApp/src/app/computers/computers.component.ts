import { Component } from '@angular/core';
import { CategoryComponent } from '../category/category.component';

@Component({
  selector: 'app-computers',
  templateUrl: '../category/category.component.html',
  styleUrls: ['../category/category.component.css'],
})
export class ComputersComponent extends CategoryComponent {
  category = 'Computers';

  ngOnInit(): void {
    super.ngOnInit();
  }
}
