/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StarInvaders;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 *
 * @author Raith
 */
public class Score {
    
    Charset utf8 = StandardCharsets.UTF_8;
    
    public void writeScore(String name,String score){

        List<String> lines = Arrays.asList(name + " : " + score);
        
        try {
            Files.write(Paths.get("scoreList.txt"), lines, utf8,StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public List<String> readScore(){
        List<String> lines = Arrays.asList("I tak nie pobijesz :D");
        try {
             lines = Files.readAllLines(Paths.get("scoreList.txt"));
        } catch (IOException e) {
            System.out.println(e);
        }

        Collections.sort(lines, new Comparator<String>() {
            public int compare(String o1, String o2) {
                return extractInt(o1) - extractInt(o2);
            }

            int extractInt(String s) {
                String num = s.replaceAll("\\D", "");
                return num.isEmpty() ? 0 : Integer.parseInt(num);
            }
        });
        
        Collections.reverse(lines);
        
        return lines;
    }
}
