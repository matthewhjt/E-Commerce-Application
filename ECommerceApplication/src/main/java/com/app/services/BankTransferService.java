package com.app.services;

import com.app.entites.BankTransfer;
import com.app.payloads.BankTransferDTO;
import com.app.payloads.BankTransferResponse;

public interface BankTransferService {
    BankTransferDTO createBankTransfer(BankTransfer bankTransfer);

    BankTransferResponse getBankTransfers(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    BankTransferDTO updateBankTransfer(BankTransfer bankTransfer, Long bankTransferId);

    String deleteBankTransfer(Long bankTransferId);
}
