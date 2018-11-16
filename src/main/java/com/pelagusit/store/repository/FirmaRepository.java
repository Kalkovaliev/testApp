package com.pelagusit.store.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.pelagusit.store.domain.Firma;


public interface FirmaRepository extends JpaSpecificationRepository<Firma>  {

/*	@Query("select p from Firma p where p.ime = ?1")
	List<Firma> getAllProizvodsByName(String name);
*/	

	@Query("select p from Firma p ")
	Page<Firma> getAllFirms(Pageable page);
	
	@Query("select p from Firma p where p.name  like %?1% ")
	Page<Firma> findFirmsbyName(String name,Pageable page);
	
	
	@Query("select p from Firma p where p.city.id =?1 ")
	Page<Firma> findFirmsbyCity(long id,Pageable page);
	
	
	@Query("select p from Firma p where p.id =?1 ")
	Firma findFirmbyId(long id);
	
	
	@Modifying  
	@Transactional
	@Query("update Firma set name=?1 where id=?2")
	List<Firma> updateFirma(String name,long id);
	
	@Query("select p from Firma p where p.name like %?1% and  p.city.id=?2")
	Page<Firma> findFirmsbyNameAndCity(String name,long id,Pageable page);
	
	

	
	@Query("select p from Firma p where p.name  like %?1% ")
	List<Firma> findFirmIdbyName(String name);
	
	/*@Query("select p from Proizvod p where p.kolicina = 0")
	List<Proizvod> getAllProizvodZalihi();
	
	

	@Query("select p from Proizvod p where p.id = ?1")
	Proizvod getProizvodById(Long id);
	
	*/
}
