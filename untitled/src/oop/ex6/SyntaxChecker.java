package oop.ex6;


import oop.ex6.validity.command_validity.*;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SyntaxChecker {


    public static final String
            // types regex
            INT_TYPE = "int",
            DOUBLE_TYPE = "double",
            STRING_TYPE = "String",
            BOOLEAN_TYPE = "boolean",
            CHAR_TYPE = "char",
            VARIABLE_TYPE = INT_TYPE + "|" + DOUBLE_TYPE + "|" + STRING_TYPE + "|" + BOOLEAN_TYPE + "|" + CHAR_TYPE,
            // values regex
            INT_VALUE = "-?\\d*",
            DOUBLE_VALUE = "-?\\d+\\.*\\d*",
            STRING_VALUE = "\".*\"",
            BOOLEAN_VALUE = "true|false",
            CHAR_VALUE = "\'.?\'",
            VARIABLE_VALUE = INT_VALUE + "|" + DOUBLE_VALUE + "|" + STRING_VALUE + "|" + BOOLEAN_VALUE + "|" +
                    CHAR_VALUE,
            // names regex
            VARIABLE_NAME = "(_(\\w)+)|(([A-Z]|[a-z])(\\w)*)",
            METHOD_NAME = "([A-Z]|[a-z])(\\w)*",
            // in-line expressions regex
            FINAL = "final",
            ASSIGNMENT = "\\s*=\\s*(" + VARIABLE_VALUE + "|" + VARIABLE_NAME + ")",
            DECLARATION_EXP = "(" + VARIABLE_NAME + ")\\s*(" + ASSIGNMENT + ")?",
            METHOD_PARAMETER = "\\s*(" + FINAL + "\\s+)?(" + VARIABLE_TYPE + ")\\s+(" + VARIABLE_NAME + ")\\s*",
            METHOD_INPUT = VARIABLE_NAME + "|" + VARIABLE_VALUE,
            CONDITION = BOOLEAN_VALUE +"|"+ INT_VALUE +"|"+ DOUBLE_VALUE +"|("+ VARIABLE_NAME + ")",
            // full lines regex
            EMPTY_LINE = "\\s*",
            COMMENT_LINE = "//.*",
            VARIABLE_DECLARATION_LINE = "\\s*" + "(" + FINAL + "\\s+)?(" + VARIABLE_TYPE + ")\\s+("
                    + "(" + DECLARATION_EXP + "\\s*,\\s*)*" + DECLARATION_EXP + ")\\s*;\\s*",
            ASSIGNMENT_LINE = "\\s*(" + VARIABLE_NAME + ")(" + ASSIGNMENT + ")\\s*;\\s*",
            METHOD_DECLARATION_LINE =  "\\s*void\\s+(" + METHOD_NAME + ")\\s*\\(" +
                    "(" + METHOD_PARAMETER + ",)*\\s*" + METHOD_PARAMETER + "\\)\\s*\\{\\s*",
            METHOD_CALL_LINE = "\\s*(" + METHOD_NAME + ")\\s*\\((" + "(\\s*(" + METHOD_INPUT + ")\\s*,)*" +
                    "\\s*(" + METHOD_INPUT + "))\\s*\\);\\s*",
            RETURN_LINE = "\\s*return\\s*;\\s*",
            END_SCOPE_LINE = "\\s*\\}\\s*",
            IF_WHILE_LINE = "\\s*(if|while)\\s*\\(" + "((\\s*(" + CONDITION + ")\\s*(\\|\\||&&))*" +
                    "\\s*(" + CONDITION + "))\\s*\\)\\s*\\{\\s*";


    public static LinkedList<CommandLine> checkLine(String line) { //CHANGE FROM VOID!!
        LinkedList<CommandLine> lineList = new LinkedList<CommandLine>();

        if (Pattern.matches(EMPTY_LINE, line)||Pattern.matches(COMMENT_LINE, line)) {
            lineList.add(new EmptyCommand());
        } else if (Pattern.matches(VARIABLE_DECLARATION_LINE, line)) {
            lineList = varDeclarationCreation(line);
        } else if (Pattern.matches(ASSIGNMENT_LINE, line)) {
            lineList.add(assignmentCreation(line));
        } else if (Pattern.matches(METHOD_DECLARATION_LINE, line)) {

        } else if (Pattern.matches(METHOD_CALL_LINE, line)) {
            lineList.add(methodCallCreation(line));
        } else if (Pattern.matches(RETURN_LINE, line)) {
            lineList.add(new ReturnLine());
        } else if (Pattern.matches(END_SCOPE_LINE, line)) {
            lineList.add(new CloseScope());
        } else if (Pattern.matches(IF_WHILE_LINE, line)) {
            lineList.add(blockCreation(line));
        } else {
            //EXCEPTION!!
        }
        return lineList;
    }

    private static CommandLine assignmentCreation(String line) {
        Pattern p = Pattern.compile(ASSIGNMENT_LINE);
        Matcher m = p.matcher(line);
        m.matches();
        String variableName = m.group(1);
        String input = m.group(8);

        return new Assigning(variableName, input);
    }

    private static LinkedList<CommandLine> varDeclarationCreation(String line) {
        LinkedList<CommandLine> lineList = new LinkedList<CommandLine>();
        Pattern p = Pattern.compile(VARIABLE_DECLARATION_LINE);
        Matcher m = p.matcher(line);
        boolean isFinal = false;
        if (m.group(1) != null)
            isFinal = true;
        String variableType = m.group(2);
        String declares = m.group(3);
        p = Pattern.compile(DECLARATION_EXP);
        m = p.matcher(declares);
        while (m.find()) {
            String variableName = m.group(1);
            String input = m.group(8);

            lineList.add(new VariableDeclaration(variableType, isFinal, variableName, input));
        }

        return lineList;
    }

    private static CommandLine blockCreation(String line) {
        Pattern p = Pattern.compile(IF_WHILE_LINE);
        Matcher m = p.matcher(line);
        m.matches();
        String conditions = m.group(2);
        p = Pattern.compile(CONDITION);
        m = p.matcher(conditions);
        LinkedList<String> variables = new LinkedList<String>();
        while (m.find()) {
            String variable = m.group(1);
            if (variable != null)
                variables.add(variable);
        }

        return new DefiningBlock(variables);
    }

    private static CommandLine methodCallCreation (String line) {
        Pattern p = Pattern.compile(METHOD_CALL_LINE);
        Matcher m = p.matcher(line);
        m.matches();
        String methodName = m.group(1);
        String inputs =m.group(4);
        p = Pattern.compile(METHOD_INPUT);
        m = p.matcher(inputs);
        LinkedList<String> inputsList = new LinkedList<String>();
        while (m.find()) {
            inputsList.add(inputs.substring(m.start(), m.end()));
        }

        return new CallingMethod(methodName, inputsList);

    }

    private static CommandLine methodDeclarationCreation (String line) {
        Pattern p =
    }
}
