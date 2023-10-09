package com.picpaysimplificado.repositories;

import com.picpaysimplificado.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionalRepository extends JpaRepository <Transactional, Long> {
}
