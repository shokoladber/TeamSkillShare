package com.skills.data;

import com.skills.models.user.Message;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessagesRepository extends PagingAndSortingRepository<Message, Integer> {

}
