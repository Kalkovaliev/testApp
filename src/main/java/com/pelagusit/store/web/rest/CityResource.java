package com.pelagusit.store.web.rest;

import java.util.List;

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
import com.pelagusit.store.domain.User;
import com.pelagusit.store.repository.UserRepository;
import com.pelagusit.store.security.SecurityUtils;
import com.pelagusit.store.service.CityService;
import com.pelagusit.store.service.FirmaService;

@RestController
@RequestMapping("/app/rest/city")
public class CityResource extends CrudResource<City, CityService> {

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CityService cityService;


	
	@Override
	public CityService getService() {
		return cityService;
	}
	
	

	@RequestMapping(value = "/getAllCities",
		    method = RequestMethod.GET,
		    produces = MediaType.APPLICATION_JSON_VALUE)
			public List<City> getAll() {
				return (List<City>) getService().findAll();
			}
			
}
