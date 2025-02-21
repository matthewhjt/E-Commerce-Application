package com.app.controllers;

import com.app.config.AppConstants;
import com.app.entites.BankTransfer;
import com.app.entites.Category;
import com.app.payloads.BankTransferDTO;
import com.app.payloads.BankTransferResponse;
import com.app.payloads.CategoryDTO;
import com.app.payloads.CategoryResponse;
import com.app.services.BankTransferService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "E-Commerce Application")
public class BankTransferController {
    @Autowired
    private BankTransferService bankTransferService;

    @GetMapping("/public/banktransfers")
    public ResponseEntity<BankTransferResponse> getBankTransfers(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_BANK_TRANSFERS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {

        BankTransferResponse bankTransferResponse = bankTransferService.getBankTransfers(pageNumber, pageSize, sortBy, sortOrder);

        return new ResponseEntity<BankTransferResponse>(bankTransferResponse, HttpStatus.FOUND);
    }

    @PostMapping("/admin/banktransfers")
    public ResponseEntity<BankTransferDTO> createBankTransfer(@Valid @RequestBody BankTransfer bankTransfer) {
        BankTransferDTO savedBankTransferDTO = bankTransferService.createBankTransfer(bankTransfer);

        return new ResponseEntity<BankTransferDTO>(savedBankTransferDTO, HttpStatus.CREATED);
    }

    @PutMapping("/admin/banktransfers/{banktransferId}")
    public ResponseEntity<BankTransferDTO> updateBankTransfer(@RequestBody BankTransfer bankTransfer,
                                                              @PathVariable Long banktransferId) {
        BankTransferDTO bankTransferDTO = bankTransferService.updateBankTransfer(bankTransfer, banktransferId);

        return new ResponseEntity<BankTransferDTO>(bankTransferDTO, HttpStatus.OK);
    }

    @DeleteMapping("/admin/banktransfers/{banktransferId}")
    public ResponseEntity<String> deleteBankTransfer(@PathVariable Long banktransferId) {
        String status = bankTransferService.deleteBankTransfer(banktransferId);

        return new ResponseEntity<String>(status, HttpStatus.OK);
    }
}