import { Component, OnInit} from '@angular/core';
import { MatDialogRef} from '@angular/material';

@Component({
  selector: 'app-add-course',
  templateUrl: './add-course.component.html',
  styleUrls: ['./add-course.component.css']
})
export class AddCourseComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<AddCourseComponent>) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit() { }

}
