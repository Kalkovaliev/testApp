package com.pelagusit.store.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pelagusit.store.domain.Firma;
import com.pelagusit.store.domain.Product;

public interface ProductService extends DefaultModelCrudService<Product>{

	//List<Firma> getAllProizvodsByName(String name);
	/*List<Firma> getAllFirms();
	List<Firma> removeFirma(long id);
	List<Firma> updateFirma(String name,long id);*/
	
	Page<Product> findProductsbyName(String name,Pageable page);
	Page<Product> findProductsbyNameAndCompany(String name,long id,Pageable page);
	Page<Product> findProductsbyCompany(long id,Pageable page);
	Page<Product> getAllProducts(Pageable page);
	public Product updateProduct(Long id, String name, Firma company, String description, Double price,boolean isAvailable);
	public Product createNewProduct(String name, Firma company, String description, Double price,boolean isAvailable);
	Product findProductbyName(String name);


}
