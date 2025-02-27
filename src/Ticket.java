import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Ticket {
    private String row;
    private int seatNumber;
    private int price;
    private Person person;

    //Constructor to initialize ticket information
    public Ticket(int row, int seatNumber,int price, Person person){
        this.row = String.valueOf((char)(row + 64));
        this.seatNumber = seatNumber;
        this.price = price;
        this.person = person;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public int getSeatNum() {
        return seatNumber;
    }

    public void setSeatNum(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String print_tickets_info(){
        return "Ticket" +
                "row =" + getRow() +
                ", seatNumber =" + getSeatNum() +
                ", price=" + getPrice() + "\n" +
                "Person info: \n" +
                getPerson().printInfo();
    }

    //Method to save reservation details to a text file
    public void save() {
        String fileName = getRow() + getSeatNum() + ".txt";
        File directory = new File("Ticket Book", fileName);

        File file = directory.getParentFile();
        if(!file.exists()) {
            file.mkdir();
        }
        try(FileWriter writer = new FileWriter(directory)) {
            writer.write(print_tickets_info());
        } catch (IOException e) {
            System.out.println("Data write failed");
        }
    }

    // A1 = A1.txt B1 = B1.txt
    //Method to delete cancelled ticket details
    public void deleteFile() {
        String filename = getRow() + getSeatNum() + ".txt";

        File file = new File("Ticket book", filename);
        if(file.exists()) {
            file.delete();
        } else {
            System.out.println("File not found");
        }
    }
}
