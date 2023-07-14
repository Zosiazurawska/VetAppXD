package pl.kurs.vetapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.kurs.vetapp.application.config.ObjectMapperHolder;
import pl.kurs.vetapp.random_service.RandomService;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableSwagger2
public class VetAppApplication {

    public static void main(String[] args) throws IOException {

        RandomService randomService = new RandomService();

        ObjectMapper mapper = ObjectMapperHolder.INSTANCE.getMapper();
        mapper.registerModule(new JavaTimeModule());

        mapper.writeValue(new File("src/main/resources/doctor1.json"), randomService.getRandomDoctor());
        mapper.writeValue(new File("src/main/resources/doctor2.json"), randomService.getRandomDoctor());

//        mapper.writeValue(new File("src/main/resources/client1.json"), randomService.getRandomClient());
//        mapper.writeValue(new File("src/main/resources/client2.json"), randomService.getRandomClient());
//
//        mapper.writeValue(new File("src/main/resources/visit1.json"), randomService.getRandomVisit());
//        mapper.writeValue(new File("src/main/resources/visit2.json"), randomService.getRandomVisit());


        System.out.println(randomService.getRandomDoctor().getNip());

        SpringApplication.run(VetAppApplication.class, args);








    }
}
