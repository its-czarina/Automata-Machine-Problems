/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rule30Automata;

import java.util.Collections;
import java.util.Scanner;

/**
 *
 * @author czarinarae
 */
public class Rule30 {
    
    public static void main(String args[]){
        Rule30 program = new Rule30();
        program.toRule30(program.retrieveInput());
    }
    
    public void print(int[] output){
        for (int a:output)
            System.out.print(a + " ");
        System.out.println("");
    }
    
    public void toRule30(String[] input){
        int generations = Integer.parseInt(input[0]);
        input[1] = input[1].replace(" ", "");//remove unwanted spaces
        int columns = input[1].length(); //get length of string
        
        int[] output = new int[columns];
        for (int i = 0; i < output.length;i++){
            output[i] = Character.getNumericValue(input[1].charAt(i));
        }
        print(output);
        
        for (int i = 0; i < generations-1; i++){
            int[] test = getLine(output);
            for (int j = 0; j < columns; j++){
                int p = Integer.parseInt(""+test[j]);
                int q = Integer.parseInt(""+test[j+1]);
                int r = Integer.parseInt(""+test[j+2]);
                output[j] = ((p + q + r + (q*r))%2); // formula to get the equivalent tile
            }
            print(output);
        }
        print(output);
    }
    
    public String[] retrieveInput(){
        String[] output = new String[2];
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter the number of generations: ");
        output[0] = sc.nextLine();
        System.out.print("\nPlease enter the initial states: ");
        output[1] = sc.nextLine();
        return output;
    }

    private int[] getLine(int[] output) {
        int[] values = new int[(output.length) + 2];
        values[0] = output[output.length-1]; // add the last item to the first part of the array (wrapped)
        System.arraycopy(output, 0, values, 1, output.length);
        values[output.length+1] = output[0]; // first item repeated on the last of the array (wrapped)
        return values;
    }
    
}
