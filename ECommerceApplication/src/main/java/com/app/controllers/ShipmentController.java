package com.app.controllers;

import com.app.config.AppConstants;
import com.app.entites.Brand;
import com.app.entites.Shipment;
import com.app.payloads.BrandDTO;
import com.app.payloads.BrandResponse;
import com.app.payloads.ShipmentDTO;
import com.app.payloads.ShipmentResponse;
import com.app.services.BrandService;
import com.app.services.ShipmentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "E-Commerce Application")
public class ShipmentController {
    @Autowired
    private ShipmentService shipmentService;

    @GetMapping("/public/shipments")
    public ResponseEntity<ShipmentResponse> getShipments(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_SHIPMENTS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {

        ShipmentResponse shipmentResponse = shipmentService.getAllShipment(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<ShipmentResponse>(shipmentResponse, HttpStatus.FOUND);
    }

    @PostMapping("/admin/shipments")
    public ResponseEntity<ShipmentDTO> addShipment(@Valid @RequestBody Shipment shipment) {
        ShipmentDTO savedShipmentDTO = shipmentService.addShipment(shipment);

        return new ResponseEntity<ShipmentDTO>(savedShipmentDTO, HttpStatus.CREATED);
    }

    @PutMapping("/admin/shipments/{shipmentId}")
    public ResponseEntity<ShipmentDTO> updateShipment(@RequestBody Shipment shipment,
                                                @PathVariable Long shipmentId) {
        ShipmentDTO updatedShipment = shipmentService.updateShipment(shipmentId, shipment);

        return new ResponseEntity<ShipmentDTO>(updatedShipment, HttpStatus.OK);
    }

    @GetMapping("/admin/shipments/{shipmentId}")
    public ResponseEntity<ShipmentDTO> getBrandById(@PathVariable Long shipmentId) {
        ShipmentDTO shipmentDTO = shipmentService.searchShipmentById(shipmentId);

        return new ResponseEntity<ShipmentDTO>(shipmentDTO, HttpStatus.OK);
    }

    @DeleteMapping("/admin/shipments/{shipmentId}")
    public ResponseEntity<String> deleteShipment(@PathVariable Long shipmentId) {
        String status = shipmentService.deleteShipment(shipmentId);

        return new ResponseEntity<String>(status, HttpStatus.OK);
    }
}
