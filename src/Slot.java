public class Slot {
    boolean gold = false;
    boolean wumpus = false;
    boolean pit = false;

    public String toString(){
        String slotString = " ";
        slotString += gold? "G" : " ";
        slotString += wumpus? "W" : " ";
        slotString += pit? "P" : " ";
        slotString += " ";
        return slotString;
    }
}
