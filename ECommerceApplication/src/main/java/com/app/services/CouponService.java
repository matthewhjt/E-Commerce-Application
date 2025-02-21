package com.app.services;

import com.app.entites.Coupon;
import com.app.payloads.*;

public interface CouponService {

    CouponDTO addCoupon(Coupon coupon);

    CouponResponse getAllCoupons(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    CouponDTO updateCoupon(Long couponId, Coupon coupon);


    CouponDTO searchCouponById(Long couponId);

    String deleteCoupon(Long couponId);
}