import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, Router, CanActivate } from '@angular/router';
import { User } from '../models/user';
import { Location } from '@angular/common';


@Injectable()
export class RoleGuard implements CanActivate {

  user : User;
  loggedIn : boolean;

  constructor (private router : Router,
    private location: Location) { }

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
      if (localStorage.getItem('loggedIn') == 'true') {
        this.loggedIn = true;
      } else {
        this.loggedIn = false;
      }

      this.user = JSON.parse(localStorage.getItem('currentUser'));

      if (this.loggedIn == false) {
        this.router.navigate(['/']);
      }

      if (this.user.type == 'USER' && next.url.pop().path == 'user') {
        return true;
      }

      if ((this.user.type == 'PMTECH'|| this.user.type == 'PMSOFT' || this.user.type == 'PMPROC') && next.url.pop().path == 'pm') {
        return true;
      }

      if (this.user.type == 'TM' && next.url.pop().path == 'tm') {
        return true;
      }
      if (this.user.type == 'ADMIN' && next.url.pop().path == 'admin') {
        return true;
      }
      

      this.location.back();
      return false;
  }
}
