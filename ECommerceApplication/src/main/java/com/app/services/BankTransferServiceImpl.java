package com.app.services;

import com.app.entites.BankTransfer;
import com.app.exceptions.APIException;
import com.app.exceptions.ResourceNotFoundException;
import com.app.payloads.BankTransferDTO;
import com.app.payloads.BankTransferResponse;
import com.app.repositories.BankTransferRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class BankTransferServiceImpl implements BankTransferService {
    @Autowired
    private BankTransferRepo bankTransferRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BankTransferDTO createBankTransfer(BankTransfer bankTransfer) {
        BankTransfer savedBankTransfer = bankTransferRepo.findBankTransferByBankTransferName(bankTransfer.getBankTransferName());

        if (savedBankTransfer != null) {
            throw new APIException("Bank Transfer with the name '" + bankTransfer.getBankTransferName() + "' already exists !!!");
        }

        savedBankTransfer = bankTransferRepo.save(bankTransfer);

        return modelMapper.map(savedBankTransfer, BankTransferDTO.class);
    }

    @Override
    public BankTransferResponse getBankTransfers(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        Page<BankTransfer> pageBankTransfer = bankTransferRepo.findAll(pageDetails);

        List<BankTransfer> bankTransfers = pageBankTransfer.getContent();

        if (bankTransfers.size() == 0) {
            throw new APIException("No bank transfer is created till now");
        }

        List<BankTransferDTO> bankTransferDTOS = bankTransfers.stream()
                .map(bankTransfer -> modelMapper.map(bankTransfer, BankTransferDTO.class)).collect(Collectors.toList());

        BankTransferResponse bankTransferResponse = new BankTransferResponse();

        bankTransferResponse.setContent(bankTransferDTOS);
        bankTransferResponse.setPageNumber(pageBankTransfer.getNumber());
        bankTransferResponse.setPageSize(pageBankTransfer.getSize());
        bankTransferResponse.setTotalElements(pageBankTransfer.getTotalElements());
        bankTransferResponse.setTotalPages(pageBankTransfer.getTotalPages());
        bankTransferResponse.setLastPage(pageBankTransfer.isLast());

        return bankTransferResponse;
    }

    @Override
    public BankTransferDTO updateBankTransfer(BankTransfer bankTransfer, Long bankTransferId) {
        BankTransfer savedBankTransfer = bankTransferRepo.findById(bankTransferId)
                .orElseThrow(() -> new ResourceNotFoundException("BankTransfer", "bankTransferId", bankTransferId));

        bankTransfer.setBankTransferId(bankTransferId);

        savedBankTransfer = bankTransferRepo.save(bankTransfer);

        return modelMapper.map(savedBankTransfer, BankTransferDTO.class);
    }

    @Override
    public String deleteBankTransfer(Long bankTransferId) {
        BankTransfer bankTransfer = bankTransferRepo.findById(bankTransferId)
                .orElseThrow(() -> new ResourceNotFoundException("BankTransfer", "bankTransferId", bankTransferId));

        bankTransferRepo.delete(bankTransfer);

        return "BankTransfer with bankTransferId: " + bankTransferId + " deleted successfully !!!";
    }
}