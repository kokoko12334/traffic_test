package com.example.Application.testdata;

import com.example.Application.domain.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

public class RandomStudentDataGenerator {

    public static void main(String[] args) {
        int numberOfStudents = 5000;

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("university");
        EntityManager em = emf.createEntityManager();

        List<Student> students = generateRandomStudents(numberOfStudents);

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            for (Student student : students) {
                em.persist(student);
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static List<Student> generateRandomStudents(int count) {
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String randomName = generateRandomName();
            String randomEmail = generateRandomEmail(randomName);
            Student student = new Student();
            student.setName(randomName);
            student.setEmail(randomEmail);
            students.add(student);
        }
        return students;
    }

    private static String generateRandomName() {
        int length = (int) (5 + Math.random() * 6);
        return RandomStringUtils.randomAlphabetic(length);
    }

    private static String generateRandomEmail(String name) {
        String[] domains = {"example.com", "test.com", "demo.com", "sample.com"};
        String domain = domains[(int) (Math.random() * domains.length)];
        return name.toLowerCase() + (int) (Math.random() * 1000) + "@" + domain;
    }
}
