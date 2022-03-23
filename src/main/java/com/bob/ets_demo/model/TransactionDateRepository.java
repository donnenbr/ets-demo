package com.bob.ets_demo.model;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TransactionDateRepository extends CrudRepository<TransactionDateModel, Long> {

    Optional<TransactionDateModel> findById(Long id);
}
