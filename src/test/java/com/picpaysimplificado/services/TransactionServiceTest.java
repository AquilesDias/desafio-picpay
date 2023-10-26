package com.picpaysimplificado.services;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.domain.user.UserType;
import com.picpaysimplificado.dto.TransactionDTO;
import com.picpaysimplificado.repositories.TransactionalRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class TransactionServiceTest {

    @Mock
    private UserService  userService;

    @Mock
    private TransactionalRepository repository;

    @Mock
    private AuthorizationService authorizationService;

    @Autowired
    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private EmailNotificationService emailNotificationService;

//    @BeforeEach
//    void setUp(){
//        MockitoAnnotations.initMocks(this);
//    }

    @Test
    @DisplayName("Should create transaction ")
    void createTransactionTest() throws Exception {

        User sender   = new User(1L, "Naruto", "Uzumaki", "123", "naruto@gmail.com", "11122233344", new BigDecimal(10), UserType.COMMON);
        User receiver = new User(2L, "Sasuke", "Uchiha", "321", "sasuke@gmail.com", "11133322244", new BigDecimal(0), UserType.COMMON);

        when( userService.findUserById(1L)).thenReturn(sender);
        when( userService.findUserById(2L)).thenReturn(receiver);

        when( authorizationService.authorizeTransaction(any(), any())).thenReturn(true);

        TransactionDTO transaction = new TransactionDTO(new BigDecimal(10), 1L, 2L);
        transactionService.createTransaction(transaction);

        verify( repository, times(1)).save(any());

        sender.setBalance(new BigDecimal(0));
        verify( userService, times(1)).save(sender);

        receiver.setBalance(new BigDecimal(10));
        verify( userService, times(1)).save(receiver);

        verify( emailNotificationService, times(1) ).sendNotification(sender, "ENVIADO");
        verify( emailNotificationService, times(1) ).sendNotification(receiver, "RECEBIDO");

    }

    @Test
    void createTransactionTest2(){

    }
}