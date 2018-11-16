package com.pelagusit.store.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.pelagusit.store.domain.Firma;
import com.pelagusit.store.domain.Order;
import com.pelagusit.store.domain.OrderProducts;

public interface OrderService extends DefaultModelCrudService<Order>{
	Page<Order> getOrdersByProductNameCompanyAndTime(String productName, Long companyId, Date orderDate, Pageable page);
	Page<Order> getOrdersByProductName(String productName,Pageable page);
	Page<Order> getAllOrders(Pageable page);
	Page<Order> getOrdersByCompanyId(Long companyId,Pageable page);
	Page<Order> getOrdersByOrderDate(Date orderDate,Pageable page);
	Page<Order> getOrdersByProductNameAndCompanyId(String productName, Long companyId,Pageable page);
	Page<Order> getOrdersByProductNameAndTime(String productName, Date orderDate,Pageable page);
	Page<Order> getOrdersByCompanyIdAndTime(Long companyId, Date orderDate,Pageable page);
	Page<Order> getOrdersByUser(String login, Pageable pageable);
	Page<Order> getOrdersByUserAndProductNameCompanyAndTime(String productName, Long companyId, Date orderDate,String login ,Pageable page);
	Page<Order> getOrdersByUserAndProductName(String productName,String login,Pageable page);
	Page<Order> getOrdersByUserAndProductNameAndCompanyId(String productName, Long companyId,String login,Pageable page);
	Page<Order> getOrdersByUserAndProductNameAndTime(String productName, Date orderDate,String login,Pageable page);
	Page<Order> getOrdersByUserAndCompanyIdAndTime(Long companyId, Date orderDate,String login,Pageable page);
	Page<Order> getOrdersByUserAndCompanyId(Long companyId,String login,Pageable page);
	Page<Order> getOrdersByUserAndOrderDate(Date orderDate,String login,Pageable page);
	
}
