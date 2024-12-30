package com.example.Application.testdata;

import com.example.Application.domain.Professor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

public class RandomProfessorDataGenerator {

    public static void main(String[] args) {
        int numberOfProfessors = 300;

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("university");
        EntityManager em = emf.createEntityManager();

        List<Professor> professors = generateRandomProfessors(numberOfProfessors);

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            for (Professor professor : professors) {
                em.persist(professor);
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

    private static List<Professor> generateRandomProfessors(int count) {
        List<Professor> professors = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String randomName = generateRandomName();
            String randomDepartment = generateRandomDepartment();
            String randomEmail = generateRandomEmail(randomName);

            Professor professor = new Professor();
            professor.setName(randomName);
            professor.setDepartment(randomDepartment);
            professor.setEmail(randomEmail);

            professors.add(professor);
        }
        return professors;
    }

    // 랜덤 이름 생성 (5~10자)
    private static String generateRandomName() {
        int length = (int) (5 + Math.random() * 6);  // 길이를 5~10으로 랜덤 설정
        return RandomStringUtils.randomAlphabetic(length);
    }

    // 랜덤 학과 생성
    private static String generateRandomDepartment() {
        String[] departments = {"Computer Science", "Mathematics", "Physics", "Chemistry", "Biology", "Engineering"};
        return departments[(int) (Math.random() * departments.length)];
    }

    // 랜덤 이메일 생성
    private static String generateRandomEmail(String name) {
        String[] domains = {"example.com", "test.com", "demo.com", "sample.com"};
        String domain = domains[(int) (Math.random() * domains.length)];
        return name.toLowerCase() + (int) (Math.random() * 1000) + "@" + domain;
    }
}
