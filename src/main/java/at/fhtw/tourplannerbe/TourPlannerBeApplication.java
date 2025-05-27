package at.fhtw.tourplannerbe;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class TourPlannerBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TourPlannerBeApplication.class, args);
    }

//    @PostConstruct
//    public void init(){
//        // Setting Spring Boot SetTimeZone
//        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
//    }

}
