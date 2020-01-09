package com.requirementsthesauri.model;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
@EntityScan
public class Domain {

    private String domainID;
    private String url;
    private String label;
    private String prefLabel;
    private String altLabel;
    private String description;
    private String linkDbpedia;
    private String broaderDomainID;
    private List<String> narrowerDomainID;
    private List<String> narrowerRequirementID;

    public Domain() {
    }

    public Domain(String domainID, String url, String label, String prefLabel, String altLabel, String description,
                  String linkDbpedia, String broaderDomainID, List<String> narrowerDomainID, List<String> narrowerRequirementID) {
        this.domainID = domainID;
        this.url = url;
        this.label = label;
        this.prefLabel = prefLabel;
        this.altLabel = altLabel;
        this.description = description;
        this.linkDbpedia = linkDbpedia;
        this.broaderDomainID = broaderDomainID;
        this.narrowerDomainID = narrowerDomainID;
        this.narrowerRequirementID = narrowerRequirementID;
    }

    public String getDomainID() {
        return domainID;
    }

    public void setDomainID(String domainID) {
        this.domainID = domainID;
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

    public String getBroaderDomainID() {
        return broaderDomainID;
    }

    public void setBroaderDomainID(String broaderDomainID) {
        this.broaderDomainID = broaderDomainID;
    }

    public List<String> getNarrowerDomainID() {
        return narrowerDomainID;
    }

    public void setNarrowerDomainID(List<String> narrowerDomainID) {
        this.narrowerDomainID = narrowerDomainID;
    }

    public List<String> getNarrowerRequirementID() {
        return narrowerRequirementID;
    }

    public void setNarrowerRequirementID(List<String> narrowerRequirementID) {
        this.narrowerRequirementID = narrowerRequirementID;
    }
}
