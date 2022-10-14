package me.khjzzm.thejavatest;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

    @Test
    @DisplayName("스터디 만들기")
    void create_new_study() {
        Study study = new Study(5);

        assertNotNull(study);
        // messages 를 람다식으로 작성하는 이유는?
        assertEquals(StudyStatus.DRAFT, study.getStatus(), () -> "스터디를 처음 만들면" + StudyStatus.DRAFT + "여야 한다.");
        assertTrue(study.getLimit() > 0, () -> "스터디 최대 참석 인원은 0 보다 커야 한다.");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Study(-10));
        assertEquals("limit 은 0보다 커야 한다.", exception.getMessage());

        assertTimeout(Duration.ofMillis(100), ()-> {
            new Study(10);
            Thread.sleep(50);
        });

        assertTimeoutPreemptively(Duration.ofMillis(100), ()-> {
            new Study(10);
            Thread.sleep(50);
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

    @Test
    @DisplayName("조건에 따라 테스트 실행하기")
    void create_con_study() {
        String test_env = System.getenv("TEST_ENV");
        System.out.println("TEST_ENV:" + test_env);
        assumeTrue("LOCAL".equalsIgnoreCase(test_env));

        assumingThat("LOCAL".equalsIgnoreCase(test_env), ()->{
            System.out.println("local");
            Study actual = new Study(100);
            assertThat(actual.getLimit()).isGreaterThan(0);
        });
    }


    @Test
    @DisplayName("조건에 따라 테스트 실행하기 annotation")
    @EnabledOnOs({OS.MAC, OS.LINUX})
    @EnabledOnJre(JRE.JAVA_8)
//    @DisabledOnOs(OS.MAC)
    @EnabledIfEnvironmentVariable(named = "TEST_ENV", matches = "LOCAL")
    void create_con1_study() {
        String test_env = System.getenv("TEST_ENV");
        System.out.println("TEST_ENV:" + test_env);
        assumeTrue("LOCAL".equalsIgnoreCase(test_env));
    }


    @Test
    @Tag("fast")
    void test_tagging() {
        System.out.println("local 환경에서 테스트 fast");
    }

    @Test
    @Tag("slow")
    void test_tagging2() {
        System.out.println("ci 환경에서 테스트 slow");
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