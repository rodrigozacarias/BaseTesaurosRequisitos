package com.requirementsthesauri.controller.webapp;

import com.requirementsthesauri.model.Domain;
import com.requirementsthesauri.service.AGUtils;
import com.requirementsthesauri.service.DomainService;
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
    AGUtils agUtils = new AGUtils();

    @GetMapping("/getAllDomains")
    public ModelAndView getAllDomains(Model model) throws Exception {

        model.addAttribute("domains", domainService.getAllDomains());

        return new ModelAndView("domain");
    }

    @PostMapping("/getAllDomains")
    public ModelAndView deleteDomain(@RequestParam(value = "uriDomain") String domainURI, Model model) throws Exception {
        domainService.deleteDomain(domainURI);

        model.addAttribute("domains", domainService.getAllDomains());

        return new ModelAndView("redirect:getAllDomains");
    }

    @GetMapping("/create")
    public ModelAndView createDomain(@ModelAttribute Domain domain, Model model) throws Exception {

        model.addAttribute("domain", domain);

        return new ModelAndView("createDomain");
    }

    @PostMapping("/create")
    public ModelAndView createNewDomain(@ModelAttribute Domain domain, BindingResult bindingResult, Model model) throws Exception {

        if (bindingResult.hasErrors()) {
            //errors processing
        }
        domain.setDomainID(agUtils.removeAccents(domain.getLabel()));

        domain.getDomainID().concat(domain.getDomainID());

        model.addAttribute("domain", domain);

//        List<Domain> domains = new ArrayList<>();
//        domains.add(domain);
//        domainService.createDomain(domains);

        return new ModelAndView("redirect:getAllDomains");
    }
}


