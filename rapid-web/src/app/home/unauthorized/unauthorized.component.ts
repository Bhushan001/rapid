import { Location } from '@angular/common';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-unauthorized',
  imports: [],
  templateUrl: './unauthorized.component.html',
  styleUrl: './unauthorized.component.scss'
})
export class UnauthorizedComponent {
  constructor(private location: Location, private router: Router) { }

  ngOnInit(): void {
  }

  goBack(): void {
    this.location.back();
  }

  goToHome(): void {
    this.router.navigate(['/']); // Adjust the homepage route as needed
  }
}
