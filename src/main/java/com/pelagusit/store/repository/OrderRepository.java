package com.pelagusit.store.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.pelagusit.store.domain.City;
import com.pelagusit.store.domain.Firma;
import com.pelagusit.store.domain.Order;
import com.pelagusit.store.domain.OrderProducts;



public interface OrderRepository extends JpaSpecificationRepository<Order>  {	
	@Query("select o.order from OrderProducts o where o.product.name like %?1% and o.product.company.id = ?2 and date(o.order.dateCreated) = date(?3)")
	Page<Order> getOrdersByProductNameCompanyAndTime(String productName, Long companyId, Date orderDate, Pageable page);
	
	@Query("select o.order from OrderProducts o where o.product.name like %?1%")
	Page<Order> getOrdersByProductName(String productName,Pageable page);
	
	@Query("select o.order from OrderProducts o where o.product.name like %?1% and o.product.company.id = ?2")
	Page<Order> getOrdersByProductNameAndCompanyId(String productName, Long companyId,Pageable page);
	
	@Query("select o.order from OrderProducts o where o.product.name like %?1% and date(o.order.dateCreated) = date(?2)")
	Page<Order> getOrdersByProductNameAndTime(String productName, Date orderDate,Pageable page);
	
	@Query("select o.order from OrderProducts o where o.product.company.id = ?1 and date(o.order.dateCreated) = date(?2)")
	Page<Order> getOrdersByCompanyIdAndTime(Long companyId, Date orderDate,Pageable page);
	

	@Query("select o.order from OrderProducts o where o.product.company.id = ?1")
	Page<Order> getOrdersByCompanyId(Long companyId,Pageable page);
	
	@Query("select o from Order o where date(o.dateCreated) = date(?1)")
	Page<Order> getOrdersByOrderDate(Date orderDate,Pageable page);
	
	@Query("select o from Order o")
	Page<Order> getAllOrders(Pageable page);
	
	@Query("select o from Order o where o.creator = ?1")
	Page<Order> getOrdersByUser(String login, Pageable pageable);
	
	
	
	
	///
	
	
	@Query("select o.order from OrderProducts o where o.product.name like %?1% and o.product.company.id = ?2 and date(o.order.dateCreated) = date(?3) and o.creator = ?4")
	Page<Order> getOrdersByUserAndProductNameCompanyAndTime(String productName, Long companyId, Date orderDate,String login ,Pageable page);
	
	@Query("select o.order from OrderProducts o where o.product.name like %?1% and o.creator = ?2")
	Page<Order> getOrdersByUserAndProductName(String productName,String login,Pageable page);
	
	@Query("select o.order from OrderProducts o where o.product.name like %?1% and o.product.company.id = ?2 and o.creator = ?3")
	Page<Order> getOrdersByUserAndProductNameAndCompanyId(String productName, Long companyId,String login,Pageable page);
	
	@Query("select o.order from OrderProducts o where o.product.name like %?1% and date(o.order.dateCreated) = date(?2) and o.creator = ?3")
	Page<Order> getOrdersByUserAndProductNameAndTime(String productName, Date orderDate,String login,Pageable page);
	
	@Query("select o.order from OrderProducts o where o.product.company.id = ?1 and date(o.order.dateCreated) = date(?2) and o.creator = ?3")
	Page<Order> getOrdersByUserAndCompanyIdAndTime(Long companyId, Date orderDate,String login,Pageable page);
	

	@Query("select o.order from OrderProducts o where o.product.company.id = ?1 and o.creator = ?2")
	Page<Order> getOrdersByUserAndCompanyId(Long companyId,String login,Pageable page);
	
	@Query("select o from Order o where date(o.dateCreated) = date(?1) and o.creator = ?2")
	Page<Order> getOrdersByUserAndOrderDate(Date orderDate,String login,Pageable page);
}
