package passwordpuzzle;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Dominic
 */
public class Attempt {
    private String attemptedPassword;               //The password attempted
    private int correct;                            //The number of correct letter
    private ArrayList<int[]> attemptPermutations;   //Permutations of possible correct letters within this attempt
    
    public Attempt(String attempt, int correct){
        this.attemptedPassword = attempt;
        this.correct = correct;
        this.attemptPermutations = this.findArrangements(attempt.length(), correct);
    }
    
    private ArrayList<int[]> findArrangements(int n, int r){
        
        ArrayList<int[]> permutations = new ArrayList<>(); 
        int[] indices = new int[n];
        
        for(int i=0;i<n;i++){
            indices[i] = i;
        }
        
        for(int i=0;i<n;i++){
            ArrayList<Integer> currentPermutation = new ArrayList<>();
            currentPermutation.add(i);
            nCr(currentPermutation,n,r,i,permutations);
        }
        
        return permutations;
    }
    
    /**
     * Performs an n choose r function gathering indices permutations and adding them to the passed list (order doesn't matter). 
     * @param currentPermutation The current state of the built permutation.
     * @param n The number of items to choose from.
     * @param r The number of items to choose.
     * @param ignore The limit to ignore while traversing the tree.
     * @param list The list to add the discovered permutations to.
     * @return The number of the most recently visited node.
     */
    private int nCr(ArrayList<Integer> currentPermutation, int n ,int r, int ignore, ArrayList<int[]> list){
        if(currentPermutation.size() == r){ //If we've chose the max number of items, we return the current permutation. We're at a leaf node of the tree.
            list.add(arrayListToIntArray(currentPermutation));
            return currentPermutation.get(r-1);
        }
        
        for(int i=0;i<n;i++){ //Iterate over all other existing indices that give non-repeating permutations. Iterates over the remaining branches at this part of the node.
            if(i > ignore){
                ArrayList<Integer> currentClone = (ArrayList<Integer>) currentPermutation.clone();
                currentClone.add(i);
                ignore = nCr(currentClone,n,r,i,list);
            }
        }
        
        return currentPermutation.get(currentPermutation.size()-1); //Done exploring the node, returning the value at this node.
    }
    
    /**
     * Converts an ArrayList<Integer> into an integer array.
     * @param list
     * @return 
     */
    private int[] arrayListToIntArray(ArrayList<Integer> list){
        Iterator<Integer> it = list.iterator();
        int[] array = new int[list.size()];
        
        for(int i=0;i<list.size();i++){
            array[i] = it.next().intValue();
        }
        return array;
    }
    
    public String getAttemptedPassword() {
        return attemptedPassword;
    }

    public int getCorrect() {
        return correct;
    }

    public ArrayList<int[]> getAttemptPermutations() {
        return attemptPermutations;
    }

    
}
