import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { User } from 'src/app/models/user';
import { ShareService } from 'src/app/services/share.service';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.css']
})
export class ModalComponent implements OnInit {

  user: User;
  dataSource1: Object;
  dataSource2: Object;
  typeStatistic: number[];
  yearStatistic: number[];

  constructor(public dialogRef: MatDialogRef<ModalComponent>,
    private shareService: ShareService) {
    this.dataSource1 = {
      chart: {
        caption: "Team's enrollment coverage",
        subCaption: "per type of course",
        xAxisName: "Type of course",
        yAxisName: "Participants",
        // numberSuffix: "K",
        theme: "candy"
      },
      // Chart Data
      data: [
        {
          label: "Tech",
          value: "Data Set 1"
        },
        {
          label: "Soft",
          value: "Data Set 2"
        },
        {
          label: "Process",
          value: "Data Set 3"
        },
      ]
    }; // end of this.dataSource1
    
    this.dataSource2 = {
      chart: {
        caption: "Number of enrollments",
        subCaption: "per month",
        xAxisName: "Month",
        yAxisName: "Participants",
        // numberSuffix: "K",
        theme: "candy"
      },
      // Chart Data
      data: [
        {
          label: "JAN",
          value: "1"
        },
        {
          label: "FEB",
          value: "2"
        },
        {
          label: "MAR",
          value: "3"
        },
        {
          label: "APR",
          value: "4"
        },
        {
          label: "MAY",
          value: "5"
        },
        {
          label: "JUN",
          value: "6"
        },
        {
          label: "JUL",
          value: "7"
        },
        {
          label: "AUG",
          value: "8"
        },
        {
          label: "SEP",
          value: "9"
        },
        {
          label: "OCT",
          value: "10"
        },
        {
          label: "NOV",
          value: "11"
        },
        {
          label: "DEC",
          value: "12"
        },
      ]
    }; // end of this.dataSource2
  } // end of constructor

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit() {
    this.user = JSON.parse(localStorage.getItem('currentUser'));
    this.shareService.typeStatistic.subscribe(data => {
      this.typeStatistic = data;
      const data0 = this.typeStatistic ? this.typeStatistic[0] : '';
      const data1 = this.typeStatistic ? this.typeStatistic[1] : '';
      const data2 = this.typeStatistic ? this.typeStatistic[2] : '';

      this.dataSource1 = {
        chart: {
          caption: "Team's enrollment coverage",
          subCaption: "per type of course",
          xAxisName: "Type of course",
          yAxisName: "Participants",
          // numberSuffix: "K",
          theme: "candy"
        },
        // Chart Data
        data: [
          {
            label: "Tech",
            value: data0
          },
          {
            label: "Soft",
            value: data1
          },
          {
            label: "Process",
            value: data2
          },
        ]
      }; // end of this.dataSource1
    });
    this.shareService.yearStatistic.subscribe(data => {
      this.yearStatistic = data;
      const data0 = this.yearStatistic ? this.yearStatistic[0] : '';
      const data1 = this.yearStatistic ? this.yearStatistic[1] : '';
      const data2 = this.yearStatistic ? this.yearStatistic[2] : '';
      const data3 = this.yearStatistic ? this.yearStatistic[3] : '';
      const data4 = this.yearStatistic ? this.yearStatistic[4] : '';
      const data5 = this.yearStatistic ? this.yearStatistic[5] : '';
      const data6 = this.yearStatistic ? this.yearStatistic[6] : '';
      const data7 = this.yearStatistic ? this.yearStatistic[7] : '';
      const data8 = this.yearStatistic ? this.yearStatistic[8] : '';
      const data9 = this.yearStatistic ? this.yearStatistic[9] : '';
      const data10 = this.yearStatistic ? this.yearStatistic[10] : '';
      const data11 = this.yearStatistic ? this.yearStatistic[11] : '';

      this.dataSource2 = {
        chart: {
          caption: "Number of enrollments",
          subCaption: "per month",
          xAxisName: "Month",
          yAxisName: "Participants",
          // numberSuffix: "K",
          theme: "candy"
        },
        // Chart Data
        data: [
          {
            label: "JAN",
            value: data0
          },
          {
            label: "FEB",
            value: data1
          },
          {
            label: "MAR",
            value: data2
          },
          {
            label: "APR",
            value: data3
          },
          {
            label: "MAY",
            value: data4
          },
          {
            label: "JUN",
            value: data5
          },
          {
            label: "JUL",
            value: data6
          },
          {
            label: "AUG",
            value: data7
          },
          {
            label: "SEP",
            value: data8
          },
          {
            label: "OCT",
            value: data9
          },
          {
            label: "NOV",
            value: data10
          },
          {
            label: "DEC",
            value: data11
          },
        ]
      }; // end of this.dataSource2
    })
  }

}