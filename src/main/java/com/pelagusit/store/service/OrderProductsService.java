package com.pelagusit.store.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pelagusit.store.domain.OrderProducts;

public interface OrderProductsService extends DefaultModelCrudService<OrderProducts>{
	
	List<OrderProducts> getOrderById(Long id);

}
