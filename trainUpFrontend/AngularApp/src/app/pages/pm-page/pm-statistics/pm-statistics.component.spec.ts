import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PmStatisticsComponent } from './pm-statistics.component';

describe('PmStatisticsComponent', () => {
  let component: PmStatisticsComponent;
  let fixture: ComponentFixture<PmStatisticsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PmStatisticsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PmStatisticsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
