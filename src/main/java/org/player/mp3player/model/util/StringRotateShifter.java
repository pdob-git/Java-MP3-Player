package org.player.mp3player.model.util;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StringRotateShifter {
    private String stringToShift;

    public String shiftLeftAndGet() {
        char charToRotate = stringToShift.charAt(0);
        String stringPartToShift = stringToShift.substring(1);
        stringToShift = stringPartToShift + charToRotate;
        return stringToShift;
    }
}
