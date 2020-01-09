package com.requirementsthesauri.model;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
@EntityScan
public class SystemType {

    private String systemTypeID;
    private String url;
    private String label;
    private String prefLabel;
    private String altLabel;
    private String description;
    private String linkDbpedia;
    private String broaderSystemTypeID;
    private List<String> narrowerSystemTypeID;
    private List<String> narrowerRequirementID;

    public SystemType() {
    }


    public SystemType(String systemTypeID, String url, String label, String prefLabel, String altLabel, String description,
                      String linkDbpedia, String broaderSystemTypeID, List<String> narrowerSystemTypeID, List<String> narrowerRequirementID) {
        this.systemTypeID = systemTypeID;
        this.url = url;
        this.label = label;
        this.prefLabel = prefLabel;
        this.altLabel = altLabel;
        this.description = description;
        this.linkDbpedia = linkDbpedia;
        this.broaderSystemTypeID = broaderSystemTypeID;
        this.narrowerSystemTypeID = narrowerSystemTypeID;
        this.narrowerRequirementID = narrowerRequirementID;
    }

    public String getSystemTypeID() {
        return systemTypeID;
    }

    public void setSystemTypeID(String systemTypeID) {
        this.systemTypeID = systemTypeID;
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

    public String getBroaderSystemTypeID() {
        return broaderSystemTypeID;
    }

    public void setBroaderSystemTypeID(String broaderSystemTypeID) {
        this.broaderSystemTypeID = broaderSystemTypeID;
    }

    public List<String> getNarrowerSystemTypeID() {
        return narrowerSystemTypeID;
    }

    public void setNarrowerSystemTypeID(List<String> narrowerSystemTypeID) {
        this.narrowerSystemTypeID = narrowerSystemTypeID;
    }

    public List<String> getNarrowerRequirementID() {
        return narrowerRequirementID;
    }

    public void setNarrowerRequirementID(List<String> narrowerRequirementID) {
        this.narrowerRequirementID = narrowerRequirementID;
    }
}
