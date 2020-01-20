package com.requirementsthesauri.controller;

import com.requirementsthesauri.model.Requirement;
import com.requirementsthesauri.service.RequirementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("requirements")
public class RequirementController {

    RequirementService requirementService = new RequirementService();

    @GetMapping("/getAllRequirements")
    public ResponseEntity<?> getAllRequirements(){
        return requirementService.getAllRequirements1();
    }

    @GetMapping(value = "/{requirementID}", produces = {"application/json",
            "application/xml",
            "application/ld+json",
            "application/n-triples",
            "application/rdf+xml",
            "application/turtle",
            "application/rdf+json"})
    public ResponseEntity<?> getRequirement(@PathVariable(value="requirementID") String requirementID, @RequestHeader("Accept") String accept) throws Exception {
        return requirementService.getRequirementDescribe(requirementID, accept);
    }

    @PostMapping(path = "/createRequirementsList", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createRequirementsList(@RequestBody List<Requirement> requirements) throws Exception {
        return requirementService.createRequirement1(requirements);
    }

    @PostMapping(path = "/createRequirement", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createRequirement(@RequestBody Requirement requirement) throws Exception {
        List<Requirement> requirements  = new ArrayList<>();
        requirements.add(requirement);
        return requirementService.createRequirement1(requirements);
    }

    @PutMapping(value = "/{requirementID}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateRequirement(@PathVariable(value="requirementID") String requirementID, @RequestBody Requirement newRequirement) throws Exception {
        return requirementService.updateRequirement(requirementID, newRequirement);
    }

    @DeleteMapping(value = "/{requirementID}")
    public ResponseEntity<?> deleteRequirement(@PathVariable(value="requirementID") String requirementID) {
        requirementService.deleteRequirement1(requirementID);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
