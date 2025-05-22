package javaCoursework;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompetitorTestCase {
    private Competitor competitor;

    @BeforeEach
    void setUp() {
        // Initialize a Competitor object before each test
        int[] scores = {8, 7, 9, 6, 10};
        competitor = new Competitor("Shrawan Mainali", "Intermediate", scores);
        competitor.setCompetitorID(101);
    }

    @Test
    void testGetOverallScore() {
        // Expected overall score calculation: Remove min (6) & max (10), average remaining (7,8,9)
        double expectedScore = (7 + 8 + 9) / 3.0;
        assertEquals(expectedScore, competitor.getOverallScore(), 0.01);
    }

    @Test
    void testGetFullDetails() {
        String expected = "Competitor number 101, name Shrawan Mainali.\nShrawan Mainali with Intermediate and has an lead overall score of " + competitor.getOverallScore() + ".";
        assertEquals(expected, competitor.getFullDetails());
    }

    @Test
    void testGetShortDetails() {
        String expected = "CN 101 (Shrawan Mainali) has an overall score of " + competitor.getOverallScore();
        assertEquals(expected, competitor.getShortDetails());
    }

    @Test
    void testSettersAndGetters() {
        competitor.setName("Shrawan Mainali");
        competitor.setLevel("Advanced");
        int[] newScores = {10, 9, 8, 7, 6};
        competitor.setScores(newScores);

        assertEquals("Shrawan Mainali", competitor.getName());
        assertEquals("Advanced", competitor.getLevel());
        assertArrayEquals(newScores, competitor.getScores());
    }
}
