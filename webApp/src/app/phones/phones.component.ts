import { Component } from '@angular/core';
import { CategoryComponent } from '../category/category.component';

@Component({
  selector: 'app-phones',
  templateUrl: '../category/category.component.html',
  styleUrls: ['../category/category.component.css'],
})
export class PhonesComponent extends CategoryComponent {
  category = 'Phones';

  ngOnInit(): void {
    super.ngOnInit();
  }
}
