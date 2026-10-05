package ru.boris.naujava.entity;

public class PatientTransfer {

    private Long id;
    private String patientId;
    private String ambulanceNumber;
    private String hospitalName;
    private String status;
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
}