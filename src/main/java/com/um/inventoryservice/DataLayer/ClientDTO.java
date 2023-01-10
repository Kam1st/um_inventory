package com.um.inventoryservice.DataLayer;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {
    private String clientId;
    private String clientName;
    private String clientEmployeeName;
    private String clientAddress;
    private String clientPhone;

    public ClientDTO(String clientName, String clientEmployeeName, String clientAddress, String clientPhone){
        this.clientName = clientName;
        this.clientEmployeeName = clientEmployeeName;
        this.clientAddress = clientAddress;
        this.clientPhone = clientPhone;
    }
}
