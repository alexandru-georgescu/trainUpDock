import { Course } from './course';

export class User {
    id : number;
    email: string;
    type: string;
    firstName: string;
    lastName: string;
    password : string;
    courses: Course[];
    leader: string;
    wishToEnroll: Course[];

    constructor (email: string,
        type: string,
        firstName: string,
        lastName: string,
        password : string,
        courses: Course[],
        leader: string,
        wishToEnroll: Course[]) {
            this.email = email;
            this.type = type;
            this.firstName = firstName;
            this.lastName = lastName;
            this.password = password;
            this.courses = courses;
            this.leader = leader;
            this.wishToEnroll = wishToEnroll;
        }

}