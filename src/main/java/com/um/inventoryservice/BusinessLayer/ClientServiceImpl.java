package com.um.inventoryservice.BusinessLayer;

import com.um.inventoryservice.DataLayer.ClientDTO;
import com.um.inventoryservice.DataLayer.ClientRepository;
import com.um.inventoryservice.util.EntityDTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClientServiceImpl implements ClientService{

    @Autowired
    ClientRepository clientRepository;

    @Override
    public Flux<ClientDTO> getAll() {
        return clientRepository.findAll()
                .map(EntityDTOUtil::toDTO);
    }

    @Override
    public Mono<ClientDTO> createClient(Mono<ClientDTO> clientDTOMono) {
        return clientDTOMono
                .map(EntityDTOUtil::toEntity)
                .doOnNext(e -> e.setClientId(EntityDTOUtil.generateClientString()))
                .flatMap((clientRepository::save))
                .map(EntityDTOUtil::toDTO);
    }
    @Override
    public Mono<ClientDTO> updateClient(String clientId, Mono<ClientDTO> clientDTOMono){
        return clientRepository.findClientByClientId(clientId)
                .flatMap(c -> clientDTOMono
                        .map(EntityDTOUtil::toEntity)
                        .doOnNext(e -> e.setClientId(c.getClientId()))
                        .doOnNext(e -> e.setId(c.getId()))
                )
                .flatMap(clientRepository::save)
                .map(EntityDTOUtil::toDTO);
    }

    @Override
    public Mono<ClientDTO> getClientById(String clientId) {
        return clientRepository.findClientByClientId(clientId)
                .map(EntityDTOUtil::toDTO);
    }

    @Override
    public Mono<Void> deleteClientById(String clientId) {
        return clientRepository.deleteClientByClientId(clientId);
    }
}
