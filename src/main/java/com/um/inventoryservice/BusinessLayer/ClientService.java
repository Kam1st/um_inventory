package com.um.inventoryservice.BusinessLayer;

import com.um.inventoryservice.DataLayer.ClientDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService {
    Flux<ClientDTO> getAll();
    Mono<ClientDTO> createClient(Mono<ClientDTO> clientDTOMono);
    Mono<ClientDTO> updateClient(String clientId, Mono<ClientDTO> clientDTOMono);
    Mono<ClientDTO> getClientById(String clientId);
    Mono<Void> deleteClientById(String clientId);
}
