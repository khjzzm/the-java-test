package me.khjzzm.thejavatest;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StudyTest {

    int value = 1;

    @Test
    @DisplayName("스터디 만들기")
    void create_new_study() {
        Study study = new Study(value++);

        assertNotNull(study);
        // messages 를 람다식으로 작성하는 이유는?
        assertEquals(StudyStatus.DRAFT, study.getStatus(), () -> "스터디를 처음 만들면" + StudyStatus.DRAFT + "여야 한다.");
        assertTrue(study.getLimit() > 0, () -> "스터디 최대 참석 인원은 0 보다 커야 한다.");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Study(-10));
        assertEquals("limit 은 0보다 커야 한다.", exception.getMessage());

        assertTimeout(Duration.ofMillis(100), () -> {
            new Study(10);
            Thread.sleep(50);
        });

        assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
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

        assumingThat("LOCAL".equalsIgnoreCase(test_env), () -> {
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
        value++;
        System.out.println("local 환경에서 테스트 fast");
    }

    @Test
    @Tag("slow")
    void test_tagging2() {
        value++;
        System.out.println("ci 환경에서 테스트 slow");
    }


    @Test
    @FastTest
    void test_annotation() {
        System.out.println("local 환경에서 테스트 fast" + value);
    }

    @Test
    @SlowTest
    void test_annotation2() {
        System.out.println("ci 환경에서 테스트 slow");
    }


    @DisplayName("반복 테스트")
    @RepeatedTest(value = 10, name = "{currentRepetition}/{totalRepetitions}")
    void repeatTest(RepetitionInfo repetitionInfo) {
        System.out.println("test" + repetitionInfo.getCurrentRepetition() + repetitionInfo.getTotalRepetitions());
    }

    @ParameterizedTest(name = "{index} / {0}")
    @ValueSource(strings = {"날씨가", "많이", "추워지고", "있네요."})
    void parameterizedTest(String message){
        System.out.println("message = " + message);
    }


    @ParameterizedTest(name = "{index} / {0}")
    @ValueSource(strings = {"날씨가", "많이", "추워지고", "있네요."})
//    @EmptySource
//    @NullSource
    @NullAndEmptySource
    void parameterizedTest2(String message){
        System.out.println("message = " + message);
    }


    @BeforeAll
    void beforeAll() {
        System.out.println("StudyTest.beforeAll");
    }

    @AfterAll
    void afterAll() {
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