package oop.ex6.command_validity;

import oop.ex6.program_members.CompilingException;
import oop.ex6.program_members.ScopeChecker;

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
