package com.um.inventoryservice.BusinessLayer;

import com.um.inventoryservice.DataLayer.Client;
import com.um.inventoryservice.DataLayer.ClientDTO;
import com.um.inventoryservice.DataLayer.ClientRepository;
import com.um.inventoryservice.DataLayer.StockItemDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureWebTestClient
class ClientServiceImplTest {

    @Autowired
    ClientService clientService;

    @MockBean
    ClientRepository clientRepository;

    Client client = buildClient();
    ClientDTO clientDTO = buildClientDTO();

    String CLIENT_ID = clientDTO.getClientId();

    @Test
    void getClientById() {
        when(clientRepository.findClientByClientId(anyString())).thenReturn(Mono.just(client));

        Mono<ClientDTO> clientDTOMono = clientService.getClientById(CLIENT_ID);

        StepVerifier
                .create(clientDTOMono)
                .consumeNextWith(foundClient -> {
                    assertEquals(client.getClientId(), foundClient.getClientId());
                    assertEquals(client.getClientName(), foundClient.getClientName());
                    assertEquals(client.getClientEmployeeName(), foundClient.getClientEmployeeName());
                    assertEquals(client.getClientAddress(), foundClient.getClientAddress());
                    assertEquals(client.getClientPhone(), foundClient.getClientPhone());
                })

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