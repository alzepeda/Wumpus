import java.util.Random;

public class Initializer {
    public static void main(String[] args){
        Slot[][] board = new Slot[4][4];
        KnowledgeBase[][] knowledge = new KnowledgeBase[4][4];
        initialize(board);
        initialize(knowledge);
        Agent.play(board, knowledge);
    }

    public static void initialize(KnowledgeBase[][] knowledge) {
        Random rand = new Random();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                knowledge[i][j] = new KnowledgeBase();
            }
        }
    }

    public static void initialize(Slot[][] board){
        Random rand = new Random();
        boolean goldSet = false, wumpusSet = false;

        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                board[i][j] = new Slot();
                if((i != 0 || j != 0) && rand.nextInt(5) == 0){
                    board[i][j].pit = true;
                }
            }
        }
        while(!goldSet){
            int i = rand.nextInt(4);
            int j = rand.nextInt(4);
            if (board[i][j].pit == false && (i != 0 || j != 0)){
                board[i][j].gold = true;
                goldSet = true;
            }
        }
        while(!wumpusSet){
            int i = rand.nextInt(4);
            int j = rand.nextInt(4);
            if (board[i][j].pit == false && board[i][j].gold == false && (i != 0 || j != 0)){
                board[i][j].wumpus = true;
                wumpusSet = true;
            }
        }
    }
}
