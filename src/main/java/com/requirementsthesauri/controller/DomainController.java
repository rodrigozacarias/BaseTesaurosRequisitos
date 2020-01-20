package com.requirementsthesauri.controller;

import com.requirementsthesauri.model.Domain;
import com.requirementsthesauri.service.DomainService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("domains")
public class DomainController {

    DomainService domainService = new DomainService();

    @GetMapping("/getAllDomains")
    public List<Domain> getAllDomains() throws Exception {
        return domainService.getAllDomains();
    }

    @GetMapping(value = "/{domainID}", produces = {"application/json",
            "application/xml",
            "application/ld+json",
            "application/n-triples",
            "application/rdf+xml",
            "application/turtle",
            "application/rdf+json"})
    public ResponseEntity<?> getDomain(@PathVariable(value="domainID") String domainID, @RequestHeader("Accept") String accept) throws Exception {
        return domainService.getDomainDescribe(domainID, accept);
    }

    @PostMapping(path = "/createDomainsList", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createDomainList(@RequestBody List<Domain> domains) throws Exception {
        return domainService.createDomain1(domains);
    }

    @PostMapping(path = "/createDomain", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createDomain(@RequestBody Domain domain) throws Exception {
        List<Domain> domains  = new ArrayList<>();
        domains.add(domain);
        return domainService.createDomain1(domains);
    }

    @PutMapping(value = "/{domainID}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateDomain(@PathVariable(value="domainID") String domainID, @RequestBody Domain newDomain) throws Exception {
        return domainService.updateDomain(domainID, newDomain);
    }

    @DeleteMapping(value = "/{domainID}")
    public ResponseEntity<?> deleteDomain(@PathVariable(value="domainID") String domainID) {
        domainService.deleteDomain1(domainID);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
