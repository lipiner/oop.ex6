package oop.ex6.validity.command_validity;

import oop.ex6.validity.CompilingException;
import oop.ex6.validity.ScopeChecker;

import java.util.LinkedList;

public class MultipleVariableDeclaration extends CommandLine {

    private LinkedList<VariableDeclaration> variableDeclarations;

    public MultipleVariableDeclaration(LinkedList<VariableDeclaration> declarations){
        variableDeclarations = declarations;
    }

    @Override
    public void check(ScopeChecker scope) throws CompilingException {
        for (VariableDeclaration declaration: variableDeclarations)
            declaration.check(scope);
    }
}
