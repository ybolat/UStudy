package kz.edu.controller;

import kz.edu.dao.AreaDAO;
import kz.edu.dao.CentersDAO;
import kz.edu.dao.ExamsDAO;
import kz.edu.dao.RequestsDAO;
import kz.edu.model.Centers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ControllerCenter {
    private final CentersDAO centersDAO;
    private final AreaDAO areaDAO;
    private final ExamsDAO examsDAO;
    private final RequestsDAO requestsDAO;

    @Autowired
    public ControllerCenter(CentersDAO centersDAO, AreaDAO areaDAO, ExamsDAO examsDAO, RequestsDAO requestsDAO) {
        this.centersDAO = centersDAO;
        this.areaDAO = areaDAO;
        this.examsDAO = examsDAO;
        this.requestsDAO = requestsDAO;
    }

    @GetMapping("/centers")
    public String getCenters(Model model){
        model.addAttribute("centers", centersDAO.getAllCenters());
        return "centers";
    }

    @GetMapping("/center")
    public String getCurrentCenter(Model model, @RequestParam("id") int id){
        model.addAttribute("center", centersDAO.find_center_by_id(id));
        model.addAttribute("areas", areaDAO.getAllAreas());
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

    @GetMapping("/createCenter")
    public String createCenter(Model model){
        model.addAttribute("areas", areaDAO.getAllAreas());
        return "createCenter";
    }

    @PostMapping("/createCenter")
    public String createCenter(@RequestParam("region") String region,
                               @RequestParam("address") String address,
                               @RequestParam("phone_number") String phone_number,
                               @RequestParam("num_of_places") int num_of_places,
                               @RequestParam("email") String email,
                               @RequestParam("areaName") String areaName){
        Centers centers = new Centers();
        centers.setAddress(address);
        centers.setArea(areaDAO.find_area_by_name(areaName));
        centers.setEmail(email);
        centers.setNum_of_places(num_of_places);
        centers.setPhone_number(phone_number);
        centers.setRegion(region);
        centersDAO.createCenter(centers);
        return "redirect:/centers";
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
