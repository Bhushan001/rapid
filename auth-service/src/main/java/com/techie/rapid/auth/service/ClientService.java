package com.techie.rapid.auth.service;

import com.techie.rapid.auth.dto.ClientDto;
import com.techie.rapid.auth.entity.Client;
import com.techie.rapid.auth.exceptions.ClientCreationFailedException;
import com.techie.rapid.auth.exceptions.ClientDeletionFailedException;
import com.techie.rapid.auth.exceptions.ClientNotFoundException;
import com.techie.rapid.auth.exceptions.ClientUpdateFailedException;
import com.techie.rapid.auth.repository.ClientRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService {

    private final UserService userService;

    @Autowired
    private ClientRepository clientRepository;

    public Client createClient(Client client, Claims claims) {
        try {
            return clientRepository.save(client);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ClientCreationFailedException("Failed to create client.", "CLIENT_CREATION_FAILED");
        }
    }

    public void deleteClient(UUID clientId) {
        try {
            clientRepository.deleteById(clientId);
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            throw new ClientDeletionFailedException("Failed to delete client.", "CLIENT_DELETION_FAILED");
        }
    }

    public List<ClientDto> getAllClients() {
        List<Client> clients = clientRepository.findAll();

        if (clients == null || clients.isEmpty()) {
            return List.of();
        }

        return clients.stream()
                .map(client -> convertToDto(client))
                .collect(Collectors.toList());
    }

    private ClientDto convertToDto(Client client) {
        if (client == null) {
            return null; // Or throw an exception
        }

        ClientDto dto = new ClientDto(
                client.getClientId(),
                client.getClientName(),
                client.getClientDescription(),
                client.getCreatedOn(),
                client.getUpdatedOn(),
                client.getCreatedBy(),
                client.getUpdatedBy()
        );

        if (client.getCreatedBy() != null) {
            try {
                String createdByName = userService.getUserDtoById(client.getCreatedBy()).getUsername();
                if (createdByName != null) {
                    dto.setCreatedByName(createdByName);
                } else {
                    log.warn("Username not found for createdBy: {}", client.getCreatedBy());
                }
            } catch (Exception e) {
                log.error("Error fetching createdBy username for id: {}", client.getCreatedBy(), e);
            }
        }

        if (client.getUpdatedBy() != null) {
            try {
                String updatedByName = userService.getUserDtoById(client.getUpdatedBy()).getUsername();
                if (updatedByName != null) {
                    dto.setUpdatedByName(updatedByName);
                } else {
                    log.warn("Username not found for updatedBy: {}", client.getUpdatedBy());
                }
            } catch (Exception e) {
                log.error("Error fetching updatedBy username for id: {}", client.getUpdatedBy(), e);
            }
        }

        return dto;
    }

    public Client updateClient(UUID clientId, Client updatedClient) {
        Optional<Client> existingClientOptional = clientRepository.findById(clientId); // Assuming you have a clientRepository
        if (existingClientOptional.isEmpty()) {
            throw new ClientNotFoundException("Client not found with ID: " + clientId, "CLIENT_NOT_FOUND");
        }
        try {
            Client existingClient = existingClientOptional.get();
            //update the existing client with the updated client data.
            existingClient.setClientName(updatedClient.getClientName());
            existingClient.setClientDescription(updatedClient.getClientDescription());
            return clientRepository.save(existingClient);
        } catch (Exception e) {
            throw new ClientUpdateFailedException("Failed to update client: " + e.getMessage(), "CLIENT_UPDATE_FAILED");
        }
    }

    public Optional<Client> getClientById(UUID clientId) {
        return clientRepository.findById(clientId);
    }
}
