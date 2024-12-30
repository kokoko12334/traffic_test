package com.example.Application.testdata;

import com.example.Application.domain.Course;
import com.example.Application.domain.Professor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomCourseDataGenerator {

    public static void main(String[] args) {
        int numberOfCourses = 500;

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("university");
        EntityManager em = emf.createEntityManager();

        List<Course> courses = generateRandomCourses(numberOfCourses, em);

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            for (Course course : courses) {
                em.persist(course);
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

    private static List<Course> generateRandomCourses(int count, EntityManager em) {
        List<Course> courses = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            String randomTitle = generateRandomTitle();
            Professor randomProfessor = getRandomProfessor(em, random);

            Course course = new Course();
            course.setTitle(randomTitle);
            course.setProfessor(randomProfessor);
            course.setCurrentCount(0);
            course.setCapacity(50);

            courses.add(course);
        }
        return courses;
    }

    private static String generateRandomTitle() {
        int length = 5 + new Random().nextInt(11);  // 5~15자 길이 랜덤 설정
        return RandomStringUtils.randomAlphabetic(length);
    }

    private static Professor getRandomProfessor(EntityManager em, Random random) {
        Long randomProfessorId = Long.valueOf(1 + random.nextInt(300));  // 1 ~ 300 사이
        return em.find(Professor.class, randomProfessorId);
    }
}
