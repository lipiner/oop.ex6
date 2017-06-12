package oop.ex6.validity.command_validity;

import oop.ex6.SyntaxChecker;
import oop.ex6.validity.CompilingException;
import oop.ex6.validity.ScopeChecker;
import oop.ex6.validity.Variable;
import oop.ex6.validity.VariableWrapper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Assigning extends CommandLine {
    private String variableName, value;
    private VariableWrapper variable;

    public Assigning(String variableName, String value){
        this.variableName = variableName;
        this.value = value;
    }

    public Assigning(VariableWrapper variable, String value){
        this.variable = variable;
        variableName = null;
        this.value = value;
    }

    @Override
    public void check(ScopeChecker scope) throws CompilingException{
        if (variableName != null)
            variable = scope.getVariable(variableName);

        if (variable == null)
            scope.addUnidentifiedCommand(this);
        else{
            Pattern variableNamePattern = Pattern.compile(SyntaxChecker.VARIABLE_NAME);
            Matcher variableNameMatcher = variableNamePattern.matcher(value);

            if (variableNameMatcher.matches()) { // the value is a variable
                VariableWrapper assignVariable = scope.getVariable(value);
                if (assignVariable == null) {
                    variable.assign(); // assuming the variable is legally assigned until the unidentified check
                    scope.addUnidentifiedCommand(this);
                }
                else
                    variable.assign(assignVariable);
            }
            else { // the value is not a variable
                variable.assign(value);
            }
        }
    }
}
