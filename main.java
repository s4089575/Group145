public class main {
    public static void main(String[] args) {
        person p = new person();

        // Set up test person
        p.setPersonID("56s_d%&fAB");
        p.setFirstName("John");
        p.setFirstName("Doe");
        p.setAddress("32|Highland Street|Melbourne|Victoria|Australia");
        p.setBirthdate("15-11-2007"); // Change to test age < 21 vs >= 21

        // Try adding demerit points
        String result = p.addDemeritPoints("15-05-2024", 4);
        System.out.println("Result 1: " + result);

        result = p.addDemeritPoints("12-06-2024", 3);
        System.out.println("Result 2: " + result);

        // Display suspension status
        System.out.println("Is Suspended: " + p.isSuspended());
    }
}
