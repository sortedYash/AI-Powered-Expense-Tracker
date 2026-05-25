package org.example.Repository;

import org.example.Entities.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends CrudRepository<UserInfo , Long> {

    public UserInfo findByUsername(String username);

}
