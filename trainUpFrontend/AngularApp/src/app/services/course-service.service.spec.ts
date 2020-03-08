    
import { TestBed } from '@angular/core/testing';

import { CourseService } from './course-service.service';

describe('CourseServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CourseService = TestBed.get(CourseService);
    expect(service).toBeTruthy();
  });
});