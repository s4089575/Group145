package com.roadregistry;

import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * This class provides functionality to update a person's personal details
 * such as ID, name, address, and birthday in the RoadRegistry system.
 * It reads from and writes to the updater_persons.txt file.
 */
public class PersonUpdater {

    // Helper method to calculate a person's age from their birthdate string
    private int calculateAge(String birthDateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate birthDate = LocalDate.parse(birthDateString, formatter);
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    /**
     * Updates a person's details in the updater_persons.txt file based on rules:
     * - Under 18s cannot change address.
     * - If birthday is changed, no other fields can be changed.
     * - If ID starts with an even digit, it cannot be changed.
     *
     * @param oldPersonID    The original ID of the person.
     * @param newPersonID    The new (or same) ID to update to.
     * @param firstName      The new first name.
     * @param lastName       The new last name.
     * @param address        The new address.
     * @param birthday       The new birthday.
     * @return true if updated successfully, false if rules were violated or person not found.
     */
    public boolean updatePersonalDetails(String oldPersonID, String newPersonID, String firstName,
                                         String lastName, String address, String birthday) {
        File file = new File("updater_persons.txt");

        try {
            List<String> lines = Files.readAllLines(file.toPath());
            boolean updated = false;

            for (int i = 0; i < lines.size(); i++) {
                String[] parts = lines.get(i).split("\\|");

                if (parts.length < 5) continue; // skip malformed entries

                if (parts[0].equals(oldPersonID)) {
                    String currentID = parts[0];
                    String currentFirstName = parts[1];
                    String currentLastName = parts[2];
                    String currentAddress = parts[3];
                    String currentBirthday = parts[4];

                    int age = calculateAge(currentBirthday);

                    // Rule 1: Under 18 cannot change address
                    if (!currentAddress.equals(address) && age < 18) {
                        return false;
                    }

                    // Rule 2: If birthday is changing, other fields must remain the same
                    if (!currentBirthday.equals(birthday)) {
                        if (!newPersonID.equals(currentID) ||
                            !firstName.equals(currentFirstName) ||
                            !lastName.equals(currentLastName) ||
                            !address.equals(currentAddress)) {
                            return false;
                        }
                    }

                    // Rule 3: If ID starts with even digit, ID cannot change
                    char firstChar = currentID.charAt(0);
                    if (Character.isDigit(firstChar) &&
                        (firstChar - '0') % 2 == 0 &&
                        !currentID.equals(newPersonID)) {
                        return false;
                    }

                    // All conditions passed, perform update
                    parts[0] = newPersonID;
                    parts[1] = firstName;
                    parts[2] = lastName;
                    parts[3] = address;
                    parts[4] = birthday;

                    lines.set(i, String.join("|", parts));
                    updated = true;
                    break;
                }
            }

            if (updated) {
                Files.write(file.toPath(), lines);
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
