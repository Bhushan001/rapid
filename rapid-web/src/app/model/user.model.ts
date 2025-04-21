import { Client } from "./client.model";
export interface User {
    userId: string;
    username: string;
    firstName?: string;
    lastName?: string;
    birthDate?: string;
    country?: string;
    roles: string[];
    clientName?: string;
    createdBy?: string;
    createdByName?: string;
    createdOn?: any;
    updatedBy?: string;
    updatedByName?: string;
    updatedOn?: any;
}
