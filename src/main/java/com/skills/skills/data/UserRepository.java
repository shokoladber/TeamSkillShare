package com.skills.skills.data;

import com.skills.skills.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

@Repository
@NoRepositoryBean
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);

}