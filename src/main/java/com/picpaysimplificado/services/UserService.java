package com.picpaysimplificado.services;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.dto.UserDTO;
import com.picpaysimplificado.repositories.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Log4j2
@Service
public class UserService {

    @Autowired
    private UserRepository repository;


    public void save(User user){
        this.repository.save(user);
    }

    public User findUserById(Long id) throws Exception{
        return this.repository.findById(id).orElseThrow(() ->
            new Exception("User not found!") );
    }

    public void validateTransaction(User sender, BigDecimal amount) throws Exception {

        if(sender.getUserType() == sender.getUserType().MERCHANT){
            throw new Exception("User not authorizated");
        }

        if(sender.getBalance().compareTo(amount) < 0 ){
            throw new Exception("Amount invalidad");
        }

    }

    public User createUser(UserDTO userDTO) {
        User newUser = new User(userDTO);
        this.save(newUser);
        return newUser;
    }

    public List<User> findAllUser() {
         return repository.findAll();
    }
}
