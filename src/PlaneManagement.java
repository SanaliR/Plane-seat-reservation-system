import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;

public class PlaneManagement {
    // A-14 B-12 C-12 D-14
    // using ragged array
    private static final int[][] seat = new int[4][];
            static {
                seat[0] = new int[14];
                seat[1] = new int[12];
                seat[2] = new int[12];
                seat[3] = new int[14];
            }
    private static final Ticket[][] Tickets = new Ticket[4][];
            static{
                Tickets[0] = new Ticket[14];
                Tickets[1] = new Ticket[12];
                Tickets[2] = new Ticket[12];
                Tickets[3] = new Ticket[14];
            }
            static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        File file = new File("Ticket Book");
        if(file.exists()){
            String[] subFiles = file.list();
            for(String fileName : subFiles){
                File subFile = new File(file.getPath(),fileName);
                subFile.delete();
            }
        }else System.out.println("Previous file not found!");

        // Call the method to print menu
        while (true){
            printMenu();
            try {
                int option = scanner.nextInt();

                switch (option) {
                    case 1:
                        System.out.println("Buy seat");
                        buy_seat();
                        break;
                    case 2:
                        System.out.println("Cancel seat");
                        cancel_seat();
                        break;
                    case 3:
                        System.out.println("Find first seat available");
                        find_first_available();
                        break;
                    case 4:
                        System.out.println("Show the seating plan \n");
                        show_seating_plan();
                        break;
                    case 5:
                        System.out.println("Print tickets");
                        print_tickets_info();
                        break;
                    case 6:
                        System.out.println("Search tickets");
                        search_ticket();
                        break;
                    case 0:
                        System.out.println("Quit");
                        System.exit(0);
                    default:
                        System.out.println("invalid option!!!");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Enter a valid option!!");
                scanner.next();
            }
        }
    }

    public static void printMenu() {
        System.out.println("\n -------- Welcome to the Plane Management application! -------- \n");
        System.out.println("*".repeat(51));
        System.out.println("*" + " ".repeat(19)+ "MENU OPTIONS" + " ".repeat(19)+ "*" );
        System.out.println("*".repeat(51));
        System.out.println("\t1) Buy a seat");
        System.out.println("\t2) Cancel a seat");
        System.out.println("\t3) Find first available seat");
        System.out.println("\t4) Show seating plan");
        System.out.println("\t5) Print tickets information and total sales");
        System.out.println("\t6) Search tickets");
        System.out.println("\t0) Quit \n");

        System.out.print("Please select an option:");
    }

    public static String nameValidation(String promptMessage){
        boolean valid = false;
        String name;
        while(true) {
            System.out.print(promptMessage);
            name = scanner.next();
            if(name != null){
                for (int i = 0; i < name.length(); i++) {
                    char letter = name.toUpperCase().charAt(i);
                    if(!(letter >= 'A' && letter <= 'Z')){
                        System.out.println("Invalid name, enter again!");
                        valid = true;
                        break;
                    }else {
                        valid = false;
                        break;
                    }
                }
            }
            if(!valid) break;
        }
        return name;
    }
    public static Person getPersonInfo() {
        String firstName = nameValidation("Enter your first name: ");

        String surname = nameValidation("Enter your surname: ");

        String email;
        while(true) {
            System.out.print("Enter your email: ");
            email = scanner.next();
            if (!email.contains("@")) {
                System.out.println("Enter a valid email");
                continue;
            }
            if(!email.contains(".")) {
                System.out.println("Check again");
                continue;
            }
            break;
        }
        return new Person(firstName, surname, email);
    }

    public static void reserveTicket(int row, int seatNumber, Person person) {
                Ticket ticket = new Ticket(row, seatNumber, getPrice(seatNumber), person);
                Tickets[row - 1][seatNumber - 1] = ticket;
                ticket.save();
    }

    //getPrice(); = seat no 1 - 200
    public static int getPrice(int seatNum) {
                int price = 0;
                if(seatNum >= 1 && seatNum <= 5) {
                    price = 200;
                } else if(seatNum >= 6 && seatNum <= 9) {
                    price = 150;
                } else if(seatNum >= 10 && seatNum <= 14) {
                    price = 180;
                }
                return price;
    }
    
    public static int getRowNum(){
        int rowNum;
        while(true) {
            System.out.print("Enter a row letter: ");
            String row = scanner.next().toUpperCase();
            rowNum = row.charAt(0) - 64;
            if (!(rowNum >= 1 && rowNum <= 4)) {
                System.out.println("Invalid Row number!!");
                continue;
            }
            // row letter = A ..... 65 ...... A; 1
            // A 1st row    A = 65-64 : 1-1 = 0
            // B 2nd row    B = 66-64 : 2-1 = 1
            // C 3rd row    C = 67-64 : 3-1 = 2
            // D 4th row    D = 68-64 : 4-1 = 3

            break;
        }
        return rowNum;
    }

    public static int getSeatNum(int rowNum){
        int seatNum;
        while(true) {
            System.out.print("Enter a seat number: ");
            seatNum = scanner.nextInt();
            if(rowNum == 1 || rowNum == 4) {
                if(!(seatNum >= 1 && seatNum <= 14)) {
                    System.out.println("Invalid seat number");
                    continue;
                }
            }
            if(rowNum == 2 || rowNum == 3) {
                if(!(seatNum >= 1 && seatNum <= 12)) {
                    System.out.println("Invalid seat number");
                    continue;
                }
            }
            break;
        }
        return seatNum;
    }

    public static void buy_seat() {
        while (true) {
            int rowNum = getRowNum();
            int seatNum = getSeatNum(rowNum);
            if (seat[rowNum - 1][seatNum - 1] == 1) {
                System.out.println("Seat already sold");
                continue;
            }
            Person buyer = getPersonInfo();
            reserveTicket(rowNum, seatNum, buyer);
            //now value = 0 (available...... value update 0 - 1)
            seat[rowNum - 1][seatNum - 1] = 1;
            System.out.println("Seat sold successfully");
            break;
        }
    }

    public static void cancel_seat() {
        while (true) {
            int rowNum = getRowNum();
            int seatNum = getSeatNum(rowNum);

            if (seat[rowNum - 1][seatNum - 1] == 0) {
                System.out.println("Seat not sold");
                continue;
            }
            Tickets[rowNum - 1][seatNum -1].deleteFile();
            Tickets[rowNum - 1][seatNum - 1] = null;
            //value 1.... sold.... value replace 1-0
            seat[rowNum - 1][seatNum - 1] = 0;
            System.out.println("Seat cancelled successfully");
            break;
        }
    }

    public static void find_first_available() {
        int i, j = 0;
        boolean isSeatFound = false;
        for (i = 0; i < seat.length; i++) {
            for (j = 0; j < seat[i].length; j++) {
                if (seat[i][j] == 0) {
                    isSeatFound = true;
                    break;
                }
            }
            //while(true)
            if (isSeatFound) {
                break;
            }
        }
        if (!isSeatFound) {
            System.out.println("All seats booked");
            return;
        }
        //j = 0 = seatNum
        //i = row letter('A','B','C','D')
        String rowNum = String.valueOf((char) (i + (int) 'A'));
        System.out.println(rowNum + ++j);
        //boolean false
    }

    public static void show_seating_plan() {
                //rows = 4
                //seat numbers = 14,12
                for (int i = 0; i < seat.length; i++) {
                    for (int j = 0; j < seat[i].length; j++) {
                        System.out.print(seat[i][j] == 0 ? " O" : " X"); // 0 or 1
                        //A1 = A = i = [0],,,, 1 = j = [0]
                    }
                    System.out.println();
                }
    }

     public static void print_tickets_info() {
        for(Ticket[] tickets : Tickets) {   //rows
            for(Ticket ticket : tickets) {
                if(ticket != null) {
                    System.out.println(ticket.print_tickets_info());
                    System.out.println("-".repeat(10));
                }
            }
        }
     }

     public static void search_ticket() {
        int rowNum = getRowNum();
        int seatNumber = getSeatNum(rowNum);
        if(seat[rowNum - 1][seatNumber - 1] == 1) {
            System.out.println("Seat is not available");
            Ticket ticket = Tickets[rowNum - 1][seatNumber - 1];
            System.out.println(ticket.print_tickets_info());
        } else {
            System.out.println("Seat is available");
        }
     }
}
