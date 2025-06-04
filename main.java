public class main {
    public static void main(String[] args) {
        person p = new person();

        p.setPersonID("56s_d%&fAB");
        p.setFirstName("John");
        p.setLastName("Doe");
        p.setAddress("32|Highland Street|Melbourne|Victoria|Australia");
        p.setBirthdate("15-11-2005");  

        String result1 = p.addDemeritPoints("01-05-2024", 4);
        System.out.println("Result 1: " + result1);

        String result2 = p.addDemeritPoints("01-06-2024", 3);
        System.out.println("Result 2: " + result2);

        System.out.println("Is Suspended: " + p.isSuspended());  
    }
}
