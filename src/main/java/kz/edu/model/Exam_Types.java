package kz.edu.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Exam_TypesEntity")
@Table(name = "exam_types")
public class Exam_Types implements Serializable {
    private int exam_type_id;
    private String exam_type_name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_type_id")
    public int getExam_type_id() {
        return exam_type_id;
    }
    public void setExam_type_id(int exam_type_id) {
        this.exam_type_id = exam_type_id;
    }

    @Column(name = "exam_type_name")
    public String getExam_type_name() {
        return exam_type_name;
    }
    public void setExam_type_name(String exam_type_name) {
        this.exam_type_name = exam_type_name;
    }
}
