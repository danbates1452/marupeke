import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for MarupekeTile
 */
public class MarupekeTileTest {
    MarupekeTile testTile;

    @Before
    public void setUp(){
        testTile = new MarupekeTile();
    }

    @Test
    public void testConstructorNoParams(){
        testTile = new MarupekeTile();
        assertEquals(PossibleValues.blank, testTile.getTileValue());
        assertTrue(testTile.isEditable());
    }

    @Test
    public void testConstructorOnlyValueBlank(){
        testTile = new MarupekeTile(PossibleValues.blank);
        assertEquals(PossibleValues.blank, testTile.getTileValue());
        assertTrue(testTile.isEditable());
    }

    @Test
    public void testConstructorOnlyValueSolid(){
        testTile = new MarupekeTile(PossibleValues.solid);
        assertEquals(PossibleValues.solid, testTile.getTileValue());
        assertTrue(testTile.isEditable());
    }

    @Test
    public void testConstructorOnlyValueX(){
        testTile = new MarupekeTile(PossibleValues.X);
        assertEquals(PossibleValues.X, testTile.getTileValue());
        assertTrue(testTile.isEditable());
    }

    @Test
    public void testConstructorOnlyValueO(){
        testTile = new MarupekeTile(PossibleValues.O);
        assertEquals(PossibleValues.O, testTile.getTileValue());
        assertTrue(testTile.isEditable());
    }

    @Test
    public void testConstructorValueBlankEditableFalse(){
        testTile = new MarupekeTile(PossibleValues.blank, false);
        assertFalse(testTile.isEditable());
        assertEquals(PossibleValues.blank, testTile.getTileValue());
    }

    @Test
    public void testConstructorValueSolidEditableTrue(){
        testTile = new MarupekeTile(PossibleValues.solid, true);
        assertTrue(testTile.isEditable());
        assertEquals(PossibleValues.solid, testTile.getTileValue());
    }

    @Test
    public void testConstructorValueXEditableFalse(){
        testTile = new MarupekeTile(PossibleValues.X, false);
        assertFalse(testTile.isEditable());
        assertEquals(PossibleValues.X, testTile.getTileValue());
    }

    @Test
    public void testConstructorValueOEditableTrue(){
        testTile = new MarupekeTile(PossibleValues.O, true);
        assertTrue(testTile.isEditable());
        assertEquals(PossibleValues.O, testTile.getTileValue());
    }

    @Test
    public void testSetEditable(){
        assertTrue(testTile.isEditable()); //Assuming default instantiation

        testTile.setEditable(false);
        assertFalse(testTile.isEditable());
    }

    @Test
    public void testToStringBlank(){
        testTile.setTileValue(PossibleValues.blank); //should already be set to this, but just to make sure
        assertEquals("_", testTile.toString());
    }

    @Test
    public void testToStringSolid(){
        testTile.setTileValue(PossibleValues.solid);
        assertEquals("#", testTile.toString());
    }

    @Test
    public void testToStringX(){
        testTile.setTileValue(PossibleValues.X);
        assertEquals("X", testTile.toString());
    }

    @Test
    public void testToStringO(){
        testTile.setTileValue(PossibleValues.O);
        assertEquals("O", testTile.toString());
    }
}
