import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MP2_CMSC141 {
    
    public static void main(String[] args) throws IOException {
        
        try {
            // file reading and initialization of input
            Scanner sc = new Scanner(new File("mp2.in"));
            MP2_CMSC141 mp2 = new MP2_CMSC141();
            int count = 0;
            
            FileWriter fw = new FileWriter("mp2.out");
            
            while (sc.hasNext()){
                    String input = sc.nextLine();
                    String s = mp2.checkGood(input, "*MLCR_*", 0); // input starts with all elements on the left of _
                    //System.out.println(input + " " +s);
                    fw.append(s + "\n");
            }
            System.out.println(count);
            fw.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MP2_CMSC141.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public String checkGood(String input, String currentVal, int across){
        if (currentVal.matches(	"^\\*+\\_+[M,C,R,L]{4,}+\\*") && input.isEmpty()) // if input string is empty and has all four elements on the right of the bridge, "OK"
            return "OK"; 
        else if (input.isEmpty()) // empty string w/o complete elements
            return "NG";
        else if (currentVal.contains("_RC*") || currentVal.contains("_CR*") || currentVal.contains("_LR*") || currentVal.contains("_RL*")) // error checking right side
            return "NG";
        else if (currentVal.contains("*RC_") || currentVal.contains("*CR_") || currentVal.contains("*LR_") || currentVal.contains("*RL_")) // error checking left side
            return "NG";
        else if (currentVal.matches("^\\*+[M]+\\_+[C,R,L]{3,}+\\*") || currentVal.matches("^\\*[C,R,L]{3,}+\\_+[M]\\*")) // food chain error checking
            return "NG";
        else{
            currentVal = currentVal.replace("M", ""); // remove man
            if (input.charAt(0) == 'N'){
                currentVal = across==0?(currentVal.substring(0, currentVal.length()-1)+"M*"):("*M"+(currentVal.substring(1, currentVal.length()))); // move man across
            }
            else{
                String curr = String.valueOf(input.charAt(0)); // get instruction
                if(across==0 && (currentVal.indexOf(curr)>currentVal.indexOf("_"))){ //check for errors (if item is at the correct side of bridge)
                    return "NG";
                }
                if(across==1 && (currentVal.indexOf(curr)<currentVal.indexOf("_"))){ //check for errors (if item is at the correct side of bridge)
                    return "NG";
                }
                currentVal = currentVal.replace(curr, ""); // remove current item to be moved
                currentVal = across==0?(currentVal.substring(0, currentVal.length()-1)+curr+"M*"):("*M"+curr+(currentVal.substring(1, currentVal.length())));                
                // if it is across, move M along with current item to the opposit side.
            }
            return checkGood(input.substring(1), currentVal, across==1?0:1); // recursive checking
        }
    }
    
}
