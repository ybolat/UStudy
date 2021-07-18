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
        model.addAttribute("byArea","byArea");
        model.addAttribute("freeChoice","freeChoice");
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

    @GetMapping("/editMyRequests")
    public String editMyRequests(Model model, @RequestParam("id") int id){
        if (requestsDAO.find_request_by_id(id).getStatus().getStatus_name().equals("pending")){
            model.addAttribute("rcList", requestsDAO.getRequestCentersOfRequest(requestsDAO.find_request_by_id(id)));
            Requests requests = requestsDAO.find_request_by_id(id);
            model.addAttribute("req", requests);
            if(!requests.getExam_types().equals("byArea") &&
            !requests.getExam_types().equals("freeChoice")){
                List<Exam_types_centers> exam_types_centersList = examsDAO.getCentersOfExamType(requests.getExam_types());
                List<Centers> centersList = new ArrayList<>();
                for (int i = 0; i < exam_types_centersList.size(); i++){
                    centersList.add(exam_types_centersList.get(i).getCenters());
                }
                model.addAttribute("cList", centersList);
            }else{
                model.addAttribute("cList", centersDAO.getAllCenters());
            }
            return "createRequestCenter";
        }else{
            return "redirect:/home";
        }
    }

    @PostMapping("/createRequestCenter")
    @ResponseBody
    public int createRequestCenter(@RequestParam("request_id") int request_id){
        Requests requests = requestsDAO.find_request_by_id(request_id);
        Requests_centers requests_centers = new Requests_centers();
        Dates dates = new Dates();
        dates.setStart_date("2021-01-01 00:00:00");
        dates.setFinish_date("2021-01-01 00:00:00");
        datesDAO.CreateDate(dates);
        requests_centers.setCenters(null);
        requests_centers.setDates(dates);
        requests_centers.setNum_of_places(0);
        requests_centers.setRequests(requests);
        requestsDAO.createRequest_Centers(requests_centers);
        return requests_centers.getRc();
    }

    @PostMapping("/updateRCCenter")
    @ResponseBody
    public int updateRCCenter(@RequestParam("rc_id") int rc_id,
                              @RequestParam("c_id") int c_id){
        Requests_centers requests_centers = requestsDAO.find_rc_by_id(rc_id);
        requests_centers.setCenters(centersDAO.find_center_by_id(c_id));
        requestsDAO.editRequest_Centers(requests_centers);
        return requests_centers.getRc();
    }

    @PostMapping("/updateRCNOP")
    @ResponseBody
    public int updateRCNOP(@RequestParam("rc_id") int rc_id,
                           @RequestParam("n_o_p") int n_o_p){
        Requests_centers requests_centers = requestsDAO.find_rc_by_id(rc_id);
        requests_centers.setNum_of_places(n_o_p);
        requestsDAO.editRequest_Centers(requests_centers);
        return requests_centers.getRc();
    }

    @PostMapping("/updateRCStart")
    @ResponseBody
    public int updateRCStart(@RequestParam("rc_id") int rc_id,
                             @RequestParam("start") String start){
        Dates dates = datesDAO.find_date_by_id(requestsDAO.find_rc_by_id(rc_id).getDates().getDates_id());
        dates.setStart_date(start);
        datesDAO.EditDates(dates);
        Requests_centers requests_centers = requestsDAO.find_rc_by_id(rc_id);
        return requests_centers.getRc();
    }

    @PostMapping("/updateRCFinish")
    @ResponseBody
    public int updateRCFinish(@RequestParam("rc_id") int rc_id,
                              @RequestParam("finish") String finish){
        Dates dates = datesDAO.find_date_by_id(requestsDAO.find_rc_by_id(rc_id).getDates().getDates_id());
        dates.setFinish_date(finish);
        datesDAO.EditDates(dates);
        Requests_centers requests_centers = requestsDAO.find_rc_by_id(rc_id);
        return requests_centers.getRc();
    }

    @GetMapping("/editRequestCenters")
    public String editRequestCenter(Model model, @RequestParam("id") int id){
        model.addAttribute("reqCen", requestsDAO.find_rc_by_id(id));
        model.addAttribute("centerList", centersDAO.getAllCenters());
        return "editRC";
    }

    @PostMapping("/editRequestCenters")
    public String editRequest(@RequestParam("start") String start,
                            @RequestParam("finish") String finish,
                            @RequestParam("id") int id,
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
        return "redirect:/requestsOfStatus";
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
            exams_centers.setNumber_of_places(requests_centersList.get(i).getNum_of_places());
            examsDAO.CreateExamCenters(exams_centers);
        }
        return "redirect:/requestsOfStatus";
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
        return "redirect:/requestsOfStatus";
    }

    @GetMapping("/editRequest")
    public String editRequest(@RequestParam("id") int id, Model model){
        List<Exams_centers> exams_centersList = examsDAO.getAllExams_Centers();
        List<Requests_centers> requests_centersList = requestsDAO.getRequestCentersOfRequest(requestsDAO.find_request_by_id(id));
        HashMap<Requests_centers, Integer> hm = new HashMap<>();
        for (int i = 0; i < requests_centersList.size(); i++){
            int counter = 0;
            for (int j = 0; j < exams_centersList.size(); j++) {
                String datetime1 = exams_centersList.get(j).getDates().getStart_date();
                String datetime2 = exams_centersList.get(j).getDates().getFinish_date();
                String datetime3 = requests_centersList.get(i).getDates().getStart_date();
                String datetime4 = requests_centersList.get(i).getDates().getFinish_date();
                String date1 = datetime1.substring(0,10);
                String date2 = datetime2.substring(0,10);
                String date3 = datetime3.substring(0,10);
                String date4 = datetime4.substring(0,10);
                int h1 = Integer.parseInt(datetime1.substring(11,13));
                int h2 = Integer.parseInt(datetime2.substring(11,13));
                int h3 = Integer.parseInt(datetime3.substring(11,13));
                int h4 = Integer.parseInt(datetime4.substring(11,13));
                if (requests_centersList.get(i).getCenters().getRegion().
                        equals(exams_centersList.get(j).getCenters().getRegion()) &&
                        (datetime1.equals(datetime3) || datetime2.equals(datetime4)
                                || datetime2.equals(datetime3) || datetime1.equals(datetime4))) {
                    counter += exams_centersList.get(j).getNumber_of_places();
                }else if(date1.equals(date3) || date2.equals(date3) || date1.equals(date4) || date2.equals(date4)){
                    if((h1 >= h3 && h4 >= h1) || (h1 <= h3 && h3 <= h2)){
                        counter += exams_centersList.get(j).getNumber_of_places();
                    }
                }
            }
            if(requests_centersList.get(i).getCenters().getNum_of_places() - counter > 0){
                hm.put(requests_centersList.get(i), requests_centersList.get(i).getCenters().getNum_of_places() - counter);
            }else if (requests_centersList.get(i).getCenters().getNum_of_places() - counter < 0){
                hm.put(requests_centersList.get(i), -1);
            }
        }
        model.addAttribute("rcHM", hm);
        return "editRequest";
    }

    @GetMapping("/myRequests")
    public String myRequests(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        model.addAttribute("myReq", requestsDAO.getRequestsOfUser(userDAO.findByUserName(currentPrincipalName)));
        model.addAttribute("pending", "pending");
        return "myRequests";
    }

}
