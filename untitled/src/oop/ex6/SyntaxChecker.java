package oop.ex6;


import oop.ex6.program_members.CompilingException;
import oop.ex6.command_validity.*;
import java.util.LinkedList;
import java.util.regex.*;

/**
 * The class encapsulates the syntax handling in the problem. It checks the syntax of a given row, and provides
 * patterns of valid syntax
 */
public class SyntaxChecker {

    public enum LineStatus {OPEN_SCOPE, CLOSE_SCOPE, STANDARD}
    public static final String
            // types regex
            INT_TYPE = "int",
            DOUBLE_TYPE = "double",
            STRING_TYPE = "String",
            BOOLEAN_TYPE = "boolean",
            CHAR_TYPE = "char",
            VARIABLE_TYPE = INT_TYPE + "|" + DOUBLE_TYPE + "|" + STRING_TYPE + "|" + BOOLEAN_TYPE + "|" + CHAR_TYPE,
            // values regex
            INT_VALUE = "-?\\d+",
            DOUBLE_VALUE = "-?\\d+\\.*\\d*",
            STRING_VALUE = "\".*\"",
            BOOLEAN_VALUE = "true|false",
            CHAR_VALUE = "\'.?\'",
            VARIABLE_VALUE = DOUBLE_VALUE + "|" + INT_VALUE + "|" + STRING_VALUE + "|" + BOOLEAN_VALUE + "|" +
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
                    "(((" + METHOD_PARAMETER + ",)*\\s*" + METHOD_PARAMETER + "))|(\\s*)\\)\\s*\\{\\s*",
            METHOD_CALL_LINE = "\\s*(" + METHOD_NAME + ")\\s*\\((" + "(\\s*(" + METHOD_INPUT + ")\\s*,)*" +
                    "\\s*(" + METHOD_INPUT + "))\\s*\\);\\s*",
            RETURN_LINE = "\\s*return\\s*;\\s*",
            END_SCOPE_LINE = "\\s*}\\s*",
            OPEN_SCOPE_LINE = ".*\\{\\s*",
            IF_WHILE_LINE = "\\s*(if|while)\\s*\\(" + "((\\s*(" + CONDITION + ")\\s*(\\|\\||&&))*" +
                    "\\s*(" + CONDITION + "))\\s*\\)\\s*\\{\\s*";
    public static Pattern
            // line patterns
            EMPTY_LINE_PATTERN,
            COMMENT_LINE_PATTERN,
            VARIABLE_DECLARATION_PATTERN,
            ASSIGNMENT_LINE_PATTERN,
            METHOD_DECLARATION_PATTERN,
            METHOD_CALL_PATTERN,
            RETURN_LINE_PATTERN,
            END_SCOPE_LINE_PATTERN,
            IF_WHILE_PATTERN,
            OPEN_SCOPE_PATTERN,
            // expression patterns
            DECLARATION_EXPRESSION_PATTERN,
            CONDITION_PATTERN,
            METHOD_INPUT_PATTERN,
            METHOD_PARAMETER_PATTERN,
            // values patterns
            VARIABLE_NAME_PATTERN,
            BOOLEAN_VALUE_PATTERN,
            STRING_VALUE_PATTERN,
            CHAR_VALUE_PATTERN,
            INT_VALUE_PATTERN,
            DOUBLE_VALUE_PATTERN;
    private static final String
            FINAL_WITHOUT_ASSIGNMENT_MSG = "Invalid declaration: final variable not assigned while declaration",
            INVALID_SYNTAX_MESSAGE = "Invalid syntax: line doesn't match s-Java syntax: ";




    private static Matcher lineMatcher;


    /**
     * The method creates the syntax static Patterns for using in the program
     */
    public static void createPatterns() {
        EMPTY_LINE_PATTERN = Pattern.compile(EMPTY_LINE);
        COMMENT_LINE_PATTERN = Pattern.compile(COMMENT_LINE);
        VARIABLE_DECLARATION_PATTERN = Pattern.compile(VARIABLE_DECLARATION_LINE);
        ASSIGNMENT_LINE_PATTERN = Pattern.compile(ASSIGNMENT_LINE);
        METHOD_DECLARATION_PATTERN = Pattern.compile(METHOD_DECLARATION_LINE);
        METHOD_CALL_PATTERN = Pattern.compile(METHOD_CALL_LINE);
        RETURN_LINE_PATTERN = Pattern.compile(RETURN_LINE);
        END_SCOPE_LINE_PATTERN = Pattern.compile(END_SCOPE_LINE);
        IF_WHILE_PATTERN = Pattern.compile(IF_WHILE_LINE);
        OPEN_SCOPE_PATTERN = Pattern.compile(OPEN_SCOPE_LINE);
        DECLARATION_EXPRESSION_PATTERN = Pattern.compile(DECLARATION_EXPRESSION);
        CONDITION_PATTERN = Pattern.compile(CONDITION);
        METHOD_INPUT_PATTERN = Pattern.compile(METHOD_INPUT);
        METHOD_PARAMETER_PATTERN = Pattern.compile(METHOD_PARAMETER);
        VARIABLE_NAME_PATTERN = Pattern.compile(VARIABLE_NAME);
        BOOLEAN_VALUE_PATTERN = Pattern.compile(BOOLEAN_VALUE);
        STRING_VALUE_PATTERN = Pattern.compile(STRING_VALUE);
        CHAR_VALUE_PATTERN = Pattern.compile(CHAR_VALUE);
        INT_VALUE_PATTERN = Pattern.compile(INT_VALUE);
        DOUBLE_VALUE_PATTERN = Pattern.compile(DOUBLE_VALUE);
    }

    /**
     * The method gets a String line and checks if it matches one of the valid syntax patterns. If it does, the method
     * returns a CommandLine matching the pattern. Otherwise, it throws an Exception.
     * @param line the text line to be checked
     * @return CommandLine matching the line pattern
     * @throws CompilingException if the line does not match any of the valid syntax patterns
     */
    public static CommandLine checkLine(String line) throws CompilingException {

        if (isMatchPattern(EMPTY_LINE_PATTERN, line)||isMatchPattern(COMMENT_LINE_PATTERN, line)) {
            return new EmptyCommand();
        } else if (isMatchPattern(VARIABLE_DECLARATION_PATTERN, line)) {
            return varDeclarationCreation();
        } else if (isMatchPattern(ASSIGNMENT_LINE_PATTERN, line)) {
            return assignmentCreation();
        } else if (isMatchPattern(METHOD_DECLARATION_PATTERN, line)) {
            return methodDeclarationCreation();
        } else if (isMatchPattern(METHOD_CALL_PATTERN, line)) {
            return methodCallCreation();
        } else if (isMatchPattern(RETURN_LINE_PATTERN, line)) {
            return new ReturnLine();
        } else if (isMatchPattern(END_SCOPE_LINE_PATTERN, line)) {
            return new CloseScope();
        } else if (isMatchPattern(IF_WHILE_PATTERN, line)) {
            return blockCreation();
        } else {
            throw new CompilingException(INVALID_SYNTAX_MESSAGE + line);
        }
    }

    /**
     * Checks what is the type of the line: is it a line that opens scope, closes scope or a regular line.
     * @param line the text line to be checked
     * @return LineStatus object representing the line type
     */
    public static LineStatus getLineType(String line) {
        if (isMatchPattern(OPEN_SCOPE_PATTERN, line))
            return LineStatus.OPEN_SCOPE;
        else if (isMatchPattern(END_SCOPE_LINE_PATTERN, line))
            return LineStatus.CLOSE_SCOPE;
        else
            return LineStatus.STANDARD;
    }

    /**
     * Gets text and a Pattern object, and checks whether the text matches the Pattern
     * @param pattern the Pattern to be checked
     * @param text the text to be checked
     * @return true iff the text matches the pattern
     */
    private static boolean isMatchPattern (Pattern pattern, String text) {
        lineMatcher = pattern.matcher(text);

        return lineMatcher.matches();
    }

    /**
     * @return an Assigning CommandLine according to the match
     */
    private static Assigning assignmentCreation() {
        String variableName = lineMatcher.group(1);
        String input = lineMatcher.group(8);

        return new Assigning(variableName, input);
    }


    /**
     * @return a MultipleVariableDeclaration CommandLine according to the match
     * @throws CompilingException if there was a declaration of final variable without assignment
     */
    private static MultipleVariableDeclaration varDeclarationCreation() throws CompilingException{
        LinkedList<VariableDeclaration> lineList = new LinkedList<VariableDeclaration>();
        boolean isFinal = false;

        // checks if the word final exist in the pattern
        if (lineMatcher.group(1) != null)
            isFinal = true;
        String variableType = lineMatcher.group(2);
        String declares = lineMatcher.group(3);

        lineMatcher = DECLARATION_EXPRESSION_PATTERN.matcher(declares);
        // finding all the declarations
        while (lineMatcher.find()) {
            String variableName = lineMatcher.group(1);
            String input = lineMatcher.group(8);

            // checks if the variable is final and it was not assigned while declaration
            if (isFinal && input == null)
                throw new CompilingException(FINAL_WITHOUT_ASSIGNMENT_MSG);
            lineList.add(new VariableDeclaration(variableType, isFinal, variableName, input));
        }

        return new MultipleVariableDeclaration(lineList);
    }

    /**
     * @return a ConditionBlock CommandLine according to the match
     */
    private static ConditionBlock blockCreation() {
        String conditions = lineMatcher.group(2);

        lineMatcher = CONDITION_PATTERN.matcher(conditions);
        LinkedList<String> variables = new LinkedList<String>();
        // finding all the conditions
        while (lineMatcher.find()) {

            // finds all the variables used as a condition
            String variable = lineMatcher.group(1);
            if (variable != null)
                variables.add(variable);
        }

        return new ConditionBlock(variables);
    }

    /**
     * @return a CallingMethod CommandLine according to the match
     */
    private static CallingMethod methodCallCreation () {
        String methodName = lineMatcher.group(1);
        String inputs = lineMatcher.group(4);

        lineMatcher = METHOD_INPUT_PATTERN.matcher(inputs);
        LinkedList<String> inputsList = new LinkedList<String>();
        // finding all the method inputs
        while (lineMatcher.find()) {
            inputsList.add(inputs.substring(lineMatcher.start(), lineMatcher.end()));
        }

        return new CallingMethod(methodName, inputsList);

    }

    /**
     * @return a MethodDeclaration CommandLine according to the match
     */
    private static MethodDeclaration methodDeclarationCreation () {
        String methodName = lineMatcher.group(1);
        String parameters = lineMatcher.group(4);

        lineMatcher = METHOD_PARAMETER_PATTERN.matcher(parameters);
        LinkedList<VariableDeclaration> methodParameters = new LinkedList<VariableDeclaration>();
        // finding all the method parameters declarations
        while (lineMatcher.find()) {
            boolean isFinal = false;
            if (lineMatcher.group(1) != null)
                isFinal = true;
            String parameterType = lineMatcher.group(2);
            String parameterName = lineMatcher.group(3);
            methodParameters.add(new VariableDeclaration(parameterType, isFinal, parameterName, null));
        }

        return new MethodDeclaration(methodName, methodParameters);
    }
}
