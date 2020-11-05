package com.ums.models.services;

import java.util.List;

import com.ums.models.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ums.models.entity.Client;
import com.ums.models.entity.Bill;
import com.ums.models.entity.Region;

public interface ClientService {

	public List<Client> findAll();
	
	public Page<Client> findAll(Pageable pageable);
	
	public Client findById(Long id);
	
	public Client save(Client client);
	
	public void delete(Long id);
	
	public List<Region> findAllRegion();
	
	public Bill findInvoiceById(Long id);
	
	public Bill saveInvoice(Bill invoice);
	
	public void deleteInvoiceById(Long id);
	
	public List<Product> findProductByName(String term);

}
