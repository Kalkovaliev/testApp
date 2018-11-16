package com.pelagusit.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pelagusit.store.domain.OrderProducts;
import com.pelagusit.store.repository.OrderProductsRepository;

import com.pelagusit.store.service.OrderProductsService;

@Service
public class OrderProductsServiceImpl extends DefaultModelCrudServiceImpl<OrderProducts, OrderProductsRepository> implements OrderProductsService{

	@Autowired
	private OrderProductsRepository orderProductsRepository;
	
	@Override
	protected OrderProductsRepository getRepository() {
		return orderProductsRepository;
	}

	@Override
	public List<OrderProducts> getOrderById(Long id) {
		// TODO Auto-generated method stub
		return getRepository().getOrderById(id);
	}

	
}
