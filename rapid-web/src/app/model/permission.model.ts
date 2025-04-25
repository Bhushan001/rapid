export interface Permission {
    id?: string; // UUID, optional for create
    name: string;
    code: string;
    permissions: any;
    createdBy?: string;
    createdByName?: string;
    createdOn?: any;
    updatedBy?: string;
    updatedByName?: string;
    updatedOn?: any;
    // Add other permission properties as needed
}