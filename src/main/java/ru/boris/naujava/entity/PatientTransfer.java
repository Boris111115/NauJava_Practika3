
package ru.boris.naujava.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "patient_transfers")
public class PatientTransfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_id")
    private String patientId;

    @Column(name = "ambulance_number")
    private String ambulanceNumber;

    @Column(name = "hospital_name")
    private String hospitalName;

    @Column(nullable = false)
    private String status;

    @Column(name = "transfer_time_minutes")
    private int transferTimeMinutes;

    public PatientTransfer() {
    }

    public PatientTransfer(
            Long id,
            String patientId,
            String ambulanceNumber,
            String hospitalName,
            String status,
            int transferTimeMinutes) {
        this.id = id;
        this.patientId = patientId;
        this.ambulanceNumber = ambulanceNumber;
        this.hospitalName = hospitalName;
        this.status = status;
        this.transferTimeMinutes = transferTimeMinutes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getAmbulanceNumber() {
        return ambulanceNumber;
    }

    public void setAmbulanceNumber(String ambulanceNumber) {
        this.ambulanceNumber = ambulanceNumber;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTransferTimeMinutes() {
        return transferTimeMinutes;
    }

    @ManyToOne
    @JoinColumn(name = "patient_ref_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "ambulance_team_id")
    private AmbulanceTeam ambulanceTeam;

    @ManyToOne
    @JoinColumn(name = "hospital_ref_id")
    private Hospital hospital;

    @ManyToOne
    @JoinColumn(name = "medical_staff_id")
    private MedicalStaff medicalStaff;

    public void setTransferTimeMinutes(int transferTimeMinutes) {
        this.transferTimeMinutes = transferTimeMinutes;
    }

    @Override
    public String toString() {
        return "PatientTransfer{" +
                "id=" + id +
                ", patientId='" + patientId + '\'' +
                ", ambulanceNumber='" + ambulanceNumber + '\'' +
                ", hospitalName='" + hospitalName + '\'' +
                ", status='" + status + '\'' +
                ", transferTimeMinutes=" + transferTimeMinutes +
                '}';
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public AmbulanceTeam getAmbulanceTeam() {
        return ambulanceTeam;
    }

    public void setAmbulanceTeam(AmbulanceTeam ambulanceTeam) {
        this.ambulanceTeam = ambulanceTeam;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public MedicalStaff getMedicalStaff() {
        return medicalStaff;
    }

    public void setMedicalStaff(MedicalStaff medicalStaff) {
        this.medicalStaff = medicalStaff;
    }
}