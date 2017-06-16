package oop.ex6.command_validity;

import oop.ex6.program_members.CompilingException;
import oop.ex6.program_members.ScopeChecker;

public class UnidentifiedCommands extends CommandLine {

    private ScopeChecker scope;
    private CommandLine commandLine;

    UnidentifiedCommands(ScopeChecker scope, CommandLine command){
        this.scope = scope;
        commandLine = command;
    }

    public void check() throws CompilingException {
        commandLine.check(scope);
    }

    @Override
    public void check(ScopeChecker scope)throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }
}
