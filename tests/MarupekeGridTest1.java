import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MarupekeGridTest1 {
    MarupekeGrid testGrid;
    static int testGridSize = 3;

    @Before
    public void setUp(){
        testGrid = new MarupekeGrid(testGridSize);
    }

    //region Constructor Correct Size Tests

    @Test(expected = IndexOutOfBoundsException.class)
    public void testCorrectSizeXOver11(){
        testGrid.setX(testGridSize + 1, testGridSize + 1, true);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testCorrectSizeOOver55(){
        testGrid.setO(testGridSize + 5, testGridSize + 5, true);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testCorrectSizeSolidOver1010(){
        testGrid.setSolid(testGridSize + 10, testGridSize + 10);
    }
    //endregion

    //region Set Method Tests (setX, setO & setSolid)

    /*
        Note: Whether canEdit is set to true or false in setX or setO is irrelevant due to the nature of the test environment (re-runs setUp() for each test)

        The newline character is accounted for in checking toString so charAt references for testSet_11 and testSet_22s will be advanced by 1 and 2 respectively
        E.g. "123n456n789", say you want the element at 2,2 in the grid, but because of toString's newline character (which only counts as one character),
         you need to check the one at char 5 rather than the expected 4.
        This applies exponentially for every following line - if you're looking for the element you'd expect at char 7, you need to look at char 9
    */

    //Set X Tests

    @Test
    public void testSetX00() {
        //top left corner
        assertTrue(testGrid.setX(0,0, true));
        assertEquals('X', testGrid.toString().charAt(0));
    }

    @Test
    public void testSetX11() {
        //center
        assertTrue(testGrid.setX(1,1, true));
        assertEquals('X', testGrid.toString().charAt(5));
    }

    @Test
    public void testSetX22() {
        //bottom right corner
        assertTrue(testGrid.setX(2,2, true));
        assertEquals('X', testGrid.toString().charAt(10));

    }

    //Set O Tests

    @Test
    public void testSetO00() {
        //top left corner
        assertTrue(testGrid.setO(0,0, true));
        assertEquals('O', testGrid.toString().charAt(0));
    }

    @Test
    public void testSetO11() {
        //center
        assertTrue(testGrid.setO(1,1, true));
        assertEquals('O', testGrid.toString().charAt(5));

    }

    @Test
    public void testSetO22() {
        //bottom right corner
        assertTrue(testGrid.setO(2,2, true));
        assertEquals('O', testGrid.toString().charAt(10));
    }

    //Set Solid Tests

    @Test
    public void testSetSolid00() {
        //top left corner
        assertTrue(testGrid.setSolid(0,0));
        assertEquals('#', testGrid.toString().charAt(0));

    }

    @Test
    public void testSetSolid11() {
        //middle center
        assertTrue(testGrid.setSolid(1,1));
        assertEquals('#', testGrid.toString().charAt(5));

    }

    @Test
    public void testSetSolid22() {
        //bottom right corner
        assertTrue(testGrid.setSolid(2,2));
        assertEquals('#', testGrid.toString().charAt(10));

    }

    //Whole Grid Tests
    @Test
    public void testFillWholeGridX() {
        for (int x = 0; x < testGridSize; x++) {
            for (int y = 0; y < testGridSize; y++) {
                assertTrue(testGrid.setX(x,y, true));
            }
        }
        //row 1
        assertEquals('X', testGrid.toString().charAt(0));
        assertEquals('X', testGrid.toString().charAt(1));
        assertEquals('X', testGrid.toString().charAt(2));
        //row 2
        assertEquals('X', testGrid.toString().charAt(4));
        assertEquals('X', testGrid.toString().charAt(5));
        assertEquals('X', testGrid.toString().charAt(6));
        //row 3
        assertEquals('X', testGrid.toString().charAt(8));
        assertEquals('X', testGrid.toString().charAt(9));
        assertEquals('X', testGrid.toString().charAt(10));
    }

    @Test
    public void testFillWholeGridO() {
        for (int x = 0; x < testGridSize; x++) {
            for (int y = 0; y < testGridSize; y++) {
                assertTrue(testGrid.setO(x,y, true));
            }
        }
        //row 1
        assertEquals('O', testGrid.toString().charAt(0));
        assertEquals('O', testGrid.toString().charAt(1));
        assertEquals('O', testGrid.toString().charAt(2));
        //row 2
        assertEquals('O', testGrid.toString().charAt(4));
        assertEquals('O', testGrid.toString().charAt(5));
        assertEquals('O', testGrid.toString().charAt(6));
        //row 3
        assertEquals('O', testGrid.toString().charAt(8));
        assertEquals('O', testGrid.toString().charAt(9));
        assertEquals('O', testGrid.toString().charAt(10));
    }

    @Test
    public void testFillWholeGridSolid() {
        for (int x = 0; x < testGridSize; x++) {
            for (int y = 0; y < testGridSize; y++) {
                assertTrue(testGrid.setSolid(x,y));
            }
        }
        //row 1
        assertEquals('#', testGrid.toString().charAt(0));
        assertEquals('#', testGrid.toString().charAt(1));
        assertEquals('#', testGrid.toString().charAt(2));
        //row 2
        assertEquals('#', testGrid.toString().charAt(4));
        assertEquals('#', testGrid.toString().charAt(5));
        assertEquals('#', testGrid.toString().charAt(6));
        //row 3
        assertEquals('#', testGrid.toString().charAt(8));
        assertEquals('#', testGrid.toString().charAt(9));
        assertEquals('#', testGrid.toString().charAt(10));
    }
    //endregion

    //region Editable Tests
    /*
        (By Set value)
    */
    @Test
    public void testEditableX00() {
        testGrid.setX(0, 0, false);
        assertFalse(testGrid.setX(0,0,false)); //false in 2nd setX is irrelevant
    }

    @Test
    public void testEditableX11() {
        testGrid.setX(1, 1, false);
        assertFalse(testGrid.setX(1,1,false)); //false in 2nd setX is irrelevant
    }

    @Test
    public void testEditableX22() {
        testGrid.setX(2, 2, false);
        assertFalse(testGrid.setX(2,2,false)); //false in 2nd setX is irrelevant
    }

    @Test
    public void testEditableO00() {
        testGrid.setO(0, 0, false);
        assertFalse(testGrid.setO(0,0,false)); //false in 2nd setO is irrelevant
    }

    @Test
    public void testEditableO11() {
        testGrid.setO(1, 1, false);
        assertFalse(testGrid.setO(1,1,false)); //false in 2nd setO is irrelevant
    }

    @Test
    public void testEditableO22() {
        testGrid.setO(2, 2, false);
        assertFalse(testGrid.setO(2,2,false)); //false in 2nd setO is irrelevant
    }

    @Test
    public void testEditableSolid00() {
        testGrid.setSolid(0,0);
        assertFalse(testGrid.setSolid(0,0));
    }

    @Test
    public void testEditableSolid11() {
        testGrid.setSolid(1,1);
        assertFalse(testGrid.setSolid(1,1));
    }

    @Test
    public void testEditableSolid22() {
        testGrid.setSolid(2,2);
        assertFalse(testGrid.setSolid(2,2));
    }
    //endregion

    //region toString Tests
    /*
        toString is used a lot in testing the set methods so this is just checking edges & corners
        if you're looking for four precalculated strings, look at all the chars checked in the Setting Tests section
    */
    @Test
    public void testToString(){
        String expected = "##X\n__X\n_OO\n";
        // This string contains edges and corners of all types (Solid, X, O and Blank)
        // ##X (corner solid, edge solid, X corner)
        // __X (edge blank, X edge)
        // _OO (corner blank, O edge, O corner)

        testGrid.setSolid(0, 0);
        testGrid.setSolid(0,1);
        testGrid.setX(0,2, false);
        testGrid.setX(1,2, false);
        testGrid.setO(2,1, false);
        testGrid.setO(2,2, false);

        assertEquals(expected, testGrid.toString());
    }
    //endregion

    //region randomPuzzle Tests

    @Test
    public void testRandomPuzzleNull(){
        try{
            testGrid = MarupekeGrid.randomPuzzle(3, 5, 5, 5);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return;
        }

        //5+5+5 (15) > 3*3 (9), so randomPuzzle() will return null
        assertNull(testGrid);
    }

    @Test
    public void testRandomPuzzle5x5_5_5_5() {
        try{
            testGrid = MarupekeGrid.randomPuzzle(5, 3, 3, 3);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return;
        }

        int fillCount = 0;
        int xCount = 0;
        int oCount = 0;
        assert testGrid != null;
        String toStr = testGrid.toString();
        for (int i = 0; i < toStr.length(); i++) {
            switch (toStr.charAt(i)) {
                case '#':
                    fillCount++;
                    break;
                case 'X':
                    xCount++;
                    break;
                case 'O':
                    oCount++;
            }
        }
        //same num of filled tiles, Xs and Os
        assertEquals(3, fillCount);
        assertEquals(3, xCount);
        assertEquals(3, oCount);
    }

    @Test
    public void testRandomPuzzle7x7_7_7_7() {
        try {
            testGrid = MarupekeGrid.randomPuzzle(7, 7, 7, 7);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return;
        }
        int fillCount = 0;
        int xCount = 0;
        int oCount = 0;
        assert testGrid != null;
        String toStr = testGrid.toString();
        for (int i = 0; i < toStr.length(); i++) {
            switch (toStr.charAt(i)) {
                case '#':
                    fillCount++;
                    break;
                case 'X':
                    xCount++;
                    break;
                case 'O':
                    oCount++;
            }
        }
        //same num of filled tiles, Xs and Os
        assertEquals(7, fillCount);
        assertEquals(7, xCount);
        assertEquals(7, oCount);
    }

    @Test
    public void testRandomPuzzle10x10_10_10_10() {
        try {
            testGrid = MarupekeGrid.randomPuzzle(10, 10, 10, 10);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return;
        }
        int fillCount = 0;
        int xCount = 0;
        int oCount = 0;
        assert testGrid != null;
        String toStr = testGrid.toString();
        for (int i = 0; i < toStr.length(); i++) {
            switch (toStr.charAt(i)) {
                case '#':
                    fillCount++;
                    break;
                case 'X':
                    xCount++;
                    break;
                case 'O':
                    oCount++;
            }
        }
        //same num of filled tiles, Xs and Os
        assertEquals(10, fillCount);
        assertEquals(10, xCount);
        assertEquals(10, oCount);
    }
    //endregion

}
