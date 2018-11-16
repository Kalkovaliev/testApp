package com.pelagusit.store.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pelagusit.store.domain.Firma;
import com.pelagusit.store.domain.Order;
import com.pelagusit.store.domain.OrderProducts;
import com.pelagusit.store.repository.FirmaRepository;
import com.pelagusit.store.repository.OrderProductsRepository;
import com.pelagusit.store.repository.OrderRepository;
import com.pelagusit.store.service.FirmaService;
import com.pelagusit.store.service.OrderService;

@Service
public class OrderServiceImpl extends DefaultModelCrudServiceImpl<Order, OrderRepository> implements OrderService{

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderProductsRepository orderProductsRepository;
	
	@Override
	protected OrderRepository getRepository() {
		return orderRepository;
	}

	@Override
	public Page<Order> getOrdersByProductNameCompanyAndTime(String productName, Long companyId, Date orderDate, Pageable page) {
		return orderRepository.getOrdersByProductNameCompanyAndTime(productName, companyId, orderDate, page);		
	}

	@Override
	public Page<Order> getAllOrders(Pageable page) {
		// TODO Auto-generated method stub
		return orderRepository.getAllOrders(page);
	}

	@Override
	public Page<Order> getOrdersByProductName(String productName, Pageable page) {
		// TODO Auto-generated method stub
		return orderRepository.getOrdersByProductName(productName, page);
	}

	@Override
	public Page<Order> getOrdersByCompanyId(Long companyId, Pageable page) {
		// TODO Auto-generated method stub
		return orderRepository.getOrdersByCompanyId(companyId, page);
	}

	@Override
	public Page<Order> getOrdersByOrderDate(Date orderDate, Pageable page) {
		// TODO Auto-generated method stub
		return orderRepository.getOrdersByOrderDate(orderDate, page);
	}

	@Override
	public Page<Order> getOrdersByProductNameAndCompanyId(String productName, Long companyId, Pageable page) {
		// TODO Auto-generated method stub
		return orderRepository.getOrdersByProductNameAndCompanyId(productName, companyId, page);
	}

	@Override
	public Page<Order> getOrdersByProductNameAndTime(String productName, Date orderDate, Pageable page) {
		// TODO Auto-generated method stub
		return orderRepository.getOrdersByProductNameAndTime(productName, orderDate, page);
	}

	@Override
	public Page<Order> getOrdersByCompanyIdAndTime(Long companyId, Date orderDate, Pageable page) {
		// TODO Auto-generated method stub
		return orderRepository.getOrdersByCompanyIdAndTime(companyId, orderDate, page);
	}

	@Override
	public Page<Order> getOrdersByUser(String login, Pageable pageable) {
		// TODO Auto-generated method stub
		return orderRepository.getOrdersByUser(login,pageable);
	}

	@Override
	public Page<Order> getOrdersByUserAndProductNameCompanyAndTime(String productName, Long companyId, Date orderDate,
			String login, Pageable page) {
		// TODO Auto-generated method stub
		return orderRepository.getOrdersByUserAndProductNameCompanyAndTime(productName, companyId, orderDate, login, page);
	}

	@Override
	public Page<Order> getOrdersByUserAndProductName(String productName, String login, Pageable page) {
		// TODO Auto-generated method stub
		return orderRepository.getOrdersByUserAndProductName(productName, login, page);
	}

	@Override
	public Page<Order> getOrdersByUserAndProductNameAndCompanyId(String productName, Long companyId, String login,
			Pageable page) {
		// TODO Auto-generated method stub
		return orderRepository.getOrdersByUserAndProductNameAndCompanyId(productName, companyId, login, page);
	}

	@Override
	public Page<Order> getOrdersByUserAndProductNameAndTime(String productName, Date orderDate, String login,
			Pageable page) {
		// TODO Auto-generated method stub
		return orderRepository.getOrdersByUserAndProductNameAndTime(productName, orderDate, login, page);
	}

	@Override
	public Page<Order> getOrdersByUserAndCompanyIdAndTime(Long companyId, Date orderDate, String login, Pageable page) {
		// TODO Auto-generated method stub
		return orderRepository.getOrdersByUserAndCompanyIdAndTime(companyId, orderDate, login, page);
	}

	@Override
	public Page<Order> getOrdersByUserAndCompanyId(Long companyId, String login, Pageable page) {
		// TODO Auto-generated method stub
		return orderRepository.getOrdersByUserAndCompanyId(companyId, login, page);
	}

	@Override
	public Page<Order> getOrdersByUserAndOrderDate(Date orderDate, String login, Pageable page) {
		// TODO Auto-generated method stub
		return orderRepository.getOrdersByUserAndOrderDate(orderDate, login, page);
	}

	
}
