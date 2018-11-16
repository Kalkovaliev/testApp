package com.pelagusit.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pelagusit.store.domain.City;
import com.pelagusit.store.domain.Firma;
import com.pelagusit.store.repository.CityRepository;
import com.pelagusit.store.service.CityService;
import com.pelagusit.store.service.FirmaService;

@Service
public class CityServiceImpl extends DefaultModelCrudServiceImpl<City, CityRepository> implements CityService{

	@Autowired
	private CityRepository cityRepository;
	
	@Override
	protected CityRepository getRepository() {
		return cityRepository;
	}

	@Override
	public City getCityByName(String name) {
		// TODO Auto-generated method stub
		return getRepository().getCityByName(name);
	}

	
//	@Override
//	public List<Firma> getAllProizvodsByName(String name) {
//		return getRepository().getAllProizvodsByName(name);
//	}

	
}
