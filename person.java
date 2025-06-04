import java.util.HashMap;
import java.io.FileWriter;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class person {

    private String personID;
    private String firstName;
    private String lastName;
    private String address;
    private String birthdate;
    private HashMap<Date, Integer> demeritPoints; 
    private boolean isSuspended;


    public void setPersonID(String personID) {
        this.personID = personID;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }
    
    public boolean isSuspended() {
        return isSuspended;
    }
    

    public void addPerson() {

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

    public String addDemeritPoints(String offenseDateStr, int points) {
        try {
            if (points < 1 || points > 6) return "Failed";
    
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date offenseDate = sdf.parse(offenseDateStr);
            Date birth = sdf.parse(birthdate);
    
            Calendar now = Calendar.getInstance();
            Calendar bday = Calendar.getInstance();
            bday.setTime(birth);
    
            int age = now.get(Calendar.YEAR) - bday.get(Calendar.YEAR);
            if (now.get(Calendar.DAY_OF_YEAR) < bday.get(Calendar.DAY_OF_YEAR)) {
                age--;
            }
            if (age < 0) return "Failed";
    
            if (demeritPoints == null) demeritPoints = new HashMap<>();
    
            Calendar limit = Calendar.getInstance();
            limit.add(Calendar.YEAR, -2);
            int total = points;
    
            for (Date d : demeritPoints.keySet()) {
                if (!d.before(limit.getTime())) {
                    total += demeritPoints.get(d);
                }
            }
    
            if ((age < 21 && total > 6) || (age >= 21 && total > 12)) {
                isSuspended = true;
            }
    
            demeritPoints.put(offenseDate, points);
    
            FileWriter fw = new FileWriter("demerit_log.txt", true);
            fw.write(personID + "," + offenseDateStr + "," + points + "," + (isSuspended ? "Suspended" : "Active") + "\n");
            fw.close();
    
            return "Success";
        } catch (Exception ex) {
            return "Failed";
        }
    }
}    