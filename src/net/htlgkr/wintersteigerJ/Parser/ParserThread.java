package net.htlgkr.wintersteigerJ.Parser;

import java.util.ArrayDeque;

public class ParserThread implements Runnable{
    private String line;
    private ArrayDeque<String> tags = new ArrayDeque<String>();
    private String output;

    public ParserThread(String line) {
        this.line = line.trim();;
    }

    @Override
    public void run() {
        char c = ' ';
        String tag = "";
        int counter = 1;

        while(c != '>'){
            c = line.charAt(counter);
            tag += c;
            counter++;
        }
        recursivelyParse(tag, counter);
    }

    public String recursivelyParse(String tag, int charIndexInLine){
        String parsedLine = "";
        int counter = charIndexInLine;
        char c = ' ';

        while(c != '<'){
            c = line.charAt(counter);
            counter++;
        }

        if(line.charAt(counter) != '/')
        {
            String nextTag = "";
            while(c != '>')
            {
                c = line.charAt(counter);
                nextTag += c;
                counter++;
            }
            parsedLine += recursivelyParse(nextTag, counter) + "\n";
        }else
        {
            return line.substring(charIndexInLine, counter-1);
        }

        return parsedLine;
    }
}
