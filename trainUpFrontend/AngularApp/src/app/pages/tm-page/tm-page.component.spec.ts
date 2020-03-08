import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TmPageComponent } from './tm-page.component';

describe('TmPageComponent', () => {
  let component: TmPageComponent;
  let fixture: ComponentFixture<TmPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TmPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TmPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
