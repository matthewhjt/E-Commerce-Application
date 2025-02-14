package com.app.repositories;

import com.app.entites.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepo extends JpaRepository<Brand, Long> {
    Brand findBrandByBrandId(Long brandId);
}
