package com.roadregistry;

import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the updatePersonalDetails method in the PersonUpdater class.
 * Ensures all specified conditions are tested and validated.
 */
public class PersonUpdaterTest {

    private final String testFilePath = "updater_persons.txt";
    private PersonUpdater updater;

    @BeforeEach
    void setUp() throws IOException {
        updater = new PersonUpdater();

        // Setup initial test data
        Files.write(Paths.get(testFilePath), String.join("\n",
            "15x!_#dAB|Alice|Young|10 Little St|15-06-2010|false|0|",       // Under 18
            "24k$%_gAC|Bob|Smith|45 Road St|20-10-1995|false|2|",           // Even-ID start
            "67m&*pZZ|Tom|Bond|55 Wall St|02-02-1990|false|4|",             // Over 21 adult
            "87z@!pGH|Jess|Stone|90 Hill St|15-11-2005|false|1|"            // Teenager
        ).getBytes());
    }

    //@AfterEach
    //void tearDown() throws IOException {
    //    Files.deleteIfExists(Paths.get(testFilePath));
    //}

    @Test
    void testValidUpdateForAdult() {
        boolean result = updater.updatePersonalDetails(
                "67m&*pZZ", "67m&*pZZ", "Tom", "Bond", "123 New St", "02-02-1990");
        assertTrue(result, "✅ Should update for valid adult, same birthday, updated address.");
    }

    @Test
    void testUnder18CannotChangeAddress() {
        boolean result = updater.updatePersonalDetails(
                "15x!_#dAB", "15x!_#dAB", "Alice", "Young", "99 New Ave", "15-06-2010");
        assertFalse(result, "❌ Should not update: under 18 attempting to change address.");
    }

    @Test
    void testBirthdayChangedButOtherFieldsAlsoChanged() {
        boolean result = updater.updatePersonalDetails(
                "87z@!pGH", "87z@!pGH", "Jess", "Stone", "New Address", "15-11-2004");
        assertFalse(result, "❌ Should not update: birthday changed + address changed.");
    }

    @Test
    void testEvenDigitIDCannotBeChanged() {
        boolean result = updater.updatePersonalDetails(
                "24k$%_gAC", "99k$%_gAC", "Bob", "Smith", "45 Road St", "20-10-1995");
        assertFalse(result, "❌ Should not update: ID starts with even digit and is changed.");
    }

    @Test
    void testOnlyBirthdayChangedAllowed() {
        boolean result = updater.updatePersonalDetails(
                "87z@!pGH", "87z@!pGH", "Jess", "Stone", "90 Hill St", "15-11-2004");
        assertTrue(result, "✅ Should update: only birthday changed, all other fields same.");
    }
}
