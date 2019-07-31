package passwordpuzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author Dominic
 */
public class PasswordSolver {

    private Attempt[] attempts;                 //Passwords attempted
    private ArrayList<String> options;          //All possible passwords
    private int possibilityCount = 0;           //Count of possibilities

    public PasswordSolver(String[] options, Attempt[] attempts) {
        this.attempts = attempts;
        this.options = new ArrayList<>(Arrays.asList(options));
    }

    /**
     * Removes the attempted passwords from the list under consideration
     */
    private void cleanData() {
        for (Attempt attempt : this.attempts) {
            String password = attempt.getAttemptedPassword();
            if (this.options.contains(password)) {
                this.options.remove(password);
            }
        }
    }

    public void solve() {
        this.cleanData();
        this.findPassword(attempts, 0, new ArrayList<>());
    }

    public boolean findPassword(Attempt[] attempts, int attempt, ArrayList<int[]> correctPositions) {

        if (correctPositions.size() == attempts.length) {
            int solution = hasSolution(correctPositions);
            if (solution != -1) {
                System.out.println("The correct password is: " + this.options.get(solution));
                return true;
            }
            return false;
        }

        Attempt currentAttempt = attempts[attempt];

        for (int[] array : currentAttempt.getAttemptPermutations()) {
            ArrayList<int[]> clonePositions = (ArrayList<int[]>) correctPositions.clone();
            clonePositions.add(array);
            findPassword(attempts, attempt + 1, clonePositions);
        }

        return false;
    }

    /**
     * Checks if the correct letters can create a word, where position matters.
     *
     * @param positions The positions at which the correct letter is guessed to
     * be.
     * @return The index of the solution, if it exists, if not return -1.
     */
    private int hasSolution(ArrayList<int[]> positions) {

        ArrayList<Character> possibleCharacters = new ArrayList<>();
        HashMap<Character, Integer> maxMap = new HashMap<>(); //Keeps track of amount of correct letters we have to filter out duplicates. I.e., late and hire. If we guess we, we don't want to add e twice to the possible letters.

        for (int i = 0; i < positions.size(); i++) {
            int[] posArray = positions.get(i);
            HashMap<Character, Integer> trackMap = new HashMap<>();
            for (int pos : posArray) { //Index in array list corresponds to index in attempts, use this to get character at that position in the attempt
                char c = this.attempts[i].getAttemptedPassword().charAt(pos);
                int count = trackMap.getOrDefault(c, 0);
                trackMap.put(c, count++);
                if (count > maxMap.getOrDefault(c, 0)) { //Ensure current word exceeds the max correct letter amount
                    possibleCharacters.add(c);
                    maxMap.put(c, count);
                }
            }
        }

        ArrayList<Character> possibleCopy = (ArrayList<Character>) possibleCharacters.clone();

        for (int i = 0; i < this.options.size(); i++) {
            String password = options.get(i);
            for (int j = 0; j < password.length(); j++) {
                char checkChar = password.charAt(j);
                if (possibleCharacters.contains(checkChar)) {
                    possibleCharacters.remove((Character) checkChar);
                }
            }
            if (possibleCharacters.isEmpty()) {
                return i;
            } else {
                possibleCharacters = (ArrayList<Character>) possibleCopy.clone();
            }
        }

        return -1;
    }

    /**
     * Checks if the correct letters can create a word, where position matters.
     *
     * @param positions The positions at which the correct letter is guessed to
     * be.
     * @return The index of the solution, if it exists, if not return -1.
     */
    private int hasSolution2(ArrayList<int[]> positions) {

        char[] correctPassword = new char[this.attempts[0].getAttemptedPassword().length()];
        Arrays.fill(correctPassword, '?');

        for (int i = 0; i < positions.size(); i++) {
            int[] posArray = positions.get(i);
            for (int pos : posArray) { //Index in array list corresponds to index in attempts, use this to get character at that position in the attempt
                correctPassword[pos] = this.attempts[i].getAttemptedPassword().charAt(pos);
            }
        }

        System.out.println(correctPassword);

        for (int i = 0; i < this.options.size(); i++) {
            String password = options.get(i);
            for (int j = 0; j < password.length(); j++) {
                if (correctPassword[j] != '?') {
                    if (password.charAt(j) != correctPassword[j]) {
                        break;
                    }
                    if (j == password.length() - 1) { //We're on the last letter comparison
                        return i; //Return indice of found password
                    }
                }
            }
        }

        return -1;
    }

    public int[] concat(int[] first, ArrayList<int[]> other) {
        int totalLength = first.length;
        totalLength = other.stream().map((array) -> array.length).reduce(totalLength, Integer::sum); //Create total length needed to concatenate all arrays
        int[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (int[] array : other) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    private void printPossiblity(ArrayList<int[]> possibility) {
        String output = "";

        for (int i = 0; i < possibility.size(); i++) {
            output += "Attempt " + i + ": (";
            for (int pos : possibility.get(i)) {
                output += Integer.toString(pos) + ", ";
            }
            output = output.substring(0, output.length() - 2);
            output += ")" + System.lineSeparator();
        }

        System.out.println(output);
    }

//    /**
//     * 
//     * @param possibility Possibility to evaluate.
//     * @return Boolean of if its a valid possibility.
//     */
//    private boolean isValidPossibility(ArrayList<int[]> possibility){
//        int toCheck = possibility.size() - 1; //Number of iterations, don't need to check final int array as it will be redundent in the end.
//        
//        for(int i=0;i<toCheck;i++){ //Iterate all possible correct position sets
//            int[] correctPositions = possibility.get(i); //The correct position set to check
//
//            for (int j = 0; j < correctPositions.length; j++) { //Iterate over each position
//                int checkPos = correctPositions[j];
//                for (int k = i + 1; k < possibility.size(); k++) { //k=i+1 to not redundently check previous arrays or the same array we're checking
//                    for (int pos : possibility.get(k)) {
//                        if (pos == checkPos) {
//                            char checkChar = this.attempts[i].getAttemptedPassword().charAt(checkPos);
//                            char posChar = this.attempts[k].getAttemptedPassword().charAt(pos);
//                            if (checkChar != posChar) { //Ensure checking same position doesn't create a logical contradiction. If two letters at the same position are different, we have a contradiction
//                                return false;
//                            }
//                        }
//                    }
//                }
//            }
//            
//        }
//        
//        return true;
//    }
    //    private boolean isValidPossibility2(ArrayList<int[]> possibility){
//        int[] merged = concat(new int[0],possibility);
//        
//        for(int i=0;i<merged.length;i++){
//            for(int j=0;j<merged.length;j++){
//                if(i != j){
//                    if(merged[i] == merged[j]){
//                        return false;
//                    }
//                }
//            }
//        }
//       
//        return true;
//    }
}
