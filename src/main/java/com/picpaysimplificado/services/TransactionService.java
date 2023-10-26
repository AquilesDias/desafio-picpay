package com.picpaysimplificado.services;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.dto.TransactionDTO;
import com.picpaysimplificado.repositories.TransactionalRepository;
import com.picpaysimplificado.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Log4j2
@Service
public class TransactionService {

    @Autowired
    UserService userService;

    @Autowired
    TransactionalRepository repository;

    @Autowired
    AuthorizationService authorizationservice;

    @Autowired
    private EmailNotificationService emailNotificationService;

    public Transactional createTransaction(TransactionDTO transactionDTO) throws Exception {

        User sender   = this.userService.findUserById(transactionDTO.senderId());
        User receiver = this.userService.findUserById(transactionDTO.receiverId());

        userService.validateTransaction(sender, transactionDTO.value());

//        boolean isAuthorizated = authorizationservice.authorizeTransaction(sender, transactionDTO.value());
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

}
