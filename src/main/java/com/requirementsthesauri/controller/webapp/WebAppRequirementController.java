package com.requirementsthesauri.controller.webapp;

import com.requirementsthesauri.model.Domain;
import com.requirementsthesauri.model.Requirement;
import com.requirementsthesauri.service.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("webapp/requirements")
public class WebAppRequirementController {

    DomainService domainService = new DomainService();
    RequirementTypeService requirementTypeService = new RequirementTypeService();
    SystemTypeService systemTypeService = new SystemTypeService();
    RequirementService requirementService = new RequirementService();
    AGUtils agUtils = new AGUtils();

    @GetMapping("/getAllRequirements")
    public ModelAndView getAllRequirements(Model model) throws Exception {

        model.addAttribute("requirements", requirementService.getAllRequirements());

        return new ModelAndView("requirement");
    }

    @PostMapping("/delete")
    public ModelAndView deleteRequirement(@RequestParam(value = "uriRequirement") String requirementURI, Model model) throws Exception {
        requirementService.deleteRequirement(requirementURI);

        model.addAttribute("requirements", requirementService.getAllRequirements());

        return new ModelAndView("redirect:getAllRequirements");
    }
    @PostMapping(value = "/getRequirement")
    public ModelAndView getRequirement(@RequestParam(value = "uriRequirement") String requirementURI,  Model model) throws Exception {

        if(requirementURI.contains("dbpedia")){
            return new ModelAndView (requirementURI);
        }else {

            Requirement requirement = requirementService.getRequirement(requirementURI);

            model.addAttribute("requirement", requirement);

            model.addAttribute("requirementBroader", requirementService.getRequirementNarrowerOrBroader(requirement.getBroaderRequirements()));
            model.addAttribute("requirementTypeBroader", requirementTypeService.getRequirementTypeNarrowerOrBroader(requirement.getBroaderRequirementTypes()));
            model.addAttribute("domainBroader", domainService.getDomainNarrowerOrBroader(requirement.getBroaderDomainID()));
            model.addAttribute("systemTypeBroader", systemTypeService.getSystemTypeNarrowerOrBroader(requirement.getBroaderSystemTypeID()));
            model.addAttribute("requirementNarrower", requirementService.getRequirementNarrowerOrBroader(requirement.getNarrowerRequirementID()));


            return new ModelAndView("getRequirement");
        }

    }

    @GetMapping("/create")
    public ModelAndView createRequirement(@ModelAttribute Requirement requirement, Model model) throws Exception {

        model.addAttribute("requirement", requirement);
        model.addAttribute("allDomains", domainService.getAllDomains());
        model.addAttribute("allRequirements", requirementService.getAllRequirements());
        model.addAttribute("allRequirementTypes", requirementTypeService.getAllRequirementTypes());
        model.addAttribute("allSystemTypes", systemTypeService.getAllSystemTypes());

        return new ModelAndView("createRequirement");
    }

    @PostMapping("/create")
    public ModelAndView createNewRequirement(@ModelAttribute Requirement requirement, BindingResult bindingResult, Model model) throws Exception {

        if(requirement.getBroaderRequirementID().equals("")){
            requirement.setBroaderRequirementID(null);
        }
        if(requirement.getBroaderRequirementTypeID().equals("")){
            requirement.setBroaderRequirementTypeID(null);
        }
        if(requirement.getBroaderDomainID().isEmpty()){
            requirement.getBroaderDomainID().add(null);
        }
        if(requirement.getBroaderSystemTypeID().isEmpty()){
            requirement.getBroaderSystemTypeID().add(null);
        }
        if(requirement.getNarrowerRequirementID().isEmpty()){
            requirement.getNarrowerRequirementID().add(null);
        }

        if (bindingResult.hasErrors()) {
            //errors processing
        }

        requirement.setRequirementID(agUtils.removeAccents(requirement.getLabel().toLowerCase()));
        requirement.setRequirementID(requirement.getRequirementID().replace(" ", ""));

        List<Requirement> requirements = new ArrayList<>();
        requirements.add(requirement);

        model.addAttribute("requirement", requirementService.createRequirement(requirements));


        return new ModelAndView("redirect:getAllRequirements");
    }

    @PostMapping(value = "/editRequirement")
    public ModelAndView editRequirement(@RequestParam(value = "uriRequirement") String requirementURI,  Model model) throws Exception {

        Requirement requirement = requirementService.getRequirement(requirementURI);

        model.addAttribute("requirement", requirement);
        model.addAttribute("allDomains", domainService.getAllDomains());
        model.addAttribute("allRequirements", requirementService.getAllRequirements());
        model.addAttribute("allRequirementTypes", requirementTypeService.getAllRequirementTypes());
        model.addAttribute("allSystemTypes", systemTypeService.getAllSystemTypes());

        return new ModelAndView("editRequirement");

    }

    @PostMapping("/edit")
    public ModelAndView editOldRequirement(@ModelAttribute Requirement requirement, @RequestParam(value = "uriRequirement") String requirementURI,  BindingResult bindingResult, Model model) throws Exception {

        if(requirement.getBroaderRequirementID().equals("")){
            requirement.setBroaderRequirementID(null);
        }
        if(requirement.getBroaderRequirementTypeID().equals("")){
            requirement.setBroaderRequirementTypeID(null);
        }
        if(requirement.getBroaderDomainID().isEmpty()){
            requirement.getBroaderDomainID().add(null);
        }
        if(requirement.getBroaderSystemTypeID().isEmpty()){
            requirement.getBroaderSystemTypeID().add(null);
        }
        if(requirement.getNarrowerRequirementID().isEmpty()){
            requirement.getNarrowerRequirementID().add(null);
        }

        requirementService.deleteRequirement(requirementURI);


        requirement.setRequirementID(agUtils.removeAccents(requirement.getLabel().toLowerCase()));
        requirement.setRequirementID(requirement.getRequirementID().replace(" ", ""));

        List<Requirement> requirements = new ArrayList<>();
        requirements.add(requirement);

        model.addAttribute("requirement", requirementService.createRequirement(requirements));

        return new ModelAndView("redirect:getAllRequirements");
    }
}
