package oop.ex6.command_validity;

import oop.ex6.SyntaxChecker;
import oop.ex6.program_members.CompilingException;
import oop.ex6.program_members.ScopeChecker;
import oop.ex6.program_members.VariableWrapper;

import java.util.regex.Matcher;

public class Assigning extends CommandLine {
    private String variableName, value;
    private VariableWrapper variable;
    private static final String TRUE_VALUE = "true";
    private static final String FALSE_VALUE = "false";
    private static final String VARIABLE_NOT_FOUND_MSG = "Invalid assignment: the variable is not found";

    public Assigning(String variableName, String value){
        this.variableName = variableName;
        this.value = value;
    }

    Assigning(VariableWrapper variable, String value){
        this.variable = variable;
        variableName = null;
        this.value = value;
    }

    @Override
    public void check(ScopeChecker scope) throws CompilingException{
        System.out.println(value); //////////////////////////////
        if (variableName != null)
            variable = scope.getVariable(variableName);

        if (variable == null)
            throw new CompilingException(VARIABLE_NOT_FOUND_MSG);
        else {
            Matcher variableNameMatcher = SyntaxChecker.VARIABLE_NAME_PATTERN.matcher(value); // SUPPORT TRUE / FALSE

            if (variableNameMatcher.matches()) {
                // the value is a variable
                VariableWrapper assignVariable = scope.getVariable(value);
                if (assignVariable != null)
                    scope.assignVariable(variable, assignVariable);
                else {
                    if (!isBooleanValue(value))
                        throw new CompilingException(VARIABLE_NOT_FOUND_MSG);
                    else
                        scope.assignVariable(variable, value);
                }
            }
            else {
                // the value is not a variable
                scope.assignVariable(variable, value);
            }
        }
    }

    private boolean isBooleanValue(String value){
        return value.equals(TRUE_VALUE) || value.equals(FALSE_VALUE);
    }
}
