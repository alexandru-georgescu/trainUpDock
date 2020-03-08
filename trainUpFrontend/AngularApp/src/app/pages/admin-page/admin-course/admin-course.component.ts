import { Component, OnInit } from '@angular/core';
import { Course } from 'src/app/models/course';
import { CourseService } from 'src/app/services/course-service.service';
import { ToastrService } from 'ngx-toastr';
import { User } from 'src/app/models/user';
import { Sort } from '@angular/material/sort';
import { UserService } from 'src/app/services/user-service.service';


@Component({
  selector: 'app-admin-course',
  templateUrl: './admin-course.component.html',
  styleUrls: ['./admin-course.component.css']
})
export class AdminCourseComponent implements OnInit {

  user: User;

  courses: Course[];
  sortedDataCourse: Course[];
  defaultCourses: Course[];
  domains : string[];
  selectedCoursePm : string[];
  selectedCourseDomain : string[];
  projectManagers : string[];


 

  constructor(    private courseService: CourseService,
    private toastr: ToastrService,
    private userService: UserService,
    ) {
    this.domains = ['RCA', 'GTB', 'NFR', 'PWCC'];
   }

  ngOnInit() {
    this.user = JSON.parse(localStorage.getItem('currentUser'));
    this.userService.usersFindAll().subscribe(users => {
      this.projectManagers = users.filter(u => u.type === 'PMSOFT' || u.type == 'PMTECH' || u.type == 'PMPROC').map(u => u.email);

    this.courseService.getAllCourses().subscribe(courses => {
      this.courses = courses;
      this.sortedDataCourse = this.courses;
      this.updateCourseData();
      if (this.defaultCourses === undefined) {
        this.defaultCourses = JSON.parse(JSON.stringify(this.courses));
      }
    });});
  }

  sortDataCourse(sort: Sort) {
    const data = this.courses;
    if (!sort.active || sort.direction === '') {
      this.sortedDataCourse = data;
      return;
    }
    this.sortedDataCourse = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'id': return compare(a.id, b.id, isAsc);
        case 'courseName': return compare(a.courseName, b.courseName, isAsc);
        case 'capacity': return compare(a.capacity, b.capacity, isAsc);
        case 'actualCapacity': return compare(a.actualCapacity, b.actualCapacity, isAsc);
        case 'projectManager': return compare(a.projectManager, b.projectManager, isAsc);
        case 'startDate': return compare(a.startDate.toString(), b.startDate.toString(), isAsc);
        case 'endDate': return compare(a.startDate.toString(), b.startDate.toString(), isAsc);
        case 'domain': return compare(a.domain, b.domain, isAsc);
        case 'timeInterval': return compare(a.timeInterval, b.timeInterval, isAsc);
        default: return 0;
      }
    });
    this.updateCourseData();
  }

  updateCourseData() {
    this.selectedCoursePm = this.sortedDataCourse.map(c => c.projectManager);
    this.selectedCourseDomain = this.sortedDataCourse.map(c => c.domain);
  }


  resetChangesCourse() {
    this.courses = this.defaultCourses;
    this.ngOnInit();
  }

  saveChangesCourse() {
    if (confirm("This changes can't be undone. Are you sure?")){
    this.defaultCourses = JSON.parse(JSON.stringify(this.courses));
    this.courseService.updateCourses(this.courses).subscribe(a => {this.ngOnInit()},
    error => {
      this.toastr.error("Failed request", "Fail!", {
        timeOut: 3000,
        positionClass: 'toast-bottom-right'
      })
    },
    () => this.toastr.success("Data has been saved", "Success!", {
      timeOut: 2000,
      positionClass: 'toast-bottom-right'
    }));
  }
  }
  
  changeProjectManager(index: number, newProjectManager: string) {
    this.sortedDataCourse[index].projectManager = newProjectManager;
    this.courses = this.sortedDataCourse;
  }

  changeDomain(index: number, newDomain: string) {
    this.sortedDataCourse[index].domain = newDomain;
    this.courses = this.sortedDataCourse;
  }

  
  deleteCourse(course : Course) {
    if(confirm("Course "+ course.courseName + " will be deleted. Are you sure?")) {
      this.courseService.removeCourseById(course.id.toString()).subscribe(a => {this.ngOnInit()},
      error => {
        this.toastr.error("Failed request", "Fail!", {
          timeOut: 3000,
          positionClass: 'toast-bottom-right'
        })
      },
      () => this.toastr.success("Course has been deleted", "Success!", {
        timeOut: 2000,
        positionClass: 'toast-bottom-right'
      }));
    }
  }
}

function compare(a: number | string, b: number | string, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}
