package com.requirementsthesauri.controller.webapp;

import com.requirementsthesauri.model.Domain;
import com.requirementsthesauri.service.AGUtils;
import com.requirementsthesauri.service.DomainService;
import com.requirementsthesauri.service.RequirementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("webapp/domains")
public class WebAppDomainController {

    DomainService domainService = new DomainService();
    RequirementService requirementService = new RequirementService();
    AGUtils agUtils = new AGUtils();

    @GetMapping("/getAllDomains")
    public ModelAndView getAllDomains(Model model) throws Exception {

        model.addAttribute("domains", domainService.getAllDomains());

        return new ModelAndView("domain");
    }

    @PostMapping("/delete")
    public ModelAndView deleteDomain(@RequestParam(value = "uriDomain") String domainURI, Model model) throws Exception {
        domainService.deleteDomain(domainURI);

        model.addAttribute("domains", domainService.getAllDomains());

        return new ModelAndView("redirect:getAllDomains");
    }

    @GetMapping("/create")
    public ModelAndView createDomain(@ModelAttribute Domain domain, Model model) throws Exception {

        model.addAttribute("domain", domain);
        model.addAttribute("allDomains", domainService.getAllDomains());
        model.addAttribute("allRequirements", requirementService.getAllRequirements());

        return new ModelAndView("createDomain");
    }

    @PostMapping("/create")
    public ModelAndView createNewDomain(@ModelAttribute Domain domain, BindingResult bindingResult, Model model) throws Exception {

        if(domain.getBroaderDomainID().equals("")){
            domain.setBroaderDomainID(null);
        }
        if(domain.getNarrowerDomainID().isEmpty()){
            domain.getNarrowerDomainID().add(null);
        }
        if(domain.getNarrowerRequirementID().isEmpty()){
            domain.getNarrowerRequirementID().add(null);
        }

        if (bindingResult.hasErrors()) {
            //errors processing
        }

        domain.setDomainID(agUtils.removeAccents(domain.getLabel().toLowerCase()));
        domain.setDomainID(domain.getDomainID().replace(" ", ""));

        List<Domain> domains = new ArrayList<>();
        domains.add(domain);

        model.addAttribute("domain", domainService.createDomain(domains));


        return new ModelAndView("redirect:getAllDomains");
    }

    @PostMapping(value = "/getDomain")
    public ModelAndView getDomain(@RequestParam(value = "uriDomain") String domainURI,  Model model) throws Exception {

        if(domainURI.contains("dbpedia")){
            return new ModelAndView (domainURI);
        }else {

            Domain domain = domainService.getDomain(domainURI);

            model.addAttribute("domain", domain);

            model.addAttribute("broaderNarrower", domainService.getDomainNarrowerOrBroader(domain.getBroaderDomains()));
            model.addAttribute("domainNarrower", domainService.getDomainNarrowerOrBroader(domain.getNarrowerDomainID()));
            model.addAttribute("requirementNarrower", requirementService.getRequirementNarrower(domain.getNarrowerRequirementID()));

            return new ModelAndView("getDomain");
        }
    }


    @PostMapping(value = "/editDomain")
    public ModelAndView editDomain(@RequestParam(value = "uriDomain") String domainURI,  Model model) throws Exception {

        Domain domain = domainService.getDomain(domainURI);

        model.addAttribute("domain", domain);
        model.addAttribute("allDomains", domainService.getAllDomains());
        model.addAttribute("allRequirements", requirementService.getAllRequirements());

            return new ModelAndView("editDomain");

    }

    @PostMapping("/edit")
    public ModelAndView editOldDomain(@ModelAttribute Domain domain, @RequestParam(value = "uriDomain") String domainURI,  BindingResult bindingResult, Model model) throws Exception {

        if(domain.getBroaderDomainID().equals("")){
            domain.setBroaderDomainID(null);
        }
        if(domain.getNarrowerDomainID().isEmpty()){
            domain.getNarrowerDomainID().add(null);
        }
        if(domain.getNarrowerRequirementID().isEmpty()){
            domain.getNarrowerRequirementID().add(null);
        }

        domainService.deleteDomain(domainURI);


        domain.setDomainID(agUtils.removeAccents(domain.getLabel().toLowerCase()));
        domain.setDomainID(domain.getDomainID().replace(" ", ""));

        List<Domain> domains = new ArrayList<>();
        domains.add(domain);

        model.addAttribute("domain", domainService.createDomain(domains));

        return new ModelAndView("redirect:getAllDomains");
    }
}


