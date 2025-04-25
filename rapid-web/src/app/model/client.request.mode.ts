export interface ClientRequest {
    clientId?: string; // UUID, optional for create
    clientName: string;
    clientDescription: string;
    // Add other client properties as needed
}