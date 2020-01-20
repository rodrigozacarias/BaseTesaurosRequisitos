package com.requirementsthesauri.controller.webapp;

import com.requirementsthesauri.model.RequirementType;
import com.requirementsthesauri.model.RequirementType;
import com.requirementsthesauri.model.RequirementType;
import com.requirementsthesauri.service.AGUtils;
import com.requirementsthesauri.service.RequirementTypeService;
import com.requirementsthesauri.service.RequirementService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("webapp/requirementTypes")
public class WebAppRequirementTypeController {

    RequirementTypeService requirementTypeService = new RequirementTypeService();
    RequirementService requirementService = new RequirementService();
    AGUtils agUtils = new AGUtils();

    @GetMapping("/getAllRequirementTypes")
    public ModelAndView getAllRequirementTypes(Model model) throws Exception {

        model.addAttribute("requirementTypes", requirementTypeService.getAllRequirementTypes());

        return new ModelAndView("requirementType");
    }

    @PostMapping("/delete")
    public ModelAndView deleteRequirementType(@RequestParam(value = "uriRequirementType") String requirementTypeURI, Model model) throws Exception {
        requirementTypeService.deleteRequirementType(requirementTypeURI);

        model.addAttribute("requirementTypes", requirementTypeService.getAllRequirementTypes());

        return new ModelAndView("redirect:getAllRequirementTypes");
    }

    @PostMapping(value = "/getRequirementType")
    public ModelAndView getRequirementType(@RequestParam(value = "uriRequirementType") String requirementTypeURI,  Model model) throws Exception {

        if(requirementTypeURI.contains("dbpedia")){
            return new ModelAndView (requirementTypeURI);
        }else {

            RequirementType requirementType = requirementTypeService.getRequirementType(requirementTypeURI);

            model.addAttribute("requirementType", requirementType);

            model.addAttribute("requirementTypeBroader", requirementTypeService.getRequirementTypeNarrowerOrBroader(requirementType.getBroaderRequirementTypes()));
            model.addAttribute("requirementTypeNarrower", requirementTypeService.getRequirementTypeNarrowerOrBroader(requirementType.getNarrowerRequirementTypeID()));
            model.addAttribute("requirementNarrower", requirementService.getRequirementNarrower(requirementType.getNarrowerRequirementID()));

            return new ModelAndView("getRequirementType");
        }
    }

    @PostMapping(value = "/editRequirementType")
    public ModelAndView editRequirementType(@RequestParam(value = "uriRequirementType") String requirementTypeURI,  Model model) throws Exception {

        RequirementType requirementType = requirementTypeService.getRequirementType(requirementTypeURI);

        model.addAttribute("requirementType", requirementType);
        model.addAttribute("allRequirementTypes", requirementTypeService.getAllRequirementTypes());
        model.addAttribute("allRequirements", requirementService.getAllRequirements());

        return new ModelAndView("editRequirementType");

    }

    @PostMapping("/edit")
    public ModelAndView editOldRequirementType(@ModelAttribute RequirementType requirementType, @RequestParam(value = "uriRequirementType") String requirementTypeURI, BindingResult bindingResult, Model model) throws Exception {

        if(requirementType.getBroaderRequirementTypeID().equals("")){
            requirementType.setBroaderRequirementTypeID(null);
        }
        if(requirementType.getNarrowerRequirementTypeID().isEmpty()){
            requirementType.getNarrowerRequirementTypeID().add(null);
        }
        if(requirementType.getNarrowerRequirementID().isEmpty()){
            requirementType.getNarrowerRequirementID().add(null);
        }

        requirementTypeService.deleteRequirementType(requirementTypeURI);


        requirementType.setRequirementTypeID(agUtils.removeAccents(requirementType.getLabel().toLowerCase()));
        requirementType.setRequirementTypeID(requirementType.getRequirementTypeID().replace(" ", ""));

        List<RequirementType> requirementTypes = new ArrayList<>();
        requirementTypes.add(requirementType);

        model.addAttribute("requirementType", requirementTypeService.createRequirementType(requirementTypes));

        return new ModelAndView("redirect:getAllRequirementTypes");
    }

    @GetMapping("/create")
    public ModelAndView createRequirementType(@ModelAttribute RequirementType requirementType, Model model) throws Exception {

        model.addAttribute("requirementType", requirementType);
        model.addAttribute("allRequirementTypes", requirementTypeService.getAllRequirementTypes());
        model.addAttribute("allRequirements", requirementService.getAllRequirements());

        return new ModelAndView("createRequirementType");
    }

    @PostMapping("/create")
    public ModelAndView createNewRequirementType(@ModelAttribute RequirementType requirementType, BindingResult bindingResult, Model model) throws Exception {

        if(requirementType.getBroaderRequirementTypeID().equals("")){
            requirementType.setBroaderRequirementTypeID(null);
        }
        if(requirementType.getNarrowerRequirementTypeID().isEmpty()){
            requirementType.getNarrowerRequirementTypeID().add(null);
        }
        if(requirementType.getNarrowerRequirementID().isEmpty()){
            requirementType.getNarrowerRequirementID().add(null);
        }

        if (bindingResult.hasErrors()) {
            //errors processing
        }

        requirementType.setRequirementTypeID(agUtils.removeAccents(requirementType.getLabel().toLowerCase()));
        requirementType.setRequirementTypeID(requirementType.getRequirementTypeID().replace(" ", ""));

        List<RequirementType> requirementTypes = new ArrayList<>();
        requirementTypes.add(requirementType);

        model.addAttribute("requirementType", requirementTypeService.createRequirementType(requirementTypes));


        return new ModelAndView("redirect:getAllRequirementTypes");
    }
}
