package com.um.inventoryservice.BusinessLayer;

import com.um.inventoryservice.DataLayer.EmployeeDTO;
import com.um.inventoryservice.DataLayer.EmployeeRepository;
import com.um.inventoryservice.util.EntityDTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public Flux<EmployeeDTO> getAll() {
        return employeeRepository.findAll()
                .map(EntityDTOUtil::toDTO);
    }

    @Override
    public Mono<EmployeeDTO> insertEmployee(Mono<EmployeeDTO> employeeDTOMono) {
        return employeeDTOMono
                .map(EntityDTOUtil::toEntity)
                .doOnNext(e -> e.setEmployeeId(EntityDTOUtil.generateEmployeeString()))
                .flatMap((employeeRepository::save))
                .map(EntityDTOUtil::toDTO);
    }


    @Override
    public Mono<EmployeeDTO> getEmployeeById(String employeeId) {
        return employeeRepository.findEmployeeByEmployeeId(employeeId)
                .map(EntityDTOUtil::toDTO);
    }

    @Override
    public Mono<EmployeeDTO> updateEmployee(String employeeId, Mono<EmployeeDTO> employeeDTOMono) {
        return employeeRepository.findEmployeeByEmployeeId(employeeId)
                .flatMap(e -> employeeDTOMono
                        .map(EntityDTOUtil::toEntity)
                        .doOnNext(x -> x.setEmployeeId(e.getEmployeeId()))
                        .doOnNext(x -> x.setId(e.getId()))
                )
                .flatMap(employeeRepository::save)
                .map(EntityDTOUtil::toDTO);
    }

    @Override
    public Mono<Void> deleteEmployeeById(String employeeId) {
        return employeeRepository.deleteEmployeeByEmployeeId(employeeId);
    }
}
