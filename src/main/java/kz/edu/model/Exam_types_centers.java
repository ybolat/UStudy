package kz.edu.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Exam_types_centersEntity")
@Table(name = "exam_types_centers")
public class Exam_types_centers implements Serializable {
    private int etc;
    private Exam_Types exam_types;
    private Centers centers;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "etc")
    public int getEtc() {
        return etc;
    }
    public void setEtc(int etc) {
        this.etc = etc;
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
    @JoinColumn(name = "center_id")
    public Centers getCenters() {
        return centers;
    }
    public void setCenters(Centers centers) {
        this.centers = centers;
    }
}
