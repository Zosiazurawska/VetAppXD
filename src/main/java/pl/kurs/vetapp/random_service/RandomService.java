package pl.kurs.vetapp.random_service;

import pl.kurs.vetapp.domain.constant.AnimalSpecialization;
import pl.kurs.vetapp.domain.constant.AnimalType;
import pl.kurs.vetapp.domain.constant.MedicalSpecialization;
import pl.kurs.vetapp.domain.model.Client;
import pl.kurs.vetapp.domain.model.Doctor;
import pl.kurs.vetapp.domain.model.Visit;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomService {

    private final Random random = new Random();
    private final List<String> firstnamesList = List.of("Jake", "Jack", "Harry", "Jacob", "George", "James", "William", "Connor", "Joe", "David");
    private final List<String> lastnamesList = List.of("Smith", "Jones", "Williams", "Taylor", "Davies", "Evans", "Thomas", "Johnson", "Roberts", "Walker");
    private final List<String> petsNamesList = List.of("Misia", "Reksio", "Fafik", "Pimpek", "Persi");
    private final List<AnimalType> animalTypesList = Arrays.stream(AnimalType.values()).toList();
    private final List<MedicalSpecialization> medicalSpecializationList = Arrays.stream(MedicalSpecialization.values()).toList();
    private final List<AnimalSpecialization> animalSpecializationList = Arrays.stream(AnimalSpecialization.values()).toList();

    public Doctor getRandomDoctor() {
        return new Doctor(
                getRandomFirstname(),
                getRandomLastname(),
                getRandomMedicalSpecialization(),
                getRandomAnimalSpecialization(),
                getRandomSalary(),
                getRandomNIP(),
                getRandomBoolean());
    }

//    public Client getRandomClient() {
//        String firstname = getRandomFirstname();
//        String lastname = getRandomLastname();
//        return Client.builder()
//                .firstname(firstname)
//                .lastname(lastname)
//                .petName(getRandomPetName())
//                .petType(getRandomAnimalType())
//                .petYears(getRandomPetYears())
//                .email(getEmail(firstname, lastname))
//                .build();
//    }

//    public Visit getRandomVisit() {
//        LocalDateTime timeStart = getRandomTimeVisit();
//        return new Visit(
//                getRandomDoctor(),
//                getRandomClient(),
//                getRandomTimeVisit());
//    }

    private String getRandomFirstname() {
        return firstnamesList.get(random.nextInt(firstnamesList.size()));
    }

    private String getRandomLastname() {
        return lastnamesList.get(random.nextInt(lastnamesList.size()));
    }

    private Double getRandomSalary() {
        double salary = random.nextDouble(30.00, 55.00);
        return Math.round(salary * 100.0) / 100.0;
    }

    private String getRandomNIP() {
        StringBuilder nip = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int x = random.nextInt(1, 9);
            nip.append(x);
        }
        return nip.toString();
    }

    private boolean getRandomBoolean() {
        return random.nextBoolean();
    }

    private MedicalSpecialization getRandomMedicalSpecialization() {
        return medicalSpecializationList.get(random.nextInt(medicalSpecializationList.size()));
    }

    private AnimalSpecialization getRandomAnimalSpecialization() {
        return animalSpecializationList.get(random.nextInt(animalSpecializationList.size()));
    }

    private String getRandomPetName() {
        return petsNamesList.get(random.nextInt(petsNamesList.size()));
    }

    private Integer getRandomPetYears() {
        return random.nextInt(3, 15);
    }

    private AnimalType getRandomAnimalType() {
        return animalTypesList.get(random.nextInt(animalTypesList.size()));
    }

    private String getEmail(String firstname, String lastname) {
        return firstname.toLowerCase() + "." + lastname.toLowerCase() + "@email.com";
    }

    private LocalDateTime getRandomTimeVisit() {
        int months = random.nextInt(6, 12);
        int days = random.nextInt(1, 30);
        int hours = random.nextInt(8, 18);
        int minutes = random.nextInt(0, 59);

        return LocalDateTime.of(2023, months, days, hours, minutes, 0);
    }

}
