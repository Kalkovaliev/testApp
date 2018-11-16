package com.pelagusit.store.web.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pelagusit.store.domain.City;
import com.pelagusit.store.domain.Firma;
import com.pelagusit.store.domain.Order;
import com.pelagusit.store.domain.OrderProducts;
import com.pelagusit.store.domain.Product;
import com.pelagusit.store.domain.User;
import com.pelagusit.store.repository.OrderRepository;
import com.pelagusit.store.repository.ProductRepository;
import com.pelagusit.store.repository.UserRepository;
import com.pelagusit.store.security.SecurityUtils;
import com.pelagusit.store.service.CityService;
import com.pelagusit.store.service.FirmaService;
import com.pelagusit.store.service.OrderProductsService;
import com.pelagusit.store.service.OrderService;
import com.pelagusit.store.web.rest.dto.OrderDTO;
import com.pelagusit.store.web.rest.dto.OrderProductDTO;
import com.pelagusit.store.web.rest.dto.OrderProductsUpdateDTO;

@RestController
@RequestMapping("/app/rest/orderProducts")
public class OrderProductsResource extends CrudResource<OrderProducts, OrderProductsService> {

	
	
	@Autowired
	private OrderProductsService orderProductsService;

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public OrderProductsService getService() {
		return orderProductsService;
	}
	
	
	
	
	 @RequestMapping(value = "/insertOrderProducts", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	  public void insertOrderProducts(@RequestBody OrderProductDTO orderDTO, HttpServletRequest request, HttpServletResponse response) {
		 
		 OrderProducts orderItem = new OrderProducts();
		 if(orderDTO!=null){
			// orderItem.setProduct(orderDTO.getProduct_id());
			// orderItem.setOrder(orderDTO.getOrder_id());
			 Product product = productRepository.findOne(orderDTO.getProduct_id());
			 if(product == null) return;
			 
			 Order order = orderRepository.findOne(orderDTO.getOrder_id());
			 if(order == null) return;
			 
			 orderItem.setQuantity(orderDTO.getQuantity());
			 orderItem.setTotalprice(orderDTO.getTotalprice());
			 orderItem.setCreator("user");
			 orderItem.setLastModifier("user");
			 orderItem.setOrder(order);
			 orderItem.setProduct(product);
					 
			 getService().saveAndFlush(orderItem);
			 
		 } else {
			 response.setStatus(HttpServletResponse.SC_CONFLICT);
		 }
	 } 
	 
		
	@RequestMapping(value = "/updateOrderProducts", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	  public void updateOrderProducts(@RequestBody OrderProductsUpdateDTO orderDTO, HttpServletRequest request, HttpServletResponse response) {
		 
		
		 if(orderDTO.getOrderProduct_id()!=0){
		
			 OrderProducts item = getService().findById(orderDTO.getOrderProduct_id());
			 Product product = productRepository.findOne(orderDTO.getProduct_id());
			 if(product == null) return ;
			 
			 Order order = orderRepository.findOne(orderDTO.getOrder_id());
			 if(order == null) return ;
			 
			 item.setQuantity(orderDTO.getQuantity());
			 item.setTotalprice(orderDTO.getTotalprice());
			 item.setCreator("user");
			 item.setLastModifier("user");
			 item.setOrder(order);
			 item.setProduct(product);
					 
			 getService().saveAndFlush(item);
			 
		 } else {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
		 }
	 } 
	 
	 
	 @RequestMapping(value = "/getOrderProducts/{id}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	  public List<OrderProducts> getOrderProducts(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
		 	
		 if(id!=null){
		return getService().getOrderById(id);
		 }
		 else return null;

	    } 
		@RequestMapping(value = "/removeOrderProducts/{id}", method = RequestMethod.POST)
		@ResponseStatus(HttpStatus.OK)
		@ResponseBody
		public void delete(@PathVariable long id) {
		
				OrderProducts item = getService().findById(id);
				
			
				if(item!=null){
				getService().delete(item);
					}
				else return;
			
		    // true -> can delete
		    // false -> cannot delete, f.e. is FK reference somewhere
		  
				
		   
		   
		}
	 

	
			
}
