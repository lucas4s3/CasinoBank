
public class Person {
    private static String firstName;
    private static String lastName;
    private  static String dateOfBirth;
    private static int height;
    private static int weight;

    public Person(String firstName, String lasstName, String datOfBirth, int height, int weight) {
        Person.firstName = firstName;
        Person.lastName = lasstName;
        Person.dateOfBirth = datOfBirth;
        Person.height = height;
        Person.weight = weight;
    }

    public String getFullName() {
        return Person.firstName + " " + Person.lastName;
    }

    public int getAge(){
        String age = Person.dateOfBirth;
        return 0;
    }
}
