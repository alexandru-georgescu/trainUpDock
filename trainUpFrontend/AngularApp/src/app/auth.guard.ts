import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, Router, CanActivate } from '@angular/router';
import { User } from './models/user';
import { Location } from '@angular/common';


@Injectable()
export class AuthGuard implements CanActivate {

  user : User;
  bool : boolean;

  constructor (private router : Router,
    private location: Location) { }

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
      if (localStorage.getItem('loggedIn') == 'true') {
        this.bool = true;
      } else {
        this.bool = false;
      }

      

      this.user = JSON.parse(localStorage.getItem('currentUser'));

      if (this.bool == false) {
        this.router.navigate(['/']);
      }

      if (this.user.type == 'USER' && next.url.pop().path == 'user') {
        return true;
      }

      if (this.user.type == 'PM' && next.url.pop().path == 'pm') {
        return true;
      }

      if (this.user.type == 'TM' && next.url.pop().path == 'tm') {
        return true;
      }

      this.location.back();
      return false;
  }
}
