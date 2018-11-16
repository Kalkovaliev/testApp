package com.pelagusit.store.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pelagusit.store.domain.City;
import com.pelagusit.store.domain.Firma;

public interface FirmaService extends DefaultModelCrudService<Firma>{

	//List<Firma> getAllProizvodsByName(String name);
	Page<Firma> getAllFirms(Pageable page);
	
	List<Firma> updateFirma(String name,long id);
	Page<Firma> findFirmsbyName(String name,Pageable page);
	
	Page<Firma> findFirmsbyNameAndCity(String name,long id,Pageable page);
	List<Firma> findFirmIdbyName(String name);
	Page<Firma> findFirmsbyCity(long id,Pageable page);

	public Firma createNewCompany(String name,City city, String adress, String phone);
	public Firma updateCompany(long id,String name,City city, String adress, String phone);
	Firma findFirmbyId(long id);
	
}
