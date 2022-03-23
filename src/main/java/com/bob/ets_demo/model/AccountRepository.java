package com.bob.ets_demo.model;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<AccountModel, String> {

    Optional<AccountModel> findById(String id);
}
