package com.requirementsthesauri.controller.webapp;

import com.requirementsthesauri.model.Domain;
import com.requirementsthesauri.service.DomainService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("webapp/domains")
public class WebAppDomainController {

    DomainService domainService = new DomainService();

    @GetMapping("/getAllDomains")
    public ModelAndView getAllDomains(Model model) throws Exception {

        model.addAttribute("domains", domainService.getAllDomains());

        return new ModelAndView("domain");
    }

    @PostMapping("/getAllDomains")
    public ModelAndView deleteDomain(@RequestParam(value="uriDomain") String domainURI, Model model) throws Exception {
        domainService.deleteDomain(domainURI);

        model.addAttribute("domains", domainService.getAllDomains());

        return new ModelAndView("redirect:getAllDomains");
    }

    @GetMapping("/create")
    public ModelAndView createDomain(Model model) throws Exception {

        return new ModelAndView("createDomain");
    }

    @PostMapping("/createDomain")
    public ModelAndView createNewDomain(@ModelAttribute Domain domain, BindingResult bindingResult, Model model) throws Exception {

        if (bindingResult.hasErrors()) {
            //errors processing
        }
        model.addAttribute("domain", domain);

        List<Domain> domains  = new ArrayList<>();
        domains.add(domain);
        domainService.createDomain(domains);

        return new ModelAndView("redirect:getAllDomains");
    }
}
