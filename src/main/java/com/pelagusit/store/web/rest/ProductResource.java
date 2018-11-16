package com.pelagusit.store.web.rest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.web.multipart.MultipartFile;

import com.google.zxing.FormatException;
import com.pelagusit.store.domain.City;
import com.pelagusit.store.domain.Firma;
import com.pelagusit.store.domain.Product;
import com.pelagusit.store.domain.UploadError;
import com.pelagusit.store.domain.User;
import com.pelagusit.store.repository.FirmaRepository;
import com.pelagusit.store.repository.ProductRepository;
import com.pelagusit.store.repository.UserRepository;
import com.pelagusit.store.security.SecurityUtils;
import com.pelagusit.store.service.CityService;
import com.pelagusit.store.service.FirmaService;
import com.pelagusit.store.service.ProductService;
import com.pelagusit.store.web.rest.util.RequestProcessor;

@RestController
@RequestMapping("/app/rest/product")
public class ProductResource extends CrudResource<Product, ProductService> {

	@Autowired
	private ProductRepository productRepository;
	


	@Autowired
	private ProductService productService;

	@Autowired
	private FirmaService firmaService;

	@Override
	public ProductService getService() {
		return productService;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void insertProduct(@RequestBody Product product, HttpServletRequest request, HttpServletResponse response) {

		if (product == null) {
			return ;
		}
		
		List<Product> products = productRepository.findProductByNameAndCompany(product.getName(),
				product.getCompany().getId());
		if (products != null && products.size() > 0 && product.getId() != products.get(0).getId()){
			response.setStatus(HttpServletResponse.SC_CONFLICT);
			return ;
		}
		productService.createNewProduct(product.getName(), product.getCompany(), product.getDescription(), product.getPrice(),product.isAvailable());
	

	}
	
	@RequestMapping(value = "/savedata", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UploadError> insertProducts(@RequestParam(value="file") MultipartFile file , @RequestParam(value="company") long company, HttpServletRequest request, HttpServletResponse response) {

		List<Product> products = new ArrayList<Product>();
		List<UploadError> errors = new ArrayList<UploadError>();
		
		Firma firma = firmaService.findFirmbyId(company);
		
		BufferedReader br = null;
		FileReader fr = null;

		try {

			fr = new FileReader(file.getOriginalFilename());
			br = new BufferedReader(fr);

			String sCurrentLine;

			br = new BufferedReader(new FileReader(file.getOriginalFilename()));
			List<String> lines = new ArrayList<String>();
			while ((sCurrentLine = br.readLine()) != null) {
				lines.add(sCurrentLine);
			}
			
			if(lines.size() == 0)
			{
				UploadError empty = new UploadError();
				///
				empty.setAttribute("No attributes in the table");
				empty.setLineNumber(0);
				empty.setErrorName("Table has no headers");
				errors.add(empty);
				
				return errors;
			}
			String[] columnsHeader = lines.get(0).split(";");

			for (int i = 0; i < columnsHeader.length; i++)
			{
				
					// logic for headers
				if(i==0){
					if (!columnsHeader[i].equals("name")) {
						UploadError header = new UploadError();
						header.setAttribute(columnsHeader[i]);
						header.setLineNumber(0);
						header.setErrorName("Header type for name is not compatible");
						errors.add(header);
						return errors;
					}
				}
				else if(i==1){
					if (!columnsHeader[i].equals("price")) {
						UploadError header = new UploadError();
						header.setAttribute(columnsHeader[i]);
						header.setLineNumber(0);
						header.setErrorName("Header type for price is not compatible");
						errors.add(header);
						return errors;
					}
				}
				else if(i==2){
					if (!columnsHeader[i].equals("description")) {
						UploadError header = new UploadError();
						header.setAttribute(columnsHeader[i]);
						header.setLineNumber(0);
						header.setErrorName("Header type for description is not compatible");
						errors.add(header);
						return errors;
					}
				}
				else
				{
					UploadError header = new UploadError();
					header.setAttribute(columnsHeader[i]);
					header.setLineNumber(0);
					header.setErrorName("Your table contains more headers than it should have");
					errors.add(header);
					return errors;
					
				}
				
			}
			
			for(int i = 1; i< lines.size(); i++)
			{
				
				String [] columns = lines.get(i).split(";");
				Product product = new Product();
					for(int j=0;j<columns.length;j++)
					{
						if(columns.length>3)
						{
								
								UploadError header = new UploadError();
								header.setAttribute("Column numbers in a row");
								header.setLineNumber(i);
								header.setErrorName("Your table with row "+i+" contains more column values than it should have");
								errors.add(header);
								continue;
								//more columns than needed
							
						}
					
						if(j==0)
						{
							//name type of String
							try{
								product.setName(columns[j]);
								
							}
							catch(Exception ex)
							{
								UploadError header = new UploadError();
								header.setAttribute(columns[j]);
								header.setLineNumber(i);
								header.setErrorName("row:"+i+" column:"+j +" attribute:"+ columns[j]+" is not in correct format");
								errors.add(header);
								
								
							}
							
						}
						else if(j==1)
						{
							//price double
							try{
								double price = Double.parseDouble(columns[j]);
								product.setPrice(price);
										
							}
							catch(NumberFormatException  ex){
								
								UploadError header = new UploadError();
								header.setAttribute(columns[j]);
								header.setLineNumber(i);
								header.setErrorName("row:"+i+" column:"+j +" attribute:"+ columns[j]+" is not in correct format");
							errors.add(header);
							
							}
						
						}
						else if(j==2) 
						{
													
							try{
								product.setDescription(columns[j]);
								products.add(product);
								
							}
							catch(Exception ex)
							{
								UploadError header = new UploadError();
								header.setAttribute(columns[j]);
								header.setLineNumber(i);
								header.setErrorName("row:"+i+" column:"+j +" attribute:"+ columns[j]+" is not in correct format");
								errors.add(header);																
							}
						}
						
					
					}
					
			}
				
			

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();
				
				if(errors.isEmpty()){
					//save and flush products;
				for(int i =0;i<products.size();i++)
				{
				Product p	= productRepository.findProductbyName(products.get(i).getName());
					if(p==null){
						
						productService.createNewProduct(products.get(i).getName(), firma, products.get(i).getDescription(), products.get(i).getPrice(),true);
					}
					else
					{
						productService.updateProduct(p.getId(),products.get(i).getName(), firma, products.get(i).getDescription(), products.get(i).getPrice(),true);
					}
				}
				}
				else
					return errors;

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
		return errors;
		
		

	}

	@RequestMapping(value = "/getProductsByNameAndCity", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<Product> getProductsByNameAndCity(String productName, long company_id, int count, int page,
			HttpServletRequest request) {
		Sort sort = RequestProcessor.sorting(request);
		Pageable pageProduct = new PageRequest(page - 1, count, sort);
		Page<Product> result = getService().findProductsbyNameAndCompany(productName, company_id, pageProduct);
		return result;
	}

	@RequestMapping(value = "/getProductsByCompany", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<Product> getProductsByCompany(String productName, long company_id, int count, int page,
			HttpServletRequest request) {
		Sort sort = RequestProcessor.sorting(request);
		Pageable pageProduct = new PageRequest(page - 1, count, sort);
		Page<Product> result = getService().findProductsbyCompany(company_id, pageProduct);
		return result;
	}

	@RequestMapping(value = "/getProductsByName", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<Product> getProductsByName(String productName, int count, int page, HttpServletRequest request) {
		Sort sort = RequestProcessor.sorting(request);
		Pageable pageProduct = new PageRequest(page - 1, count, sort);
		Page<Product> result = getService().findProductsbyName(productName, pageProduct);
		return result;
	}

	@RequestMapping(value = "/getFirmIdByName", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Firma> getAll(@RequestBody String name) {
		return firmaService.findFirmIdbyName(name);
	}

	@RequestMapping(value = "/getProduct/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Product getAll(@PathVariable Long id) {
		return getService().findById(id);
	}

	@RequestMapping(value = "/removeProduct/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void delete(@PathVariable long id) {

		Product product = getService().findById(id);

		if (product != null) {
		
			getService().delete(product);
		}
	}
	 @RequestMapping(value = "/getProducts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public Page<Product> getAll(String productName, long companyId, int count, int page,
				HttpServletRequest request) {
			Sort sort = RequestProcessor.sorting(request);
			Pageable pageable = new PageRequest(page - 1, count, sort);
					Page<Product> products = null;
		
			

			if ((productName == null || productName.isEmpty()) && (companyId == 0)) {
				products = getService().getAllProducts(pageable);
			} else if (productName == null || productName.isEmpty()) {
			
				products = getService().findProductsbyCompany(companyId, pageable);

			}
			else if (companyId == 0)
			{
				products = getService().findProductsbyName(productName, pageable);
				
			}
			else
			{
				products = getService().findProductsbyNameAndCompany(productName, companyId, pageable);
			}

			return products;
		}

	 
	

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateProduct(@RequestBody Product product,HttpServletRequest request, HttpServletResponse response) {
		List<Product> products = productRepository.findProductByNameAndCompany(product.getName(), product.getCompany().getId());
		if (products != null && products.size() > 0 && product.getId() != products.get(0).getId()) {
			response.setStatus(HttpServletResponse.SC_CONFLICT);
			return;
		}
		productService.updateProduct(product.getId(),product.getName(),product.getCompany(),product.getDescription(), product.getPrice(),product.isAvailable());	
		

	}

	@RequestMapping(value = "/getAllProducts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Product> getAll() {
		return (List<Product>) getService().findAll();
	}

}
