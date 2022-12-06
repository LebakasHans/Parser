package net.htlgkr.wintersteigerJ.Parser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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
        ExecutorService executor = Executors.newFixedThreadPool(lines.size());
        List<ParserThread> parserThreads = new ArrayList<>();
        List<Future<List<String>>> futures;

        for(int i = 0; i < lines.size(); i++){
            parserThreads.add(new ParserThread(lines.get(i)));
        }

        try {
            futures = executor.invokeAll(parserThreads);


        for (Future<List<String>> future : futures){
            future.get()
                    .stream()
                    .filter(s -> !s.isEmpty())
                    .forEach(System.out::println);
        }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        executor.shutdown();
    }
}