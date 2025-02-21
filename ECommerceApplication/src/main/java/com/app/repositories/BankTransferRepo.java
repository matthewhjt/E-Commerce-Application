package com.app.repositories;

import com.app.entites.BankTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankTransferRepo extends JpaRepository<BankTransfer, Long> {
    BankTransfer findBankTransferByBankTransferName(String bankTransferName);
}
