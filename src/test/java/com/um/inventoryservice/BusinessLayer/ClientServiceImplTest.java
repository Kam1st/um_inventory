package com.um.inventoryservice.BusinessLayer;

import com.um.inventoryservice.DataLayer.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
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
    void getAllClients(){

        when(clientRepository.findAll()).thenReturn(Flux.just(client));

        Flux<ClientDTO> clientDTOFlux = clientService.getAll();

        StepVerifier
                .create(clientDTOFlux)
                .consumeNextWith(foundClient ->{
                    assertEquals(client.getClientId(), foundClient.getClientId());
                    assertEquals(client.getClientName(), foundClient.getClientName());
                    assertEquals(client.getClientEmployeeName(), foundClient.getClientEmployeeName());
                    assertEquals(client.getClientAddress(), foundClient.getClientAddress());
                    assertEquals(client.getClientPhone(),foundClient.getClientPhone());
                })
                .verifyComplete();
    }
    @Test
    void insertClient() {

        clientService.createClient(Mono.just(clientDTO))
                .map(clientDTO1 -> {
                    assertEquals(clientDTO1.getClientId(), client.getClientId());
                    assertEquals(clientDTO1.getClientName(), client.getClientName());
                    assertEquals(clientDTO1.getClientEmployeeName(), client.getClientEmployeeName());
                    assertEquals(clientDTO1.getClientAddress(), client.getClientAddress());
                    assertEquals(clientDTO1.getClientPhone(),client.getClientPhone());
                    return clientDTO1;
                });
    }
    @Test
    void updateStockItem() {
        when(clientRepository.save(any(Client.class))).thenReturn(Mono.just(client));

        when(clientRepository.findClientByClientId(anyString())).thenReturn(Mono.just(client));
        clientService.updateClient(CLIENT_ID, (Mono.just(clientDTO)))
                .map(clientDTO1 -> {
                    assertEquals(clientDTO1.getClientId(), clientDTO.getClientId());
                    assertEquals(clientDTO1.getClientName(), clientDTO.getClientName());
                    assertEquals(clientDTO1.getClientEmployeeName(), clientDTO.getClientEmployeeName());
                    assertEquals(clientDTO1.getClientAddress(), clientDTO.getClientAddress());
                    assertEquals(clientDTO1.getClientPhone(),clientDTO.getClientPhone());
                    return clientDTO1;
                });
    }
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

    @Test
    void deleteClient() {
        clientService.deleteClientById(CLIENT_ID);
        verify(clientRepository, times(1)).deleteClientByClientId(CLIENT_ID);
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