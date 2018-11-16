package com.pelagusit.store.web.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.pelagusit.store.domain.City;
import com.pelagusit.store.domain.Firma;
import com.pelagusit.store.domain.Order;
import com.pelagusit.store.domain.Product;
import com.pelagusit.store.domain.User;
import com.pelagusit.store.repository.FirmaRepository;
import com.pelagusit.store.repository.UserRepository;
import com.pelagusit.store.security.SecurityUtils;
import com.pelagusit.store.service.CityService;
import com.pelagusit.store.service.FirmaService;
import com.pelagusit.store.web.rest.util.RequestProcessor;

@RestController
@RequestMapping("/app/rest/company")
public class FirmaResource extends CrudResource<Firma, FirmaService> {

	@Autowired
	private FirmaRepository firmaRepository;

	@Autowired
	private FirmaService firmaService;

	@Autowired
	private CityService cityService;

	@Override
	public FirmaService getService() {
		return firmaService;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void insertCompany(@RequestBody Firma firm, HttpServletRequest request, HttpServletResponse response) {

		/*
		 * if (firm == null) { return null; }
		 * 
		 * 
		 * 
		 * firm.setLastModifier("admin"); firm.setCreator("admin"); return
		 * getService().saveAndFlush(firm);
		 */
		List<Firma> companies = firmaRepository.findFirmIdbyName(firm.getName());
		if (companies != null && companies.size() > 0 && firm.getId() != companies.get(0).getId()) {
			response.setStatus(HttpServletResponse.SC_CONFLICT);
			return;
		}

		firmaService.createNewCompany(firm.getName(), firm.getCity(), firm.getAdress(), firm.getPhone());
	}

	@RequestMapping(value = "/getCityByName", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public City getAll(@RequestBody String name) {
		return cityService.getCityByName(name);
	}

	@RequestMapping(value = "/getFirmsByName", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<Firma> getFirmsByName(String firmaName, int count, int page, HttpServletRequest request) {
		Sort sort = RequestProcessor.sorting(request);
		Pageable pageFirma = new PageRequest(page - 1, count, sort);
		Page<Firma> result = getService().findFirmsbyName(firmaName, pageFirma);
		return result;
	}

	@RequestMapping(value = "/getFirmsByCity", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<Firma> getFirmsByCity(String firmaName, long city_id, int count, int page, HttpServletRequest request) {
		Sort sort = RequestProcessor.sorting(request);
		Pageable pageFirma = new PageRequest(page - 1, count, sort);
		Page<Firma> result = getService().findFirmsbyCity(city_id, pageFirma);
		return result;
	}

	@RequestMapping(value = "/getFirmsByNameAndCity", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<Firma> getFirmsByNameAndCity(String firmaName, long city_id, int count, int page,
			HttpServletRequest request) {
		Sort sort = RequestProcessor.sorting(request);
		Pageable pageFirma = new PageRequest(page - 1, count, sort);
		Page<Firma> result = getService().findFirmsbyNameAndCity(firmaName, city_id, pageFirma);
		return result;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public Firma get(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
		Firma entity = getService().findById(id);
		if (entity == null) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}
		return entity;
	}

	@RequestMapping(value = "/getCompany/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Firma getAll(@PathVariable Long id) {
		return getService().findById(id);
	}

	@RequestMapping(value = "/removeCompany/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void delete(@PathVariable long id) {

		Firma firm = getService().findById(id);

		if (firm != null) {

			getService().delete(firm);
		}

	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateFirma(@RequestBody Firma firm, HttpServletRequest request, HttpServletResponse response) {
		List<Firma> companies = firmaRepository.findFirmIdbyName(firm.getName());
		if (companies != null && companies.size() > 0 && firm.getId() != companies.get(0).getId()) {
			response.setStatus(HttpServletResponse.SC_CONFLICT);
			return;
		}
		firmaService.updateCompany(firm.getId(), firm.getName(), firm.getCity(), firm.getAdress(), firm.getPhone());
	}

	@RequestMapping(value = "/getCompanies", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<Firma> getAll(String companyName, long cityId, int count, int page, HttpServletRequest request) {
		Sort sort = RequestProcessor.sorting(request);
		Pageable pageable = new PageRequest(page - 1, count, sort);
		Page<Firma> companies = null;

		if ((companyName == null || companyName.isEmpty()) && (cityId == 0)) {
			companies = getService().getAllFirms(pageable);
		} else if (companyName == null || companyName.isEmpty()) {

			companies = getService().findFirmsbyCity(cityId, pageable);

		} else if (cityId == 0) {
			companies = getService().findFirmsbyName(companyName, pageable);

		} else {
			companies = getService().findFirmsbyNameAndCity(companyName, cityId, pageable);
		}

		return companies;
	}

	@RequestMapping(value = "/getAllCompanies", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Firma> getAll() {
		return (List<Firma>) getService().findAll();
	}

}
