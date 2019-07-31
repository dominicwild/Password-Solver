package passwordpuzzle;

/**
 * 
 * This program aims to solve the following problem:
 * 
 * Your task is to decipher the code Find the correct password from seven words
 * that are displayed below. Attempts to guess the password have been made. Each
 * attempt has a number of letters that have matched the password. The number is
 * indicated in the table below. Given the information provided, what is the
 * password? 
 * 
 * All words must be the same length
 *
 * @author Dominic
 */
public class PasswordPuzzle {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Attempt[] attempts = new Attempt[]{
            new Attempt("hire",2),
            new Attempt("lake",2)
        };
        
        String[] passwords = new String[]{
            "hire","hero","lime","lake","type","late","barn"
        };
        
        PasswordSolver solver = new PasswordSolver(passwords,attempts);
        solver.solve();
        
        attempts = new Attempt[]{
            new Attempt("behold",3),
            new Attempt("simple",4)
        };
        
        passwords = new String[]{
            "insect","engage","stride","repeat","insole","simple","behold"
        };
    
        solver = new PasswordSolver(passwords,attempts);
        solver.solve();
        
        attempts = new Attempt[]{
            new Attempt("blond",3),
            new Attempt("smoke",3)
        };
        
        passwords = new String[]{
            "blind","blond","smoke","drawn","taste","forum","match","lemon"
        };
    
        solver = new PasswordSolver(passwords,attempts);
        solver.solve();
    }
    
}
