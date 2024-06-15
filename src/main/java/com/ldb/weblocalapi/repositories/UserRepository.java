package com.ldb.weblocalapi.repositories;

import com.ldb.weblocalapi.entities.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Repository
@Transactional
public interface UserRepository extends CrudRepository<Users, Long> {
    Optional<Users> findByUsername(String username);
    Optional<Users> findByUserIdOrderByRolesAsc(Long userIds);
    Optional<Users> findByUsernameOrderByRolesAsc(String username);


}
