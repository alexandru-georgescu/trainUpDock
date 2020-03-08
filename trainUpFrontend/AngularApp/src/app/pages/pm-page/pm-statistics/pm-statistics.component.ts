import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user';
import { MatDialogRef } from '@angular/material';
import { ShareService } from 'src/app/services/share.service';

@Component({
  selector: 'app-pm-statistics',
  templateUrl: './pm-statistics.component.html',
  styleUrls: ['./pm-statistics.component.css']
})
export class PmStatisticsComponent implements OnInit {

  user: User;
  dataSource1: Object;
  maxDomains: string[];
  below50: string[];

  constructor(
    public dialogRef: MatDialogRef<PmStatisticsComponent>,
    private shareService: ShareService
  ) {
    this.dataSource1 = {
      chart: {
        caption: "Courses",
        subCaption: "per type of domain",
        xAxisName: "Type of domain",
        yAxisName: "Courses",
        numberSuffix: "%",
        theme: "candy"
      },
      // Chart Data
      data: [
        {
          label: "RCA",
          value: "Data Set 1"
        },
        {
          label: "NFR",
          value: "Data Set 2"
        },
        {
          label: "GTB",
          value: "Data Set 3"
        },
        {
          label: "PWCC",
          value: "Data Set 4"
        },
      ]
    }; // end of this.dataSource1
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit() {
    this.user = JSON.parse(localStorage.getItem('currentUser'));
    this.shareService.maxDomains.subscribe(data => {
      this.maxDomains = data;
      const data0 = this.maxDomains ? this.maxDomains[0] : '';
      const data1 = this.maxDomains ? this.maxDomains[1] : '';
      const data2 = this.maxDomains ? this.maxDomains[2] : '';
      const data3 = this.maxDomains ? this.maxDomains[3] : '';

      this.dataSource1 = {
        chart: {
          caption: "Courses",
          subCaption: "per type of domain",
          xAxisName: "Type of domain",
          yAxisName: "Courses",
          numberSuffix: "%",
          theme: "candy"
        },
        // Chart Data
        data: [
          {
            label: "RCA",
            value: data0
          },
          {
            label: "NFR",
            value: data1
          },
          {
            label: "GTB",
            value: data2
          },
          {
            label: "PWCC",
            value: data3
          },
        ]
      }; // end of this.dataSource1
    });
    this.shareService.below50.subscribe(data => {
      this.below50 = data;
    })
  }
}