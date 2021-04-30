/**
 * Class to represent a tile in MarupekeGrid, storing the values
 */
public class MarupekeTile {

    PossibleValues tileValue;
    private boolean editable;

    public MarupekeTile() {
        tileValue = PossibleValues.blank;
        editable = true;
    }

    public MarupekeTile(PossibleValues initValue){
        tileValue = initValue;
        editable = true;
    }

    public MarupekeTile(PossibleValues initValue, boolean initEditable){
        tileValue = initValue;
        editable = initEditable;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public PossibleValues getTileValue() {
        return tileValue;
    }

    public void setTileValue(PossibleValues tileValue) {
        this.tileValue = tileValue;
    }

    @Override
    public String toString(){
        String returnStr = "";
        switch (tileValue){
            case blank:
                returnStr = "_";
                break;
            case solid:
                returnStr = "#";
                break;
            case O:
                returnStr = "O";
                break;
            case X:
                returnStr = "X";
                break;
        }
        return returnStr;
    }
}
