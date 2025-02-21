package com.app.services;

import com.app.payloads.OrderDTO;
import com.app.payloads.OrderResponse;

import java.util.List;

public interface OrderService {
	
	OrderDTO placeOrder(String email, Long cartId, String paymentMethod, Long couponId);
	
	OrderDTO getOrder(String email, Long orderId);
	
	List<OrderDTO> getOrdersByUser(String email);
	
	OrderResponse getAllOrders(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
	
	OrderDTO updateOrder(String email, Long orderId, String orderStatus);
}
