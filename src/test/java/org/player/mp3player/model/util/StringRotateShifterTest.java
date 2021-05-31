package org.player.mp3player.model.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringRotateShifterTest {

    @Test
    void shiftLeftAndGet() {
        String patternToShift="Shifter test Pattern";
        StringRotateShifter stringRotateShifter = new StringRotateShifter(patternToShift);

        assertNotNull(stringRotateShifter);
        assertEquals("hifter test PatternS",stringRotateShifter.shiftLeftAndGet());

        stringRotateShifter.shiftLeftAndGet();
        stringRotateShifter.shiftLeftAndGet();
        stringRotateShifter.shiftLeftAndGet();
        stringRotateShifter.shiftLeftAndGet();
        stringRotateShifter.shiftLeftAndGet();
        stringRotateShifter.shiftLeftAndGet();
        assertEquals("test PatternShifter ", stringRotateShifter.shiftLeftAndGet());

    }
}