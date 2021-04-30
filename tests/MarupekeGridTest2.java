import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests of MarupekeGrid for Part Two
 */
public class MarupekeGridTest2 {
    MarupekeGrid testGrid;

    @Before
    public void setUp(){
        testGrid = new MarupekeGrid(4);
    }

    @Test
    public void testIsLegalGrid_Legal() {
        //testgrid is automatically legal by design
        assertTrue(testGrid.isLegalGrid());
    }

    @Test
    public void testIsLegalGrid_Illegal() {
        testGrid.setX(0, 0, true);
        testGrid.setX(1, 0, true);
        testGrid.setX(2, 0, true);
        //(Added a violation)
        assertFalse(testGrid.isLegalGrid());
    }

    /*
        Illegalities captured are bi-directional, so these tests reflect that.
        As a result, checking the Top-Left diagonal and Bottom-Right diagonal occur in the same test, as do the Top-Right and Bottom-Left.
    */

    @Test
    public void testIllegalitiesInGridVertical(){
        testGrid.setX(0, 0, true);
        testGrid.setX(1, 0, true);
        testGrid.setX(2, 0, true);
        testGrid.setO(0, 2, true);
        testGrid.setO(1, 2, true);
        testGrid.setO(2, 2, true);
        List<Reason> expected = new ArrayList<Reason>();
        expected.add(new VerticalReason(0,0, PossibleValues.X));
        expected.add(new VerticalReason(0,2, PossibleValues.O));
        assertEquals(expected, testGrid.illegalitiesInGrid());
    }

    @Test
    public void testIllegalitiesInGridHorizontal(){
        testGrid.setX(0, 0, true);
        testGrid.setX(1, 0, true);
        testGrid.setX(2, 0, true);
        testGrid.setO(0, 2, true);
        testGrid.setO(1, 2, true);
        testGrid.setO(2, 2, true);
        List<Reason> expected = new ArrayList<Reason>();
        expected.add(new VerticalReason(0,0, PossibleValues.X));
        expected.add(new VerticalReason(0,2, PossibleValues.O));
        assertEquals(expected, testGrid.illegalitiesInGrid());
    }

    @Test
    public void testIllegalitiesInGridDiagonal_TL_BR(){
        List<Reason> expected = new ArrayList<Reason>();
        /* the following set the grid up as:
        X__
        _X_
        __X
         */
        testGrid.setX(0, 0, true);
        testGrid.setX(1, 1, true);
        testGrid.setX(2, 2, true);
        expected.add(new DiagonalReason(0,0, PossibleValues.X));
        expected.add(new DiagonalReason(2,2, PossibleValues.X)); //recognised from both sides as there's no directionality
        assertEquals(expected, testGrid.illegalitiesInGrid());
    }

    @Test
    public void testIllegalitiesInGridDiagonal_TR_BL(){
        List<Reason> expected = new ArrayList<Reason>();
        /* the following set the grid up as:
        __O
        _O_
        O__
         */
        testGrid.setO(2, 2, true);
        testGrid.setO(1, 1, true);
        testGrid.setO(0, 0, true);
        expected.add(new DiagonalReason(0,0, PossibleValues.O));
        expected.add(new DiagonalReason(2,2, PossibleValues.O));
        assertEquals(expected, testGrid.illegalitiesInGrid());
    }

    @Test
    public void testMarkX_Low() {
        testGrid.markX(-1, -1); //should print "Row or Column doesn't exist" and do nothing else
        assertNotEquals(PossibleValues.X.toString().charAt(0), testGrid.toString().charAt(0));
        //out of bounds - if both the charAt and y parameter in markO were set to 3, they would be equal
    }

    @Test
    public void testMarkX_Normal() {
        testGrid.markX(0, 1);
        assertEquals(PossibleValues.X.toString().charAt(0), testGrid.toString().charAt(1));
    }

    @Test
    public void testMarkX_High() {
        testGrid.markX(0, 4); //should print "Row or Column doesn't exist" and do nothing else
        assertNotEquals(PossibleValues.X.toString().charAt(0), testGrid.toString().charAt(4));
        //out of bounds - if both the charAt and y parameter in markO were set to 3, they would be equal
    }

    @Test
    public void testMarkO_Low() {
        testGrid.markO(-1, -1); //should print "Row or Column doesn't exist" and do nothing else
        assertNotEquals(PossibleValues.O.toString().charAt(0), testGrid.toString().charAt(0));
        //out of bounds - if both the charAt and y parameter in markO were set to 3, they would be equal
    }

    @Test
    public void testMarkO_Normal() {
        testGrid.markO(0, 1);
        assertEquals(PossibleValues.O.toString().charAt(0), testGrid.toString().charAt(1));
    }

    @Test
    public void testMarkO_High() {
        testGrid.markO(0, 4); //should print "Row or Column doesn't exist" and do nothing else
        assertNotEquals(PossibleValues.O.toString().charAt(0), testGrid.toString().charAt(4));
        //out of bounds - if both the charAt and y parameter in markO were set to 3, they would be equal
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testUnmark_Low() {
        //something must be set to test this, and we can't make other methods out of bounds when testing this, so I've gone with close-as-possible values
        testGrid.setX(0,0, true);
        assertEquals('X', testGrid.toString().charAt(0));
        assertFalse(testGrid.unmark(-1, -1));
        assertEquals('X', testGrid.toString().charAt(0));
    }

    @Test
    public void testUnmark_Normal() {
        testGrid.setX(0,0, true);
        assertEquals('X', testGrid.toString().charAt(0));
        assertTrue(testGrid.unmark(0,0));
        assertEquals('_', testGrid.toString().charAt(0));
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testUnmark_High() {
        //something must be set to test this, and we can't make other methods out of bounds when testing this, so I've gone with close-as-possible values
        testGrid.setX(0,3, true);
        assertEquals('X', testGrid.toString().charAt(3));
        assertFalse(testGrid.unmark(0, 4));
        assertEquals('X', testGrid.toString().charAt(0));
    }

    /*
        As a result of all tiles being blank by default, we cannot therefore test the puzzle being 'not filled'
        Therefore both tests for isPuzzleComplete can't test this.
    */

    @Test
    public void testIsPuzzleComplete_Legal_Filled() {
        //testGrid is already 'legal' (no violations, even if blank) and all of tileGrid is already filled by default
        assertTrue(testGrid.isPuzzleComplete());
    }

    @Test
    public void testIsPuzzleComplete_Illegal_Filled() {
        //to test an 'illegal' puzzle, we must introduce at least one violation - lets use testIllegalitiesInGridDiagonal_TL_BR's example:
        testGrid.setX(0, 0, true);
        testGrid.setX(1, 1, true);
        testGrid.setX(2, 2, true);
        assertFalse(testGrid.isPuzzleComplete());
    }
}