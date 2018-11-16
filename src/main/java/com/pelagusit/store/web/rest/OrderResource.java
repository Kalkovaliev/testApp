package com.pelagusit.store.web.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import com.pelagusit.store.domain.Authority;
import com.pelagusit.store.domain.City;
import com.pelagusit.store.domain.Firma;
import com.pelagusit.store.domain.Order;
import com.pelagusit.store.domain.OrderProducts;
import com.pelagusit.store.domain.Product;
import com.pelagusit.store.domain.User;
import com.pelagusit.store.repository.UserRepository;
import com.pelagusit.store.security.SecurityUtils;
import com.pelagusit.store.service.CityService;
import com.pelagusit.store.service.FirmaService;
import com.pelagusit.store.service.OrderService;
import com.pelagusit.store.service.UserService;
import com.pelagusit.store.web.rest.util.RequestProcessor;

@RestController
@RequestMapping("/app/rest/order")
public class OrderResource extends CrudResource<Order, OrderService> {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OrderService orderService;

	@Autowired
	private FirmaService firmaService;

	@Autowired
	private UserService userService;

	@Override
	public OrderService getService() {
		return orderService;
	}

	@RequestMapping(value = "/insertOrder", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Order insertOrder(@RequestBody Order order) {

		Order currentOrder = new Order();
		User currentUser = userRepository.findOne(SecurityUtils.getCurrentLogin());
		if (order != null) {
			currentOrder.setDescription(order.getDescription());

			order.setLastModifier(currentUser.getLogin());
			currentOrder.setCreator(currentUser.getLogin());
			try {
				 getService().saveAndFlush(order);
			} catch (Exception ex) {

			}
		}
		return order;

	}

	@RequestMapping(value = "/removeOrder/{id}", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void delete(@PathVariable long id) {

		Order item = getService().findById(id);

		if (item != null) {
			getService().delete(item);
		} else
			return;

	}

	@RequestMapping(value = "/updateOrder", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Order updateOrder(@RequestBody Order order) {

		Order currentOrder = new Order();

		if (order != null) {
			currentOrder.setId(order.getId());
			currentOrder.setDescription(order.getDescription());
			currentOrder.setLastModifier(order.getLastModifier());
			currentOrder.setCreator(order.getCreator());
			try {
				return getService().saveAndFlush(currentOrder);
			} catch (Exception ex) {

			}
		}
		return null;

	}

	@RequestMapping(value = "/getOrder/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Order getAll(@PathVariable Long id) {
		return getService().findById(id);
	}

	@RequestMapping(value = "/getOrders", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<Order> getAll(String productName, long companyId, String orderDate, int count, int page,
			HttpServletRequest request) {
		Sort sort = RequestProcessor.sorting(request);
		Pageable pageable = new PageRequest(page - 1, count, sort);
		SimpleDateFormat data = new SimpleDateFormat("yyyy-MM-dd");
		Page<Order> orders = null;
		Date dateCreated = new Date();
		boolean flag = false;

		User user = userService.getUserWithAuthorities();
		User currentUser = userRepository.findOne(SecurityUtils.getCurrentLogin());

		if (orderDate != null && !orderDate.isEmpty()) {
			try {
				dateCreated = data.parse(orderDate);
				// dateCreated.getDate();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for (Authority authority : user.getAuthorities()) {
		if (authority.getName().contains("ROLE_ADMIN")) {
				flag = true;
				
			}
		}
		if (flag) {
			if ((productName == null || productName.isEmpty()) && (companyId == 0)
					&& (orderDate == null || orderDate.isEmpty())) {
				orders = getService().getAllOrders(pageable);

			} else if ((companyId == 0) && (orderDate == null || orderDate.isEmpty())) {

				orders = getService().getOrdersByProductName(productName, pageable);

			} else if ((productName == null || productName.isEmpty()) && (orderDate == null || orderDate.isEmpty())) {
				orders = getService().getOrdersByCompanyId(companyId, pageable);

			} else if ((productName == null || productName.isEmpty()) && (companyId == 0)) {

				orders = getService().getOrdersByOrderDate(dateCreated, pageable);

			} else if ((orderDate == null || orderDate.isEmpty())) {

				orders = getService().getOrdersByProductNameAndCompanyId(productName, companyId, pageable);

			} else if (companyId == 0) {
				try {
					dateCreated = data.parse(orderDate);

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				orders = getService().getOrdersByProductNameAndTime(productName, dateCreated, pageable);

			} else if (productName == null || productName.isEmpty()) {
				try {
					dateCreated = data.parse(orderDate);

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				orders = getService().getOrdersByCompanyIdAndTime(companyId, dateCreated, pageable);

			} else {
				try {
					dateCreated = data.parse(orderDate);

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				orders = getService().getOrdersByProductNameCompanyAndTime(productName, companyId, dateCreated,
						pageable);
			}

		} else {
			// orders =
			// getService().getOrdersByUser(currentUser.getLogin(),pageable);
			if ((productName == null || productName.isEmpty()) && (companyId == 0)
					&& (orderDate == null || orderDate.isEmpty())) {
				orders = getService().getOrdersByUser(currentUser.getLogin(), pageable);

			} else if ((companyId == 0) && (orderDate == null || orderDate.isEmpty())) {

				orders = getService().getOrdersByUserAndProductName(productName, currentUser.getLogin(), pageable);

			} else if ((productName == null || productName.isEmpty()) && (orderDate == null || orderDate.isEmpty())) {
				orders = getService().getOrdersByUserAndCompanyId(companyId, currentUser.getLogin(), pageable);

			} else if ((productName == null || productName.isEmpty()) && (companyId == 0)) {

				orders = getService().getOrdersByUserAndOrderDate(dateCreated, currentUser.getLogin(), pageable);

			} else if ((orderDate == null || orderDate.isEmpty())) {

				orders = getService().getOrdersByUserAndProductNameAndCompanyId(productName, companyId,
						currentUser.getLogin(), pageable);

			} else if (companyId == 0) {
				try {
					dateCreated = data.parse(orderDate);

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				orders = getService().getOrdersByUserAndProductNameAndTime(productName, dateCreated,
						currentUser.getLogin(), pageable);

			} else if (productName == null || productName.isEmpty()) {
				try {
					dateCreated = data.parse(orderDate);

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				orders = getService().getOrdersByUserAndCompanyIdAndTime(companyId, dateCreated, currentUser.getLogin(),
						pageable);

			} else {
				try {
					dateCreated = data.parse(orderDate);

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				orders = getService().getOrdersByUserAndProductNameCompanyAndTime(productName, companyId, dateCreated,
						currentUser.getLogin(), pageable);
			}
		}

		return orders;
	}

}
