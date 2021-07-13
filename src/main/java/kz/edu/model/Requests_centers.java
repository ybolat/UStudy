package kz.edu.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Requests_centersEntity")
@Table(name = "requests_centers")
public class Requests_centers implements Serializable {
    private int rc;
    private Requests requests;
    private Centers centers;
    private int num_of_places;
    private Dates dates;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rc")
    public int getRc() {
        return rc;
    }
    public void setRc(int rc) {
        this.rc = rc;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "request_id")
    public Requests getRequests() {
        return requests;
    }
    public void setRequests(Requests requests) {
        this.requests = requests;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "center_id")
    public Centers getCenters() {
        return centers;
    }
    public void setCenters(Centers centers) {
        this.centers = centers;
    }

    @Column(name = "num_of_places")
    public int getNum_of_places() {
        return num_of_places;
    }
    public void setNum_of_places(int num_of_places) {
        this.num_of_places = num_of_places;
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
