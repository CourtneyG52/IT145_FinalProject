/*Courtney Gliszczynski
IT145
*/

//importing java functions needed for the program
import java.io.File;
import java.security.MessageDigest;
import java.util.Scanner;

//Defining file name
public class ZooAuthentication {
    
    public static void main(String[] args) throws Exception {
        Scanner scnr = new Scanner (System.in);
        
        //Define variables
        String userName = "";
        String passwordEntered = "";
        int passwordAttempt = 0;
        String record = "";
        String exitChoice = "";
        
        /*Start a while loop until there was a successful login or the max
        number of attempts have been met.*/
        while(true) {
            //Have user enter their username & password
            System.out.println("Enter username: ");
            userName = scnr.nextLine();
            System.out.println("Enter password: ");
            passwordEntered = scnr.nextLine();
            
            //Hash code inserted. Converts given password to hash password.
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(passwordEntered.getBytes());
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            //Assign boolean value for (un)successful login
            boolean loginSuccess = false;
            Scanner checkData = new Scanner (new File("credentials.txt"));
            
            while(checkData.hasNextLine()) {
                record = checkData.nextLine();
                //Splits the records in the file by seperating them by tab.
                String columns [] = record.split("\t");
                
                //Compares credentials in file with username inputted.
                if(columns[0].trim().equals(userName)) {
                    //Compares credentials in file with password inputted.
                    if(columns[1].trim().equals(sb.toString())) {
                        /*Sets the login Success to true because the username
                        and password are correct*/
                        loginSuccess = true;
                        
                        Scanner inputReader = new Scanner(new File(columns[3].trim()+".txt"));
                        
                        while(inputReader.hasNextLine()) {
                            //Outputs the corresponding message depending on user
                            System.out.println(inputReader.nextLine());
                        }
                        break;
                    }
                }
            }
            //Once logged in, this will prompt user if they want to logout.
            if(loginSuccess) {
                System.out.println("Would you like to log out?(y/n)");
                exitChoice = scnr.nextLine();
                
                //User chooses to logout
                if(exitChoice.toLowerCase().charAt(0)=='y') {
                    System.out.println("You have successfully logged out.");
                    break;
                }
                else {
                    loginSuccess = false;
                }
            }
            
            //If user inputted incorrect username/password.
            else {
                //Adds 1 to the number of attempts
                passwordAttempt = (passwordAttempt + 1);
                
                //Once password attempts reach 3, it will log user out
                if(passwordAttempt==3) {
                    System.out.println("Maximum login attempts reached.");
                    System.out.println("Exiting Program.");
                    
                    break;
                }
                /*Attempts haven't been reached, prompt user to re-enter correct
                credentials*/
                else {
                    System.out.println("Incorrect username/password. Please try again.");
                }
            }

        }
    }
    
}
