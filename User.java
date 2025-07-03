public class User{
   //private fields for class program
   private String name;
   private String accountNumber;
   private String pin;
   private double balance;

   //full-arg/4-arg constructor for class
   public User(String name, String accountNumber, String pin, double balance){
      //assigning variables
      this.name = name;
      this.accountNumber = accountNumber;
      this.pin = pin;
      this.balance = balance;
   }
   //class getters for all 4 fields
   public String getName(){
      return name;
   }
   public String getAccountNumber(){
      return accountNumber;
   }
   public String getPin(){
      return pin;
   }
   public double getBalance(){
      return balance;
   }
   //deposit method that shows logic of money being added through the ATM
   public void deposit(double amount){
      if(amount > 0){
         balance += amount; 
      }
      else{
         System.out.print("the amount you deposited is invalid, please try again");
      }
   }
   //withdraw method that shows logic of how money is taken out of the ATM
   public boolean withdraw(double amount){
   boolean success;

      if(amount > 0 && amount <= balance){
         balance -= amount;
         success = true;
      }
      else{
         success = false;
      }
      return success;

   }
}