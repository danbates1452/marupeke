import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

/**
 * Class to act as a virtual <b>Marupeke Grid</b>
 * Marupeke Model
 * @author CandNo 234558
 */
public class MarupekeGrid {
    private MarupekeTile[][] tileGrid;
    private Exception TooManyMarkedSquares;

    /**
     * Constructs a blank MarupekeGrid of provided size
     * @param size int (width and length)
     */

    public MarupekeGrid(int size) {
        if (size < 3) size = 3; //forces minimum size of 3
        else if (size > 10) size = 10; //forces maximum size of 10

        tileGrid = new MarupekeTile[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                tileGrid[x][y] = new MarupekeTile(); //create new tile
            }
        }
    }

    public MarupekeGrid(MarupekeTile[][] tiles) {
        tileGrid = new MarupekeTile[tiles.length][tiles.length];
        System.arraycopy(tiles,0,  tileGrid, 0, tiles.length);
    }

    /**
     * If editable, sets the grid cell at provided spot x,y to solid. Regardless, returns whether the grid cell is editable or not
     * @param x int
     * @param y int
     * @return boolean (value of editable)
     */
    public boolean setSolid(int x, int y) {
        if (tileGrid[x][y].isEditable()) {
            tileGrid[x][y].setTileValue(PossibleValues.solid);
            tileGrid[x][y].setEditable(false);
            return true; //return old value of editable rather than the new one
        }
        else return false;
    }

    /**
     * If editable, sets the grid cell at provided spot x,y to X. Regardless, returns whether the grid cell is editable or not
     * @param x int
     * @param y int
     * @return boolean (value of editable)
     */
    public boolean setX(int x, int y, boolean canEdit) {
        if (tileGrid[x][y].isEditable()) {
            tileGrid[x][y].setTileValue(PossibleValues.X);
            tileGrid[x][y].setEditable(canEdit);
            return true; //return old value of editable rather than the new one
        }
        else return false;
    }

    /**
     * If editable, sets the grid cell at provided spot x,y to O. Regardless, returns whether the grid cell is editable or not
     * @param x int
     * @param y int
     * @return boolean (value of editable)
     */
    public boolean setO(int x, int y, boolean canEdit) {
        if (tileGrid[x][y].isEditable()) {
            tileGrid[x][y].setTileValue(PossibleValues.O);
            tileGrid[x][y].setEditable(canEdit);
            return true; //return old value of editable rather than the new one
        }
        else return false;
    }

    /**
     * Returns a randomly generated MarupekeGrid based on supplied numFill, numX and numO parameters
     * If the sum of these parameters are greater than the full size (size^2) of the grid then null is returned instead
     * @param size int (width and length of the grid)
     * @param numFill int (number of Solid tiles)
     * @param numX int (number of X tiles)
     * @param numO int (number of O tiles)
     * @return MarupekeGrid (generated randomly)
     */
    public static MarupekeGrid randomPuzzle(int size, int numFill, int numX, int numO) throws TooManyMarkedSquares {
        int markedSquares = numFill + numX + numO;
        if (markedSquares > (size*size)) return null;
        else if (markedSquares > ((size*size) / 2)) throw new TooManyMarkedSquares("Marked Squares are greater than half the available tiles.");
        MarupekeGrid newPuzzle = new MarupekeGrid(size);

        Random rand = new Random();

        for (int si = 0; si < numFill; si++) {
            boolean solidValid = false;
            //if solid is not filled validly, repeat.
            //This should not go on forever as there should be space for all Solids, Xs and Os due to the initial return null.
            while (!solidValid) solidValid = newPuzzle.setSolid(rand.nextInt(size), rand.nextInt(size));
        }

        for (int xi = 0; xi < numX; xi++) {
            boolean xValid = false;
            //if x is not filled validly, repeat.
            //This should not go on forever as there should be space for all Solids, Xs and Os due to the initial return null.
            while (!xValid) xValid = newPuzzle.setX(rand.nextInt(size), rand.nextInt(size), false);
        }

        for (int oi = 0;oi < numO; oi++) {
            boolean oValid = false;
            //if o is not filled validly, repeat.
            //This should not go on forever as there should be space for all Solids, Xs and Os due to the initial return null.
            while (!oValid) oValid = newPuzzle.setO(rand.nextInt(size), rand.nextInt(size), false);
        }

        return newPuzzle;
    }

    /**
     * Returns a text-based version of the current state of the MarupekeGrid, based on the tileGrid[][] tileValues
     * @return String - result of str.toString(), a grid in the format (where N represents the newline character) 012N345N678
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int x = 0; x < tileGrid[0].length; x++) {
            for (int y = 0; y < tileGrid[1].length; y++) {
                str.append(tileGrid[x][y].toString());
            }
            str.append("\n");
        }
        return str.toString();
    }

    /**
     * Returns whether the tileGrid is legal or not (no more than 2 consecutive symbols of the same type)
     * @return Boolean - is legal or not
     */
    public boolean isLegalGrid(){
        final int gridSize = tileGrid.length - 1;

        for (int x = 0; x <= gridSize; x++) {
            for (int y = 0; y <= gridSize; y++) {
                if (!(tileGrid[x][y].getTileValue().equals(PossibleValues.X)
                        || tileGrid[x][y].getTileValue().equals(PossibleValues.O))) continue; //skip this index if not on an X or O

                boolean hConsecutive = false;
                if (y + 2 <= gridSize){
                    hConsecutive = (tileGrid[x][y].getTileValue().equals(tileGrid[x][y + 1].getTileValue()))
                            && (tileGrid[x][y].getTileValue().equals(tileGrid[x][y + 2].getTileValue()));
                }
                // hConsecutive stores the boolean of tiles in horizontal sequence: ((1st tile = 2nd tile) && (1st tile = 3rd tile))
                // so will be true if more than two (3 or more) consecutive cells are the same symbol horizontally

                boolean vConsecutive = false;
                if (x + 2 <= gridSize) {
                    vConsecutive = (tileGrid[x][y].getTileValue().equals(tileGrid[x + 1][y].getTileValue()))
                            && (tileGrid[x][y].getTileValue().equals(tileGrid[x + 2][y].getTileValue()));
                }
                // vConsecutive stores the boolean of tiles in vertical sequence: ((1st tile = 2nd tile) && (1st tile = 3rd tile))
                // so will be true if more than two (3 or more) consecutive cells are the same symbol vertically

                boolean dConsecutive = false; //store boolean value of whether a line of 3+ consecutive Xs or Os has been found diagonally
                //top left
                if (x >= 2 && y >= 2){
                    if (tileGrid[x][y].getTileValue().equals(tileGrid[x - 1][y - 1].getTileValue())
                            && tileGrid[x][y].getTileValue().equals(tileGrid[x - 2][y - 2].getTileValue())) dConsecutive = true;
                }
                //top right
                if ((x + 2 <= gridSize) && y >= 2){
                    if (tileGrid[x][y].getTileValue().equals(tileGrid[x + 1][y - 1].getTileValue())
                            && tileGrid[x][y].getTileValue().equals(tileGrid[x + 2][y - 2].getTileValue())) dConsecutive = true;
                }
                //bottom left
                if (x >= 2 && (y + 2 <= gridSize)){
                    if (tileGrid[x][y].getTileValue().equals(tileGrid[x - 1][y + 1].getTileValue())
                            && tileGrid[x][y].getTileValue().equals(tileGrid[x - 2][y + 2].getTileValue())) dConsecutive = true;
                }
                //bottom right
                if ((x + 2 <= gridSize) && (y + 2 <= gridSize)){
                    if (tileGrid[x][y].getTileValue().equals(tileGrid[x + 1][y + 1].getTileValue())
                            && tileGrid[x][y].getTileValue().equals(tileGrid[x + 2][y + 2].getTileValue())) dConsecutive = true;
                }

                if (hConsecutive || vConsecutive || dConsecutive) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns a List of Reason, where the Reasons are violations detected in the current tileGrid
     * If isLegalGrid is true, returns null instead as there won't be any violations
     * @return List&lt;Reason&gt; - list of violations in tileGrid
     */
    public List<Reason> illegalitiesInGrid() {
        if(isLegalGrid()) return null;
        List<Reason> illegalList = new ArrayList<Reason>();

        final int gridSize = tileGrid.length - 1;

        for (int x = 0; x <= gridSize; x++) {
            for (int y = 0; y <= gridSize; y++) {
                if (!(tileGrid[x][y].getTileValue().equals(PossibleValues.X)
                        || tileGrid[x][y].getTileValue().equals(PossibleValues.O))) continue; //skip this index if not on an X or O

                if (y + 2 <= gridSize){
                    if ((tileGrid[x][y].getTileValue().equals(tileGrid[x][y + 1].getTileValue()))
                            && (tileGrid[x][y].getTileValue().equals(tileGrid[x][y + 2].getTileValue()))){
                        //if Horizontally Consecutive - similar logic to isLegalGrid()
                        illegalList.add(new HorizontalReason(x, y, tileGrid[x][y].getTileValue()));
                    }
                }

                if (x + 2 <= gridSize) {
                    if ((tileGrid[x][y].getTileValue().equals(tileGrid[x + 1][y].getTileValue()))
                            && (tileGrid[x][y].getTileValue().equals(tileGrid[x + 2][y].getTileValue()))) {
                        //if Vertically Consecutive - similar logic to isLegalGrid()
                        illegalList.add(new VerticalReason(x, y, tileGrid[x][y].getTileValue()));
                    }
                }

                //top left
                if (x >= 2 && y >= 2){
                    if (tileGrid[x][y].getTileValue().equals(tileGrid[x - 1][y - 1].getTileValue())
                            && tileGrid[x][y].getTileValue().equals(tileGrid[x - 2][y - 2].getTileValue())) illegalList.add(new DiagonalReason(x, y, tileGrid[x][y].getTileValue()));
                }
                //top right
                if ((x + 2 <= gridSize) && y >= 2){
                    if (tileGrid[x][y].getTileValue().equals(tileGrid[x + 1][y - 1].getTileValue())
                            && tileGrid[x][y].getTileValue().equals(tileGrid[x + 2][y - 2].getTileValue())) illegalList.add(new DiagonalReason(x, y, tileGrid[x][y].getTileValue()));
                }
                //bottom left
                if (x >= 2 && (y + 2 <= gridSize)){
                    if (tileGrid[x][y].getTileValue().equals(tileGrid[x - 1][y + 1].getTileValue())
                            && tileGrid[x][y].getTileValue().equals(tileGrid[x - 2][y + 2].getTileValue())) illegalList.add(new DiagonalReason(x, y, tileGrid[x][y].getTileValue()));
                }
                //bottom right
                if ((x + 2 <= gridSize) && (y + 2 <= gridSize)){
                    if (tileGrid[x][y].getTileValue().equals(tileGrid[x + 1][y + 1].getTileValue())
                            && tileGrid[x][y].getTileValue().equals(tileGrid[x + 2][y + 2].getTileValue())) illegalList.add(new DiagonalReason(x, y, tileGrid[x][y].getTileValue()));
                }
            }
        }

        return illegalList;
    }

    /**
     * Marks coordinates on the grid as X for the user (used in conjunction with user-input)
     * Prints "Row or Column doesn't exist" if the value is out of bounds
     * @param x int
     * @param y int
     */
    public void markX(int x, int y){
        if (x > (tileGrid.length - 1) || y > (tileGrid.length - 1) || x < 0 || y < 0) System.out.println("Row or Column doesn't exist");
        else setX(x, y, true);
    }

    /**
     * Marks coordinates on the grid as O for the user (used in conjunction with user-input)
     * Prints "Row or Column doesn't exist" if the value is out of bounds
     * @param x int
     * @param y int
     */
    public void markO(int x, int y){
        if (x > (tileGrid.length - 1) || y > (tileGrid.length - 1) || x < 0 || y < 0) System.out.println("Row or Column doesn't exist");
        else setO(x, y, true);
    }

    /**
     * 'Unmarks' coordinates on the grid (sets as blank) for the user (used in conjunction with user-input)
     * @param x int
     * @param y int
     */
    public boolean unmark(int x, int y){
        if (tileGrid[x][y].isEditable()){
            tileGrid[x][y].setTileValue(PossibleValues.blank);
            return true;
        }
        return false;
    }

    /**
     * Returns a boolean value denoting whether a puzzle is deemed 'complete' (isLegalGrid is true, and all tiles have filled values)
     * @return Boolean - is puzzle complete or not
     */
    public boolean isPuzzleComplete(){
        if (isLegalGrid()){
            int tilesFilled = 0;
            for (int x = 0; x < tileGrid.length; x++) {
                for (int y = 0; y < tileGrid.length; y++) {
                    switch (tileGrid[x][y].getTileValue()){
                        case X:
                        case O:
                        case solid:
                        case blank:
                            tilesFilled++;
                    }
                }
            }
            return tilesFilled == tileGrid.length * tileGrid.length;
        }
        return false;
    }

    /**
     * Returns the current value(s) of the tileGrid
     * @return MarupekeTile[][] tileGrid
     */
    public MarupekeTile[][] getTileGrid() {
        return tileGrid;
    }

    /**
     * Returns if the puzzle is fully filled in
     * @return Boolean whether the puzzle is filled (no blank tiles)
     */
    public boolean isPuzzleFilled(){
        if (isLegalGrid()){
            for (int x = 0; x < tileGrid.length; x++) {
                for (int y = 0; y < tileGrid.length; y++) {
                    if (tileGrid[x][y].getTileValue().equals(PossibleValues.blank)) return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns a boolean = whether the current grid is reasonably solved (filled in and is legal)
     * @return Boolean isPuzzleFilled() && isLegalGrid()
     */
    public boolean isSolved() {
        return isPuzzleFilled() && isLegalGrid();
    }

    public MarupekeGrid solvePuzzle() {
        LinkedList<GridState> toSearch = new LinkedList<GridState>();
        LinkedList<GridState> seenAlready = new LinkedList<GridState>();
        toSearch.add(new GridState(tileGrid));
        while (!toSearch.isEmpty()) {
            GridState x = toSearch.remove();
            if (x.isSolved()) return new MarupekeGrid(x.getTiles());
            else {
                seenAlready.add(x);
                LinkedList<GridState> tempList = x.getChildren(); //get children of x in state graph
                for (GridState y : tempList) {
                    if (!toSearch.contains(y) && !seenAlready.contains(y)) toSearch.add(y);
                }
            }
        }
        return null;
    }

    public boolean isFinishable() {
        return solvePuzzle() != null;
    }
}