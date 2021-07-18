package kz.edu.controller;

import kz.edu.dao.*;
import kz.edu.model.Dates;
import kz.edu.model.Exam_types_centers;
import kz.edu.model.Exams;
import kz.edu.model.Exams_centers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ControllerExam {
    private final ExamsDAO examsDAO;
    private final CentersDAO centersDAO;
    private final RequestsDAO requestsDAO;
    private final DatesDAO datesDAO;
    private final AreaDAO areaDAO;

    @Autowired
    public ControllerExam(ExamsDAO examsDAO, CentersDAO centersDAO, RequestsDAO requestsDAO, DatesDAO datesDAO, AreaDAO areaDAO) {
        this.examsDAO = examsDAO;
        this.centersDAO = centersDAO;
        this.requestsDAO = requestsDAO;
        this.datesDAO = datesDAO;
        this.areaDAO = areaDAO;
    }

    @GetMapping("/getAllExams")
    public String getAllExams(Model model){
        model.addAttribute("examList", examsDAO.getAllExams());
        model.addAttribute("examCentersList", examsDAO.getAllExams_Centers());
        model.addAttribute("centersList", centersDAO.getAllCenters());
        model.addAttribute("areasList", areaDAO.getAllAreas());
        return "calendar";
    }

    @PostMapping("/editExamTypesCenters")
    public String editExamTypesCenters(@RequestParam("etName") String etName,
                                       @RequestParam("text") String text,
                                       @RequestParam("id") int id){
        Exam_types_centers exam_types_centers = examsDAO.findExamTypeCentersById(id);
        exam_types_centers.setExam_types(examsDAO.find_examType_by_name(etName));
        exam_types_centers.setCenters(centersDAO.findCenter(text));
        examsDAO.EditExamTypesCenters(exam_types_centers);
        return "redirect:/calendar";
    }

    @PostMapping("/addExamTypesCenters")
    public String addExamTypesCenters(@RequestParam("etName") String etName,
                                       @RequestParam("text") String text){
        Exam_types_centers exam_types_centers = new Exam_types_centers();
        exam_types_centers.setExam_types(examsDAO.find_examType_by_name(etName));
        exam_types_centers.setCenters(centersDAO.findCenter(text));
        examsDAO.AddExamTypesCenters(exam_types_centers);
        return "redirect:/calendar";
    }

    @PostMapping("/createExam")
    public String createExam(@RequestParam("et") String type, @RequestParam("name") String name){
        Exams exams = new Exams();
        exams.setRequests(requestsDAO.find_request_by_name(name));
        exams.setExam_types(examsDAO.find_examType_by_name(type));
        examsDAO.CreateExam(exams);
        return "redirect:/calendar";
    }

    @PostMapping("/createExamCenters")
    public String createExamCenters(@RequestParam("id") int id, @RequestParam("nop") int nop,
                                    @RequestParam("text") String text,
                                    @RequestParam("start") String start,
                                    @RequestParam("finish") String finish){
        Exams_centers exams_centers = new Exams_centers();
        exams_centers.setExams(examsDAO.findExamById(id));
        exams_centers.setCenters(centersDAO.findCenter(text));
        exams_centers.setNumber_of_places(nop);
        Dates dates = new Dates();
        dates.setStart_date(start);
        dates.setFinish_date(finish);
        exams_centers.setDates(dates);
        examsDAO.CreateExamCenters(exams_centers);
        return "redirect:/calendar";
    }

    @PostMapping("/editExam")
    public String editExam(@RequestParam("et") String type, @RequestParam("name") String name,
                           @RequestParam("id") int id){
        Exams exams = examsDAO.findExamById(id);
        exams.setRequests(requestsDAO.find_request_by_name(name));
        exams.setExam_types(examsDAO.find_examType_by_name(type));
        examsDAO.EditExam(exams);
        return "redirect:/home";
    }

    @PostMapping("/editExamCenters")
    public String editExamCenters(@RequestParam("name") String name,
                                  @RequestParam("id") int id,
                                  @RequestParam("text") String text,
                                  @RequestParam("d_id") int d_id,
                                  @RequestParam("newStart") String start,
                                  @RequestParam("newFinish") String finish,
                                  @RequestParam("nop") int nop){
        Exams_centers exams_centers = examsDAO.findExamCentersById(id);
        exams_centers.setExams(examsDAO.findExamByRequest(requestsDAO.find_request_by_name(name)));
        exams_centers.setCenters(centersDAO.findCenter(text));
        exams_centers.setNumber_of_places(nop);
        Dates dates = datesDAO.find_date_by_id(d_id);
        dates.setFinish_date(finish);
        dates.setStart_date(start);
        datesDAO.EditDates(dates);
        examsDAO.EditExamCenters(exams_centers);
        return "redirect:/home";
    }

    @GetMapping("/deleteExam/{id}")
    public String deleteExam(@PathVariable("id") int id){
        examsDAO.deleteExamCenters(examsDAO.findExamById(id));
        examsDAO.deleteExam(examsDAO.findExamById(id));
        return "redirect:/calendar";
    }
}
