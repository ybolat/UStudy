package kz.edu.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "RequestsEntity")
@Table(name = "requests")
public class Requests implements Serializable {
    private int request_id;
    private User user;
    private Exam_Types exam_types;
    private String exam_name;
    private Statuses status;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    public int getRequest_id() {
        return request_id;
    }
    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }
    public void setUser(User contact) {
        this.user = contact;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "exam_type_id")
    public Exam_Types getExam_types() {
        return exam_types;
    }
    public void setExam_types(Exam_Types exam_types) {
        this.exam_types = exam_types;
    }

    @Column(name = "exam_name")
    public String getExam_name() {
        return exam_name;
    }
    public void setExam_name(String exam_name) {
        this.exam_name = exam_name;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "status_id")
    public Statuses getStatus() {
        return status;
    }
    public void setStatus(Statuses status) {
        this.status = status;
    }
}
