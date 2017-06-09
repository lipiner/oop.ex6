package oop.ex6.validity.command_validity;

public class MethodDeclaration extends CommandLine {

    MethodDeclaration(String command){
        super(command);
    }

    @Override
    public boolean isOpenScope() {
        return true;
    }
}
