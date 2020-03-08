import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PreviousCoursesComponent } from './previous-courses.component';

describe('PreviousCoursesComponent', () => {
  let component: PreviousCoursesComponent;
  let fixture: ComponentFixture<PreviousCoursesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PreviousCoursesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PreviousCoursesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
