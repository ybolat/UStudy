package kz.edu.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "StatusesEntity")
@Table(name = "statuses")
public class Statuses implements Serializable {
    private int status_id;
    private String status_name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    public int getStatus_id() {
        return status_id;
    }
    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    @Column(name = "status_name")
    public String getStatus_name() {
        return status_name;
    }
    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }
}
