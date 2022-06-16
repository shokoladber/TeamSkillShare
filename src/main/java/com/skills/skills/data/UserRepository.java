package com.skills.skills.data;

<<<<<<< HEAD
import com.skills.skills.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

@Repository
@NoRepositoryBean
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);

}
=======
import com.skills.skills.models.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

    User findByUsername(String username);

}
>>>>>>> dev
