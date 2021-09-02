import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatMenuModule } from '@angular/material/menu';
import { RouterTestingModule } from '@angular/router/testing';

import { HeaderComponent } from './header.component';

describe('HeaderComponent', () => {
  let component: HeaderComponent;
  let fixture: ComponentFixture<HeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, HttpClientTestingModule, RouterTestingModule, MatDialogModule, MatMenuModule],
      declarations: [HeaderComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('ngOnInit() should exists', () => {
    expect(component.ngOnInit).toBeTruthy();
  });

  it('onClickPost() should exists', () => {
    expect(component.onClickPost).toBeTruthy();
  });

  it('onClickLogOut() should exists', () => {
    expect(component.onClickLogOut).toBeTruthy();
  });

  it('onClickMyProfile() should exists', () => {
    expect(component.onClickMyProfile).toBeTruthy();
  });

  it('onClickMyPosts() should exists', () => {
    expect(component.onClickMyPosts).toBeTruthy();
  });

  it('onClickPhones() should exists', () => {
    expect(component.onClickPhones).toBeTruthy();
  });

  it('onClickComputers() should exists', () => {
    expect(component.onClickComputers).toBeTruthy();
  });

  it('onClickAccessories() should exists', () => {
    expect(component.onClickAccessories).toBeTruthy();
  });
});