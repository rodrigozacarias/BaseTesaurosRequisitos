package com.requirementsthesauri.model;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
@EntityScan
public class Requirement {

    private String requirementID;
    private String url;
    private String label;
    private String language;
    private String prefLabel;
    private String altLabel;
    private String problem;
    private String context;
    private String template;
    private String example;
    private String broaderRequirementTypeID;
    private String broaderRequirementID;
    private List<String> broaderDomainID;
    private List<String> broaderSystemTypeID;
    private List<String> narrowerRequirementID;

    public Requirement() {
    }

    public Requirement(String requirementID, String url, String label, String language, String prefLabel, String altLabel,
                       String problem, String context, String template, String example, String broaderRequirementTypeID,
                       String broaderRequirementID, List<String> broaderDomainID, List<String> broaderSystemTypeID,
                       List<String> narrowerRequirementID) {
        this.requirementID = requirementID;
        this.url = url;
        this.label = label;
        this.language = language;
        this.prefLabel = prefLabel;
        this.altLabel = altLabel;
        this.problem = problem;
        this.context = context;
        this.template = template;
        this.example = example;
        this.broaderRequirementTypeID = broaderRequirementTypeID;
        this.broaderRequirementID = broaderRequirementID;
        this.broaderDomainID = broaderDomainID;
        this.broaderSystemTypeID = broaderSystemTypeID;
        this.narrowerRequirementID = narrowerRequirementID;
    }

    public String getRequirementID() {
        return requirementID;
    }

    public void setRequirementID(String requirementID) {
        this.requirementID = requirementID;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
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

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getBroaderRequirementTypeID() {
        return broaderRequirementTypeID;
    }

    public void setBroaderRequirementTypeID(String broaderRequirementTypeID) {
        this.broaderRequirementTypeID = broaderRequirementTypeID;
    }

    public String getBroaderRequirementID() {
        return broaderRequirementID;
    }

    public void setBroaderRequirementID(String broaderRequirementID) {
        this.broaderRequirementID = broaderRequirementID;
    }

    public List<String> getBroaderDomainID() {
        return broaderDomainID;
    }

    public void setBroaderDomainID(List<String> broaderDomainID) {
        this.broaderDomainID = broaderDomainID;
    }

    public List<String> getBroaderSystemTypeID() {
        return broaderSystemTypeID;
    }

    public void setBroaderSystemTypeID(List<String> broaderSystemTypeID) {
        this.broaderSystemTypeID = broaderSystemTypeID;
    }

    public List<String> getNarrowerRequirementID() {
        return narrowerRequirementID;
    }

    public void setNarrowerRequirementID(List<String> narrowerRequirementID) {
        this.narrowerRequirementID = narrowerRequirementID;
    }
}
