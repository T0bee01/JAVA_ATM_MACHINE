import java.io.*;
import java.util.*;
public class ATM {
    private static final String FILE_NAME = "users.txt";
    private static List<User> users = new ArrayList<>();
    private static Scanner input = new Scanner(System.in);

    public static void main(String[]args){
        //method that loads the users from file000
        loadUsers();
        //welcome message to user
        System.out.println("Welcome to the best ATM!");

        User currentUser = login();
        //if statement that says if there is no user and they are valid to go ahead and show meny and save the user to file after use.
        if(currentUser != null){
            showMenu(currentUser);//from show menu method
            saveUsers();//from save users method
        }
        else{
            //error message
            System.out.println("Too many failed tries... Exiting page...");
        }
    }
    //static method to load users from existing file
    private static void loadUsers(){
        //try catch to either load the user or start  new if the user is not found
        try(Scanner fileScanner = new Scanner(new File(FILE_NAME))){
            while(fileScanner.hasNextLine()){
                String[] parts = fileScanner.nextLine().split(",");
                //if statement to save each field to  the parts array
                if(parts.length == 4){
                    String name = parts[0].trim();
                    String accNo = parts[1].trim();
                    String pin = parts[2].trim();
                    double balance = Double.parseDouble(parts[3].trim());
                    users.add(new User(name, accNo, pin, balance));
                }
            }
        }
        catch(Exception ex){
            System.out.println("No user found, starting with a empty user list");
        }
    }
    //method to save users info to file
    private static void saveUsers(){
        //try-catch block to write the info to file and to throw an exception if unable to
        try(PrintWriter writer = new PrintWriter(FILE_NAME)){
            for(User user: users){
                writer.println(user.getName() + "," + user.getAccountNumber() + "," + user.getPin() + "," + user.getBalance());
            }
        }
        catch(Exception ex){
            System.out.println("Error saving user data: " + ex.getMessage());
        }
        }
    //method for logging in
    private static User login(){
        int attempts = 0;
        //while loop to ensure login is attempted correctly and if not  gives error message
        while(attempts < 3){
            System.out.println("Please enter Account Number: ");
            String accNo = input.nextLine().trim();
            System.out.println("please Enter PIN: ");
            String pin = input.nextLine().trim();
            //for loop for successful login attempt
            for(User user: users){
                if(user.getAccountNumber().equals(accNo) && user.getPin().equals(pin)){
                    System.out.println("Login Successful!, Welcome "+ user.getName() + "!");
                    return user;
                }
            }
            System.out.println("Incorrect login credentials, Try again!");
            attempts++;
        }
        return null;
    }
    
    private static void showMenu(User user){
        while(true){
            System.out.println("\n    ATM MENU    ");
            System.out.println("1. View Balance");
            System.out.println("2. Deposit funds");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. EXIT");
            System.out.println("Choose an option: ");

            String choice = input.nextLine();

            switch(choice){
                case "1":
                    System.out.println("Your current balance: $" + user.getBalance());
                    break;
                case "2": 
                    System.out.println("Enter deposit amount: ");
                    double depositAmount = Double.parseDouble(input.nextLine());    
                    user.deposit(depositAmount);
                    System.out.println("Deposit successful");
                    break;
                case "3":
                    System.out.println("Enter Withdrawal Amount: ");
                    double withdrawAmount = Double.parseDouble(input.nextLine());
                    if(user.withdraw(withdrawAmount)){
                        System.out.println("Withdrawal Successful");
                    }    
                    else{
                        System.out.println("Insufficient funds.");
                    }
                    break;
                case "4": 
                    System.out.println("Enter recipients account number: " );
                    String recAcc = input.nextLine();
                    User recipient = findUser(recAcc);
                    
                    if(recipient == null){
                        System.out.println("Account not found.");
                    }
                    else if(recipient == user){
                        System.out.println("Transfer to own account no allowed");
                    }
                    else{
                        System.out.println("Enter amount to transfer: ");
                        double amount = Double.parseDouble(input.nextLine());
                        if(user.withdraw(amount)){
                            System.out.println("Transfer Successful!");
                        }
                        else{
                            System.out.println("Insufficient Balance!, deposit some funds");
                        }
                    }
                    break;
                case "5": 
                    System.out.println("Thank you for using the ATM today, have a good day!");
                    return;
                default:
                System.out.println("Invalid option, try again.");    
            }
        }
    }
    private static User findUser(String accNo){
        for(User user: users){
            if(user.getAccountNumber().equals(accNo)){
                return user;
            }
        }
        return null;
    }
}
