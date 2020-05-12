import java.util.Random;

public class Initializer {

    public static boolean wumpusDead = false;

    public static void main(String[] args){
        Slot[][] realWorld = new Slot[4][4];
        initialize(realWorld);
        initialize();
        playInforming(realWorld);
    }

    public static void initialize() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Agent.knowledgeBase.knowledgeBoard[i][j] = new Slot();
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

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                if (j < 3){
                    if(board[i][j+1].pit) {
                        board[i][j].breeze = true;
                    }else if(board[i][j+1].wumpus){
                        board[i][j].stink = true;
                    }
                }
                if (i < 3){
                    if(board[i+1][j].pit) {
                        board[i][j].breeze = true;
                    }else if(board[i+1][j].wumpus){
                        board[i][j].stink = true;
                    }
                }
                if (j > 0){
                    if(board[i][j-1].pit) {
                        board[i][j].breeze = true;
                    }else if(board[i][j-1].wumpus){
                        board[i][j].stink = true;
                    }
                }
                if(i > 0){
                    if(board[i-1][j].pit) {
                        board[i][j].breeze = true;
                    }else if(board[i-1][j].wumpus){
                        board[i][j].stink = true;
                    }
                }
            }
        }
    }

    public static void playInforming(Slot[][] realWorld){
        displayBoard(realWorld);

        while (!realWorld[Agent.knowledgeBase.agentY][Agent.knowledgeBase.agentX].gold &&
                !(realWorld[Agent.knowledgeBase.agentY][Agent.knowledgeBase.agentX].wumpus && !wumpusDead) &&
                !realWorld[Agent.knowledgeBase.agentY][Agent.knowledgeBase.agentX].pit &&
                Agent.knowledgeBase.timesAtStart < 5 && !Agent.knowledgeBase.noSafeMoves) {
            Agent.knowledgeBase.knowledgeBoard[Agent.knowledgeBase.agentY][Agent.knowledgeBase.agentX].okay = true;
            //if it loops too much there is no safe way to reach the gold
            Agent.knowledgeBase.timesAtStart += Agent.knowledgeBase.agentY == 0 && Agent.knowledgeBase.agentX == 0 ? 1 : 0;
            Agent.knowledgeBase.knowledgeBoard[Agent.knowledgeBase.agentY][Agent.knowledgeBase.agentX].visited = true;

            //perceptions made, is it stinky or breezy?
            if(realWorld[Agent.knowledgeBase.agentY][Agent.knowledgeBase.agentX].stink){
                Agent.knowledgeBase.knowledgeBoard[Agent.knowledgeBase.agentY][Agent.knowledgeBase.agentX].stink = true;
                Agent.locateAndKillWumpus();
            }else if(realWorld[Agent.knowledgeBase.agentY][Agent.knowledgeBase.agentX].breeze){
                Agent.knowledgeBase.knowledgeBoard[Agent.knowledgeBase.agentY][Agent.knowledgeBase.agentX].breeze = true;
            }

            Agent.makeMove();
            //Agent.randomMove();
        }
        Agent.knowledgeBase.score += realWorld[KnowledgeBase.agentY][KnowledgeBase.agentX].gold ? 999 : 0; //plus 1000 for gold, minus 1 for grab
        Agent.knowledgeBase.score -= realWorld[KnowledgeBase.agentY][KnowledgeBase.agentX].wumpus
                || realWorld[KnowledgeBase.agentY][KnowledgeBase.agentX].pit? 1000 : 0;
        //when not eaten or fallen into pit, next we have to get out
        System.out.println(KnowledgeBase.score);
        if(KnowledgeBase.score > -1000){
            while(!(KnowledgeBase.agentX == 0 && KnowledgeBase.agentY == 0) &&
                    !(realWorld[Agent.knowledgeBase.agentY][Agent.knowledgeBase.agentX].wumpus && !wumpusDead) &&
                    !realWorld[Agent.knowledgeBase.agentY][Agent.knowledgeBase.agentX].pit){
                Agent.knowledgeBase.knowledgeBoard[Agent.knowledgeBase.agentY][Agent.knowledgeBase.agentX].visited = true;
                if(realWorld[Agent.knowledgeBase.agentY][Agent.knowledgeBase.agentX].stink){
                    Agent.knowledgeBase.knowledgeBoard[Agent.knowledgeBase.agentY][Agent.knowledgeBase.agentX].stink = true;
                    Agent.locateAndKillWumpus();
                }else if(realWorld[Agent.knowledgeBase.agentY][Agent.knowledgeBase.agentX].breeze){
                    Agent.knowledgeBase.knowledgeBoard[Agent.knowledgeBase.agentY][Agent.knowledgeBase.agentX].breeze = true;
                }
                Agent.makeMove();
                //Agent.randomMove();
            }
            Agent.knowledgeBase.score -= realWorld[KnowledgeBase.agentY][KnowledgeBase.agentX].wumpus
                    || realWorld[KnowledgeBase.agentY][KnowledgeBase.agentX].pit? 1000 : 0;
        }
        System.out.println("Your final score is "+ Agent.knowledgeBase.score);
    }

    private static void displayBoard(Slot[][] board){
        System.out.println("-----------------------------------------");
        for(int i = 0; i < board.length; i++){
            System.out.print("|");
            for(int j = 0; j < board[i].length; j++){
                System.out.print(board[i][j]);
                System.out.print("|");
            }
            System.out.println();
            System.out.println("-----------------------------------------");
        }
    }
}
