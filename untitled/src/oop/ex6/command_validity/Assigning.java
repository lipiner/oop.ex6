package oop.ex6.command_validity;

import oop.ex6.SyntaxChecker;
import oop.ex6.program_members.CompilingException;
import oop.ex6.program_members.ScopeChecker;
import oop.ex6.program_members.VariableWrapper;

import java.util.regex.Matcher;

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
            throw new CompilingException();
            //scope.addUnidentifiedCommand(this);
        else {
            Matcher variableNameMatcher = SyntaxChecker.VARIABLE_NAME_PATTERN.matcher(value);

            if (variableNameMatcher.matches()) { // the value is a variable
                VariableWrapper assignVariable = scope.getVariable(value);
                if (assignVariable == null) {
                    throw new CompilingException();
                    //variable.assign(); // assuming the variable is legally assigned until the unidentified check
                    //scope.addUnidentifiedCommand(this);
                }
                else
                    scope.assignVariable(variable, assignVariable);
            }
            else { // the value is not a variable
                scope.assignVariable(variable, value);
            }
        }
    }
}
