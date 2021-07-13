package kz.edu.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "AreasEntity")
@Table(name = "areas")
public class Areas implements Serializable {
    private int area_id;
    private String area_name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "area_id")
    public int getArea_id() {
        return area_id;
    }
    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }

    @Column(name = "area_name")
    public String getArea_name() {
        return area_name;
    }
    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }
}
