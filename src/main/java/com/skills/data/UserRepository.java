package com.skills.data;

import com.skills.models.user.User;
import com.skills.models.user.UserProfile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    UserProfile findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
