package oop.ex6.validity.command_validity;

import oop.ex6.SyntaxChecker;
import oop.ex6.validity.CompilingException;
import oop.ex6.validity.ScopeChecker;
import oop.ex6.validity.Variable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Assigning extends CommandLine {
    private String variableName, value;

    public Assigning(String variableName, String value){
        this.variableName = variableName;
        this.value = value;
    }

    @Override
    public void check(ScopeChecker scope) throws CompilingException{
        Variable variable = scope.getVariable(variableName);
        check(scope, variable);
    }

    public void check(ScopeChecker scope, Variable variable) throws CompilingException{
        if (variable == null)
            scope.addUnidentifiedCommand(this); /// CHECK THIS - WHEN CALLING FROM UNIDENTIFIED
        else{
            Pattern variableNamePattern = Pattern.compile(SyntaxChecker.VARIABLE_NAME);
            Matcher variableNameMatcher = variableNamePattern.matcher(value);

            if (variableNameMatcher.matches()) { // the value is a variable
                Variable assignVariable = scope.getVariable(value);
                if (assignVariable == null)
                    scope.addUnidentifiedCommand(this); // SHOULD SOLVE THE RECURSION WHEN CALLING IT IN THE END
                else if (variable.getType() != assignVariable.getType() || !assignVariable.isAssigned()) // SHOULDN'T IT BE IN VARIABLE?
                    throw new CompilingException();
            }
            else { // the value is not a variable
                variable.assign(value);
            }
        }
    }
}
