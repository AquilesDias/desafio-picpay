package com.picpaysimplificado.services;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.dto.TransactionDTO;
import com.picpaysimplificado.repositories.TransactionalRepository;
import com.picpaysimplificado.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Log4j2
@Service
public class TransactionService {

    @Autowired
    UserService userService;

    @Autowired
    TransactionalRepository repository;

    @Autowired
    private EmailNotificationService emailNotificationService;

    @Autowired
    RestTemplate restTemplate;

    public Transactional createTransaction(TransactionDTO transactionDTO) throws Exception {

        User sender   = this.userService.findUserById(transactionDTO.senderId());
        User receiver = this.userService.findUserById(transactionDTO.receiverId());

        userService.validateTransaction(sender, transactionDTO.value());

//        boolean isAuthorizated = this.authorizeTransaction(sender, transactionDTO.value());
//        if(!isAuthorizated){
//            throw new Exception("Not authorizated");
//        }

        Transactional newTransactional = new Transactional();
        newTransactional.setAmount(transactionDTO.value());
        newTransactional.setSender(sender);
        newTransactional.setReceiver(receiver);
        newTransactional.setLocalDateTime(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transactionDTO.value()));
        receiver.setBalance(receiver.getBalance().add(transactionDTO.value()));

        this.repository.save(newTransactional);
        this.userService.save(sender);
        this.userService.save(receiver);
        this.emailNotificationService.sendNotification(sender, "ENVIADO");
        this.emailNotificationService.sendNotification(receiver, "RECEBIDO");


        return newTransactional;
    }

    public boolean authorizeTransaction(User sender, BigDecimal value){

        ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://run.mocky.io/v3/8fafdd68-a090-496f-8c9a-3442cf30dae6", Map.class);

        if(authorizationResponse.getStatusCode() == HttpStatus.OK){
            String message = (String) authorizationResponse.getBody().get("message");
            return "Autorizado".equalsIgnoreCase(message);
        } else
            return false;
    }
}
