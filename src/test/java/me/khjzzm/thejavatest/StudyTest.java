package me.khjzzm.thejavatest;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

    @Test
    @DisplayName("스터디 만들기")
    void create_new_study() {
        Study study = new Study();
        assertNotNull(study);
        System.out.println("create");
    }

    @Test
    @Disabled
    void create_new_study_again(){
        System.out.println("create");
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