import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransactionWizardComponent } from './transaction-wizard.component';

describe('TransactionWizardComponent', () => {
  let component: TransactionWizardComponent;
  let fixture: ComponentFixture<TransactionWizardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TransactionWizardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransactionWizardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
