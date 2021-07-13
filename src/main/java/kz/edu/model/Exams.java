package kz.edu.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "ExamsEntity")
@Table(name = "exams")
public class Exams implements Serializable {
    private int exam_id;
    private Exam_Types exam_types;
    private Requests requests;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_id")
    public int getExam_id() {
        return exam_id;
    }
    public void setExam_id(int exam_id) {
        this.exam_id = exam_id;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "exam_type_id")
    public Exam_Types getExam_types() {
        return exam_types;
    }
    public void setExam_types(Exam_Types exam_types) {
        this.exam_types = exam_types;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "request_id")
    public Requests getRequests() {
        return requests;
    }
    public void setRequests(Requests requests) {
        this.requests = requests;
    }
}
