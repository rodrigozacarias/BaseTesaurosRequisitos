package com.requirementsthesauri.controller.webapp;

import com.requirementsthesauri.service.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
}
