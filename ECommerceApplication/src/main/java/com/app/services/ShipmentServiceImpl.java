package com.app.services;

import com.app.entites.BankTransfer;
import com.app.entites.Brand;
import com.app.entites.Shipment;
import com.app.exceptions.APIException;
import com.app.exceptions.ResourceNotFoundException;
import com.app.payloads.*;
import com.app.repositories.ShipmentRepo;
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
public class ShipmentServiceImpl implements ShipmentService {
    @Autowired
    private ShipmentRepo shipmentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ShipmentDTO addShipment(Shipment shipment) {
        Shipment savedShipment = shipmentRepo.findShipmentByShipmentId(shipment.getShipmentId());

        if (savedShipment != null) {
            throw new APIException("Shipment with Shipment id '" + shipment.getShipmentId() + "' already exists !!!");
        }

        savedShipment = shipmentRepo.save(shipment);
        return modelMapper.map(savedShipment, ShipmentDTO.class);
    }

    @Override
    public ShipmentResponse getAllShipment(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        Page<Shipment> pageShipment = shipmentRepo.findAll(pageDetails);

        List<Shipment> shipments = pageShipment.getContent();

        if (shipments.size() == 0) {
            throw new APIException("No shipment is created till now");
        }

        List<ShipmentDTO> shipmentDTOS = shipments.stream()
                .map(shipment -> modelMapper.map(shipment, ShipmentDTO.class)).collect(Collectors.toList());

        ShipmentResponse shipmentResponse = new ShipmentResponse();

        shipmentResponse.setContent(shipmentDTOS);
        shipmentResponse.setPageNumber(pageShipment.getNumber());
        shipmentResponse.setPageSize(pageShipment.getSize());
        shipmentResponse.setTotalElements(pageShipment.getTotalElements());
        shipmentResponse.setTotalPages(pageShipment.getTotalPages());
        shipmentResponse.setLastPage(pageShipment.isLast());

        return shipmentResponse;
    }

    @Override
    public ShipmentDTO updateShipment(Long shipmentId, Shipment shipment) {
        Shipment savedShipment = shipmentRepo.findById(shipmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment", "shipmentId", shipmentId));

        savedShipment.setShipmentId(shipmentId);

        savedShipment = shipmentRepo.save(shipment);

        return modelMapper.map(savedShipment, ShipmentDTO.class);
    }

    @Override
    public ShipmentDTO searchShipmentById(Long shipmentId) {
        Shipment shipment = shipmentRepo.findShipmentByShipmentId(shipmentId);

        if (shipment == null) {
            throw new ResourceNotFoundException("Shipment", "shipmentId", shipmentId);
        }

        return modelMapper.map(shipment, ShipmentDTO.class);
    }

    @Override
    public String deleteShipment(Long shipmentId) {
        Shipment shipment = shipmentRepo.findById(shipmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment", "shipmentId", shipmentId));

        shipmentRepo.delete(shipment);

        return "Shipment with shipmentId: " + shipmentId + " deleted successfully !!!";
    }
}
