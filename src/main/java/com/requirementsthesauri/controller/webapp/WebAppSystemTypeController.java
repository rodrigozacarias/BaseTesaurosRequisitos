package com.requirementsthesauri.controller.webapp;


import com.requirementsthesauri.model.SystemType;
import com.requirementsthesauri.model.SystemType;
import com.requirementsthesauri.model.SystemType;
import com.requirementsthesauri.model.SystemType;
import com.requirementsthesauri.service.AGUtils;
import com.requirementsthesauri.service.RequirementService;
import com.requirementsthesauri.service.SystemTypeService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("webapp/systemTypes")
public class WebAppSystemTypeController {

    SystemTypeService systemTypeService = new SystemTypeService();
    RequirementService requirementService = new RequirementService();
    AGUtils agUtils = new AGUtils();


    @GetMapping("/getAllSystemTypes")
    public ModelAndView getAllSystemTypes(Model model) throws Exception {

        model.addAttribute("systemTypes", systemTypeService.getAllSystemTypes());

        return new ModelAndView("systemType");
    }

    @PostMapping("/delete")
    public ModelAndView deleteSystemType(@RequestParam(value = "uriSystemType") String systemTypeURI, Model model) throws Exception {
        systemTypeService.deleteSystemType(systemTypeURI);

        model.addAttribute("systemTypes", systemTypeService.getAllSystemTypes());

        return new ModelAndView("redirect:getAllSystemTypes");
    }

    @PostMapping(value = "/getSystemType")
    public ModelAndView getSystemType(@RequestParam(value = "uriSystemType") String systemTypeURI,  Model model) throws Exception {

        if(systemTypeURI.contains("dbpedia")){
            return new ModelAndView (systemTypeURI);
        }else {

            SystemType systemType = systemTypeService.getSystemType(systemTypeURI);

            model.addAttribute("systemType", systemType);

            model.addAttribute("systemTypeBroader", systemTypeService.getSystemTypeNarrowerOrBroader(systemType.getBroaderSystemTypes()));
            model.addAttribute("systemTypeNarrower", systemTypeService.getSystemTypeNarrowerOrBroader(systemType.getNarrowerSystemTypeID()));
            model.addAttribute("requirementNarrower", requirementService.getRequirementNarrower(systemType.getNarrowerRequirementID()));

            return new ModelAndView("getSystemType");
        }
    }
    @GetMapping("/create")
    public ModelAndView createSystemType(@ModelAttribute SystemType systemType, Model model) throws Exception {

        model.addAttribute("systemType", systemType);
        model.addAttribute("allSystemTypes", systemTypeService.getAllSystemTypes());
        model.addAttribute("allRequirements", requirementService.getAllRequirements());

        return new ModelAndView("createSystemType");
    }

    @PostMapping("/create")
    public ModelAndView createNewSystemType(@ModelAttribute SystemType systemType, BindingResult bindingResult, Model model) throws Exception {

        if(systemType.getBroaderSystemTypeID().equals("")){
            systemType.setBroaderSystemTypeID(null);
        }
        if(systemType.getNarrowerSystemTypeID().isEmpty()){
            systemType.getNarrowerSystemTypeID().add(null);
        }
        if(systemType.getNarrowerRequirementID().isEmpty()){
            systemType.getNarrowerRequirementID().add(null);
        }

        if (bindingResult.hasErrors()) {
            //errors processing
        }

        systemType.setSystemTypeID(agUtils.removeAccents(systemType.getLabel().toLowerCase()));
        systemType.setSystemTypeID(systemType.getSystemTypeID().replace(" ", ""));

        List<SystemType> systemTypes = new ArrayList<>();
        systemTypes.add(systemType);

        model.addAttribute("systemType", systemTypeService.createSystemType(systemTypes));


        return new ModelAndView("redirect:getAllSystemTypes");
    }

    @PostMapping(value = "/editSystemType")
    public ModelAndView editSystemType(@RequestParam(value = "uriSystemType") String systemTypeURI,  Model model) throws Exception {

        SystemType systemType = systemTypeService.getSystemType(systemTypeURI);

        model.addAttribute("systemType", systemType);
        model.addAttribute("allSystemTypes", systemTypeService.getAllSystemTypes());
        model.addAttribute("allRequirements", requirementService.getAllRequirements());

        return new ModelAndView("editSystemType");

    }

    @PostMapping("/edit")
    public ModelAndView editOldSystemType(@ModelAttribute SystemType systemType, @RequestParam(value = "uriSystemType") String systemTypeURI,  BindingResult bindingResult, Model model) throws Exception {

        if(systemType.getBroaderSystemTypeID().equals("")){
            systemType.setBroaderSystemTypeID(null);
        }
        if(systemType.getNarrowerSystemTypeID().isEmpty()){
            systemType.getNarrowerSystemTypeID().add(null);
        }
        if(systemType.getNarrowerRequirementID().isEmpty()){
            systemType.getNarrowerRequirementID().add(null);
        }

        systemTypeService.deleteSystemType(systemTypeURI);


        systemType.setSystemTypeID(agUtils.removeAccents(systemType.getLabel().toLowerCase()));
        systemType.setSystemTypeID(systemType.getSystemTypeID().replace(" ", ""));

        List<SystemType> systemTypes = new ArrayList<>();
        systemTypes.add(systemType);

        model.addAttribute("systemType", systemTypeService.createSystemType(systemTypes));

        return new ModelAndView("redirect:getAllSystemTypes");
    }
}
