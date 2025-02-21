package com.app.services;

import com.app.entites.Brand;
import com.app.entites.Coupon;
import com.app.payloads.BrandDTO;
import com.app.payloads.BrandResponse;
import com.app.payloads.CouponDTO;
import com.app.payloads.CouponResponse;

public interface BrandService {
    BrandDTO addBrand(Brand brand);

    BrandResponse getAllBrands(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    BrandDTO updateBrand(Long brandId, Brand brand);

    BrandDTO searchBrandById(Long brandId);

    String deleteBrand(Long brandId);
}
