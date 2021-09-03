/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PostComponent } from './post.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { MatDialogModule, MatDialogRef } from '@angular/material/dialog';

describe('PostComponent', () => {
  let component: PostComponent;
  let fixture: ComponentFixture<PostComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, MatDialogModule, HttpClientTestingModule, RouterTestingModule],
      providers: [{ provide: MatDialogRef, useValue: {} }],
      declarations: [PostComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PostComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('onSubmit() should exists', () => {
    expect(component.submit).toBeTruthy();
  });

  it('ngOnInit() should exists', () => {
    expect(component.ngOnInit).toBeTruthy();
  });

  it('testing title field is required', () => {
    const title = component.form.controls.title;
    title.setValue('testName');
    expect(title).toBeTruthy();
  });

  it('testing empty title field is required', () => {
    const title = component.form.controls.title;
    title.setValue('');
    expect(title.valid).toBeFalsy();
  });
});