package kz.edu.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Exams_centersEntity")
@Table(name = "exams_centers")
public class Exams_centers implements Serializable {
    private int ec;
    private Exams exams;
    private Centers centers;
    private Dates dates;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ec")
    public int getEc() {
        return ec;
    }
    public void setEc(int ec) {
        this.ec = ec;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "exam_id")
    public Exams getExams() {
        return exams;
    }
    public void setExams(Exams exams) {
        this.exams = exams;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "center_id")
    public Centers getCenters() {
        return centers;
    }
    public void setCenters(Centers centers) {
        this.centers = centers;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "dates_id")
    public Dates getDates() {
        return dates;
    }
    public void setDates(Dates dates) {
        this.dates = dates;
    }
}
