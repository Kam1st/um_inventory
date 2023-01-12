package com.um.inventoryservice.PresentationLayer;

import com.um.inventoryservice.DataLayer.*;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
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
    Client client2 = buildClient2();
    ClientDTO clientDTO = buildClientDTO();

    String CLIENT_ID = client.getClientId();

    @Test
    void getAllClients() {
        Publisher<Client> setup = clientRepository.deleteAll().thenMany(clientRepository.save(client));

        StepVerifier
                .create(setup)
                .expectNextCount(1)
                .verifyComplete();

        webTestClient
                .get()
                .uri("/clients")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$[0].clientId").isEqualTo(client.getClientId())
                .jsonPath("$[0].clientName").isEqualTo(client.getClientName())
                .jsonPath("$[0].clientEmployeeName").isEqualTo(client.getClientEmployeeName())
                .jsonPath("$[0].clientAddress").isEqualTo(client.getClientAddress())
                .jsonPath("$[0].clientPhone").isEqualTo(client.getClientPhone());
    }

    @Test
    void insertClient() {
        Publisher<Void> setup = clientRepository.deleteAll();

        StepVerifier
                .create(setup)
                .expectNextCount(0)
                .verifyComplete();

        webTestClient
                .post()
                .uri("/clients")
                .body(Mono.just(client), Client.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ClientDTO.class)
                .value((dto) -> {
                    assertThat(dto.getClientName()).isEqualTo(client.getClientName());
                    assertThat(dto.getClientEmployeeName()).isEqualTo(client.getClientEmployeeName());
                    assertThat(dto.getClientAddress()).isEqualTo(client.getClientAddress());
                    assertThat(dto.getClientPhone()).isEqualTo(client.getClientPhone());
                });

    }
    @Test
    void updateClient() {
        Publisher<Client> setup = clientRepository.deleteAll().thenMany(clientRepository.save(client2));
        StepVerifier
                .create(setup)
                .expectNextCount(1)
                .verifyComplete();

        webTestClient
                .put()
                .uri("/clients/" + CLIENT_ID)
                .body(Mono.just(clientDTO), ClientDTO.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.clientId").isEqualTo(clientDTO.getClientId())
                .jsonPath("$.clientName").isEqualTo(clientDTO.getClientName())
                .jsonPath("$.clientEmployeeName").isEqualTo(clientDTO.getClientEmployeeName())
                .jsonPath("$.clientAddress").isEqualTo(clientDTO.getClientAddress())
                .jsonPath("$.clientPhone").isEqualTo(clientDTO.getClientPhone());
    }
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

    @Test
    void deleteClient() {
        Publisher<Client> setup = clientRepository.deleteAll().thenMany(clientRepository.save(client));

        StepVerifier
                .create(setup)
                .expectNextCount(1)
                .verifyComplete();

        webTestClient
                .delete()
                .uri("/clients/" + CLIENT_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody();
    }

    @Test
    void toStringBuilders() {
        System.out.println(Client.builder());
        System.out.println(ClientDTO.builder());
    }
    private Client buildClient() {
        return Client.builder()
                .clientId("297445493")
                .clientName("Super Plumbing")
                .clientEmployeeName("Mario")
                .clientAddress("Mushroom Kingdom")
                .clientPhone("9995557777")
                .build();
    }

    private Client buildClient2() {
        return Client.builder()
                .clientId("297445493")
                .clientName("Super Plumbing")
                .clientEmployeeName("Mario")
                .clientAddress("Mushroom Kingdom")
                .clientPhone("9995557777")
                .build();
    }

    private ClientDTO buildClientDTO() {
        return ClientDTO.builder()
                .clientId("297445493")
                .clientName("Super Plumbing")
                .clientEmployeeName("Luigi")
                .clientAddress("Mushroom Kingdom")
                .clientPhone("8885559999")
                .build();

    }
}