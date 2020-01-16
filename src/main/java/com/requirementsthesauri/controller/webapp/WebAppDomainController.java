package com.requirementsthesauri.controller.webapp;

import com.requirementsthesauri.service.DomainService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("webapp/domains")
public class WebAppDomainController {

    DomainService domainService = new DomainService();

    @GetMapping("/getAllDomains")
    public ModelAndView getAllDomains(Model model) throws Exception {

        model.addAttribute("domains", domainService.getAllDomains());

        return new ModelAndView("domain");
    }

    @DeleteMapping(value = "/excluir")
    public ModelAndView deleteDomain(@PathVariable(value="domainURI") String domainURI, Model model) throws Exception {
        domainService.deleteDomain(domainURI);

        model.addAttribute("domains", domainService.getAllDomains());

        return new ModelAndView("redirect: domain");
    }

}
