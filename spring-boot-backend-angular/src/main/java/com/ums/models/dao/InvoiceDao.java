package com.ums.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.ums.models.entity.Bill;

public interface InvoiceDao extends CrudRepository<Bill, Long>{

}
