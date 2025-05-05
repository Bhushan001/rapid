import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IfscAutofillComponent } from './ifsc-autofill.component';

describe('IfscAutofillComponent', () => {
  let component: IfscAutofillComponent;
  let fixture: ComponentFixture<IfscAutofillComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IfscAutofillComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IfscAutofillComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
