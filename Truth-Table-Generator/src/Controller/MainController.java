package Controller;

import Model.ExpressionHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 *
 * @author youssefali
 */
public class MainController {
    
    private ArrayList<ExpressionHandler> expressions;
    private int maxCapacity;
    
    public MainController(){
        maxCapacity = 1000000;
        expressions = new ArrayList<>();
        for(int i=0; i<maxCapacity; i++){
            expressions.add(new ExpressionHandler(""));
        }
    }
    
    public ArrayList<ExpressionHandler> getExpressions(){
        return expressions;
    }
    
    public void addExpressionAt(String expression, int index){
        ExpressionHandler exprHandler = new ExpressionHandler(expression);
        expressions.set(index, exprHandler);
    }
    
    public ExpressionHandler getExpressionAt(int index){
        return expressions.get(index);
    }

    public String[][] getTruthTable(int index) {
        Boolean[][] truthTable = expressions.get(index).getTruthTable();
        String[][] sTruthTable = new String[truthTable.length][truthTable[0].length];
        for(int i=0; i<truthTable.length; i++){
            for(int j=0; j<truthTable[0].length; j++){
                sTruthTable[i][j] = truthTable[i][j] == true? "T" : "F";
            }
        }
            
        return sTruthTable;
    }

    public String[] getTitles(int index) {
        ArrayList<String> titles = new ArrayList<>();
        Character[] symbols = expressions.get(index).getSymbols();
        
        for(Character symbol : symbols){
            titles.add(symbol + "");
        }
        titles.add("Output");
        
        return titles.toArray(new String[0]);
    }

    public String testEquiv(int expr1, int expr2) {
        Boolean result = expressions.get(expr1).isEquivalence(expressions.get(expr2).getTruthTable());
        return result.equals(true) ? "They are equivalent expressions" : "They are not equivalent expressions";
    }

    public String testTautology(int expr) {
        Boolean result = expressions.get(expr).isTautology();
        return result.equals(true) ? 
                "This expression is tautology.\nIt's always true." 
                : "This expression is not tautology.\nThere is at least one combination makes it false.";
    }

    public String testContradiction(int expr) {
        Boolean result = expressions.get(expr).isContradiction();
        return result.equals(true) ? 
                "This expression is contradiction.\nIt's always true." 
                : "This expression is not contradiction.\nThere is at least one combination makes it true.";
    }

    public void saveTruthTable(File file, int index) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(file);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        Boolean[][] truthTable = expressions.get(index).getTruthTable();
        Character[] symbols = expressions.get(index).getSymbols();

        pw.println("Truth Table of:");
        pw.println(expressions.get(index).getExpression());
        pw.println("--------------------------------------");

        int i=0;
        for(Character symbol : symbols){
            if(i > 0){
                pw.print(" | ");
            }
            pw.print(symbol+"");
            i++;
        }
        pw.print(" | ");
        pw.println("Output");

        for(Boolean[] row : truthTable){
            i=0;
            for(Boolean cell : row){
                if(i > 0){
                    pw.print(" | ");
                }
                pw.print(cell? "T":"F");
                i++;
            }
            pw.println();
        }

        pw.println("--------------------------------------");
        pw.println("Generated by Truth Table Generator Application.");
        pw.println("By Youssef Ali & Mostafa Labib.");

        pw.close();
    }
    
}
