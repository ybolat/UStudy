package kz.edu.controller;

import kz.edu.dao.*;
import kz.edu.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class ControllerRequest {
    private final RequestsDAO requestsDAO;
    private final ExamsDAO examsDAO;
    private final UserDAO userDAO;
    private final CentersDAO centersDAO;
    private final DatesDAO datesDAO;
    private final AreaDAO areaDAO;

    @Autowired
    public ControllerRequest(RequestsDAO requestsDAO, ExamsDAO examsDAO, UserDAO userDAO, CentersDAO centersDAO, DatesDAO datesDAO, AreaDAO areaDAO) {
        this.requestsDAO = requestsDAO;
        this.examsDAO = examsDAO;
        this.userDAO = userDAO;
        this.centersDAO = centersDAO;
        this.datesDAO = datesDAO;
        this.areaDAO = areaDAO;
    }

    @GetMapping("/createRequest")
    public String createRequest(Model model){
        model.addAttribute("typeList", examsDAO.getAllExamTypes());
        model.addAttribute("areaList", areaDAO.getAllAreas());
        return "createRequest";
    }

    @PostMapping("/createRequest")
    public String createRequest(@RequestParam("examName") String examName,
                                @RequestParam("exam_types") String exam_types, Model model,
                                @RequestParam("area_name") String area_name,
                                @RequestParam("free_choice") String free_choice) {
        Requests requests = new Requests();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        requests.setUser(userDAO.findByUserName(currentPrincipalName));
        requests.setExam_name(examName);
        if (!exam_types.equals("none")){
        requests.setExam_types(examsDAO.find_examType_by_name(exam_types));
        List<Exam_types_centers> exam_types_centersList = examsDAO.getCentersOfExamType(requests.getExam_types());
        List<Centers> centersList = new ArrayList<>();
        for (int i = 0; i < exam_types_centersList.size(); i++){
            centersList.add(exam_types_centersList.get(i).getCenters());
        }
        model.addAttribute("cList", centersList);
        }
        String statusName = "pending";
        requests.setStatus(requestsDAO.find_status_by_name(statusName));
        if(!area_name.equals("none")){
            requests.setExam_types(examsDAO.find_examType_by_name("byArea"));
            model.addAttribute("cList", centersDAO.getCentersOfArea(areaDAO.find_area_by_name(area_name)));
        }else if(free_choice.equals("free_choice")){
            requests.setExam_types(examsDAO.find_examType_by_name("freeChoice"));
            model.addAttribute("cList", centersDAO.getAllCenters());
        }
        requestsDAO.createRequest(requests);
        model.addAttribute("req", requestsDAO.find_request_by_name(examName));
        model.addAttribute("rcList", requestsDAO.getRequestCentersOfRequest(requestsDAO.find_request_by_name(examName)));
        return "createRequestCenter";
    }

    @PostMapping("/createRequestCenter")
    @ResponseBody
    public int createRequestCenter(@RequestParam("name") String name,
                                   @RequestParam("c_id") int c_id,
                                   @RequestParam("n_o_p") int n_o_p,
                                   @RequestParam("start") String start,
                                   @RequestParam("finish") String finish, Model model){
        Dates dates = new Dates();
        dates.setStart_date(start);
        dates.setFinish_date(finish);
        Requests_centers requests_centers = new Requests_centers();
        requests_centers.setCenters(centersDAO.find_center_by_id(c_id));
        requests_centers.setDates(dates);
        requests_centers.setNum_of_places(n_o_p);
        requests_centers.setRequests(requestsDAO.find_request_by_name(name));
        requestsDAO.createRequest_Centers(requests_centers);
        return requests_centers.getRc();
    }

    @GetMapping("/editRequestCenters/{id}")
    public String editRequestCenter(Model model, @PathVariable("id") int id){
        model.addAttribute("reqCen", requestsDAO.find_rc_by_id(id));
        model.addAttribute("centerList", centersDAO.getAllCenters());
        return "editRC";
    }

    @PostMapping("/editRequestCenters/{id}")
    @ResponseBody
    public int editRequest(@RequestParam("start") String start,
                            @RequestParam("finish") String finish,
                            @PathVariable("id") int id,
                            @RequestParam("nop") int nop,
                            @RequestParam("center") String center) {
        Requests_centers requests_centers = requestsDAO.find_rc_by_id(id);
        requests_centers.setCenters(centersDAO.findCenter(center));
        requests_centers.setNum_of_places(nop);
        Dates dates = datesDAO.find_date_by_id(requests_centers.getDates().getDates_id());
        dates.setStart_date(start);
        dates.setFinish_date(finish);
        datesDAO.EditDates(dates);
        requestsDAO.editRequest_Centers(requests_centers);
        return requests_centers.getRc();
    }

    @GetMapping("/requestsOfStatus")
    public String requestsOfStatus(Model model){
        String sName = "pending";
        model.addAttribute("resList", requestsDAO.getRequestsOfStatus(requestsDAO.find_status_by_name(sName)));
        return "requests";
    }

    @PostMapping("/accept")
    public String accept(@RequestParam("id") int id){
        Requests requests = requestsDAO.find_request_by_id(id);
        String status = "accept";
        requests.setStatus(requestsDAO.find_status_by_name(status));
        requestsDAO.editRequest(requests);
        Exams exams = new Exams();
        exams.setExam_types(requests.getExam_types());
        exams.setRequests(requests);
        examsDAO.CreateExam(exams);
        List<Requests_centers> requests_centersList = requestsDAO.getRequestCentersOfRequest(requests);
        for(int i = 0; i < requests_centersList.size(); i++){
            Exams_centers exams_centers = new Exams_centers();
            exams_centers.setExams(exams);
            exams_centers.setDates(requests_centersList.get(i).getDates());
            exams_centers.setCenters(requests_centersList.get(i).getCenters());
            examsDAO.CreateExamCenters(exams_centers);
        }
        return "redirect:/home";
    }

    @PostMapping("/decline")
    public String decline(@RequestParam("id") int id){
        Requests requests = requestsDAO.find_request_by_id(id);
        String status = "decline";
        requests.setStatus(requestsDAO.find_status_by_name(status));
        requestsDAO.editRequest(requests);
        List<Requests_centers> requests_centersList = requestsDAO.getRequestCentersOfRequest(requests);
        for(int i = 0; i < requests_centersList.size(); i++){
            requestsDAO.deleteRequestsCenters(requests_centersList.get(i));
        }
        return "redirect:/home";
    }

    @GetMapping("/editRequest/{id}")
    public String editRequest(@PathVariable("id") int id, Model model){
        List<Exams_centers> exams_centersList = examsDAO.getAllExams_Centers();
        List<Requests_centers> requests_centersList = requestsDAO.getRequestCentersOfRequest(requestsDAO.find_request_by_id(id));
        HashMap<Requests_centers, Integer> hm = new HashMap<>();
        for (int i = 0; i < requests_centersList.size(); i++){
            int counter = 0;
            for (int j = 0; j < exams_centersList.size(); j++){
                if (requests_centersList.get(i).getCenters() == exams_centersList.get(j).getCenters() &&
                        (requests_centersList.get(i).getDates().getStart_date().
                                equals(exams_centersList.get(i).getDates().getStart_date()) ||
                requests_centersList.get(i).getDates().getFinish_date().equals(exams_centersList.get(i).
                        getDates().getStart_date()) || requests_centersList.get(i).getDates().getStart_date().
                                equals(exams_centersList.get(i).getDates().getFinish_date()) ||
                                requests_centersList.get(i).getDates().getFinish_date().equals(exams_centersList.get(i).
                                        getDates().getFinish_date()))){
                    counter++;
                    break;
                }
            }
            if (counter == 0){
                hm.put(requests_centersList.get(i), 0);
            }else{
                hm.put(requests_centersList.get(i), 1);
            }
        }
        model.addAttribute("rcHM", hm);
        return "editRequest";
    }
}
