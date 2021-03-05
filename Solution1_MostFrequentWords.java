package com.shuchi.globalsign;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
 

public class Solution1_MostFrequentWords {
    public static void main(String[] args) throws IOException {

    	File file=new File("C:\\Users\\Shuchi.Sinha\\Desktop\\My Assignment\\mobydick.txt");
		//path where the text file is located
		 StringBuilder fileContents = new StringBuilder((int)file.length());        

		    try (Scanner scanner = new Scanner((Readable) new BufferedReader(new FileReader(file)))) {
		        while(scanner.hasNextLine()) {
		            //fileContents.append(scanner.nextLine() + System.lineSeparator());
		        	fileContents.append(scanner.nextLine()).append(System.lineSeparator()); 
		        }
	  
		       String content=fileContents.toString().toLowerCase();
         Pattern r = Pattern.compile("\\p{javaLowerCase}+");
        Matcher matcher = r.matcher(content);
        Map<String, Integer> freq = new HashMap<>();
        while (matcher.find()) {
            String word = matcher.group();
            Integer current = freq.getOrDefault(word, 0);
            freq.put(word, current + 1);
        }
         List<Map.Entry<String, Integer>> entries = freq.entrySet()
            .stream()
            .sorted((i1, i2) -> Integer.compare(i2.getValue(), i1.getValue()))
            .limit(20)
            .collect(Collectors.toList());
      
        for (Map.Entry<String, Integer> entry : entries) {
            String word = entry.getKey();
            Integer count = entry.getValue();
            System.out.printf(" %5d   %-4s    \n",  count, word);
        }
    }
    }
}