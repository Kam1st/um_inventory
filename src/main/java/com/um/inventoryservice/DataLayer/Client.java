package com.um.inventoryservice.DataLayer;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    private String id;
    private String clientId;
    private String clientName;
    private String clientEmployeeName;
    private String clientAddress;
    private String clientPhone;
}
