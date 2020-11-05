package com.ums.controllers;

import com.ums.models.entity.Bill;
import com.ums.models.entity.Product;
import com.ums.models.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class InvoiceRestController {

    @Autowired
    private ClientService clientService;

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/invoice/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Bill show(@PathVariable Long id) {
        return clientService.findInvoiceById(id);
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/invoice/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        clientService.deleteInvoiceById(id);
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/filter/product/{name}")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> findProductName(@PathVariable String name) {
        return clientService.findProductByName(name);
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/invoice")
    @ResponseStatus(HttpStatus.CREATED)
    public Bill create(@RequestBody Bill invoice) {
        return clientService.saveInvoice(invoice);
    }

}
