package com.pelagusit.store.service;

import java.util.List;

import com.pelagusit.store.domain.City;


public interface CityService extends DefaultModelCrudService<City>{

	//List<Firma> getAllProizvodsByName(String name);
/*	List<Firma> getAllFirms();
	List<Firma> removeFirma(long id);
	List<Firma> updateFirma(String name,long id);

	
*/
	
	City getCityByName(String name);
	
}

