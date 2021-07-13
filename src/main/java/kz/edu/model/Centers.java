package kz.edu.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "CentersEntity")
@Table(name = "centers")
public class Centers implements Serializable {
    private int center_id;
    private String region;
    private String address;
    private String phone_number;
    private String email;
    private int num_of_places;
    private Areas area;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "center_id")
    public int getCenter_id() {
        return center_id;
    }
    public void setCenter_id(int center_id) {
        this.center_id = center_id;
    }

    @Column(name = "region")
    public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "phone_number")
    public String getPhone_number() {
        return phone_number;
    }
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "num_of_places")
    public int getNum_of_places() {
        return num_of_places;
    }
    public void setNum_of_places(int num_of_places) {
        this.num_of_places = num_of_places;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "area_id")
    public Areas getArea() {
        return area;
    }
    public void setArea(Areas area) {
        this.area = area;
    }
}
