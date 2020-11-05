package com.ums.models.services;

import com.ums.models.dao.ClientDao;
import com.ums.models.dao.InvoiceDao;
import com.ums.models.dao.ProductDao;
import com.ums.models.entity.Bill;
import com.ums.models.entity.Client;
import com.ums.models.entity.Product;
import com.ums.models.entity.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientDao clientDao;

    @Autowired
    private InvoiceDao invoiceDao;

    @Autowired
    private ProductDao productDao;

    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return (List<Client>) clientDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Client> findAll(Pageable pageable) {
        return clientDao.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Client findById(Long id) {
        return clientDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Client save(Client client) {
        return clientDao.save(client);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        clientDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Region> findAllRegion() {
        return clientDao.findAllRegion();
    }

    @Override
    @Transactional(readOnly = true)
    public Bill findInvoiceById(Long id) {
        return invoiceDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Bill saveInvoice(Bill invoice) {
        return invoiceDao.save(invoice);
    }

    @Override
    @Transactional
    public void deleteInvoiceById(Long id) {
        invoiceDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findProductByName(String term) {
        return productDao.findByNameContainingIgnoreCase(term);
    }

}
