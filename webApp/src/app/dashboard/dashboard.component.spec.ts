import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatMenuModule } from '@angular/material/menu';
import { RouterTestingModule } from '@angular/router/testing';

import { DashboardComponent } from './dashboard.component';

describe('DashboardComponent', () => {
  let component: DashboardComponent;
  let fixture: ComponentFixture<DashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ HttpClientModule, ReactiveFormsModule, RouterTestingModule, MatMenuModule],
      declarations: [ DashboardComponent ],
      providers: []
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // test to check ngOnInit() method existence
  it('ngOnInit() should exists', () => {
    expect(component.ngOnInit).toBeTruthy();
  });

  it('onClickPost() should exists', () => {
    expect(component.onClickPost).toBeTruthy();
  });

  it('onClickLogOut() should exists', () => {
    expect(component.onClickLogOut).toBeTruthy();
  });

  it('onClickProfile() should exists', () => {
    expect(component.onClickMyProfile).toBeTruthy();
  });
});
