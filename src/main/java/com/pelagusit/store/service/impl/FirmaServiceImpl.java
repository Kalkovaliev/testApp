package com.pelagusit.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pelagusit.store.domain.City;
import com.pelagusit.store.domain.Firma;
import com.pelagusit.store.repository.FirmaRepository;
import com.pelagusit.store.service.FirmaService;

@Service
public class FirmaServiceImpl extends DefaultModelCrudServiceImpl<Firma, FirmaRepository> implements FirmaService{

	@Autowired
	private FirmaRepository firmaRepository;
	
	@Override
	protected FirmaRepository getRepository() {
		return firmaRepository;
	}

	

	@Override
	public List<Firma> updateFirma(String name, long id) {
		// TODO Auto-generated method stub
		return firmaRepository.updateFirma(name, id);
	}

	@Override
	public Page<Firma> findFirmsbyName(String name, Pageable page) {
		// TODO Auto-generated method stub
		return firmaRepository.findFirmsbyName(name, page);
	}


	@Override
	public Page<Firma> findFirmsbyNameAndCity(String name, long id, Pageable page) {
		// TODO Auto-generated method stub
		return firmaRepository.findFirmsbyNameAndCity(name, id, page);
	}


	@Override
	public Page<Firma> findFirmsbyCity(long id, Pageable page) {
		// TODO Auto-generated method stub
		return firmaRepository.findFirmsbyCity(id, page);
	}

	@Override
	public List<Firma> findFirmIdbyName(String name) {
		// TODO Auto-generated method stub
		return  firmaRepository.findFirmIdbyName(name);
	}

	@Override
	public Page<Firma> getAllFirms(Pageable page) {
		// TODO Auto-generated method stub
		return firmaRepository.getAllFirms(page);
	}



	@Override
	public Firma createNewCompany(String name,City city, String adress, String phone) {
	
			Firma newCompany = new Firma();		
			newCompany.setName(name);
			newCompany.setAdress(adress);
			newCompany.setCity(city);
			newCompany.setPhone(phone);
			newCompany.setLastModifier("admin");
			newCompany.setCreator("admin");
			firmaRepository.saveAndFlush(newCompany);		
			return newCompany;
	}
	
	@Override
	public Firma updateCompany(long id,String name,City city,String adress, String phone ) {
		Firma currentCompany = firmaRepository.findOne(id);
		currentCompany.setName(name);
		currentCompany.setAdress(adress);
		currentCompany.setPhone(phone);
		currentCompany.setCity(city);
		currentCompany.setLastModifier("admin");
		firmaRepository.save(currentCompany);
		
		return currentCompany;
	}



	@Override
	public Firma findFirmbyId(long id) {
		// TODO Auto-generated method stub
		return firmaRepository.findFirmbyId(id);
	}


	
}
