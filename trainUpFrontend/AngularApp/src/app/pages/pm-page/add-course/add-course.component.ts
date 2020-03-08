import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Course } from 'src/app/models/course';
import { CourseService } from 'src/app/services/course-service.service';
import { User } from 'src/app/models/user';

@Component({
  selector: 'app-add-course',
  templateUrl: './add-course.component.html',
  styleUrls: ['./add-course.component.css']
})
export class AddCourseComponent implements OnInit {

  courseForm: FormGroup;
  course: Course;
  user: User;
  submitted = false;
  alreadyRegistered = false;
  minDate = new Date();

  constructor(
    public dialogRef: MatDialogRef<AddCourseComponent>,
    private formBuilder: FormBuilder,
    private courseService: CourseService) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onClick() {
    this.submitted = true;

    if (this.courseForm.invalid) {
      return;
    }

    this.dialogRef.close();
    this.user = JSON.parse(localStorage.getItem('currentUser'));
    let courseType = null;
    if (this.user.type === "PMSOFT") {
      courseType = "SOFT";
    }

    if (this.user.type === "PMPROC") {
      courseType = "PROCESS";
    }

    if (this.user.type === "PMTECH") {
      courseType = "TECH";
    }

    let startDate = new Date(this.controls.startDate.value);
    let endDate = new Date(this.controls.endDate.value);

    startDate = new Date(startDate.getTime() + Math.abs(startDate.getTimezoneOffset() * 60000));
    endDate = new Date(endDate.getTime() + Math.abs(endDate.getTimezoneOffset() * 60000));
    let course = new Course(this.controls.name.value, this.controls.capacity.value, this.controls.capacity.value, startDate, endDate, this.user.email, this.controls.domain.value, this.controls.timeInterval.value, courseType);
    this.courseService.addCourse(course).subscribe(data => {
      this.course = data;
    });

  }

  get controls() {
    return this.courseForm.controls;
  }

  ngOnInit() {
    this.courseForm = this.formBuilder.group({
      name: ['', Validators.required],
      capacity: ['', Validators.required],
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
      domain: ['', Validators.required],
      timeInterval: ['', Validators.required]
    });
  }
}