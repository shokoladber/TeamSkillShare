package com.skills.skills.data;

import com.skills.skills.models.event.EventDetails;
import org.springframework.data.repository.CrudRepository;

public interface EventDetailsRepository  extends CrudRepository<EventDetails, Integer> {
}
