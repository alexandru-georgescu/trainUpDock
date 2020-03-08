import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NextCoursesComponent } from './next-courses.component';

describe('NextCoursesComponent', () => {
  let component: NextCoursesComponent;
  let fixture: ComponentFixture<NextCoursesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NextCoursesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NextCoursesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
