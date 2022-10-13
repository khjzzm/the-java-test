package me.khjzzm.thejavatest;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class StudyTest {

    @Test
    void create() {
        Study study = new Study();
        assertNotNull(study);
        System.out.println("create");
    }

    @Test
    @Disabled
    void create1(){
        System.out.println("create1");
    }

    @BeforeAll
    static void beforeAll(){
        System.out.println("StudyTest.beforeAll");
    }

    @AfterAll
    static void afterAll(){
        System.out.println("StudyTest.afterAll");
    }

    @BeforeEach
    void beforeEach(){
        System.out.println("StudyTest.beforeEach");
    }

    @AfterEach
    void afterEach(){
        System.out.println("StudyTest.afterEach");
    }

}