package com.um.inventoryservice.DataLayer;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class ClientRepositoryTest {

    @Autowired
    ClientRepository clientRepository;

    Client client = buildClient();
    ClientDTO clientDTO = buildClientDTO();

    String CLIENT_ID = clientDTO.getClientId();

    @Test
    public void getAllClients() {
        Client client = buildClient();

        Publisher<Client> setup = clientRepository.deleteAll().thenMany(clientRepository.save(client));

        StepVerifier
                .create(setup)
                .expectNextCount(1)
                .verifyComplete();

        Flux<Client> find = clientRepository.findAll();
        Publisher<Client> composite = Mono
                .from(setup)
                .thenMany(find);

        StepVerifier.create(composite)
                .consumeNextWith(foundClient -> {
                    assertEquals(client.getClientId(), foundClient.getClientId());
                    assertEquals(client.getClientName(), foundClient.getClientName());
                    assertEquals(client.getClientEmployeeName(), foundClient.getClientEmployeeName());
                    assertEquals(client.getClientAddress(), foundClient.getClientAddress());
                    assertEquals(client.getClientPhone(),foundClient.getClientPhone());
                })
                .verifyComplete();
    }
    @Test
    void getClientById() {
        Publisher<Client> setup = clientRepository.deleteAll().thenMany(clientRepository.save(client));

        StepVerifier
                .create(setup)
                .expectNextCount(1)
                .verifyComplete();

        Mono<Client> find = clientRepository.findClientByClientId(client.getClientId());
        Publisher<Client> composite = Mono
                .from(setup)
                .then(find);

        StepVerifier
                .create(composite)
                .consumeNextWith(foundClient -> {
                    assertEquals(client.getClientId(), foundClient.getClientId());
                    assertEquals(client.getClientName(), foundClient.getClientName());
                    assertEquals(client.getClientEmployeeName(), foundClient.getClientEmployeeName());
                    assertEquals(client.getClientAddress(), foundClient.getClientAddress());
                    assertEquals(client.getClientPhone(), foundClient.getClientPhone());
                })

                .verifyComplete();
    }
    @Test
    public void insertClient() {
        Client client = buildClient();

        Publisher<Client> setup = clientRepository.deleteAll().thenMany(clientRepository.save(client));

        StepVerifier
                .create(setup)
                .consumeNextWith(foundClient -> {
                    assertEquals(client.getClientId(), foundClient.getClientId());
                    assertEquals(client.getClientName(), foundClient.getClientName());
                    assertEquals(client.getClientEmployeeName(), foundClient.getClientEmployeeName());
                    assertEquals(client.getClientAddress(), foundClient.getClientAddress());
                    assertEquals(client.getClientPhone(), foundClient.getClientPhone());
                })
                .verifyComplete();
    }

    @Test
    public void deleteClientById() {

        Publisher<Client> setup = clientRepository.deleteAll().thenMany(clientRepository.save(buildClient()));
        StepVerifier
                .create(setup)
                .consumeNextWith(foundClient -> {
                    assertEquals(client.getClientId(), foundClient.getClientId());
                    assertEquals(client.getClientName(), foundClient.getClientName());
                    assertEquals(client.getClientEmployeeName(), foundClient.getClientEmployeeName());
                    assertEquals(client.getClientAddress(), foundClient.getClientAddress());
                    assertEquals(client.getClientPhone(), foundClient.getClientPhone());
                })
                .then(this::deleteClientById)
                .verifyComplete();
    }

    private Client buildClient() {
        return client.builder()
                .clientId("12345678")
                .clientName("Super Plumbing")
                .clientEmployeeName("Mario")
                .clientAddress("Mushroom Kingdom")
                .clientPhone("9995557777")
                .build();
    }
    private ClientDTO buildClientDTO() {
        return clientDTO.builder()
                .clientId("87654321")
                .clientName("Super Plumbing")
                .clientEmployeeName("Luigi")
                .clientAddress("Mushroom Kingdom")
                .clientPhone("8885559999")
                .build();
    }

}