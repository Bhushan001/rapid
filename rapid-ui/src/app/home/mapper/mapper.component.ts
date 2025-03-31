import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-mapper',
  imports: [
    CommonModule,
    RouterModule
  ],
  templateUrl: './mapper.component.html',
  styleUrl: './mapper.component.scss'
})
export class MapperComponent {

}
