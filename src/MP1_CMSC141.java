import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author czarinarae
 */
public class MP1_CMSC141 {
    
    int PC;
    FileWriter fw;
    
    public static void main(String[] args) {
        try {
            MP1_CMSC141 mp = new MP1_CMSC141();
            
            // file reading and initialization of input
            Scanner sc = new Scanner(new File("mp1.in"));
            String in[] = sc.nextLine().split(" ");
            int[] input = new int[in.length];
            
            // code to convert input to its integer equivalent
            for(int i = 0; i < in.length; i++){
                input[i] = Integer.valueOf(in[i]);
            }
                        
            //to retrieve all instructions in file
            //store in an arraylist (dynamic)
            ArrayList<String> st = new ArrayList();
            while (sc.hasNext()){
                st.add(sc.nextLine());
            }
            
            //to ready array for executing instructions
            char[] code = new char[st.size()+1]; // contains 1 letter chars to
                                //denote type of instruction
            int[][] indexes = new int[st.size()+1][3];
            
            //code to insert values into code and indexes array
            //values from initially read file
            for (int i = 1; i <= st.size(); i++){
                String[] split = st.get(i-1).split(" ");
                code[i] = split[0].charAt(0);
                if (code[i]!='J')
                    indexes[i][0] = Integer.valueOf(split[1]);
                else{
                    indexes[i][0] = Integer.valueOf(split[1]); 
                    indexes[i][1] = Integer.valueOf(split[2]);
                    indexes[i][2] = Integer.valueOf(split[3]);
                }
             }
            

                //printing entire instructions
//            for (int i = 1; i < code.length; i++){
//                int j = 0;
//                System.out.print(code[i] + " ");
//                if (code[i]!='J')
//                    System.out.println(indexes[i][0]);
//                else{
//                    System.out.println(indexes[i][0] + " " +  indexes[i][1] + " " + indexes[i][2]);
//                }
//            }
            try { 
                mp.fw = new FileWriter("mp1.out");
                mp.PC = 1;
                mp.fw.append(mp.printValues(input) + "\n");
                while (mp.PC < code.length){
                    input = mp.execute(code, indexes, input);
                    mp.fw.append(mp.printValues(input) + "\n");
                }
                mp.fw.close();
            } catch (IOException ex) {
                System.out.println("Unable to create output file!");
            }
            

            
            
            
            
        } catch (FileNotFoundException ex) {
            
            System.out.println("File Not Found!");
            
        }
    }
    
    public String printValues (int[] input){
        String s = ""; 
        for(int a : input)
            s += (a + " ");
        return s;
    }
    
    public int[] execute(char[] code, int[][] indexes, int[] values) throws IOException{
        if (code[PC] == 'S'){
            values[indexes[PC][0]]++;
            PC++;
        }
        else if (code[PC] == 'Z'){
            values[indexes[PC][0]]=0;
            PC++;
        }
        else if (code[PC] == 'C'){
            int temp = values[indexes[PC][0]];
            values[indexes[PC][0]] = values[indexes[PC][1]];
            values[indexes[PC][1]] = temp;
            PC++;
        }
        else if (code[PC] == 'J'){
            if (values[indexes[PC][0]] == values[indexes[PC][1]]){
                PC = indexes[PC][2];
            }
            else{
                PC++;
            }
        }
        return values;
    }
    
}
