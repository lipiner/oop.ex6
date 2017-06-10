package oop.ex6;


import oop.ex6.validity.command_validity.*;
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
            CONDITION = BOOLEAN_VALUE +"|"+ INT_VALUE +"|"+ DOUBLE_VALUE +"|"+ VARIABLE_NAME,
    // full lines regex
    EMPTY_LINE = "\\s*",
            COMMENT_LINE = "//.*",
            VARIABLE_DECLARATION_LINE = "\\s*" + "(" + FINAL + "\\s+)?(" + VARIABLE_TYPE + ")\\s+"
                    + "(" + DECLARATION_EXP + "\\s*,\\s*)*" + DECLARATION_EXP + "\\s*;\\s*",
            ASSIGNMENT_LINE = "\\s*(" + VARIABLE_NAME + ")(" + ASSIGNMENT + ")\\s*;\\s*",
            METHOD_DECLERATION_LINE =  "\\s*void\\s+(" + METHOD_NAME + ")\\s*\\(" +
                    "(" + METHOD_PARAMETER + ",)*\\s*" + METHOD_PARAMETER + "\\)\\s*\\{\\s*",
            METHOD_CALL_LINE = "\\s*" + METHOD_NAME + "\\s*\\(" + "(\\s*(" + METHOD_INPUT + ")\\s*,)*" +
                    "\\s*(" + METHOD_INPUT + ")\\s*\\);\\s*",
            RETURN_LINE = "\\s*return\\s*;\\s*",
            END_SCOPE_LINE = "\\s*\\}\\s*",
            IF_WHILE_LINE = "\\s*(if|while)\\s*\\(" + "(\\s*(" + CONDITION + ")\\s*(\\|\\||&&))*" +
                    "\\s*(" + CONDITION + ")\\s*\\)\\s*\\{\\s*";

    public static CommandLine CheckLine(String line) { //CHANGE FROM VOID!!
        Pattern p;
        Matcher m;

        if (Pattern.matches(EMPTY_LINE, line)||Pattern.matches(COMMENT_LINE, line)) {

        } else if (Pattern.matches(VARIABLE_DECLARATION_LINE, line)) {

        } else if (Pattern.matches(ASSIGNMENT_LINE, line)) {

        } else if (Pattern.matches(METHOD_DECLERATION_LINE, line)) {

        } else if (Pattern.matches(METHOD_CALL_LINE, line)) {

        } else if (Pattern.matches(RETURN_LINE, line)) {

        } else if (Pattern.matches(END_SCOPE_LINE, line)) {

        } else if (Pattern.matches(IF_WHILE_LINE, line)) {

        } else {

        }
    }
}
