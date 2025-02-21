package com.app.services;

import com.app.entites.Brand;
import com.app.entites.Coupon;
import com.app.exceptions.APIException;
import com.app.exceptions.ResourceNotFoundException;
import com.app.payloads.BrandDTO;
import com.app.payloads.BrandResponse;
import com.app.payloads.CouponDTO;
import com.app.payloads.CouponResponse;
import com.app.repositories.BrandRepo;
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
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepo brandRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BrandDTO addBrand(Brand brand) {
        Brand savedBrand = brandRepo.findBrandByBrandId(brand.getBrandId());

        if (savedBrand != null) {
            throw new APIException("Brand with brand id '" + brand.getBrandId() + "' already exists !!!");
        }

        savedBrand = brandRepo.save(brand);
        return modelMapper.map(savedBrand, BrandDTO.class);
    }

    @Override
    public BrandResponse getAllBrands(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        Page<Brand> pageBrands = brandRepo.findAll(pageDetails);

        List<Brand> brands = pageBrands.getContent();

        if (brands.size() == 0) {
            throw new APIException("No brands is created till now");
        }

        List<BrandDTO> brandDTOS = brands.stream()
                .map(brand -> modelMapper.map(brand, BrandDTO.class)).collect(Collectors.toList());

        BrandResponse brandResponse = new BrandResponse();

        brandResponse.setContent(brandDTOS);
        brandResponse.setPageNumber(pageBrands.getNumber());
        brandResponse.setPageSize(pageBrands.getSize());
        brandResponse.setTotalElements(pageBrands.getTotalElements());
        brandResponse.setTotalPages(pageBrands.getTotalPages());
        brandResponse.setLastPage(pageBrands.isLast());

        return brandResponse;
    }

    @Override
    public BrandDTO updateBrand(Long brandId, Brand brand) {
        Brand savedBrand = brandRepo.findById(brandId)
                .orElseThrow(() -> new ResourceNotFoundException("Brand", "brandId", brandId));

        brand.setBrandId(brandId);

        savedBrand = brandRepo.save(brand);

        return modelMapper.map(savedBrand, BrandDTO.class);
    }

    @Override
    public BrandDTO searchBrandById(Long brandId) {
        Brand brand = brandRepo.findBrandByBrandId(brandId);

        if (brand == null) {
            throw new ResourceNotFoundException("Brand", "brandId", brandId);
        }

        return modelMapper.map(brand, BrandDTO.class);
    }

    @Override
    public String deleteBrand(Long brandId) {
        Brand brand = brandRepo.findById(brandId)
                .orElseThrow(() -> new ResourceNotFoundException("Brand", "brandId", brandId));

        brandRepo.delete(brand);

        return "Brand with brandId: " + brandId + " deleted successfully !!!";
    }
}