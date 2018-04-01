package com.mJunction.drm;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

import com.mJunction.drm.dao.ErrorTableRepository;
import com.mJunction.drm.dao.MjReportRepository;


@SpringBootApplication
//@EnableTransactionManagement
//@EntityScan(basePackages = {"com.mJunction.drm.common.entity"})
//@EnableAutoConfiguration
//@EnableJpaRepositories(basePackages = {"com.mJunction.drm.dao"})
@ImportResource("classpath:bam-application-context.xml")
public class DRMApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(DRMApplication.class);

//    @Autowired
//    private ProcessStateTableRepository processStateTableRepository;
    @Autowired
    private ErrorTableRepository errorTableRepository;
    @Autowired
    private MjReportRepository mjReportRepository;


    public static void main(String[] args) {

        SpringApplication.run(DRMApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime later = now.minus(365, ChronoUnit.DAYS);
        Date dateAfterSubstractingDays = Date.from(later.atZone(ZoneId.systemDefault()).toInstant());
        Date endDateSystem = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
//        Integer countFromProcessStateTable = this.processStateTableRepository.getCountByTimeStampAndFinalStatusDateTest(dateAfterSubstractingDays,endDateSystem,"Processed");
//
//        Integer countFromProcessStateTableForFailure = this.processStateTableRepository.getCountByTimeStampAndFinalStatusDateTest(dateAfterSubstractingDays,endDateSystem,"Failure");
        ///((ProcessStateTableRepository)ctx.getBean("processStateTableRepository"))

       // Integer countFromErrorReportTable = this.errorTableRepository.getCountByDateColumnDateTest(dateAfterSubstractingDays,endDateSystem);

       // Integer countFromMjReportTable = this.mjReportRepository.getCountByDateTimeStampAndFinalStatus(dateAfterSubstractingDays,endDateSystem,"Processed");

//        LOGGER.info("[main] : countFromProcessStateTable for processed : ",countFromProcessStateTable);
//
//        LOGGER.info("[main] : countFromProcessStateTable for failure : ",countFromProcessStateTableForFailure);

      //  LOGGER.info("[main] : countFromErrorTableForFailure : ",countFromErrorReportTable);

      //  LOGGER.info("[main] : countFromMjReportTable : ",countFromMjReportTable);


        return (String... args) -> {

//            System.out.println("Let's inspect the beans provided by Spring Boot:");
//
//            String[] beanNames = ctx.getBeanDefinitionNames();
//            Arrays.sort(beanNames);
//            for (String beanName : beanNames) {
//                System.out.println(beanName);
//            }
        };
    }

    @Bean
    public SessionFactory sessionFactory(HibernateEntityManagerFactory hemf) {
        return hemf.getSessionFactory();
    }

}
