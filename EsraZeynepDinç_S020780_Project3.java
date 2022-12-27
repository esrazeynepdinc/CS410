import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class EsraZeynepDinç_S020780_Project3 {
    private int numInputVars;
    private String inputAlphabet;
    private int numTapeVars;
    private String tapeAlphabet;
    private char blankSymbol;
    private int numStates;
    private String states;
    private String startState;
    private String acceptState;
    private String rejectState;
    private String[][] transitionRules;
    private String inputString;
    private char[] tape;
    private int tapeHead;
    private String currentState;
    private boolean accept;
    public EsraZeynepDinç_S020780_Project3(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            numInputVars = Integer.parseInt(reader.readLine());
            inputAlphabet = reader.readLine();
            numTapeVars = Integer.parseInt(reader.readLine());
            tapeAlphabet = reader.readLine();
            blankSymbol = reader.readLine().charAt(0);
            numStates = Integer.parseInt(reader.readLine());
            states = reader.readLine();
            startState = reader.readLine();
            acceptState = reader.readLine();
            rejectState = reader.readLine();
            transitionRules = new String[numStates][5];
            for (int i = 0; i < numStates; i++) {
                String line = reader.readLine();
                String[] parts = line.split("\\s+");
                transitionRules[i][0] = parts[0]; // current state
                transitionRules[i][1] = parts[1]; // input symbol
                transitionRules[i][2] = parts[2]; // output symbol
                transitionRules[i][3] = parts[3]; // direction
                transitionRules[i][4] = parts[4]; // next state
            }
            inputString = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void simulate() {

        tape = inputString.toCharArray();
        tapeHead = tape.length / 2;

        ArrayList<Object> route = new ArrayList<>();
        route.add(startState);
        currentState = startState;


        accept = false;
        while (true) {
            String[] rule = null;
            for (int i = 0; i < numStates; i++) {
                if (transitionRules[i][0].equals(currentState) && transitionRules[i][1].charAt(0) == tape[tapeHead]) {
                    rule = transitionRules[i];
                    break;
                }
            }
            if (rule == null) {
                for (int i = 0; i < numStates; i++) {
                    if (transitionRules[i][0].equals(currentState) && transitionRules[i][1].charAt(0) == blankSymbol) {
                        rule = transitionRules[i];
                        break;
                    }
                }
            }

            if (rule == null) {
                currentState = rejectState;
                break;
            }
            tape[tapeHead] = rule[2].charAt(0);
            if (rule[3].equals("L")) {
                tapeHead--;
            } else if (rule[3].equals("R")) {
                tapeHead++;
            }
            if (tapeHead < 0 || tapeHead >= tape.length) {
                currentState = rejectState;
                break;
            }
            currentState = rule[4];
            route.add(currentState);

            if (currentState.equals(acceptState)) {
                accept = true;
            }
        }
        System.out.print("ROUTE: ");
        for (Object state : route) {
            System.out.print(state + " ");
        }
        System.out.println();
        if (accept) {
            System.out.println("RESULT: accepted");
        } else {
            System.out.println("RESULT: rejected");
        }
    }

    public static void main(String[] args) {
        TuringMachine tm = new TuringMachine("input.txt");
        tm.simulate();

    }
}