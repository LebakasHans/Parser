package net.htlgkr.wintersteigerJ.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class ParserThread implements Callable<List<String>> {
    private String line;
    private List<String> parsedLines = new ArrayList<>();
    private int currentIndexInLine;

    public ParserThread(String line) {
        this.line = line.trim();;
        currentIndexInLine = indexAfterFirstTag();
    }

    @Override
    public List<String> call() throws Exception {
        recursivelyParse(0);
        return parsedLines;
    }

    private int indexAfterFirstTag(){
        line = line.trim();
        if(line.startsWith("<")) {
            char c = ' ';
            int counter = 0;

            while (c != '>') {
                c = line.charAt(counter);
                counter++;
            }
            return counter;
        }else{
            return 0;
        }
    }

    public List<String> recursivelyParse(int index){
        if(index >= parsedLines.size()){
            parsedLines.add("");
        }

        String output = "";

        while(currentIndexInLine < line.length()){
            boolean inQuotes = false;
            char c = line.charAt(currentIndexInLine);
            while (c != '<' || inQuotes){
                output += c;
                currentIndexInLine++;
                if(currentIndexInLine >= line.length()){
                    parsedLines.set(index, parsedLines.get(index) + output);
                    return parsedLines;
                }
                c = line.charAt(currentIndexInLine);
                if(c == '"'){
                    inQuotes = true;
                }else if(c == '"'){
                    inQuotes = false;
                }
            }
            currentIndexInLine++;
            if(line.charAt(currentIndexInLine) != '/'){
                c = line.charAt(currentIndexInLine);
                int tagLength = 0;
                while (c != '>'){
                    tagLength++;
                    currentIndexInLine++;
                    c = line.charAt(currentIndexInLine);
                }
                currentIndexInLine++;
                recursivelyParse(parsedLines.size());
            }else{
                c = line.charAt(currentIndexInLine);
                while (c != '>'){
                    currentIndexInLine++;
                    c = line.charAt(currentIndexInLine);
                }
                currentIndexInLine++;
                parsedLines.set(index, parsedLines.get(index) + output);
                return parsedLines;
            }
        }
        return parsedLines;
    }
}
