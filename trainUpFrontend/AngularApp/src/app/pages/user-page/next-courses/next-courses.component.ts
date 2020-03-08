import { Component, OnInit } from '@angular/core';
import { ShareService } from 'src/app/services/share.service';
import { User } from 'src/app/models/user';
import { Course } from 'src/app/models/course';
import { UserService } from 'src/app/services/user-service.service';
import { Sort } from '@angular/material';

@Component({
  selector: 'app-next-courses',
  templateUrl: './next-courses.component.html',
  styleUrls: ['./next-courses.component.css']
})
export class NextCoursesComponent implements OnInit {

  user: User;
  courses: Course[];
  sortedData: Course[];


  constructor(private share: ShareService,
    private userService: UserService,
  ) { }

  sortData(sort: Sort) {
    const data = this.courses.slice();
    if (!sort.active || sort.direction === '') {
      this.sortedData = data;
      return;
    }
    this.sortedData = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'courseName': return compare(a.courseName, b.courseName, isAsc);
        case 'capacity': return compare(a.capacity, b.capacity, isAsc);
        case 'actualCapacity': return compare(a.actualCapacity, b.actualCapacity, isAsc);
        default: return 0;
      }
    });
  }

  ngOnInit() {
    this.share.currentMessage.subscribe(data => this.user = JSON.parse(data));
    this.userService.getFutureCourses().subscribe(data =>  { this.courses = data , this.sortedData = this.courses.slice();});

  }

  rowClick(course: Course): void {
    this.userService.addWishToEnroll(this.user, course).subscribe(data => this.user = data);
    localStorage.setItem('currentUser', JSON.stringify(this.user));

  }

}

function compare(a: number | string, b: number | string, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}
