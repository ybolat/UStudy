package kz.edu.controller;

import kz.edu.dao.*;
import kz.edu.model.Centers;
import kz.edu.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ControllerCenter {
    private final CentersDAO centersDAO;
    private final AreaDAO areaDAO;
    private final ExamsDAO examsDAO;
    private final RequestsDAO requestsDAO;
    private final UserDAO userDAO;

    @Autowired
    public ControllerCenter(CentersDAO centersDAO, AreaDAO areaDAO, ExamsDAO examsDAO, RequestsDAO requestsDAO, UserDAO userDAO) {
        this.centersDAO = centersDAO;
        this.areaDAO = areaDAO;
        this.examsDAO = examsDAO;
        this.requestsDAO = requestsDAO;
        this.userDAO = userDAO;
    }

    @GetMapping("/centers")
    public String getCenters(Model model){
        model.addAttribute("centers", centersDAO.getAllCenters());
        model.addAttribute("exams_types", examsDAO.getAllExamTypes());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userDAO.findByUserName(currentPrincipalName);
        model.addAttribute("user", user);
        return "centers";
    }

    @GetMapping("/center")
    public String getCurrentCenter(Model model, @RequestParam("id") int id){
        model.addAttribute("center", centersDAO.find_center_by_id(id));
        model.addAttribute("areas", areaDAO.getAllAreas());
        model.addAttribute("etc", examsDAO.getExamTypesOfCenter(centersDAO.find_center_by_id(id)));
        model.addAttribute("exams_types", examsDAO.getAllExamTypes());
        return "center";
    }

    @PostMapping("/center")
    public String ChangeCurrentCenter(@RequestParam("id") int id,
                                    @RequestParam("region") String region,
                                    @RequestParam("address") String address,
                                    @RequestParam("phone") String phone,
                                    @RequestParam("email") String email,
                                    @RequestParam("area") String area,
                                    @RequestParam("nop") int nop){
        Centers centers = centersDAO.find_center_by_id(id);
        centers.setAddress(address);
        centers.setEmail(email);
        centers.setRegion(region);
        centers.setNum_of_places(nop);
        centers.setPhone_number(phone);
        centers.setArea(areaDAO.find_area_by_name(area));
        centersDAO.EditCenter(centers);
        return "redirect:/centers";
    }

//    @GetMapping("/createCenter")
//    public String createCenter(Model model){
//        model.addAttribute("areas", areaDAO.getAllAreas());
//        return "createCenter";
//    }

    @PostMapping("/createCenter")
    public String createCenter(Model model){
        Centers centers = new Centers();
        centers.setAddress("");
        centers.setArea(areaDAO.find_area_by_name("Almaty"));
        centers.setEmail("");
        centers.setNum_of_places(0);
        centers.setPhone_number("");
        centers.setRegion("");
        centersDAO.createCenter(centers);
        model.addAttribute("areas", areaDAO.getAllAreas());
        model.addAttribute("exams_types", examsDAO.getAllExamTypes());
        model.addAttribute("centers", centers);
        model.addAttribute("byArea","byArea");
        return "createCenter";
    }

    @PostMapping("/updateAddress")
    @ResponseBody
    public int updateAddress(@RequestParam("address") String address, @RequestParam("id") int id){
        Centers centers = centersDAO.find_center_by_id(id);
        centers.setAddress(address);
        return centers.getCenter_id();
    }

    @PostMapping("/updateArea")
    @ResponseBody
    public int updateArea(@RequestParam("area") String area, @RequestParam("id") int id){
        Centers centers = centersDAO.find_center_by_id(id);
        centers.setArea(areaDAO.find_area_by_name(area));
        centersDAO.EditCenter(centers);
        return centers.getCenter_id();
    }

    @PostMapping("/updateEmail")
    @ResponseBody
    public int updateEmail(@RequestParam("email") String email, @RequestParam("id") int id){
        Centers centers = centersDAO.find_center_by_id(id);
        centers.setEmail(email);
        centersDAO.EditCenter(centers);
        return centers.getCenter_id();
    }

    @PostMapping("/updateNOP")
    @ResponseBody
    public int updateNOP(@RequestParam("nop") int NOP, @RequestParam("id") int id){
        Centers centers = centersDAO.find_center_by_id(id);
        centers.setNum_of_places(NOP);
        centersDAO.EditCenter(centers);
        return centers.getCenter_id();
    }

    @PostMapping("/updatePhone")
    @ResponseBody
    public int updatePhone(@RequestParam("phone") String phone, @RequestParam("id") int id){
        Centers centers = centersDAO.find_center_by_id(id);
        centers.setPhone_number(phone);
        centersDAO.EditCenter(centers);
        return centers.getCenter_id();
    }

    @PostMapping("/updateRegion")
    @ResponseBody
    public int updateRegion(@RequestParam("region") String region, @RequestParam("id") int id){
        Centers centers = centersDAO.find_center_by_id(id);
        centers.setRegion(region);
        centersDAO.EditCenter(centers);
        return centers.getCenter_id();
    }

    @PostMapping("/deleteCenter/{id}")
    public String deleteCenter(@PathVariable("id") int id){
        requestsDAO.deleteRequestsCenters(centersDAO.find_center_by_id(id));
        examsDAO.deleteExamTypesCenters(centersDAO.find_center_by_id(id));
        examsDAO.deleteExamCenters(centersDAO.find_center_by_id(id));
        centersDAO.deleteCenter(id);
        return "redirect:/centers";
    }

    @PostMapping("/findCentersOfArea")
    public void findCentersOfArea(Model model, @RequestParam("areaName") String name){
        model.addAttribute("centersList", centersDAO.getCentersOfArea(areaDAO.find_area_by_name(name)));
    }

    @PostMapping("/searchCenter")
    public void searchCenter(Model model, @RequestParam("text") String text){
        model.addAttribute("result", centersDAO.findCenter(text));
    }

    @PostMapping("/getExamTypesOfCenter")
    public void getExamTypesOfCenter(Model model, @RequestParam("Et") String type){
        model.addAttribute("etList", centersDAO.getCenterOfExamTypes(examsDAO.find_examType_by_name(type)));
    }

    @PostMapping("/getAllAreas")
    public void getAllAreas(Model model){
        model.addAttribute("areaList", areaDAO.getAllAreas());
    }
}
