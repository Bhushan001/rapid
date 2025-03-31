import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ClientService } from '../services/client.service';
import { ToastService } from '../../../services/toast.service';

@Component({
  selector: 'app-create-client',
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ],
  templateUrl: './create-client.component.html',
  styleUrl: './create-client.component.scss'
})
export class CreateClientComponent implements OnInit {
  clientForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private _clientService: ClientService,
    private toastr: ToastService
  ) {
    this.clientForm = this.fb.group({ // Initialize directly
      clientName: ['', [Validators.required, Validators.pattern('^[A-Z]+$')]], // Added pattern validator
      clientDescription: ['']
    })
  }

  ngOnInit() {

  }

  onSubmit() {
    if (this.clientForm.valid) {
      this._clientService.createClient(this.clientForm.value).subscribe(
        (res) => {
          this.clientForm.reset();
          this.toastr.showToast('Success', `Client created successfully.`, 'success');
        },
        (err) => {
          this.toastr.showToast('Failed', `Client could not be created`, 'danger');
        }
      );
    } else {
      // Handle form validation errors
      console.log('Form is invalid');
    }
  }

}
