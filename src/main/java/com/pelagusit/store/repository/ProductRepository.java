package com.pelagusit.store.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.pelagusit.store.domain.Firma;
import com.pelagusit.store.domain.Product;


public interface ProductRepository extends JpaSpecificationRepository<Product>  {

/*	@Query("select p from Firma p where p.ime = ?1")
	List<Firma> getAllProizvodsByName(String name);
*/	

	/*@Query("select p from Firma p where p.id >0")
	List<Firma> getAllFirms();
	
	@Query("Delete from Firma  where id=?1")
	List<Firma> removeFirma(long id);
	
	@Modifying  
	@Transactional
	@Query("update Firma set name=?1 where id=?2")
	List<Firma> updateFirma(String name,long id);*/
	
	
	
	/*@Query("select p from Proizvod p where p.kolicina = 0")
	List<Proizvod> getAllProizvodZalihi();
	
	

	@Query("select p from Proizvod p where p.id = ?1")
	Proizvod getProizvodById(Long id);
	
	
	
	*/


	
	@Query("select p from Product p")
	Page<Product> getAllProducts(Pageable page);
	
	
	@Query("select p from Product p where p.name  like %?1% ")
	Page<Product> findProductsbyName(String name,Pageable page);
	
	@Query("select p from Product p where p.name  like %?1% ")
	Product findProductbyName(String name);
	
	
	
	@Query("select p from Product p where p.company.id=?1")
	Page<Product> findProductsbyCompany(long id,Pageable page);		
	
	@Query("select p from Product p where p.name like %?1% and  p.company.id=?2")
	Page<Product> findProductsbyNameAndCompany(String name,long id,Pageable page);	

	@Query("select p from Product p where p.name=?1 and  p.company.id=?2")
	List<Product> findProductByNameAndCompany(String name,long companyId);
	
}
