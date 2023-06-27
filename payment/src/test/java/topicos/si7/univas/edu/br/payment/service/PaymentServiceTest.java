package topicos.si7.univas.edu.br.payment.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import topicos.si7.univas.edu.br.payment.dto.PaymentDTO;
import topicos.si7.univas.edu.br.payment.dto.PaymentDTOFind;
import topicos.si7.univas.edu.br.payment.entities.PaymentEntity;
import topicos.si7.univas.edu.br.payment.enums.Status;
import topicos.si7.univas.edu.br.payment.enums.TransactionDTO;
import topicos.si7.univas.edu.br.payment.repository.PaymentRepository;
import topicos.si7.univas.edu.br.payment.support.ObjectNotFound;
import topicos.si7.univas.edu.br.payment.support.PaymentException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PaymentServiceTest {

    @Mock
    private PaymentRepository repo;

    @InjectMocks
    private PaymentService service;

    public PaymentServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePayment() {
        // Mocking the input DTO and repository save method
        PaymentEntity paymentEntity = new PaymentEntity("Method1", new Date(), 100);
        PaymentDTO paymentDTO = new PaymentDTO(paymentEntity);
        when(repo.save(any())).thenReturn(new PaymentEntity());

        // Calling the service method
        service.createPayment(paymentDTO);

        // Verifying that the repository save method was called
        verify(repo, times(1)).save(any());
    }

    @Test
    public void testFindAll() {
        // Mocking repository response
        List<PaymentEntity> paymentList = new ArrayList<>();
        paymentList.add(new PaymentEntity("Method1", new Date(), 100));
        paymentList.add(new PaymentEntity("Method2", new Date(), 200));
        when(repo.findAll()).thenReturn(paymentList);

        // Calling the service method
        List<PaymentDTOFind> result = service.findAll();

        // Verifying the result
        assertEquals(2, result.size());
        assertEquals("Method1", result.get(0).getMethod());
        assertEquals(100, result.get(0).getPaymentValue());
        assertEquals("Method2", result.get(1).getMethod());
        assertEquals(200, result.get(1).getPaymentValue());

        // Verifying that the repository method was called
        verify(repo, times(1)).findAll();
    }

    @Test
    public void testFindById_ValidId_ReturnsPaymentEntity() {
        // Mocking repository response
        PaymentEntity paymentEntity = new PaymentEntity("Method1", new Date(), 100);
        when(repo.findById(1)).thenReturn(Optional.of(paymentEntity));

        // Calling the service method
        PaymentEntity result = service.findById(1);

        // Verifying the result
        assertEquals(paymentEntity, result);

        // Verifying that the repository method was called
        verify(repo, times(1)).findById(1);
    }
    
    @Test
    public void testUpdatePayment_InvalidId_ThrowsPaymentException() {
        // Calling the service method with null id and null payment and verifying the exception
        PaymentException exception = assertThrows(PaymentException.class, () -> service.updatePayment(null, null));
        assertEquals("Invalid id.", exception.getMessage());

        // Verifying that the repository findById method was not called
        verify(repo, never()).findById(anyInt());
        // Verifying that the repository save method was not called
        verify(repo, never()).save(any());
    }

    @Test
    public void testFindById_InvalidId_ThrowsObjectNotFound() {
        // Mocking repository response
        when(repo.findById(1)).thenReturn(Optional.empty());

        // Calling the service method and verifying the exception
        ObjectNotFound exception = assertThrows(ObjectNotFound.class, () -> service.findById(1));
        assertEquals("Payment not found - 1", exception.getMessage());

        // Verifying that the repository method was called
        verify(repo, times(1)).findById(1);
    }

    @Test
    public void testCancel_PendingPayment_UpdatesPayment() {
        // Mocking the repository response
        PaymentEntity paymentEntity = new PaymentEntity("Method1", new Date(), 100);
        paymentEntity.setStatus(Status.PENDING);
        Optional<PaymentEntity> optionalPayment = Optional.of(paymentEntity);
        when(repo.findById(1)).thenReturn(optionalPayment);
        when(repo.save(any())).thenReturn(new PaymentEntity());

        // Calling the service method
        service.cancel(1);

        // Verifying that the repository findById and save methods were called
        verify(repo, times(1)).findById(1);
        verify(repo, times(1)).save(any());

        // Verifying the updated payment
        assertEquals(Status.CANCELED, paymentEntity.getStatus());
        assertNull(paymentEntity.getPaidAt());
        assertEquals(0, paymentEntity.getTransactionId());
    }

    @Test
    public void testCancel_NonPendingPayment_ThrowsPaymentException() {
        // Mocking the repository response
        PaymentEntity paymentEntity = new PaymentEntity("Method1", new Date(), 100);
        paymentEntity.setStatus(Status.PAID);
        Optional<PaymentEntity> optionalPayment = Optional.of(paymentEntity);
        when(repo.findById(1)).thenReturn(optionalPayment);

        // Calling the service method and verifying the exception
        PaymentException exception = assertThrows(PaymentException.class, () -> service.cancel(1));
        assertEquals("Payment already finished.", exception.getMessage());

        // Verifying that the repository findById method was called
        verify(repo, times(1)).findById(1);
        // Verifying that the repository save method was not called
        verify(repo, never()).save(any());
    }

    @Test
    public void testPay_PendingPayment_UpdatesPayment() {
        // Mocking the repository response
        PaymentEntity paymentEntity = new PaymentEntity("Method1", new Date(), 100);
        paymentEntity.setStatus(Status.PENDING);
        Optional<PaymentEntity> optionalPayment = Optional.of(paymentEntity);
        when(repo.findById(1)).thenReturn(optionalPayment);
        when(repo.save(any())).thenReturn(new PaymentEntity());

        // Creating a TransactionDTO
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionId(123456);

        // Calling the service method
        service.pay(transactionDTO, 1);

        // Verifying that the repository findById and save methods were called
        verify(repo, times(1)).findById(1);
        verify(repo, times(1)).save(any());

        // Verifying the updated payment
        assertEquals(123456, paymentEntity.getTransactionId());
        assertNotNull(paymentEntity.getPaidAt());
        assertEquals(Status.PAID, paymentEntity.getStatus());
    }

    @Test
    public void testPay_NonPendingPayment_ThrowsPaymentException() {
        // Mocking the repository response
        PaymentEntity paymentEntity = new PaymentEntity("Method1", new Date(), 100);
        paymentEntity.setStatus(Status.PAID);
        Optional<PaymentEntity> optionalPayment = Optional.of(paymentEntity);
        when(repo.findById(1)).thenReturn(optionalPayment);

        // Creating a TransactionDTO
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionId(123456);

        // Calling the service method and verifying the exception
        PaymentException exception = assertThrows(PaymentException.class, () -> service.pay(transactionDTO, 1));
        assertEquals("Payment already finished.", exception.getMessage());

        // Verifying that the repository findById method was called
        verify(repo, times(1)).findById(1);
        // Verifying that the repository save method was not called
        verify(repo, never()).save(any());
    }

    @Test
    public void testUpdatePayment_PendingPayment_UpdatesPayment() {
        // Mocking the repository response
        PaymentEntity existingPayment = new PaymentEntity("Method1", new Date(), 100);
        existingPayment.setId(1);
        existingPayment.setStatus(Status.PENDING);
        Optional<PaymentEntity> optionalPayment = Optional.of(existingPayment);
        when(repo.findById(1)).thenReturn(optionalPayment);
        when(repo.save(any())).thenReturn(existingPayment);

        // Creating a new PaymentEntity with updated values
        PaymentEntity updatedPayment = new PaymentEntity("Method2", new Date(), 200);
        updatedPayment.setId(1);

        // Calling the service method
        service.updatePayment(updatedPayment, 1);

        // Verifying that the repository findById and save methods were called
        verify(repo, times(1)).findById(1);
        verify(repo, times(1)).save(existingPayment);

        // Verifying the updated payment
        assertEquals("Method2", existingPayment.getMethod());
        assertEquals(updatedPayment.getDueDate(), existingPayment.getDueDate());
        assertEquals(updatedPayment.getPaymentValue(), existingPayment.getPaymentValue());
    }

    @Test
    public void testUpdatePayment_NonPendingPayment_ThrowsPaymentException() {
        // Mocking the repository response
        PaymentEntity existingPayment = new PaymentEntity("Method1", new Date(), 100);
        existingPayment.setId(1);
        existingPayment.setStatus(Status.PAID);
        Optional<PaymentEntity> optionalPayment = Optional.of(existingPayment);
        when(repo.findById(1)).thenReturn(optionalPayment);

        // Creating a new PaymentEntity with updated values
        PaymentEntity updatedPayment = new PaymentEntity("Method2", new Date(), 200);
        updatedPayment.setId(1);

        // Calling the service method and verifying the exception
        PaymentException exception = assertThrows(PaymentException.class, () -> service.updatePayment(updatedPayment, 1));
        assertEquals("Payment already finished.", exception.getMessage());

        // Verifying that the repository findById method was called
        verify(repo, times(1)).findById(1);
        // Verifying that the repository save method was not called
        verify(repo, never()).save(any());
    }
}