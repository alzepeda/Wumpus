public class KnowledgeBase {
    boolean okay = false;
    boolean breeze = false;
    boolean stink = false;
    boolean visited = false;

    public String toString(){
        String slotString = " ";
        slotString += okay? "O" : " ";
        slotString += breeze? "B" : " ";
        slotString += stink? "S" : " ";
        slotString += visited? "V" : " ";
        slotString += " ";
        return slotString;
    }
}
