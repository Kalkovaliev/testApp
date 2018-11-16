package com.pelagusit.store.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.pelagusit.store.domain.Order;
import com.pelagusit.store.domain.OrderProducts;



public interface OrderProductsRepository extends JpaSpecificationRepository<OrderProducts>  {


	@Query("select p from OrderProducts p where p.order.id = ?1")
	List<OrderProducts> getOrderById(Long id);
	
	@Query("select op from OrderProducts op where op.product.name like %?1% and op.product.company.id = ?2 and date(op.order.dateCreated) = date(?3)")
	Page<OrderProducts> getOrdersByProductNameCompanyAndTime(String productName, Long companyId, Date orderDate, Pageable page);
}
