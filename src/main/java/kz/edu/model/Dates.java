package kz.edu.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "DatesEntity")
@Table(name = "dates")
public class Dates implements Serializable {
    private int dates_id;
    private String start_date;
    private String finish_date;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dates_id")
    public int getDates_id() {
        return dates_id;
    }
    public void setDates_id(int dates_id) {
        this.dates_id = dates_id;
    }

    @Column(name = "start_date")
    public String getStart_date() {
        return start_date;
    }
    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    @Column(name = "finish_date")
    public String getFinish_date() {
        return finish_date;
    }
    public void setFinish_date(String finish_date) {
        this.finish_date = finish_date;
    }
}
