package com.skills.skills.data;

import com.skills.skills.models.skill.SkillsCategory;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillsCategoryRepository extends PagingAndSortingRepository<SkillsCategory,Integer> {
}
