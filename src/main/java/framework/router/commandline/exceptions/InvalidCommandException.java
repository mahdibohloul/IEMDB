package framework.router.commandline.exceptions;

import infrastructure.exceptions.IemdbException;

public class InvalidCommandException extends IemdbException {

    public InvalidCommandException(String command) {
        super("Invalid command: " + command);
    }

    @Override
    public String toString() {
        return "InvalidCommand";
    }
}
