package oop.ex6.validity;

import oop.ex6.program_members.CompilingException;
import oop.ex6.program_members.GlobalMembers;
import oop.ex6.program_members.ScopeChecker;
import oop.ex6.program_members.VariableWrapper;
import java.util.LinkedList;

/**
 * The class represents a code line that declares a new method
 */
public class MethodDeclaration extends CommandLine {

    private String methodName;
    private LinkedList<VariableDeclaration> methodVariableDeclares;

    /**
     * constructs a new MethodDeclaration instance when given the name and the declaration of the parameters
     * @param methodName the name of the declared method
     * @param methodVariableDeclares the declaration of the method parameters
     */
    MethodDeclaration(String methodName, LinkedList<VariableDeclaration> methodVariableDeclares){
        this.methodName = methodName;
        this.methodVariableDeclares = methodVariableDeclares;
    }

    @Override
    public void check(ScopeChecker scope) throws CompilingException{
        LinkedList<VariableWrapper> methodVariables = new LinkedList<VariableWrapper>();

        // creates the method parameters using the given CommandLines
        for (VariableDeclaration declareLine: methodVariableDeclares) {
            VariableWrapper variable = declareLine.check();
            methodVariables.add(variable);
        }
        // adding the method
        GlobalMembers.getInstance().addMethod(methodName, methodVariables, scope);
    }
}
