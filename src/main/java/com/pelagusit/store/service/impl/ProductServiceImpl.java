package com.pelagusit.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pelagusit.store.domain.Firma;
import com.pelagusit.store.domain.Product;
import com.pelagusit.store.repository.FirmaRepository;
import com.pelagusit.store.repository.ProductRepository;
import com.pelagusit.store.service.FirmaService;
import com.pelagusit.store.service.ProductService;

@Service
public class ProductServiceImpl extends DefaultModelCrudServiceImpl<Product, ProductRepository> implements ProductService{


	@Autowired
	private ProductRepository productRepository;
	
	@Override
	protected ProductRepository getRepository() {
		return productRepository;
	}
/*
	@Override
	public List<Firma> getAllFirms() {
		// TODO Auto-generated method stub
		return firmaRepository.getAllFirms();
	}

	@Override
	public List<Firma> removeFirma(long id) {
		// TODO Auto-generated method stub
		return firmaRepository.removeFirma(id);
	}

	@Override
	public List<Firma> updateFirma(String name, long id) {
		// TODO Auto-generated method stub
		return firmaRepository.updateFirma(name, id);
	}
*/
//	@Override
//	public List<Firma> getAllProizvodsByName(String name) {
//		return getRepository().getAllProizvodsByName(name);
//	}

	@Override
	public Page<Product> findProductsbyName(String name, Pageable page) {
		// TODO Auto-generated method stub
		return getRepository().findProductsbyName(name, page);
	}

	@Override
	public Page<Product> findProductsbyNameAndCompany(String name, long id, Pageable page) {
		// TODO Auto-generated method stub
		return getRepository().findProductsbyNameAndCompany(name, id, page);
	}

	@Override
	public Page<Product> findProductsbyCompany(long id, Pageable page) {
		// TODO Auto-generated method stub
		return getRepository().findProductsbyCompany(id, page);
	}

	@Override
	public Page<Product> getAllProducts(Pageable page) {
		// TODO Auto-generated method stub
		return getRepository().getAllProducts(page);
	}
	

	@Override
	public Product createNewProduct(String name, Firma company, String description, Double price,boolean isAvailable) {
		Product newProduct = new Product();
		newProduct.setName(name);
		newProduct.setCompany(company);
		newProduct.setDescription(description);
		newProduct.setPrice(price);
		newProduct.setAvailable(isAvailable);
		newProduct.setLastModifier("admin");
		newProduct.setCreator("admin");
		productRepository.saveAndFlush(newProduct);
		return newProduct;
	}

	@Override
	public Product updateProduct(Long id, String name, Firma company, String description, Double price,boolean isAvailable) {
		Product currentProduct = productRepository.findOne(id);
		currentProduct.setName(name);
		currentProduct.setCompany(company);
		currentProduct.setDescription(description);
		currentProduct.setPrice(price);
		currentProduct.setAvailable(isAvailable);
		currentProduct.setLastModifier("admin");
		productRepository.save(currentProduct);
		return currentProduct;
	}

	@Override
	public Product findProductbyName(String name) {
		// TODO Auto-generated method stub
		return getRepository().findProductbyName(name);
	}

	

	
}
