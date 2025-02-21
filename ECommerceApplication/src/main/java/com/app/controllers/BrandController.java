package com.app.controllers;

import com.app.config.AppConstants;
import com.app.entites.Brand;
import com.app.payloads.BrandDTO;
import com.app.payloads.BrandResponse;
import com.app.services.BrandService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "E-Commerce Application")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping("/public/brands")
    public ResponseEntity<BrandResponse> getBrands(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_BRANDS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {

        BrandResponse brandResponse = brandService.getAllBrands(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<BrandResponse>(brandResponse, HttpStatus.FOUND);
    }

    @PostMapping("/admin/brands")
    public ResponseEntity<BrandDTO> addBrand(@Valid @RequestBody Brand brand) {
        BrandDTO savedBrandDTO = brandService.addBrand(brand);

        return new ResponseEntity<BrandDTO>(savedBrandDTO, HttpStatus.CREATED);
    }

    @PutMapping("/admin/brands/{brandId}")
    public ResponseEntity<BrandDTO> updateBrand(@RequestBody Brand brand,
                                                  @PathVariable Long brandId) {
        BrandDTO updatedBrand = brandService.updateBrand(brandId, brand);

        return new ResponseEntity<BrandDTO>(updatedBrand, HttpStatus.OK);
    }

    @GetMapping("/admin/brands/{brandId}")
    public ResponseEntity<BrandDTO> getBrandById(@PathVariable Long brandId) {
        BrandDTO brandDTO = brandService.searchBrandById(brandId);

        return new ResponseEntity<BrandDTO>(brandDTO, HttpStatus.OK);
    }

    @DeleteMapping("/admin/brands/{brandId}")
    public ResponseEntity<String> deleteBrand(@PathVariable Long brandId) {
        String status = brandService.deleteBrand(brandId);

        return new ResponseEntity<String>(status, HttpStatus.OK);
    }
}
