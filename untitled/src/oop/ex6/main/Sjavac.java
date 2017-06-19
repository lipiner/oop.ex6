package oop.ex6.main;

import oop.ex6.validity.SyntaxChecker;
import oop.ex6.program_members.CompilingException;
import oop.ex6.program_members.GlobalMembers;
import oop.ex6.program_members.GlobalScope;
import oop.ex6.program_members.Method;

import java.io.*;

public class Sjavac {

    private static final int FILE_NAME_POSITION = 0,
        ARGS_NUMBER = 1;
    private static final String
        CORRECT_CODE = "0",
        COMPILATION_PROBLEM = "1",
        IO_PROBLEM = "2",
        SJAVA_FILE_SUFFIX = ".sjava",
        UNCLOSED_METHOD_MSG = "Method declaration was not closed",
        WRONG_ARGUMENTS_NUMBER_MESSAGE = "Wrong number of arguments",
        WRONG_FILE_TYPE_MESSAGE = "Wrong file type: the given file is not sJava file";

    /**
     * The main method that runs the program and checks the file. Prints 0 for valid code, 1 for invalid code and 2
     * for IO problems while reading the file.
     * @param args one argument- the file name to be checked
     */
    public static void main (String[] args) throws InvalidInputException {
        // checking input validity
        if (args.length != ARGS_NUMBER)
            throw new InvalidInputException(WRONG_ARGUMENTS_NUMBER_MESSAGE);
        if (!args[FILE_NAME_POSITION].endsWith(SJAVA_FILE_SUFFIX))
            throw new InvalidInputException(WRONG_FILE_TYPE_MESSAGE);

        RandomAccessFile reader = null;
        try {
            reader = new RandomAccessFile(args[FILE_NAME_POSITION], "r");
            // creating all syntax patterns
            SyntaxChecker.createPatterns();

            // checking the global scope of the code
            GlobalScope scope = new GlobalScope();
            readGlobalScope(scope, reader);
            // setting the reader to the beginning of the file and checking the methods
            reader.seek(0);
            readMethods(scope, reader);

            System.out.println(CORRECT_CODE);

        }
        catch (IOException e) {
            System.out.println(IO_PROBLEM);
        }
        catch (CompilingException e) {
            System.err.println(e.getMessage());
            System.out.println(COMPILATION_PROBLEM);
        } finally {
            //closes the file
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e){
                System.out.println(IO_PROBLEM);
            }

            // deletes the GlobalMembers instance to nullify the global members of the program
            GlobalMembers.nullifyInstance();
        }

    }

    /**
     * Checks the global scope of the file
     * @param scope the global scope (GlobalScope object)
     * @param reader the file reader
     * @throws IOException if IO problem occurred while reading the file
     * @throws CompilingException if the global scope did not compile
     */
    private static void readGlobalScope(GlobalScope scope, RandomAccessFile reader)
            throws IOException, CompilingException{
        String newLine = reader.readLine();
        /* the scope depth represents in what depth of scopes the reader is found, when 0 is the global scope, and
        when opening new scope the number id going up.*/
        int scopeDepth = 0;

        while (newLine != null) {
            GlobalMembers.getInstance().updateLineNumber();
            // reading only the global scope
            if (scopeDepth == 0)
                scope.readLine(newLine);

            // checking if there is a change in the depth of the scope
            SyntaxChecker.LineStatus lineStatus = SyntaxChecker.getLineType(newLine);
            if (lineStatus == SyntaxChecker.LineStatus.OPEN_SCOPE)
                scopeDepth ++;
            else if (lineStatus == SyntaxChecker.LineStatus.CLOSE_SCOPE)
                scopeDepth --;

            newLine = reader.readLine();
        }

        // checking if there is unclosed scope, so the depth in the end of the program is not 0
        if (scopeDepth > 0)
            throw new CompilingException(UNCLOSED_METHOD_MSG);
    }

    /**
     * Checks the methods declared in the file
     * @param scope the global scope
     * @param reader the file reader
     * @throws IOException if IO problem occurred while reading the file
     * @throws CompilingException if the global scope did not compile
     */
    private static void readMethods(GlobalScope scope, RandomAccessFile reader)
            throws IOException, CompilingException{
        int currentLineNumber = 1;
        // checking all the methods
        for (Method method: GlobalMembers.getInstance().getAllMethods()) {
            method.openScope(); // opens new inner scope for the method
            // ignoring the global lines until getting to the method lines
            while (currentLineNumber < method.getLineNumber() + 1) {
                reader.readLine();
                currentLineNumber ++;
            }

            // checking the method lines until the method is closed
            while (!scope.isMethodClosed()) {
                currentLineNumber++;
                scope.readLine(reader.readLine());
            }
        }
    }


}
