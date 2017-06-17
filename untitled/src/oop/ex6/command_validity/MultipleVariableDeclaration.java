package oop.ex6.command_validity;

import oop.ex6.program_members.CompilingException;
import oop.ex6.program_members.ScopeChecker;
import java.util.LinkedList;

/**
 * The class represents a code line the declares multiple variables
 */
public class MultipleVariableDeclaration extends CommandLine {
    private LinkedList<VariableDeclaration> variableDeclarations;

    /**
     * constructs a new MultipleVariableDeclaration instance when given the declarations
     * @param declarations a LinkedList of VariableDeclaration objects
     */
    public MultipleVariableDeclaration(LinkedList<VariableDeclaration> declarations){
        variableDeclarations = declarations;
    }

    @Override
    public void check(ScopeChecker scope) throws CompilingException {
        // checks all the declarations
        for (VariableDeclaration declaration: variableDeclarations)
            declaration.check(scope);
    }
}
