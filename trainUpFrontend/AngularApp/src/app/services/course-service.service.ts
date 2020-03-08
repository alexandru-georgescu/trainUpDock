import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Course } from '../models/course';
import { Observable } from 'rxjs';
import { Consts } from '../consts';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class CourseService {

  constructor(private http: HttpClient) {
  }

  public addCourse(data: Course): Observable<Course> {
    let headers_object = new HttpHeaders();
    headers_object = headers_object.append('Content-Type', 'application/json');
    headers_object = headers_object.append('Authorization', 'Basic ' + btoa('admin:AlexGAdmin'));

    const httpOptions = {
      headers: headers_object
    };
    return this.http.post<Course>(Consts.backUrl + 'course/add', data, httpOptions);
  }

  public getPmCourses(data: User): Observable<Course[]> {
    let headers_object = new HttpHeaders();
    headers_object = headers_object.append('Content-Type', 'application/json');
    headers_object = headers_object.append('Authorization', 'Basic ' + btoa('admin:AlexGAdmin'));

    const httpOptions = {
      headers: headers_object
    };
    return this.http.post<Course[]>(Consts.backUrl + 'course/findByPm', data, httpOptions);
  }

  public getRejectedUsers(data: Course): Observable<User[]> {
    let headers_object = new HttpHeaders();
    headers_object = headers_object.append('Content-Type', 'application/json');
    headers_object = headers_object.append('Authorization', 'Basic ' + btoa('admin:AlexGAdmin'));

    const httpOptions = {
      headers: headers_object
    };
    return this.http.post<User[]>(Consts.backUrl + 'course/findRejectedByPm', data, httpOptions);
  }

  public getAcceptedUsers(data: Course): Observable<User[]> {
    let headers_object = new HttpHeaders();
    headers_object = headers_object.append('Content-Type', 'application/json');
    headers_object = headers_object.append('Authorization', 'Basic ' + btoa('admin:AlexGAdmin'));

    const httpOptions = {
      headers: headers_object
    };
    return this.http.post<User[]>(Consts.backUrl + 'course/findAcceptedByPm', data, httpOptions);
  }

  public getAllCourses(): Observable<any> {
    let headers_object = new HttpHeaders();
    headers_object = headers_object.append('Content-Type', 'application/json');
    headers_object = headers_object.append('Authorization', 'Basic ' + btoa('admin:AlexGAdmin'));

    const httpOptions = {
      headers: headers_object
    };
    return this.http.get<any>(Consts.backUrl + 'course', httpOptions);
  }

  public removeCourseById(data: String): Observable<any> {
    let headers_object = new HttpHeaders();
    headers_object = headers_object.append('Content-Type', 'application/json');
    headers_object = headers_object.append('Authorization', 'Basic ' + btoa('admin:AlexGAdmin'));

    const httpOptions = {
      headers: headers_object
    };
    return this.http.get<any>(Consts.backUrl + 'course/remove?id=' + data, httpOptions);
  }

  public updateCourses(courses: Course[]): Observable<any> {
    let headers_object = new HttpHeaders();
    headers_object = headers_object.append('Content-Type', 'application/json');
    headers_object = headers_object.append('Authorization', 'Basic ' + btoa('admin:AlexGAdmin'));

    const httpOptions = {
      headers: headers_object
    };
    return this.http.post<any>(Consts.backUrl + 'course/update_courses', courses, httpOptions);
  }
}