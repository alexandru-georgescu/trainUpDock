import { Injectable } from '@angular/core';
import { User } from '../models/user';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Consts } from '../consts'
import { Course } from '../models/course';

@Injectable()
export class UserService {

  constructor(private http: HttpClient) {
  }

  public register(data: User): Observable<User> {
    let headers_object = new HttpHeaders();
    headers_object = headers_object.append('Content-Type', 'application/json');
    headers_object = headers_object.append('Authorization', 'Basic ' + btoa('admin:AlexGAdmin'));

    const httpOptions = {
      headers: headers_object
    };
    return this.http.post<User>(Consts.backUrl + 'user/register', data, httpOptions);
  }

  public login(data: User): Observable<User> {
    let headers_object = new HttpHeaders();
    headers_object = headers_object.append('Content-Type', 'application/json');
    headers_object = headers_object.append('Authorization', 'Basic ' + btoa('admin:AlexGAdmin'));

    const httpOptions = {
      headers: headers_object
    };
    return this.http.post<User>(Consts.backUrl + 'user/login', data, httpOptions);
  }

  public addWishToEnroll(user: User, course: Course): Observable<User> {
    let data = JSON.stringify({ user: user, course: course });
    let headers_object = new HttpHeaders();
    headers_object = headers_object.append('Content-Type', 'application/json');
    headers_object = headers_object.append('Authorization', 'Basic ' + btoa('admin:AlexGAdmin'));

    const httpOptions = {
      headers: headers_object
    };
    return this.http.post<User>(Consts.backUrl + 'user/wish', data, httpOptions);
  }

  public getCurrentCourses(data: User): Observable<Course[]> {
    let headers_object = new HttpHeaders();
    headers_object = headers_object.append('Content-Type', 'application/json');
    headers_object = headers_object.append('Authorization', 'Basic ' + btoa('admin:AlexGAdmin'));

    const httpOptions = {
      headers: headers_object
    };
    return this.http.post<Course[]>(Consts.backUrl + 'course/isCurrent', data, httpOptions);
  }

  public getPreviousCourses(data: User): Observable<Course[]> {
    let headers_object = new HttpHeaders();
    headers_object = headers_object.append('Content-Type', 'application/json');
    headers_object = headers_object.append('Authorization', 'Basic ' + btoa('admin:AlexGAdmin'));

    const httpOptions = {
      headers: headers_object
    };
    return this.http.post<Course[]>(Consts.backUrl + 'course/isBefore', data, httpOptions);
  }

  public getFutureCourses(user: User): Observable<Course[]> {
    let headers_object = new HttpHeaders();
    headers_object = headers_object.append('Content-Type', 'application/json');
    headers_object = headers_object.append('Authorization', 'Basic ' + btoa('admin:AlexGAdmin'));

    const httpOptions = {
      headers: headers_object
    };
    return this.http.post<Course[]>(Consts.backUrl + 'course/isFuture', user, httpOptions);
  }

  public getTMUsers(data: String): Observable<User[]> {
    let headers_object = new HttpHeaders();
    headers_object = headers_object.append('Content-Type', 'application/json');
    headers_object = headers_object.append('Authorization', 'Basic ' + btoa('admin:AlexGAdmin'));

    const httpOptions = {
      headers: headers_object
    };
    return this.http.get<User[]>(Consts.backUrl + 'user/findByLeader?leader=' + data, httpOptions);
  }

  public addWaitToEnroll(user: User, course: Course): Observable<User> {
    let data = JSON.stringify({ user: user, course: course });
    let headers_object = new HttpHeaders();
    headers_object = headers_object.append('Content-Type', 'application/json');
    headers_object = headers_object.append('Authorization', 'Basic ' + btoa('admin:AlexGAdmin'));

    const httpOptions = {
      headers: headers_object
    };
    return this.http.post<User>(Consts.backUrl + 'user/waitToEnroll', data, httpOptions);
  }

  public refuseToEnroll(user: User, course: Course): Observable<User> {
    let data = JSON.stringify({ user: user, course: course });
    let headers_object = new HttpHeaders();
    headers_object = headers_object.append('Content-Type', 'application/json');
    headers_object = headers_object.append('Authorization', 'Basic ' + btoa('admin:AlexGAdmin'));

    const httpOptions = {
      headers: headers_object
    };
    return this.http.post<User>(Consts.backUrl + 'user/refuseToEnroll', data, httpOptions);
  }

  public getWaitUserCourses(course: Course): Observable<User[]> {
    let headers_object = new HttpHeaders();
    headers_object = headers_object.append('Content-Type', 'application/json');
    headers_object = headers_object.append('Authorization', 'Basic ' + btoa('admin:AlexGAdmin'));

    const httpOptions = {
      headers: headers_object
    };
    return this.http.post<User[]>(Consts.backUrl + 'user/findWaitByCourse', course, httpOptions);
  }

  public acceptCourse(user: User, course: Course): Observable<User> {
    let data = JSON.stringify({ user: user, course: course });
    let headers_object = new HttpHeaders();
    headers_object = headers_object.append('Content-Type', 'application/json');
    headers_object = headers_object.append('Authorization', 'Basic ' + btoa('admin:AlexGAdmin'));

    const httpOptions = {
      headers: headers_object
    };
    return this.http.post<User>(Consts.backUrl + 'user/acceptFromWait', data, httpOptions);
  }

  public denyCourse(user: User, course: Course): Observable<User> {
    let data = JSON.stringify({ user: user, course: course });
    let headers_object = new HttpHeaders();
    headers_object = headers_object.append('Content-Type', 'application/json');
    headers_object = headers_object.append('Authorization', 'Basic ' + btoa('admin:AlexGAdmin'));

    const httpOptions = {
      headers: headers_object
    };
    return this.http.post<User>(Consts.backUrl + 'user/rejectFromWait', data, httpOptions);
  }

  public getUser(data: String): Observable<User> {
    let headers_object = new HttpHeaders();
    headers_object = headers_object.append('Content-Type', 'application/json');
    headers_object = headers_object.append('Authorization', 'Basic ' + btoa('admin:AlexGAdmin'));

    const httpOptions = {
      headers: headers_object
    };
    return this.http.get<User>(Consts.backUrl + 'user/findByName?name=' + data, httpOptions);
  }

  public resetPassword(user: User): Observable<User> {
    let headers_object = new HttpHeaders();
    headers_object = headers_object.append('Content-Type', 'application/json');
    headers_object = headers_object.append('Authorization', 'Basic ' + btoa('admin:AlexGAdmin'));

    const httpOptions = {
      headers: headers_object
    };
    return this.http.post<User>(Consts.backUrl + 'trainup/reset_pass', user, httpOptions);
  }

  public usersFindAll(): Observable<User[]> {
    let headers_object = new HttpHeaders();
    headers_object = headers_object.append('Content-Type', 'application/json');
    headers_object = headers_object.append('Authorization', 'Basic ' + btoa('admin:AlexGAdmin'));

    const httpOptions = {
      headers: headers_object
    };
    return this.http.get<User[]>(Consts.backUrl + 'user', httpOptions);
  }

  public acceptedParticipants(user: User): Observable<number> {
    let headers_object = new HttpHeaders();
    headers_object = headers_object.append('Content-Type', 'application/json');
    headers_object = headers_object.append('Authorization', 'Basic ' + btoa('admin:AlexGAdmin'));

    const httpOptions = {
      headers: headers_object
    };
    return this.http.post<number>(Consts.backUrl + 'tm_statistics/accepted', user, httpOptions);
  }

  public rejectedParticipants(user: User): Observable<number> {
    let headers_object = new HttpHeaders();
    headers_object = headers_object.append('Content-Type', 'application/json');
    headers_object = headers_object.append('Authorization', 'Basic ' + btoa('admin:AlexGAdmin'));

    const httpOptions = {
      headers: headers_object
    };
    return this.http.post<number>(Consts.backUrl + 'tm_statistics/rejected', user, httpOptions);
  }

  public updateUsers(users: User[]): Observable<any> {
    let headers_object = new HttpHeaders();
    headers_object = headers_object.append('Content-Type', 'application/json');
    headers_object = headers_object.append('Authorization', 'Basic ' + btoa('admin:AlexGAdmin'));

    const httpOptions = {
      headers: headers_object
    };
    return this.http.post<any>(Consts.backUrl + 'user/update_users', users, httpOptions);
  }

  public enrollRejected(user: User, course: Course): Observable<User> {
    let data = JSON.stringify({ user: user, course: course });
    let headers_object = new HttpHeaders();
    headers_object = headers_object.append('Content-Type', 'application/json');
    headers_object = headers_object.append('Authorization', 'Basic ' + btoa('admin:AlexGAdmin'));

    const httpOptions = {
      headers: headers_object
    };
    return this.http.post<User>(Consts.backUrl + 'user/moveToAccepted', data, httpOptions);
  }

  public kickAccepted(user: User, course: Course): Observable<User> {
    let data = JSON.stringify({ user: user, course: course });
    let headers_object = new HttpHeaders();
    headers_object = headers_object.append('Content-Type', 'application/json');
    headers_object = headers_object.append('Authorization', 'Basic ' + btoa('admin:AlexGAdmin'));

    const httpOptions = {
      headers: headers_object
    };
    return this.http.post<User>(Consts.backUrl + 'user/moveToRejected', data, httpOptions);
  }

  public acceptAll(users: User[], course: Course): Observable<User[]> {
    let data = JSON.stringify({ users: users, course: course });
    let headers_object = new HttpHeaders();
    headers_object = headers_object.append('Content-Type', 'application/json');
    headers_object = headers_object.append('Authorization', 'Basic ' + btoa('admin:AlexGAdmin'));

    const httpOptions = {
      headers: headers_object
    };
    return this.http.post<User[]>(Consts.backUrl + 'user/acceptAll', data, httpOptions);
  }

  public attendedDays(user: User): Observable<number> {
    let headers_object = new HttpHeaders();
    headers_object = headers_object.append('Content-Type', 'application/json');
    headers_object = headers_object.append('Authorization', 'Basic ' + btoa('admin:AlexGAdmin'));

    const httpOptions = {
      headers: headers_object
    };
    return this.http.post<number>(Consts.backUrl + 'user_statistics/days', user, httpOptions);
  }

  public upcomingDays(user: User): Observable<number> {
    let headers_object = new HttpHeaders();
    headers_object = headers_object.append('Content-Type', 'application/json');
    headers_object = headers_object.append('Authorization', 'Basic ' + btoa('admin:AlexGAdmin'));

    const httpOptions = {
      headers: headers_object
    };
    return this.http.post<number>(Consts.backUrl + 'user_statistics/days_left', user, httpOptions);
  }

  public courseStatistic(user: User): Observable<number[]> {
    let headers_object = new HttpHeaders();
    headers_object = headers_object.append('Content-Type', 'application/json');
    headers_object = headers_object.append('Authorization', 'Basic ' + btoa('admin:AlexGAdmin'));

    const httpOptions = {
      headers: headers_object
    };
    return this.http.post<number[]>(Consts.backUrl + 'user_statistics/course_statistic', user, httpOptions);
  }

  public removeUserById(data: String): Observable<any> {
    let headers_object = new HttpHeaders();
    headers_object = headers_object.append('Content-Type', 'application/json');
    headers_object = headers_object.append('Authorization', 'Basic ' + btoa('admin:AlexGAdmin'));

    const httpOptions = {
      headers: headers_object
    };
    return this.http.get<any>(Consts.backUrl + 'user/remove?id=' + data, httpOptions);
  }

  public typeStatistic(user: User): Observable<number[]> {
    let headers_object = new HttpHeaders();
    headers_object = headers_object.append('Content-Type', 'application/json');
    headers_object = headers_object.append('Authorization', 'Basic ' + btoa('admin:AlexGAdmin'));

    const httpOptions = {
      headers: headers_object
    };
    return this.http.post<number[]>(Consts.backUrl + 'user_statistics/type_statistic', user, httpOptions);
  }

  public yearStatistic(user: User): Observable<number[]> {
    let headers_object = new HttpHeaders();
    headers_object = headers_object.append('Content-Type', 'application/json');
    headers_object = headers_object.append('Authorization', 'Basic ' + btoa('admin:AlexGAdmin'));

    const httpOptions = {
      headers: headers_object
    };
    return this.http.post<number[]>(Consts.backUrl + 'tm_statistics/year_statistic', user, httpOptions);
  }

  public maxEnrollmentDomains(user: User): Observable<string[]> {
    let headers_object = new HttpHeaders();
    headers_object = headers_object.append('Content-Type', 'application/json');
    headers_object = headers_object.append('Authorization', 'Basic ' + btoa('admin:AlexGAdmin'));

    const httpOptions = {
      headers: headers_object
    };
    return this.http.post<string[]>(Consts.backUrl + 'pm_statistics/max_enrollment_domains', user, httpOptions);
  }

  public courseBelow50(user: User): Observable<string[]> {
    let headers_object = new HttpHeaders();
    headers_object = headers_object.append('Content-Type', 'application/json');
    headers_object = headers_object.append('Authorization', 'Basic ' + btoa('admin:AlexGAdmin'));

    const httpOptions = {
      headers: headers_object
    };
    return this.http.post<string[]>(Consts.backUrl + 'pm_statistics/course_below_50', user, httpOptions);
  }
}

