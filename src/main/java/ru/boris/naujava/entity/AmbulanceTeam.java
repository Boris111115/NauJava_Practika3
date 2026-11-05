
package ru.boris.naujava.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "ambulance_teams")
public class AmbulanceTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "team_code", nullable = false, unique = true)
    private String teamCode;

    @Column(name = "vehicle_number", nullable = false)
    private String vehicleNumber;

    @Column(nullable = false)
    private String shift;

    @Column
    private String phone;

    @OneToMany(mappedBy = "ambulanceTeam")
    private List<PatientTransfer> transfers;

    public AmbulanceTeam() {
    }

    public Long getId() {
        return id;
    }

    public String getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
