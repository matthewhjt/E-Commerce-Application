package com.app.repositories;

import com.app.entites.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepo extends JpaRepository<Coupon, Long> {
    Coupon findCouponByCouponCode(String couponCode);

    Coupon findCouponByCouponId(Long couponId);
}
