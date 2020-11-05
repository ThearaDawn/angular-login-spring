package com.ums.controllers;

import com.ums.models.entity.Client;
import com.ums.models.entity.Region;
import com.ums.models.services.ClientService;
import com.ums.models.services.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClientRestController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private UploadFileService uploadService;

    // private final Logger log = LoggerFactory.getLogger(ClientRestController.class);

    @GetMapping("/clients")
    public List<Client> index() {
        return clientService.findAll();
    }

    @GetMapping("/clients/page/{page}")
    public Page<Client> index(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 4);
        return clientService.findAll(pageable);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/clients/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {

        Client cliente = null;
        Map<String, Object> response = new HashMap<>();

        try {
            cliente = clientService.findById(id);
        } catch (DataAccessException e) {
            response.put("message", "Failed to query the database");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (cliente == null) {
            response.put("message", "Client ID: ".concat(id.toString().concat(" does not exist in the database!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Client>(cliente, HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/clients")
    public ResponseEntity<?> create(@Valid @RequestBody Client clientReq, BindingResult result) {

        Client client = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {

            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "Field'" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            client = clientService.save(clientReq);
        } catch (DataAccessException e) {
            response.put("message", "Failed to insert into database");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "The client has been created successfully!");
        response.put("client", client);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/clients/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Client client, BindingResult result, @PathVariable Long id) {

        Client clientObject = clientService.findById(id);

        Client clientUpdate = null;

        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {

            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "Field '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if (clientObject == null) {
            response.put("message", "Error: could not edit, customer ID: "
                    .concat(id.toString().concat(" does not exist in the database!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {

            clientObject.setLastName(client.getLastName());
            clientObject.setFirstName(client.getFirstName());
            clientObject.setEmail(client.getEmail());
            clientObject.setCreateAt(client.getCreateAt());
            clientObject.setRegion(client.getRegion());

            clientUpdate = clientService.save(clientObject);

        } catch (DataAccessException e) {
            response.put("message", "Failed to update client in database");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "The client has been updated successfully!");
        response.put("client", clientUpdate);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/clients/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();

        try {
            Client client = clientService.findById(id);
            String nameOfPhotoArchive = client.getPhoto();

            uploadService.remove(nameOfPhotoArchive);

            clientService.delete(id);
        } catch (DataAccessException e) {
            response.put("message", "Failed to remove client from database");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "The customer successfully removed!");

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/client/upload")
    public ResponseEntity<?> upload(@RequestParam("archive") MultipartFile archive, @RequestParam("id") Long id) {
        Map<String, Object> response = new HashMap<>();

        Client client = clientService.findById(id);

        if (!archive.isEmpty()) {

            String nameArchive = null;
            try {
                nameArchive = uploadService.load(archive);
            } catch (IOException e) {
                response.put("message", "Failed to upload client image");
                response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            String nameOfPhotoArchive = client.getPhoto();

            uploadService.remove(nameOfPhotoArchive);

            client.setPhoto(nameArchive);

            clientService.save(client);

            response.put("client", client);
            response.put("message", "You have successfully uploaded the image: " + nameArchive);

        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @GetMapping("/uploads/img/{namePhoto:.+}")
    public ResponseEntity<Resource> seePhoto(@PathVariable String namePhoto) {

        Resource recurso = null;

        try {
            recurso = uploadService.load(namePhoto);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");

        return new ResponseEntity<Resource>(recurso, httpHeaders, HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/clients/regions")
    public List<Region> listAllRegion() {
        return clientService.findAllRegion();
    }
}
