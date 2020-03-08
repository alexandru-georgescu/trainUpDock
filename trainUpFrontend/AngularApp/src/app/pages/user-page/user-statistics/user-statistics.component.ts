import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material';
import { ShareService } from 'src/app/services/share.service';
import { User } from 'src/app/models/user';
import { Course } from 'src/app/models/course';

@Component({
  selector: 'app-user-statistics',
  templateUrl: './user-statistics.component.html',
  styleUrls: ['./user-statistics.component.css']
})
export class UserStatisticsComponent implements OnInit {

  user: User;
  attendedDays: number;
  upcomingDays: number;
  courseStatistic: number[];
  dataSource: Object;

  constructor(
    public dialogRef: MatDialogRef<UserStatisticsComponent>,
    private shareService: ShareService
  ) {
    this.dataSource = {
      chart: {
        caption: "My enrollment coverage",
        subCaption: "per type of course",
        xAxisName: "Type of course",
        yAxisName: "Courses",
        // numberSuffix: "K",
        theme: "candy"
      },
      // Chart Data
      data: [
        {
          label: 'Tech',
          value: 'Data Set 1'
        },
        {
          label: 'Soft',
          value: 'Data Set 2'
        },
        {
          label: 'Process',
          value: 'Data Set 3'
        },
      ]
    }; // end of this.dataSource
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit() {
    this.user = JSON.parse(localStorage.getItem('currentUser'));
    this.shareService.attendedDays.subscribe(data => {
      this.attendedDays = data;
    });
    this.shareService.upcomingDays.subscribe(data => {
      this.upcomingDays = data;
    });
    this.shareService.courseStatistic.subscribe(data => {
      this.courseStatistic = data;
      const data0 = this.courseStatistic ? this.courseStatistic[0] : '';
      const data1 = this.courseStatistic ? this.courseStatistic[1] : '';
      const data2 = this.courseStatistic ? this.courseStatistic[2] : '';

      this.dataSource = {
        chart: {
          caption: "My enrollment coverage",
          subCaption: "per type of course",
          xAxisName: "Type of course",
          yAxisName: "Courses",
          // numberSuffix: "K",
          theme: "candy"
        },
        // Chart Data
        data: [
          {
            label: 'Tech',
            value: data0
          },
          {
            label: 'Soft',
            value: data1
          },
          {
            label: 'Process',
            value: data2
          },
        ]
      }; // end of this.dataSource
    });
  }
}