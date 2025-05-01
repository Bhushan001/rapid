import { ComponentFixture, TestBed } from '@angular/core/testing';

import { H1HeaderComponent } from './h1-header.component';

describe('H1HeaderComponent', () => {
  let component: H1HeaderComponent;
  let fixture: ComponentFixture<H1HeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [H1HeaderComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(H1HeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
