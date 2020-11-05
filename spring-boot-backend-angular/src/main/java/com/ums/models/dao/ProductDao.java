package com.ums.models.dao;

import java.util.List;

import com.ums.models.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProductDao extends CrudRepository<Product, Long>{

	@Query("select p from Product p where p.name like %?1%")
	public List<Product> findByName(String term);
	
	public List<Product> findByNameContainingIgnoreCase(String term);
	
	public List<Product> findByNameStartingWithIgnoreCase(String term);
}
