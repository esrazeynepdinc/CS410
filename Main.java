import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
public class Main {


    static ArrayList<String> alphabet = new ArrayList<>();
    static ArrayList<String> states = new ArrayList<>();
    static ArrayList<String> start = new ArrayList<>();
    static ArrayList<String> finalState = new ArrayList<>();
    static ArrayList<String> transitions = new ArrayList<>();
    public static String filename = "NFA1.txt";


    public static void main(String[] args) throws IOException {

        File file = new File(filename);
        Scanner scan = new Scanner(file);


        String deneme = scan.nextLine();
        if (deneme.equals("ALPHABET")) {
            String parse = scan.nextLine();
            while (!parse.equals("STATES")) {
                //  System.out.println(parse);
                alphabet.add(parse);
                parse = scan.nextLine();

            }
            //   System.out.println("---------------");
            String stateLine = scan.nextLine();
            while (!stateLine.equals("START")) {
                // System.out.println(stateLine);
                states.add(stateLine);
                stateLine = scan.nextLine();

            }
            //   System.out.println("---------------");
            String startLine = scan.nextLine();
            while (!startLine.equals("FINAL")) {
                //  System.out.println(startLine);
                start.add(startLine);
                startLine = scan.nextLine();

            }
            //  System.out.println("---------------");
            String finalLine = scan.nextLine();
            while (!finalLine.equals("TRANSITIONS")) {

                // System.out.println(finalLine);
                finalState.add(finalLine);
                finalLine = scan.nextLine();

            }
            //  System.out.println("---------------");
            String transitionsLine = scan.nextLine();
            while (!transitionsLine.equals("END")) {

                // System.out.println(transitionsLine);
                transitions.add(transitionsLine);
                transitionsLine = scan.nextLine();

            }
            //NFA Table- 4 row 3 column matrix I create
            //x    0     1
            /*A    A    A,B
              B    null  C
              C    null  null
             */
            int statesize = states.size(); //3
            int alphabetsize = alphabet.size(); //2
            //   System.out.println(statesize);
            //   System.out.println(alphabetsize);

            String[][] array = new String[alphabetsize+1][statesize+1];
            array[0][0] = "x";


            for (int i = 0; i <= statesize-1; i++) { //statesize
                for (int j = 0; j <= alphabetsize-1; j++) { //alphabetsize
                    for (int b = 0; b <= 3; b++) {
                        array[0][i + 1] = states.get(i);
                        array[j + 1][0] = alphabet.get(j);

                        for (int k = 1; k <= 2; k++) {
                            for (int t = 1; t <= 3; t++) {
                                if (array[0][i + 1].equals(transitions.get(b).substring(0, 1)) && array[j + 1][0].equals(transitions.get(b).substring(2, 3))) {
                                    array[k][t] = transitions.get(b).substring(4, 5);

                                //    System.out.println(array[1][1]);
                                }
                                //nfatodfa
                                if (array[k][t]==transitions.get(b).substring(4,5) && array[k][t]==transitions.get(b+1).substring(4,5)){
                                 //   System.out.println("["+states.get(i)+ states.get(i+1)+"]" +alphabet.get(i)+" "+transitions.get(b).substring(4,5));

                                    {
                                }


                                }


                            }


                        }


                    }


                }


            }

        }
    }
}






















