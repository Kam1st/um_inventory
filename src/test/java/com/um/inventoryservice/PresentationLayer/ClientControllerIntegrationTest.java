package com.um.inventoryservice.PresentationLayer;

import com.um.inventoryservice.DataLayer.Client;
import com.um.inventoryservice.DataLayer.ClientDTO;
import com.um.inventoryservice.DataLayer.ClientRepository;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureWebTestClient
class ClientControllerIntegrationTest {

    @Autowired
    WebTestClient webTestClient;
    @Autowired
    ClientRepository clientRepository;

    Client client = buildClient();

    ClientDTO clientDTO = buildClientDTO();

    String CLIENT_ID = client.getClientId();

    @Test
    void getClientById() {
        Publisher<Client> setup = clientRepository.deleteAll().thenMany(clientRepository.save(client));

        StepVerifier
                .create(setup)
                .expectNextCount(1)
                .verifyComplete();

        webTestClient
                .get()
                .uri("/clients/" + CLIENT_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.clientId").isEqualTo(client.getClientId())
                .jsonPath("$.clientName").isEqualTo(client.getClientName())
                .jsonPath("$.clientEmployeeName").isEqualTo(client.getClientEmployeeName())
                .jsonPath("$.clientAddress").isEqualTo(client.getClientAddress())
                .jsonPath("$.clientPhone").isEqualTo(client.getClientPhone());
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