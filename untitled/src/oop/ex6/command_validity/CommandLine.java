package oop.ex6.command_validity;

import oop.ex6.program_members.CompilingException;
import oop.ex6.program_members.ScopeChecker;

/**
 * The class represents a line of code from the code file. It is an abstract class that all types of code lines
 * inherit from.
 */
public abstract class CommandLine {

    /**
     * Checks if the line of code is valid. Otherwise, throws exception.
     * @param scope the scope from which the line was read
     * @throws CompilingException if the line of code is not valid
     */
    public abstract void check(ScopeChecker scope) throws CompilingException;

}
