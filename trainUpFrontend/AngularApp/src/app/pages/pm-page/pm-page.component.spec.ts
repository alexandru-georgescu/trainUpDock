import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PmPageComponent } from './pm-page.component';

describe('PmPageComponent', () => {
  let component: PmPageComponent;
  let fixture: ComponentFixture<PmPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PmPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PmPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
