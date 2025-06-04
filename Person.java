import java.util.HashMap;
import java.util.Date;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class Person { // Changed from 'person' to 'Person'

    private String personID;
    private String firstName;
    private String lastName;
    private String address;
    private String birthdate;
    private HashMap<Date, Integer> demeritPoints; // A variable that holds the demerit points with the offense day
    private boolean isSuspended;
    private static final String DATA_FILE_NAME = "persons.txt";
    
 // Constructor
    public Person(String personID, String firstName, String lastName, String address, String birthdate) {
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.birthdate = birthdate;
        this.demeritPoints = new HashMap<>(); 
        this.isSuspended = false; 
    }
 // Getters 
   
    public String getPersonID() {
         return personID;
         }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getAddress() {
        return address;
    }
    public String getBirthdate() {
        return birthdate;
    }
    public HashMap<Date, Integer> getDemeritPoints() {
        return demeritPoints;
    }
    public boolean isSuspended() {
        return isSuspended;
    }
    private boolean isValidPersonID(String user_id){
        if (user_id.length() != 10){
            System.out.println("Person ID must be exactly 10 characters long.");

            return false;
        }
        char firstChar = user_id.charAt(0);
        char secondChar = user_id.charAt(1);
        if (firstChar < '2' || firstChar > '9' || secondChar < '2' || secondChar > '9') {
            System.out.println("The first two characters of the Person ID must be numbers between 2 and 9.");
            return false;
        }
        int specialCharCount = 0;
        for (int i = 2; i < 8; i++) {
            char c = user_id.charAt(i);
            if (!Character.isLetterOrDigit(c)) {
                specialCharCount++;
            }
        }
        if (specialCharCount < 2) {
            System.out.println("There should be at least two special characters between characters 3 and 8.");
            return false;
        }
        char lastChar = user_id.charAt(8);
        char secondLastChar = user_id.charAt(9);
        if (!Character.isUpperCase(lastChar) || !Character.isUpperCase(secondLastChar)) {
            System.out.println("The last two characters of the Person ID must be upper case letters (A – Z).");
            return false;
        }
        return true;
 }
    private boolean isValidAddress(String address) {
        String[] parts = address.split("\\|");
        if (parts.length != 5) {
            System.out.println("Address must follow the format: Street Number|Street|City|State|Country.");
            return false;
        }
        if (!parts[3].equalsIgnoreCase("Victoria")) {
            System.out.println("The State must be Victoria.");
            return false;
        }
        return true;
    }
    private boolean isValidBirthdate(String birthdate) {
        String[] parts = birthdate.split("-");
        if (parts.length != 3) {
            System.out.println("Birthdate must follow the format: DD-MM-YYYY.");
            return false;
        }
        if (parts[0].length() != 2 || parts[1].length() != 2 || parts[2].length() != 4) {
            System.out.println("Birthdate must be in the format DD-MM-YYYY.");
            return false;
        }
        return true;
    }

    public boolean addPerson() {
          
        //TODO: This method adds information about a person to a TXT file.

        //Condition 1: personID should be exactly 10 characters long;
        //the first two characters should be numbers between 2 and 9, there should be at least two special characters between characters 3 and 8,
        //and the last two characters should be upper case letters (A – Z). Example: “56s_d%&fAB”
       

        //Condition 2: The address of the Person should follow the following format: Street Number|Street|City|State|Country.
        //The State should be only Victoria. Example: 32|Highland Street|Melbourne|Victoria|Australia.

        //Condition 3: The format of the birth date of the person should follow the following format: DD-MM-YYYY. Example: 15-11-1990

        //Instruction: If the Person's information meets the above conditions and any other conditions you may want to consider,
        //the information should be inserted into a TXT file, and the addPerson function should return true.
        //Otherwise, the information should not be inserted into the TXT file, and the addPerson function should return false.
        if (!isValidPersonID(this.personID)) {
            System.out.println("Add person failed: Invalid PersonID for '" + this.personID + "'.");
            return false;
        }
        if (!isValidAddress(this.address)) {
            System.out.println("Add person failed: Invalid Address for '" + this.personID + "'. Address: '" + this.address + "'.");
            return false;
        }
        if (!isValidBirthdate(this.birthdate)) {
            System.out.println("Add person failed: Invalid Birthdate for '" + this.personID + "'. Birthdate: '" + this.birthdate + "'.");
            return false;
        }

        try (FileWriter fw = new FileWriter(DATA_FILE_NAME, true); // 'true' to append to file if it exists
             PrintWriter pw = new PrintWriter(fw)) {

           
            String record = String.join(", ", this.personID, this.firstName, this.lastName, this.address, this.birthdate);
            pw.println(record); 
            System.out.println("Person added successfully to " + DATA_FILE_NAME + ": ID " + this.personID);
            return true;
        } catch (IOException e) {
            System.err.println("Error writing person data to file '" + DATA_FILE_NAME + "': " + e.getMessage());
            return false;
        }
    }

    public boolean updatePersonalDetails() {

        //TODO: This method allows updating a given person’s ID, firstName, lastName, address and birthday in a TXT file.
        //Changing personal details will not affect their demerit points or the suspension status.

        // All relevant conditions discussed for the addPerson function also need to be considered and checked in the updatePerson function.

        //Condition 1: If a person is under 18, their address cannot be changed.

        //Condition 2: If a person’s birthday is going to be changed, then no other personal detail (i.e, person’s ID, firstName, lastName, address) can be changed.

        //Condition 3: If the first character/digit of a person’s ID is an even number, then their ID cannot be changed.

        //Instruction: If the Person's updated information meets the above conditions and any other conditions you may want to consider,
        //the Person's information should be updated in the TXT file with the updated information, and the updatePersonalDetails function should return true;
        //Otherwise, the Person's updated information should not be updated in the TXT file, and the updatePersonalDetails function should return false.

        return true;
    }

    public String addDemeritPoints()
    {
        //TODO: This method adds demerit points for a given person in a TXT file.

        //Condition 1: The format of the date of the offense should follow the following format: DD-MM-YYYY. Example: 15-11-1990

        //Condition 2: The demerit points must be a whole number between 1–6

        //Condition 3: If the person is under 21, the isSuspended variable should be set to true if the total demerit points within two years exceed 6.
        //If the person is over 21, the isSuspended variable should be set to true if the total demerit points within two years exceed 12.

        //Instruction: If the above conditions and any other conditions you may want to consider are met, the demerit points for a person should be inserted into the TXT file,
        //and the addDemeritPoints function should return “Success”. Otherwise, the addDemeritPoints function should return “Failed”.

        return "Success";
    }

    public static void main(String[] args) { // Moved inside the class and fixed indentation
        Person person1 = new Person("56s_d%&fAB", "John", "Citizen", "123|Example St|Melbourne|Victoria|Australia", "15-11-1990");
        if (person1.addPerson()) {
            System.out.println("Person added successfully.");
        } else {
            System.out.println("Failed to add person.");
        }
    }
}
