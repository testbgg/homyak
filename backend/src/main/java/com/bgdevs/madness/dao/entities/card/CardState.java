package com.bgdevs.madness.dao.entities.card;

/**
 * @author Nikita Shaldenkov
 */
public enum CardState {

    REQUESTED {
        @Override
        public boolean canTransitTo(CardState newState) {
            return newState == ACTIVE || newState == BLOCKED || newState == CLOSED;
        }
    },
    ACTIVE {
        @Override
        public boolean canTransitTo(CardState newState) {
            return newState == BLOCKED || newState == CLOSED;
        }
    },
    BLOCKED {
        @Override
        public boolean canTransitTo(CardState newState) {
            return newState == ACTIVE || newState == CLOSED;
        }
    },
    CLOSED {
        @Override
        public boolean canTransitTo(CardState newState) {
            return false;
        }
    };

    public static boolean canBeReissued(CardState state) {
        return state == ACTIVE || state == BLOCKED || state == CLOSED;
    }

    public abstract boolean canTransitTo(CardState newState);
}
