package net.htlgkr.wintersteigerJ.Parser;

import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Parser{
    public final String FILENAME;
    List<String> lines = new ArrayList<>();

    public Parser(String filename) {
        this.FILENAME = filename;
        readFile();
    }

    private void readFile(){
        try(BufferedReader reader = new BufferedReader((new InputStreamReader(new FileInputStream(FILENAME))))){
            lines = reader.lines().collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void parse(){
        /*
        ExecutorService executor = Executors.newFixedThreadPool(lines.size());

        for(int i = 0; i < lines.size(); i++){
            executor.execute(new ParserThread(lines.get(i)));
        }
        TODO remove comment
         */

        String test = recursivelyParse(3);
        System.out.println(test);
    }


    //TODO make Thread
    public String recursivelyParse(int startIndexInLine){
        String line = lines.get(3); //TODO delete
        String output = "";
        int counter = startIndexInLine;

        while(counter < line.length()){
            char c = line.charAt(counter);
            String s = "";
            while (c != '<'){
                s += c;
                counter++;
                c = line.charAt(counter);
            }
            output += s;

            if(line.charAt(counter+1) == '/'){
                return output;
            }else{
                counter++;
                c = line.charAt(counter);
                String nextTag = "";
                while (c != '>'){
                    nextTag += c;
                    counter++;
                    c = line.charAt(counter);
                }
                var temp = line.charAt(counter);
                String recursiveOutput = recursivelyParse(counter+1);
                output += recursiveOutput;
                counter = counter + 3 + nextTag.length()*2 + recursiveOutput.length();
            }
        }

        return output;
    }
}