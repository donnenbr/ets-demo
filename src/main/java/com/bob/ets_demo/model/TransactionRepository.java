package com.bob.ets_demo.model;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.domain.Sort;

import java.util.Optional;

public interface TransactionRepository extends CrudRepository<TransactionModel, Long> {
    Optional<TransactionModel> findById(Long id);

    // one way which returns an iterable (ie, the list) sorted however you want
    Iterable<TransactionModel> findByUserId(String userId, Sort sort);

    // this will return the transaction with the most recent timestamp.  ideally this only returns one
    // row from the db, not many rows as above.
    Optional<TransactionModel> findFirstByUserIdOrderByTransactionDateTimeDesc(String userId);
}
