package com.app.services;


import com.app.entites.Shipment;
import com.app.payloads.ShipmentDTO;
import com.app.payloads.ShipmentResponse;

public interface ShipmentService {
    ShipmentDTO addShipment(Shipment shipment);

    ShipmentResponse getAllShipment(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ShipmentDTO updateShipment(Long shipmentId, Shipment shipment);

    ShipmentDTO searchShipmentById(Long shipmentId);

    String deleteShipment(Long shipmentId);
}
