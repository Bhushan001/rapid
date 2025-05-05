import { CommonModule } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { ChartOptions, ChartType, ChartData } from 'chart.js';
import { NgChartsModule } from 'ng2-charts';

@Component({
  selector: 'app-bar-chart',
  templateUrl: './bar-chart.component.html',
  styleUrls: ['./bar-chart.component.scss'],
  imports:[
    CommonModule,
    NgChartsModule
  ]
})
export class BarChartComponent implements OnInit {
  @Input() chartData: number[] = []; // Data for the bars
  @Input() chartLabels: string[] = []; // Labels for the bars
  @Input() chartBackgroundColor: string[] = []; // Bar colors

  barChartOptions: ChartOptions = {
    responsive: true,
    scales: {
      y: {
        beginAtZero: true
      }
    }
  };
  barChartType: ChartType = 'bar';
  barChartLegend = true;
  barChartData: ChartData = {
    labels: this.chartLabels,
    datasets: [{
      data: this.chartData,
      backgroundColor: this.chartBackgroundColor.length ? this.chartBackgroundColor : 'rgba(0, 123, 255, 0.6)',
    }]
  };

  ngOnInit(): void {
    this.barChartData = {
      labels: this.chartLabels,
      datasets: [{
        data: this.chartData,
        backgroundColor: this.chartBackgroundColor.length ? this.chartBackgroundColor : 'rgba(0, 123, 255, 0.6)',
      }]
    };
  }
}
