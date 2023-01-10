package com.um.inventoryservice.PresentationLayer;

import com.um.inventoryservice.BusinessLayer.ClientService;
import com.um.inventoryservice.DataLayer.ClientDTO;
import com.um.inventoryservice.DataLayer.InventoryItemDTO;
import com.um.inventoryservice.DataLayer.StockItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("clients")
public class ClientController {
    @Autowired
    ClientService clientService;

    @GetMapping()
    public Flux<ClientDTO> getAllClients() {
        return clientService.getAll();
    }
    @PostMapping
    public Mono<ClientDTO> createClient(@RequestBody Mono<ClientDTO> clientDTOMono) {
        return clientService.createClient(clientDTOMono);
    }
    @PutMapping("{clientId}")
    public Mono<ResponseEntity<ClientDTO>> updateClient(@PathVariable String clientId, @RequestBody Mono<ClientDTO> clientDTOMono) {
        return clientService.updateClient(clientId, clientDTOMono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("{clientId}")
    public Mono<ResponseEntity<ClientDTO>> getClientById(@PathVariable String clientId) {
        return clientService
                .getClientById(clientId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{clientId}")
    public Mono<Void> deleteClient(@PathVariable String clientId) {
        return clientService.deleteClientById(clientId);
    }
}
