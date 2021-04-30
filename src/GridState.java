import java.util.LinkedList;

public class GridState{
    private final MarupekeTile[][] tiles;

    public GridState(MarupekeTile[][] t) {
        tiles = new MarupekeTile[t.length][t.length];
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t.length; j++) {
                tiles[i][j] = new MarupekeTile(t[i][j].getTileValue());
            }
        }
    }

    public void setTile(PossibleValues value, int x, int y) {
        tiles[x][y].tileValue = value;
    }

    public MarupekeTile[][] getTiles() {
        return tiles;
    }

    public boolean isLegal() {
        return new MarupekeGrid(tiles).isLegalGrid();
    }

    public LinkedList<GridState> getChildren() {
        LinkedList<GridState> children = new LinkedList<GridState>();
        int x = 0;
        int y = 0;
        while (!tiles[x][y].getTileValue().equals(PossibleValues.blank)) {
            y++;
            if (y == tiles.length){
                x++;
                y = 0;
            } else if (x == tiles.length) return children;
        }

        GridState stateX = new GridState(getTiles());
        stateX.setTile(PossibleValues.X, x, y);
        if (stateX.isLegal()) children.add(stateX);

        GridState stateO = new GridState(getTiles());
        stateO.setTile(PossibleValues.O, x, y);
        if (stateO.isLegal()) children.add(stateO);


        return children;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                str.append(tiles[i][j]);
            }
            str.append("\n");
        }
        return str.toString();
    }

    public boolean isSolved() {
        return new MarupekeGrid(tiles).isSolved();
    }
}
