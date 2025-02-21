package com.app.repositories;

import com.app.entites.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepo extends JpaRepository<Shipment, Long> {
    Shipment findShipmentByShipmentId(long id);

    Shipment findShipmentByShipmentType(String shipmentType);
}
