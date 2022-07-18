package com.ebs.marketplace.session;

import com.ebs.marketplace.model.Token;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends CrudRepository<Token, String> {

    boolean existsBySessionId (String id);
}
