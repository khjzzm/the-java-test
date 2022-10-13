package me.khjzzm.thejavatest;

import org.junit.jupiter.api.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

    @Test
    @DisplayName("스터디 만들기")
    void create_new_study() {
        Study study = new Study(0);

        assertNotNull(study);
        // messages 를 람다식으로 작성하는 이유는?
        assertEquals(StudyStatus.DRAFT, study.getStatus(), () -> "스터디를 처음 만들면" + StudyStatus.DRAFT + "여야 한다.");
        assertTrue(study.getLimit() > 0, () -> "스터디 최대 참석 인원은 0 보다 커야 한다.");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Study(-10));
        assertEquals("limit 은 0보다 커야 한다.", exception.getMessage());

        assertTimeout(Duration.ofMillis(100), ()-> {
            new Study(10);
            Thread.sleep(300);
        });

        assertTimeoutPreemptively(Duration.ofMillis(100), ()-> {
            new Study(10);
            Thread.sleep(300);
        });

        assertAll(
                () -> assertNotNull(study),
                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(), () -> "스터디를 처음 만들면" + StudyStatus.DRAFT + "여야 한다."),
                () -> assertTrue(study.getLimit() > 0, () -> "스터디 최대 참석 인원은 0 보다 커야 한다."),
                () -> assertThrows(IllegalArgumentException.class, () -> new Study(-10))
        );

    }

    @Test
    @Disabled
    void create_new_study_again() {
        System.out.println("create");
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("StudyTest.beforeAll");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("StudyTest.afterAll");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("StudyTest.beforeEach");
    }

    @AfterEach
    void afterEach() {
        System.out.println("StudyTest.afterEach");
    }

}