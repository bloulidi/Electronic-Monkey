import { Component } from '@angular/core';
import { CategoryComponent } from '../category/category.component';

@Component({
  selector: 'app-accessories',
  templateUrl: '../category/category.component.html',
  styleUrls: ['../category/category.component.css'],
})
export class AccessoriesComponent extends CategoryComponent {
  category = 'Accessories';

  ngOnInit(): void {
    super.ngOnInit();
  }
}
