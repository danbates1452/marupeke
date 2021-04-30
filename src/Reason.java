/**
 * Abstract class representing the coordinates of a violating tile and the violating tile value as one object 'Reason'
 */
public abstract class Reason {
    int x;
    int y;
    PossibleValues violatingTileValue; //can only be X or O

    public Reason(int x, int y, PossibleValues violatingTileValue) {
        this.x = x;
        this.y = y;
        this.violatingTileValue = violatingTileValue;
    }

    @Override
    public boolean equals(Object o) { //required for testing equality
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reason reason = (Reason) o;
        return x == reason.x && y == reason.y && violatingTileValue == reason.violatingTileValue;
    }
}

/**
 * Class inherited from Reason, denoting a Vertical violation, with the toString method overridden to inform user
 */
class VerticalReason extends Reason{
    public VerticalReason(int x, int y, PossibleValues violatingTileValue) {
        super(x, y, violatingTileValue);
    }
    @Override
    public String toString(){
        return "Illegal placement of consecutive Vertical "+ violatingTileValue.toString() +"s starting at (" + (y+1) + "," + (x+1) + ").";
    }
}

/**
 * Class inherited from Reason, denoting a Horizontal violation, with the toString method overridden to inform user
 */
class HorizontalReason extends Reason{
    public HorizontalReason(int x, int y, PossibleValues violatingTileValue) {
        super(x, y, violatingTileValue);
    }
    @Override
    public String toString(){
        return "Illegal placement of consecutive Horizontal "+ violatingTileValue.toString() +"s starting at (" + (y+1) + "," + (x+1) + ").";
    }

}

/**
 * Class inherited from Reason, denoting a Diagonal violation, with the toString method overridden to inform user
 */
class DiagonalReason extends Reason{
    public DiagonalReason(int x, int y, PossibleValues violatingTileValue) {
        super(x, y, violatingTileValue);
    }
    @Override
    public String toString(){
        return "Illegal placement of consecutive Diagonal "+ violatingTileValue.toString() +"s starting at (" + (y+1) + "," + (x+1) + ").";
    }

}
