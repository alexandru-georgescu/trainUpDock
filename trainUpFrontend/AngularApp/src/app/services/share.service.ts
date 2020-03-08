import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ShareService {

  private messageSource = new BehaviorSubject('default message');
  currentMessage = this.messageSource.asObservable();

  constructor() { }

  changeMessage(message: string) {
    this.messageSource.next(message)
  }

  private acceptedSource = new BehaviorSubject(undefined);
  accepted = this.acceptedSource.asObservable();

  changeAccepted(accepted: number) {
    this.acceptedSource.next(accepted);
  }

  private rejectedSource = new BehaviorSubject(undefined);
  rejected = this.rejectedSource.asObservable();

  changeRejected(rejected: number) {
    this.rejectedSource.next(rejected);
  }

  private attendedDaysSource = new BehaviorSubject(undefined);
  attendedDays = this.attendedDaysSource.asObservable();

  changeAttendedDays(attendedDays: number) {
    this.attendedDaysSource.next(attendedDays);
  }

  private upcomingDaysSource = new BehaviorSubject(undefined);
  upcomingDays = this.upcomingDaysSource.asObservable();

  changeUpcomingDays(upcomingDays: number) {
    this.upcomingDaysSource.next(upcomingDays);
  }

  private courseStatisticSource = new BehaviorSubject(undefined);
  courseStatistic = this.courseStatisticSource.asObservable();

  changeCourseStatistic(courseStatistic: number[]) {
    this.courseStatisticSource.next(courseStatistic);
  }
  
  private typeStatisticSource = new BehaviorSubject(undefined);
  typeStatistic = this.typeStatisticSource.asObservable();

  changeTypeStatistic(typeStatistic: number[]) {
    this.typeStatisticSource.next(typeStatistic);
  }

  private yearStatisticSource = new BehaviorSubject(undefined);
  yearStatistic = this.yearStatisticSource.asObservable();

  changeYearStatistic(yearStatistic: number[]) {
    this.yearStatisticSource.next(yearStatistic);
  }

  private maxDomainsSource = new BehaviorSubject(undefined);
  maxDomains = this.maxDomainsSource.asObservable();

  changeMaxDomains(maxDomains: string[]) {
    this.maxDomainsSource.next(maxDomains);
  }

  private below50Source = new BehaviorSubject(undefined);
  below50 = this.below50Source.asObservable();

  changeBelow50(below50: string[]) {
    this.below50Source.next(below50);
  }
}