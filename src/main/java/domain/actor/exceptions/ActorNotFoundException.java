package domain.actor.exceptions;

import infrastructure.exceptions.IemdbException;

public class ActorNotFoundException extends IemdbException {
    public ActorNotFoundException(Integer id) {
        super("Actor with id " + id + " not found");
    }

    @Override
    public String toString() {
        return "ActorNotFound";
    }
}
