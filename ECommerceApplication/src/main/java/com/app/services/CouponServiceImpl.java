package com.app.services;

import com.app.entites.Coupon;
import com.app.exceptions.APIException;
import com.app.exceptions.ResourceNotFoundException;
import com.app.payloads.CouponDTO;
import com.app.payloads.CouponResponse;
import com.app.repositories.CouponRepo;
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
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepo couponRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CouponDTO addCoupon(Coupon coupon) {
        Coupon savedCoupon = couponRepo.findCouponByCouponCode(coupon.getCouponCode());

        if (savedCoupon != null) {
            throw new APIException("Category with coupon code '" + coupon.getCouponCode() + "' already exists !!!");
        }

        savedCoupon = couponRepo.save(coupon);
        return modelMapper.map(savedCoupon, CouponDTO.class);
    }

    @Override
    public CouponResponse getAllCoupons(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        Page<Coupon> pageCoupons = couponRepo.findAll(pageDetails);

        List<Coupon> coupons = pageCoupons.getContent();

        if (coupons.size() == 0) {
            throw new APIException("No coupons is created till now");
        }

        List<CouponDTO> couponDTOS = coupons.stream()
                .map(coupon -> modelMapper.map(coupon, CouponDTO.class)).collect(Collectors.toList());

        CouponResponse couponResponse = new CouponResponse();

        couponResponse.setContent(couponDTOS);
        couponResponse.setPageNumber(pageCoupons.getNumber());
        couponResponse.setPageSize(pageCoupons.getSize());
        couponResponse.setTotalElements(pageCoupons.getTotalElements());
        couponResponse.setTotalPages(pageCoupons.getTotalPages());
        couponResponse.setLastPage(pageCoupons.isLast());

        return couponResponse;
    }

    @Override
    public CouponDTO updateCoupon(Long couponId, Coupon coupon) {
        Coupon savedCoupon = couponRepo.findById(couponId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", couponId));

        coupon.setCouponId(couponId);

        savedCoupon = couponRepo.save(coupon);

        return modelMapper.map(savedCoupon, CouponDTO.class);
    }

    @Override
    public CouponDTO searchCouponById(Long couponId) {
        Coupon coupon = couponRepo.findCouponByCouponId(couponId);

        if (coupon == null) {
            throw new ResourceNotFoundException("Coupon", "couponId", couponId);
        }

        return modelMapper.map(coupon, CouponDTO.class);
    }

    @Override
    public String deleteCoupon(Long couponId) {
        Coupon coupon = couponRepo.findById(couponId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", couponId));

        couponRepo.delete(coupon);

        return "Category with categoryId: " + couponId + " deleted successfully !!!";
    }
}
