package domain.user.exceptions;

import infrastructure.exceptions.IemdbException;

public class AgeLimitErrorException extends IemdbException {
    public AgeLimitErrorException(Integer userAge, Integer ageLimit) {
        super("User age is " + userAge + " and age limit is " + ageLimit);
    }
}
