import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class EsraZeynep_Dinç_S020780_Project2 {
    public static void main(String[] args) throws FileNotFoundException {

        String filename = "G1.txt";
        Scanner scanner = new Scanner(new File(filename));

        List<String> nonTerminals = new ArrayList<>();
        List<String> terminals = new ArrayList<>();
        List<String> rules = new ArrayList<>();

        readFile(scanner, nonTerminals, terminals, rules);
        Map<String, List<String>> nonterminalMap = createNonterminalMap(rules);
        eliminateEmptyRightHandSides(nonterminalMap);
        eliminateProductionsWithLongRightHandSides(nonterminalMap);
        eliminateProductionsWithTerminalSymbols(nonterminalMap, terminals);
        eliminateProductionsWithTwoNonterminalSymbols(nonterminalMap);
        printCNF(nonterminalMap);

    }
    private static void printCNF(Map<String, List<String>> nonterminalMap) {
        if (nonterminalMap.isEmpty()) {
            System.out.println("The CNF grammar is empty.");
        } else {
            for (String nonterminal : nonterminalMap.keySet()) {
                List<String> productions = nonterminalMap.get(nonterminal);
                System.out.print(nonterminal + " -> ");
                System.out.println(String.join(" | ", productions));
            }
        }
    }
    private static void eliminateProductionsWithTwoNonterminalSymbols(Map<String, List<String>> nonterminalMap) {
        Map<String, List<String>> updatedMap = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : nonterminalMap.entrySet()) {
            String nonterminal = entry.getKey();
            List<String> productions = entry.getValue();
            List<String> updatedProductions = new ArrayList<>();
            for (String production : productions) {
                String[] symbols = production.split(" ");
                if (symbols.length == 1) {
                    updatedProductions.add(production);
                } else {
                    String newNonterminal = nonterminal + "'";
                    updatedProductions.add(symbols[0] + " " + newNonterminal);
                    updatedProductions.add(newNonterminal + " " + symbols[1]);
                }
            }
            updatedMap.put(nonterminal, updatedProductions);
        }
        nonterminalMap.clear();
        nonterminalMap.putAll(updatedMap);

    }
    private static void eliminateProductionsWithTerminalSymbols(Map<String, List<String>> nonterminalMap, List<String> terminals) {
        Map<String, List<String>> updatedMap = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : nonterminalMap.entrySet()) {
            String nonterminal = entry.getKey();
            List<String> productions = entry.getValue();
            List<String> updatedProductions = new ArrayList<>();
            for (String production : productions) {
                String[] symbols = production.split(" ");
                if (symbols.length == 1) {
                    updatedProductions.add(production);
                } else {
                    if (terminals.contains(symbols[0]) || terminals.contains(symbols[1])) {
                        updatedProductions.add(production);
                    }
                }
            }
            updatedMap.put(nonterminal, updatedProductions);
        }
        nonterminalMap.clear();
        nonterminalMap.putAll(updatedMap);
    }
    private static void eliminateProductionsWithLongRightHandSides(Map<String, List<String>> nonterminalMap) {
        Map<String, List<String>> updatedMap = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : nonterminalMap.entrySet()) {
            String nonterminal = entry.getKey();
            List<String> productions = entry.getValue();
            List<String> updatedProductions = new ArrayList<>();
            for (String production : productions) {
                String[] symbols = production.split(" ");
                if (symbols.length <= 2) {
                    updatedProductions.add(production);
                }
            }
            updatedMap.put(nonterminal, updatedProductions);
        }
        nonterminalMap.clear();
        nonterminalMap.putAll(updatedMap);
    }


    private static void eliminateEmptyRightHandSides(Map<String, List<String>> nonterminalMap) {
        Map<String, List<String>> updatedMap = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : nonterminalMap.entrySet()) {
            String nonterminal = entry.getKey();
            List<String> productions = entry.getValue();

            List<String> updatedProductions = new ArrayList<>();

            for (String production : productions) {
                if (!production.isEmpty()) {
                    updatedProductions.add(production);
                }
            }
            updatedMap.put(nonterminal, updatedProductions);
        }
        nonterminalMap.clear();
        nonterminalMap.putAll(updatedMap);
    }


    private static Map<String, List<String>> createNonterminalMap(List<String> rules) {
        Map<String, List<String>> nonterminalMap = new HashMap<>();
        for (String rule : rules) {

            String[] parts = rule.split(":", 2);
            String lhs = parts[0];
            String rhs = parts[1];

            nonterminalMap.put(lhs, new ArrayList<>());

            String[] productions = rhs.split("\\|");

            for (String production : productions) {
                nonterminalMap.get(lhs).add(production.trim());
            }
        }

        return nonterminalMap;
    }



    private static void readFile(Scanner scanner, List<String> nonTerminals, List<String> terminals, List<String> rules) {

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("NON-TERMINAL")) {
                while (scanner.hasNextLine()) {
                    line = scanner.nextLine();
                    if (line.startsWith("TERMINAL")) {
                        break;
                    }
                    nonTerminals.add(line);
                }
            }

            if (line.startsWith("TERMINAL")) {
                while (scanner.hasNextLine()) {
                    line = scanner.nextLine();
                    if (line.startsWith("RULES")) {
                        break;
                    }
                    terminals.add(line);
                }
            }
            if (line.startsWith("RULES")) {
                while (scanner.hasNextLine()) {
                    line = scanner.nextLine();
                    if (line.startsWith("START")) {
                        break;
                    }
                    rules.add(line);
                }
            }
        }
    }

}