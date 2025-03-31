import { Component, OnInit, TemplateRef } from '@angular/core';
import { ClientService } from '../services/client.service';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { NgbModal, NgbModule, NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';
import { ToastService } from '../../../services/toast.service';
import { Client } from '../../../model/client.model';

@Component({
  selector: 'app-list-clients',
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
    NgbPaginationModule
  ],
  templateUrl: './list-clients.component.html',
  styleUrl: './list-clients.component.scss'
})
export class ListClientsComponent implements OnInit {
  clients: Client[] = [];
  filteredClients: Client[] = [];
  selectedClientId: string = "";

  editForm: FormGroup;

  page = 1;
  pageSize = 5;
  collectionSize = 0;
  searchText = '';

  constructor(
    private modalService: NgbModal,
    private _clientService: ClientService,
    private toastr: ToastService,
    private fb: FormBuilder
  ) {
    this.editForm = this.fb.group({
      clientName: ['', [Validators.required, Validators.pattern('^[A-Z]+$')]],
      clientDescription: ['']
    });
  }

  ngOnInit(): void {
    this.getAllClients();
  }

  refreshClients(): void {
    this.selectedClientId = "";
    this.filteredClients = this.clients
      .filter(client => {
        const term = this.searchText.toLowerCase();
        return (
          client.clientName.toLowerCase().includes(term) ||
          client.clientDescription.toLowerCase().includes(term) ||
          client.clientId.toLowerCase().includes(term)
        );
      })
      .map((client, i) => ({ id: i + 1, ...client }))
      .slice((this.page - 1) * this.pageSize, (this.page - 1) * this.pageSize + this.pageSize);
  }

  search(): void {
    this.page = 1;
    this.refreshClients();
  }

  getAllClients() {
    this.selectedClientId = "";
    this._clientService.getAllClients().subscribe(
      (res: any) => {
        this.clients = res.body;
        this.collectionSize = this.clients.length;
        this.refreshClients();
      },
      (err) => {
        console.log(err);
      }
    );
  }

  openEditModal(event: Event, client: Client, editModal: TemplateRef<any>) {
    this.selectedClientId = client.clientId;
    event.stopPropagation();
    this.editForm.patchValue(client);
    this.modalService.open(editModal, { centered: true, size: 'lg', backdrop: 'static' })
      .result.then((result) => {
        if (result === 'save') {
          this.saveClient(this.selectedClientId, this.editForm.value);
        }
      }, () => { });
  }


  saveClient(clientId: string, client: Client): void {
    this._clientService.updateClient(clientId, client).subscribe(
      (res) => {
        this.getAllClients();
        this.toastr.showToast('Success', `Client updated successfully.`, 'success');
      },
      (err) => {
        console.log(err);
        this.toastr.showToast('Failed', `Client could not be updated`, 'danger');
      }
    );
  }


  openDeleteConfirmationModal(event: Event, client: Client, modalContent: TemplateRef<any>) {
    event.stopPropagation(); // Prevent workspace selection when clicking delete

    if (!client.clientId) {
      console.error("Invalid client ID:", client);
      this.toastr.showToast('Error', 'Invalid Client ID.', 'danger');
      return;
    }

    // Open the modal and pass workspace data
    const modalRef = this.modalService.open(modalContent, { centered: true, size: 'sm', backdrop: 'static' });
    modalRef.result.then(
      (result) => {
        if (result === 'confirm') {
          if (client.clientId) {
            this.deleteClient(client.clientId);
          }
        }
      },
      () => { } // Handle dismiss
    );
  }

  deleteClient(clientId: string): void {
    this._clientService.deleteClient(clientId).subscribe(
      (res) => {
        this.getAllClients();
        this.toastr.showToast('Success', `Client deleted successfully.`, 'success');
      },
      (err) => {
        this.toastr.showToast('Failed', `Client could not be deleted`, 'danger');
      }
    );
  }
}
