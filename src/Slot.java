public class Slot {
    boolean okay = false;
    boolean breeze = false;
    boolean stink = false;
    boolean glitter = false;
    boolean wumpus = false;
    boolean pit = false;
    boolean visited = false;

    public String toString(){
        String slotString = " ";
        slotString += okay? "O" : " ";
        slotString += breeze? "B" : " ";
        slotString += stink? "S" : " ";
        slotString += glitter? "G" : " ";
        slotString += wumpus? "W" : " ";
        slotString += pit? "P" : " ";
        slotString += visited? "V" : " ";
        slotString += " ";
        return slotString;
    }
}
