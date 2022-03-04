package framework.router.javalin.exceptions;

import infrastructure.exceptions.IemdbException;

public class PathNotFoundException extends IemdbException {
    public PathNotFoundException(String path) {
        super("Path not found: " + path);
    }
}
