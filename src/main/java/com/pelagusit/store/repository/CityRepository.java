package com.pelagusit.store.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.pelagusit.store.domain.City;
import com.pelagusit.store.domain.Firma;



public interface CityRepository extends JpaSpecificationRepository<City>  {

/*	@Query("select p from Firma p where p.ime = ?1")
	List<Firma> getAllProizvodsByName(String name);
*/	

	/*@Query("select p from Firma p where p.id >0")
List<Firma> getAllFirms();
	
	@Query("Delete from Firma  where id=?1")
	List<Firma> removeFirma(long id);		@Modifying  
	@Transactional
	@Query("update Firma set name=?1 where id=?2")
	List<Firma> updateFirma(String name,long id);
*/
	
	@Query("select c from City c  where c.name like %?1%")
	City getCityByName(String name);
	
	
	/*@Query("select p from Proizvod p where p.kolicina = 0")
	List<Proizvod> getAllProizvodZalihi();
	
	

	@Query("select p from Proizvod p where p.id = ?1")
	Proizvod getProizvodById(Long id);
	
	*/
}
