package oop.ex6.command_validity;

import oop.ex6.program_members.*;

import java.util.LinkedList;

public class ConditionBlock extends CommandLine {

    private LinkedList<String> conditionVariables;

    public ConditionBlock(LinkedList<String> variables){
        conditionVariables = variables;
    }

    @Override
    public void check(ScopeChecker scope) throws CompilingException{
        for (String variableName: conditionVariables){
            checkVariable(variableName, scope);

        }
    }

    private void checkVariable (String variableName, ScopeChecker scope) throws CompilingException {
        VariableWrapper variable = scope.getVariable(variableName);

        // the variable does not exist
        if (variable == null)
            throw new CompilingException();
        // the variable type is wrong
        else if (variable.getType().equals(Variable.Type.CHAR) || variable.getType().equals(Variable.Type.STRING))
            throw new CompilingException();
        // the variable is not assigned
        else if (!variable.isAssigned())
            throw new CompilingException();

        // opening new scope
        ScopeChecker newScope = new LocalScope(scope);
    }
}
