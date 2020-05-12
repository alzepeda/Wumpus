public class Slot {
    boolean gold = false;
    boolean wumpus = false;
    boolean pit = false;
    boolean okay = false;
    boolean breeze = false;
    boolean stink = false;
    boolean visited = false;

    public String toString(){
        String slotString = " ";
        slotString += gold? "G" : " ";
        slotString += wumpus? "W" : " ";
        slotString += pit? "P" : " ";
        slotString += okay? "O" : " ";
        slotString += breeze? "B" : " ";
        slotString += stink? "S" : " ";
        slotString += visited? "V" : " ";
        slotString += " ";
        return slotString;
    }
}
