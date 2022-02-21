package domain.comment.valueobjects;

import domain.comment.exceptions.InvalidVoteValueException;

public enum VoteType {
    NONE,
    LIKE,
    DISLIKE;

    public static VoteType fromInt(int i) throws InvalidVoteValueException {
        return switch (i) {
            case 0 -> NONE;
            case 1 -> LIKE;
            case -1 -> DISLIKE;
            default -> throw new InvalidVoteValueException(i);
        };
    }
}
