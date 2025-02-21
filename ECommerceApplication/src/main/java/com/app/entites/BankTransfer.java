package com.app.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "bank_transfers")
@NoArgsConstructor
@AllArgsConstructor
public class BankTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bankTransferId;

    @Column(unique = true, nullable = false)
    private String bankTransferName;

    @Column(unique = true, nullable = false)
    private String bankTransferNumber;
}