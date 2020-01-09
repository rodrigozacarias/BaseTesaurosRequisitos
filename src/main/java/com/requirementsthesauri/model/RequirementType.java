package com.requirementsthesauri.model;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
@EntityScan
public class RequirementType {

    private String requirementTypeID;
    private String url;
    private String label;
    private String prefLabel;
    private String altLabel;
    private String description;
    private String linkDbpedia;
    private String broaderRequirementTypeID;
    private List<String> narrowerRequirementTypeID;
    private List<String> narrowerRequirementID;

    public RequirementType() {
    }

    public RequirementType(String requirementTypeID, String url, String label, String prefLabel, String altLabel, String description, String linkDbpedia,
                           String broaderRequirementTypeID, List<String> narrowerRequirementTypeID, List<String> narrowerRequirementID) {
        this.requirementTypeID = requirementTypeID;
        this.url = url;
        this.label = label;
        this.prefLabel = prefLabel;
        this.altLabel = altLabel;
        this.description = description;
        this.linkDbpedia = linkDbpedia;
        this.broaderRequirementTypeID = broaderRequirementTypeID;
        this.narrowerRequirementTypeID = narrowerRequirementTypeID;
        this.narrowerRequirementID = narrowerRequirementID;
    }

    public String getRequirementTypeID() {
        return requirementTypeID;
    }

    public void setRequirementTypeID(String requirementTypeID) {
        this.requirementTypeID = requirementTypeID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPrefLabel() {
        return prefLabel;
    }

    public void setPrefLabel(String prefLabel) {
        this.prefLabel = prefLabel;
    }

    public String getAltLabel() {
        return altLabel;
    }

    public void setAltLabel(String altLabel) {
        this.altLabel = altLabel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLinkDbpedia() {
        return linkDbpedia;
    }

    public void setLinkDbpedia(String linkDbpedia) {
        this.linkDbpedia = linkDbpedia;
    }

    public String getBroaderRequirementTypeID() {
        return broaderRequirementTypeID;
    }

    public void setBroaderRequirementTypeID(String broaderRequirementTypeID) {
        this.broaderRequirementTypeID = broaderRequirementTypeID;
    }

    public List<String> getNarrowerRequirementTypeID() {
        return narrowerRequirementTypeID;
    }

    public void setNarrowerRequirementTypeID(List<String> narrowerRequirementTypeID) {
        this.narrowerRequirementTypeID = narrowerRequirementTypeID;
    }

    public List<String> getNarrowerRequirementID() {
        return narrowerRequirementID;
    }

    public void setNarrowerRequirementID(List<String> narrowerRequirementID) {
        this.narrowerRequirementID = narrowerRequirementID;
    }
}
