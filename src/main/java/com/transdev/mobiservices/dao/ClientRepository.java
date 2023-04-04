package com.transdev.mobiservices.dao;

import com.transdev.mobiservices.entity.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client,Long> {
}
