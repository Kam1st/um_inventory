package com.um.inventoryservice.DataLayer;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ClientRepository extends ReactiveMongoRepository<Client, String> {

    Mono<Client> findClientByClientId(String clientId);
    Mono<Void> deleteClientByClientId(String clientId);
}
