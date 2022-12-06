package net.htlgkr.wintersteigerJ.Parser;

public class Main {

    public static void main(String[] args){
        Parser parser = new Parser("simple1.txt");
        parser.parse();
        System.out.println("----------------------------------------------------------------");
        parser = new Parser("sample1.html");
        parser.parse();
        System.out.println("----------------------------------------------------------------");
        parser = new Parser("sample2.html");
        parser.parse();
        System.out.println("----------------------------------------------------------------");
        parser = new Parser("sample3.html");
        parser.parse();
        System.out.println("----------------------------------------------------------------");
        parser = new Parser("nothing.html");
        parser.parse();
        System.out.println("----------------------------------------------------------------");
        parser = new Parser("advanced.html");
        parser.parse();
    }
}