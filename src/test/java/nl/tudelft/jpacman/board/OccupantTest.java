package nl.tudelft.jpacman.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test suite to confirm that {@link Unit}s correctly (de)occupy squares.
 *
 * @author Jeroen Roosen 
 *
 */
class OccupantTest {

    /**
     * The unit under test.
     */
    private Unit unit;

    /**
     * Resets the unit under test.
     */
    @BeforeEach
    void setUp() {
        unit = new BasicUnit();
    }

    /**
     * Asserts that a unit has no square to start with.
     */
    @Test
    void noStartSquare() {
        // iff unit has no square start
        assertThat(unit.hasSquare()).isEqualTo(false);
    }

    /**
     * Tests that the unit indeed has the target square as its base after
     * occupation.
     */
    @Test
    void testOccupy() {
        // if a unit is occupied by a(ny) basic square, then one should contain the other.
        Square square = new BasicSquare();

        unit.occupy(square);

        assertThat(unit.hasSquare()).isEqualTo(true);
        assertThat(square.getOccupants().contains(unit)).isEqualTo(true);
    }

    /**
     * Test that the unit indeed has the target square as its base after
     * double occupation.
     */
    @Test
    void testReoccupy() {
        // Remove the following placeholder:
        Square square1 = new BasicSquare();
        Square square2 = new BasicSquare();

        unit.occupy(square1);
        unit.occupy(square2);

        assertThat(unit.getSquare()).isEqualTo(square2);
        assertThat(square1.getOccupants().contains(unit)).isEqualTo(false);
        assertThat(square2.getOccupants().contains(unit)).isEqualTo(true);
    }
}
