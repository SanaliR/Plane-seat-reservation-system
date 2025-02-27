public class Person {
    private String name;
    private String surname;
    private String email;  //declare
    //instance variables

    public Person(String name1, String surname1, String email1) {
        this.name = name1;
        this.surname = surname1;
        this.email = email1;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String printInfo() {
        return "First name: " + getName() + "\n" +
                "surname: " + getSurname() + "\n" +
                "Email: " + getEmail();
    }
}
