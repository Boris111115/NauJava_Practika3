package ru.boris.naujava.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.boris.naujava.dao.PatientTransferRepository;
import ru.boris.naujava.entity.PatientTransfer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientTransferServiceImplTest {

    @Mock
    private PatientTransferRepository repository;

    private PatientTransferServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new PatientTransferServiceImpl(repository);
    }

    @Test
    void createTransferShouldSaveValidTransfer() {
        service.createTransfer(1L, "P-1", "AMB-1", "City Hospital", "DONE", 8);

        verify(repository).create(argThat(transfer ->
                transfer.getId().equals(1L)
                        && transfer.getPatientId().equals("P-1")
                        && transfer.getTransferTimeMinutes() == 8));
    }

    @Test
    void createTransferShouldRejectNullId() {
        assertThrows(IllegalArgumentException.class,
                () -> service.createTransfer(null, "P-1", "AMB-1", "Hospital", "DONE", 8));
        verifyNoInteractions(repository);
    }

    @Test
    void createTransferShouldRejectBlankPatientId() {
        assertThrows(IllegalArgumentException.class,
                () -> service.createTransfer(1L, " ", "AMB-1", "Hospital", "DONE", 8));
        verifyNoInteractions(repository);
    }

    @Test
    void createTransferShouldRejectNegativeTime() {
        assertThrows(IllegalArgumentException.class,
                () -> service.createTransfer(1L, "P-1", "AMB-1", "Hospital", "DONE", -1));
        verifyNoInteractions(repository);
    }

    @Test
    void findByIdShouldReturnRepositoryResult() {
        PatientTransfer expected = new PatientTransfer(1L, "P-1", "AMB-1", "Hospital", "DONE", 5);
        when(repository.read(1L)).thenReturn(expected);

        assertSame(expected, service.findById(1L));
        verify(repository).read(1L);
    }

    @Test
    void updateShouldRejectNegativeTime() {
        assertThrows(IllegalArgumentException.class,
                () -> service.updateTransfer(1L, "P-1", "AMB-1", "Hospital", "DONE", -5));
        verifyNoInteractions(repository);
    }

    @Test
    void deleteShouldDelegateToRepository() {
        service.deleteById(7L);
        verify(repository).delete(7L);
    }

    @Test
    void findAllShouldReturnAllTransfers() {
        List<PatientTransfer> transfers = List.of(
                new PatientTransfer(1L, "P-1", "AMB-1", "Hospital", "DONE", 5));
        when(repository.findAll()).thenReturn(transfers);

        assertEquals(transfers, service.findAll());
    }

    @Test
    void transferIsDelayedWhenTimeIsGreaterThanTenMinutes() {
        PatientTransfer transfer = new PatientTransfer(1L, "P-1", "AMB-1", "Hospital", "DONE", 11);
        when(repository.read(1L)).thenReturn(transfer);

        assertTrue(service.isTransferDelayed(1L));
    }

    @Test
    void transferIsNotDelayedAtTenMinutes() {
        PatientTransfer transfer = new PatientTransfer(1L, "P-1", "AMB-1", "Hospital", "DONE", 10);
        when(repository.read(1L)).thenReturn(transfer);

        assertFalse(service.isTransferDelayed(1L));
    }

    @Test
    void isTransferDelayedShouldThrowWhenTransferDoesNotExist() {
        when(repository.read(404L)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> service.isTransferDelayed(404L));
    }
}
