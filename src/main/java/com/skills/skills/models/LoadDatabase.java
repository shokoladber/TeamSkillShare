//package com.skills.skills.models;
//
//import com.skills.skills.data.SkillsCategoryRepository;
//import com.skills.skills.data.SkillsRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
//@Configuration
//public class LoadDatabase {
//
//    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
//    @Bean
//    CommandLineRunner initDatabase(SkillsCategoryRepository categoryRepository) {
//
//        return args -> {
//            log.info("Preloading " + categoryRepository.save(new SkillsCategory("Dance")));
//            log.info("Preloading " + categoryRepository.save(new SkillsCategory("Language")));
//            log.info("Preloading " + categoryRepository.save(new SkillsCategory("Sports")));
//            log.info("Preloading " + categoryRepository.save(new SkillsCategory("Coding")));
//            log.info("Preloading " + categoryRepository.save(new SkillsCategory("Performance Arts")));
//        };
//    }
//}
