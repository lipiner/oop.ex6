package oop.ex6.command_validity;

import oop.ex6.program_members.GlobalMembers;
import oop.ex6.program_members.ScopeChecker;
import oop.ex6.program_members.Variable;
import oop.ex6.program_members.VariableWrapper;

import java.util.LinkedList;

public class MethodDeclaration extends CommandLine {

    String methodName;
    LinkedList<VariableDeclaration> methodVariableDeclares;

    public MethodDeclaration(String methodName, LinkedList<VariableDeclaration> methodVariableDeclares){
        this.methodName = methodName;
        this.methodVariableDeclares = methodVariableDeclares;
    }

    @Override
    public void check(ScopeChecker scope) {

        LinkedList<VariableWrapper> methodVariables = new LinkedList<VariableWrapper>();
        for (VariableDeclaration declareLine: methodVariableDeclares) {
            VariableWrapper variable = declareLine.check();
            methodVariables.add(variable);
        }

        GlobalMembers.getInstance().addMethod(methodName, methodVariables, scope);

    }
}
