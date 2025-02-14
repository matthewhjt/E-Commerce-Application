package com.app.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankTransferDTO {
    private Long bankTransferID;
    private String bankTransferName;
    private String bankTransferAddress;
}
