package com.requirementsthesauri.controller.webapp;

import com.requirementsthesauri.service.DomainService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}
