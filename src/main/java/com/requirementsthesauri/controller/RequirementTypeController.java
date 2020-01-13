package com.requirementsthesauri.controller;

import com.requirementsthesauri.model.RequirementType;
import com.requirementsthesauri.service.RequirementTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("requirementTypes")
public class RequirementTypeController {

    RequirementTypeService requirementTypeService = new RequirementTypeService();

    @GetMapping("/getAllRequirementTypes")
    public ResponseEntity<?> getAllRequirementTypes(){
        return requirementTypeService.getAllRequirementTypes();
    }

    @GetMapping(value = "/{requirementTypeID}", produces = {"application/json",
            "application/xml",
            "application/ld+json",
            "application/n-triples",
            "application/rdf+xml",
            "application/turtle",
            "application/rdf+json"})
    public ResponseEntity<?> getRequirementType(@PathVariable(value="requirementTypeID") String requirementTypeID, @RequestHeader("Accept") String accept) throws Exception {
        return requirementTypeService.getRequirementType(requirementTypeID, accept);
    }

    @PostMapping(path = "/createRequirementTypesList", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createRequirementTypesList(@RequestBody List<RequirementType> requirementTypes) throws Exception {
        return requirementTypeService.createRequirementType(requirementTypes);
    }

    @PostMapping(path = "/createRequirementType", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createRequirementTypesList(@RequestBody RequirementType requirementType) throws Exception {
        List<RequirementType> requirementTypes  = new ArrayList<>();
        requirementTypes.add(requirementType);
        return requirementTypeService.createRequirementType(requirementTypes);
    }

    @PutMapping(value = "/{requirementTypeID}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateRequirementType(@PathVariable(value="requirementTypeID") String requirementTypeID, @RequestBody RequirementType newRequirementType) throws Exception {
        return requirementTypeService.updateRequirementType(requirementTypeID, newRequirementType);
    }

    @DeleteMapping(value = "/{requirementTypeID}")
    public ResponseEntity<?> deleteRequirementType(@PathVariable(value="requirementTypeID") String requirementTypeID) {
        requirementTypeService.deleteRequirementType(requirementTypeID);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
    

