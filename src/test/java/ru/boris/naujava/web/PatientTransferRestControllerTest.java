package ru.boris.naujava.web;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.boris.naujava.entity.PatientTransfer;
import ru.boris.naujava.repository.PatientTransferRepository;

import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

class PatientTransferRestControllerTest {

    private final PatientTransferRepository repository = mock(PatientTransferRepository.class);

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.standaloneSetup(new PatientTransferRestController(repository));
    }

    @AfterEach
    void tearDown() {
        RestAssuredMockMvc.reset();
    }

    @Test
    void findByPatientIdShouldReturnMatchingTransfers() {
        when(repository.findByPatientId("P-100")).thenReturn(List.of(
                new PatientTransfer(1L, "P-100", "AMB-1", "City Hospital", "DONE", 7)));

        given().queryParam("patientId", "P-100")
                .when().get("/custom/patient-transfers/findByPatientId")
                .then().statusCode(200)
                .body("size()", is(1))
                .body("[0].patientId", equalTo("P-100"));
    }

    @Test
    void findByPatientIdShouldReturnEmptyArrayWhenNoMatches() {
        when(repository.findByPatientId("unknown")).thenReturn(List.of());

        given().queryParam("patientId", "unknown")
                .when().get("/custom/patient-transfers/findByPatientId")
                .then().statusCode(200).body("size()", is(0));
    }

    @Test
    void findByPatientIdShouldReturnBadRequestWhenParameterIsMissing() {
        given().when().get("/custom/patient-transfers/findByPatientId")
                .then().statusCode(400);
    }

    @Test
    void findByHospitalNameShouldReturnMatchingTransfers() {
        when(repository.findByHospitalNameUsingQuery("Central" )).thenReturn(List.of(
                new PatientTransfer(2L, "P-200", "AMB-2", "Central Hospital", "DONE", 12)));

        given().queryParam("name", "Central")
                .when().get("/custom/patient-transfers/findByHospitalName")
                .then().statusCode(200)
                .body("[0].hospitalName", equalTo("Central Hospital"));
    }

    @Test
    void findByHospitalNameShouldReturnBadRequestWhenParameterIsMissing() {
        given().when().get("/custom/patient-transfers/findByHospitalName")
                .then().statusCode(400);
    }
}
