package kz.edu.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "AudienceEntity")
@Table(name = "audience")
public class Audience implements Serializable {
    private int audience_id;
    private String audience_number;
    private int places;
    private Centers centers;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "audience_id")
    public int getAudience_id() {
        return audience_id;
    }
    public void setAudience_id(int audience_id) {
        this.audience_id = audience_id;
    }

    @Column(name = "audience_number")
    public String getAudience_number() {
        return audience_number;
    }
    public void setAudience_number(String audience_number) {
        this.audience_number = audience_number;
    }

    @Column(name = "places")
    public int getPlaces() {
        return places;
    }
    public void setPlaces(int places) {
        this.places = places;
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
