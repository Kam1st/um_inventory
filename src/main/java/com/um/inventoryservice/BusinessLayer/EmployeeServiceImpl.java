package com.um.inventoryservice.BusinessLayer;

import com.um.inventoryservice.DataLayer.EmployeeDTO;
import com.um.inventoryservice.DataLayer.EmployeeRepository;
import com.um.inventoryservice.util.EntityDTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public Mono<EmployeeDTO> insertEmployee(Mono<EmployeeDTO> employeeDTOMono) {
        return employeeDTOMono
                .map(EntityDTOUtil::toEntity)
                .flatMap((employeeRepository::save))
                .map(EntityDTOUtil::toDTO);
    }
}
