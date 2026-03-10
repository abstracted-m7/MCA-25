/*
Q28. Create a Java application that performs statistical analysis on a block of text entered by the user. The program should process the input string to determine the total number of words, vowels, digits, and special characters present in the text. The design should emphasize efficient string traversal and proper use of Java’s built-in string handling capabilities to generate accurate textual statistics. 
*/

import java.util.Scanner;

public class TextAnalyzer{

    void analyzerText(String text){
        int vowelCount = 0, wordsCount = 0, digitCount = 0, spacialCharCount = 0;

        // count words using split methods
        String words[] = text.trim().split("\\s+");
        wordsCount = words.length;

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);

            char lowerCase = Character.toLowerCase(ch);

            if (lowerCase == 'a' || lowerCase == 'e'||lowerCase == 'i'||lowerCase == 'o'||lowerCase == 'u') {
                vowelCount++;
            }else if (Character.isDigit(ch)) {
                digitCount++;
            }else if (!Character.isLetter(ch) && !Character.isWhitespace(ch)) {
                spacialCharCount++;
            }
            
        }
        //display
        System.out.println("\n===Statistical Results===");
        System.out.println("Total Words: "+wordsCount);
        System.out.println("Total Vowels: "+vowelCount);
        System.out.println("Total Digits: "+digitCount);
        System.out.println("Total Special Character: "+spacialCharCount);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the Text for analyze: ");
        String text = sc.nextLine();

        TextAnalyzer analyze = new TextAnalyzer();
        analyze.analyzerText(text);
    }
}