package oop.ex6;


import oop.ex6.validity.CompilingException;
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
            DECLARATION_EXPRESSION = "(" + VARIABLE_NAME + ")\\s*(" + ASSIGNMENT + ")?",
            METHOD_PARAMETER = "\\s*(" + FINAL + "\\s+)?(" + VARIABLE_TYPE + ")\\s+(" + VARIABLE_NAME + ")\\s*",
            METHOD_INPUT = VARIABLE_NAME + "|" + VARIABLE_VALUE,
            CONDITION = BOOLEAN_VALUE +"|"+ INT_VALUE +"|"+ DOUBLE_VALUE +"|("+ VARIABLE_NAME + ")",
            // full lines regex
            EMPTY_LINE = "\\s*",
            COMMENT_LINE = "//.*",
            VARIABLE_DECLARATION_LINE = "\\s*" + "(" + FINAL + "\\s+)?(" + VARIABLE_TYPE + ")\\s+("
                    + "(" + DECLARATION_EXPRESSION + "\\s*,\\s*)*" + DECLARATION_EXPRESSION + ")\\s*;\\s*",
            ASSIGNMENT_LINE = "\\s*(" + VARIABLE_NAME + ")(" + ASSIGNMENT + ")\\s*;\\s*",
            METHOD_DECLARATION_LINE =  "\\s*void\\s+(" + METHOD_NAME + ")\\s*\\(" +
                    "((" + METHOD_PARAMETER + ",)*\\s*" + METHOD_PARAMETER + ")\\)\\s*\\{\\s*",
            METHOD_CALL_LINE = "\\s*(" + METHOD_NAME + ")\\s*\\((" + "(\\s*(" + METHOD_INPUT + ")\\s*,)*" +
                    "\\s*(" + METHOD_INPUT + "))\\s*\\);\\s*",
            RETURN_LINE = "\\s*return\\s*;\\s*",
            END_SCOPE_LINE = "\\s*\\}\\s*",
            IF_WHILE_LINE = "\\s*(if|while)\\s*\\(" + "((\\s*(" + CONDITION + ")\\s*(\\|\\||&&))*" +
                    "\\s*(" + CONDITION + "))\\s*\\)\\s*\\{\\s*";
    private static Matcher lineMatcher;


    /**
     *
     * @param line
     * @return
     */
    public static CommandLine checkLine(String line) throws CompilingException {

        if (isMatchPattern(EMPTY_LINE, line)||isMatchPattern(COMMENT_LINE, line)) {
            return new EmptyCommand();
        } else if (isMatchPattern(VARIABLE_DECLARATION_LINE, line)) {
            return varDeclarationCreation(line);
        } else if (isMatchPattern(ASSIGNMENT_LINE, line)) {
            return assignmentCreation(line);
        } else if (isMatchPattern(METHOD_DECLARATION_LINE, line)) {
            return methodDeclarationCreation(line);
        } else if (isMatchPattern(METHOD_CALL_LINE, line)) {
            return methodCallCreation(line);
        } else if (isMatchPattern(RETURN_LINE, line)) {
            return new ReturnLine();
        } else if (isMatchPattern(END_SCOPE_LINE, line)) {
            return new CloseScope();
        } else if (isMatchPattern(IF_WHILE_LINE, line)) {
            return blockCreation(line);
        } else {
            throw new CompilingException();
        }
    }

    /**
     *
     * @param stringPattern
     * @param text
     * @return
     */
    private static boolean isMatchPattern (String stringPattern, String text) {
        Pattern pattern = Pattern.compile(stringPattern);
        lineMatcher = pattern.matcher(text);

        return lineMatcher.matches();
    }

    /**
     *
     * @param line
     * @return
     */
    private static CommandLine assignmentCreation(String line) {
        String variableName = lineMatcher.group(1);
        String input = lineMatcher.group(8);

        return new Assigning(variableName, input);
    }



    /**
     *
     * @param line
     * @return
     */
    private static CommandLine varDeclarationCreation(String line) throws CompilingException{
        LinkedList<VariableDeclaration> lineList = new LinkedList<VariableDeclaration>();
        boolean isFinal = false;
        if (lineMatcher.group(1) != null)
            isFinal = true;
        String variableType = lineMatcher.group(2);
        String declares = lineMatcher.group(3);

        Pattern declarationExpPattern = Pattern.compile(DECLARATION_EXPRESSION);
        lineMatcher = declarationExpPattern.matcher(declares);
        while (lineMatcher.find()) {
            String variableName = lineMatcher.group(1);
            String input = lineMatcher.group(8);

            if (isFinal && input == null)
                throw new CompilingException();
            lineList.add(new VariableDeclaration(variableType, isFinal, variableName, input));
        }

        return new MultipleVariableDeclaration(lineList);
    }

    /**
     *
     * @param line
     * @return
     */
    private static CommandLine blockCreation(String line) {
        String conditions = lineMatcher.group(2);
        Pattern conditionPattern = Pattern.compile(CONDITION);

        lineMatcher = conditionPattern.matcher(conditions);
        LinkedList<String> variables = new LinkedList<String>();
        while (lineMatcher.find()) {
            String variable = lineMatcher.group(1);
            if (variable != null)
                variables.add(variable);
        }

        return new DefiningBlock(variables);
    }

    /**
     *
     * @param line
     * @return
     */
    private static CommandLine methodCallCreation (String line) {
        String methodName = lineMatcher.group(1);
        String inputs = lineMatcher.group(4);

        Pattern methodInputPattern = Pattern.compile(METHOD_INPUT);
        lineMatcher = methodInputPattern.matcher(inputs);
        LinkedList<String> inputsList = new LinkedList<String>();
        while (lineMatcher.find()) {
            inputsList.add(inputs.substring(lineMatcher.start(), lineMatcher.end()));
        }

        return new CallingMethod(methodName, inputsList);

    }

    /**
     *
     * @param line
     * @return
     */
    private static CommandLine methodDeclarationCreation (String line) {
        String methodName = lineMatcher.group(1);
        String parameters = lineMatcher.group(4);

        Pattern methodParameterPattern = Pattern.compile(METHOD_PARAMETER);
        lineMatcher = methodParameterPattern.matcher(parameters);
        LinkedList<VariableDeclaration> methodParameters = new LinkedList<VariableDeclaration>();
        while (lineMatcher.find()) {
            boolean isFinal = false;
            if (lineMatcher.group(1) != null)
                isFinal = true;
            String parameterType = lineMatcher.group(2);
            String parameterName = lineMatcher.group(3);
            methodParameters.add(new VariableDeclaration(parameterType, isFinal, parameterName));
        }

        return new MethodDeclaration(methodName, methodParameters);
    }
}
