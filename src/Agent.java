import java.util.Random;

class Agent {

    static KnowledgeBase knowledgeBase = new KnowledgeBase();

    public static void makeMove(){
        if(!knowledgeBase.knowledgeBoard[KnowledgeBase.agentY][KnowledgeBase.agentX].stink
                && !knowledgeBase.knowledgeBoard[KnowledgeBase.agentY][KnowledgeBase.agentX].breeze){
            if(KnowledgeBase.agentX < 3){
                knowledgeBase.knowledgeBoard[KnowledgeBase.agentY][KnowledgeBase.agentX+1].okay = true;
            }
            if(KnowledgeBase.agentY < 3){
                knowledgeBase.knowledgeBoard[KnowledgeBase.agentY+1][KnowledgeBase.agentX].okay = true;
            }
            if(KnowledgeBase.agentX > 0){
                knowledgeBase.knowledgeBoard[KnowledgeBase.agentY][KnowledgeBase.agentX-1].okay = true;
            }
            if(KnowledgeBase.agentY > 0){
                knowledgeBase.knowledgeBoard[KnowledgeBase.agentY-1][KnowledgeBase.agentX].okay = true;
            }
        }
        if(KnowledgeBase.agentX < 3 && knowledgeBase.knowledgeBoard[KnowledgeBase.agentY][KnowledgeBase.agentX+1].okay
                && !knowledgeBase.knowledgeBoard[KnowledgeBase.agentY][KnowledgeBase.agentX+1].visited){
            moveRight();
        }else if(KnowledgeBase.agentY < 3 && knowledgeBase.knowledgeBoard[KnowledgeBase.agentY+1][KnowledgeBase.agentX].okay
                && !knowledgeBase.knowledgeBoard[KnowledgeBase.agentY+1][KnowledgeBase.agentX].visited){
            moveDown();
        }else if(KnowledgeBase.agentX > 0 && knowledgeBase.knowledgeBoard[KnowledgeBase.agentY][KnowledgeBase.agentX-1].okay
                && !knowledgeBase.knowledgeBoard[KnowledgeBase.agentY][KnowledgeBase.agentX-1].visited){
            moveLeft();
        }else if(KnowledgeBase.agentY > 0 && knowledgeBase.knowledgeBoard[KnowledgeBase.agentY-1][KnowledgeBase.agentX].okay
                && !knowledgeBase.knowledgeBoard[KnowledgeBase.agentY-1][KnowledgeBase.agentX].visited){
            moveUp();
        }else{
            safeMove();
        }

        displayKnowledge();
    }

    public static void displayKnowledge(){
        System.out.println("-----------------------------------------");
        for(int i = 0; i < knowledgeBase.knowledgeBoard.length; i++){
            System.out.print("|");
            for(int j = 0; j < knowledgeBase.knowledgeBoard[i].length; j++){
                if(i == KnowledgeBase.agentY && KnowledgeBase.agentX == j){
                    if(KnowledgeBase.facing.equals("left")){
                        System.out.print("<");
                    }if(KnowledgeBase.facing.equals("right")){
                        System.out.print(">");
                    }if(KnowledgeBase.facing.equals("up")){
                        System.out.print("^");
                    }if(KnowledgeBase.facing.equals("down")){
                        System.out.print("v");
                    }
                }
                System.out.print(knowledgeBase.knowledgeBoard[i][j]);
                System.out.print("|");
            }
            System.out.println();
            System.out.println("-----------------------------------------");
        }
    }

    public static void randomMove(){
        Random rand = new Random();
        boolean moveDecided = false;
        while(!moveDecided) {
            int move = rand.nextInt(3);
            switch(move){
                case 0: if(KnowledgeBase.agentX < 3){
                    moveRight();
                    displayKnowledge();
                    moveDecided = true;
                }
                    break;
                case 1: if(KnowledgeBase.agentY < 3){
                    moveDown();
                    displayKnowledge();
                    moveDecided = true;
                }
                    break;
                case 2: if(KnowledgeBase.agentX > 0){
                    moveLeft();
                    displayKnowledge();
                    moveDecided = true;
                }
                    break;
                case 3: if(KnowledgeBase.agentY > 0){
                    moveUp();
                    displayKnowledge();
                    moveDecided = true;
                }
            }
        }
    }

    private static void moveRight(){
        if(knowledgeBase.facing.equals("left")){
            knowledgeBase.score -= 2;
        }if(KnowledgeBase.facing.equals("up") || KnowledgeBase.facing.equals("down")){
            knowledgeBase.score -= 1;
        }
        knowledgeBase.facing = "right";
        knowledgeBase.agentX += 1;
        knowledgeBase.score -= 1;
    }

    private static void moveLeft(){
        if(KnowledgeBase.facing.equals("right")){
            knowledgeBase.score -= 2;
        }if(KnowledgeBase.facing.equals("up") || KnowledgeBase.facing.equals("down")){
            knowledgeBase.score -= 1;
        }
        knowledgeBase.facing = "left";
        knowledgeBase.agentX -= 1;
        knowledgeBase.score -= 1;
    }

    private static void moveUp(){
        if(KnowledgeBase.facing.equals("down")){
            knowledgeBase.score -= 2;
        }if(KnowledgeBase.facing.equals("left") || KnowledgeBase.facing.equals("right")){
            knowledgeBase.score -= 1;
        }
        knowledgeBase.facing = "up";
        knowledgeBase.agentY -= 1;
        knowledgeBase.score -= 1;
    }

    private static void moveDown(){
        if(knowledgeBase.facing.equals("up")){
            knowledgeBase.score -= 2;
        }if(KnowledgeBase.facing.equals("left") || KnowledgeBase.facing.equals("right")){
            knowledgeBase.score -= 1;
        }
        knowledgeBase.facing = "down";
        knowledgeBase.agentY += 1;
        knowledgeBase.score -= 1;
    }

    public static void locateAndKillWumpus(){
        for(int i = 0; i < knowledgeBase.knowledgeBoard.length; i++){
            for(int j = 0; j < knowledgeBase.knowledgeBoard[0].length; j++){
                if((i != KnowledgeBase.agentY || j != KnowledgeBase.agentX) && knowledgeBase.knowledgeBoard[i][j].stink){
                    int wumpusY, wumpusX;
                    //at least one other stink, that is enough to find the wumpus
                    if(KnowledgeBase.agentY == i){
                        wumpusY = KnowledgeBase.agentY;
                        wumpusX = (j + KnowledgeBase.agentX)/2;
                    }else if(KnowledgeBase.agentX == j){
                        wumpusX = KnowledgeBase.agentX;
                        wumpusY = (i + KnowledgeBase.agentY)/2;
                    }else{
                        if(knowledgeBase.knowledgeBoard[i][KnowledgeBase.agentX].okay){
                            wumpusX = j;
                            wumpusY = KnowledgeBase.agentY;
                        }else{
                            wumpusX = KnowledgeBase.agentX;
                            wumpusY = i;
                        }
                    }
                    //reposition to shoot
                    if(wumpusX == KnowledgeBase.agentX+1){
                        KnowledgeBase.score -= KnowledgeBase.facing.equals("left")? 2 : 0;
                        KnowledgeBase.score -= KnowledgeBase.facing.equals("up") || KnowledgeBase.facing.equals("down")? 1 : 0;
                        KnowledgeBase.facing = "right";
                        KnowledgeBase.score -= 10;
                    }else if(wumpusX == KnowledgeBase.agentX-1){
                        KnowledgeBase.score -= KnowledgeBase.facing.equals("right")? 2 : 0;
                        KnowledgeBase.score -= KnowledgeBase.facing.equals("up") || KnowledgeBase.facing.equals("down")? 1 : 0;
                        KnowledgeBase.facing = "left";
                        KnowledgeBase.score -= 10;
                    }else if(wumpusY == KnowledgeBase.agentY+1){
                        KnowledgeBase.score -= KnowledgeBase.facing.equals("up")? 2 : 0;
                        KnowledgeBase.score -= KnowledgeBase.facing.equals("left") || KnowledgeBase.facing.equals("right")? 1 : 0;
                        KnowledgeBase.facing = "down";
                        KnowledgeBase.score -= 10;
                    }else if(wumpusY == KnowledgeBase.agentY-1){
                        KnowledgeBase.score -= KnowledgeBase.facing.equals("down")? 2 : 0;
                        KnowledgeBase.score -= KnowledgeBase.facing.equals("left") || KnowledgeBase.facing.equals("right")? 1 : 0;
                        KnowledgeBase.facing = "up";
                        KnowledgeBase.score -= 10;
                    }
                    //kill the wumpus
                    if(!KnowledgeBase.arrowUsed){
                        KnowledgeBase.arrowUsed = true;
                        Initializer.wumpusDead = true;
                    }
                    //if there is a scream, we can remove all warnings for the wumpus
                    if(KnowledgeBase.scream){
                        for(int k = 0; k < KnowledgeBase.knowledgeBoard.length; k++){
                            for(int l = 0; l < KnowledgeBase.knowledgeBoard[0].length; l++){
                                KnowledgeBase.knowledgeBoard[i][j].stink = false;
                            }
                        }
                    }
                }
            }
        }
    }

    public static void safeMove(){
        Random rand = new Random();
        int safeCount = 0;
        safeCount += KnowledgeBase.agentX < 3
                && KnowledgeBase.knowledgeBoard[KnowledgeBase.agentY][KnowledgeBase.agentX+1].okay? 1 : 0;
        safeCount += KnowledgeBase.agentX > 0
                && KnowledgeBase.knowledgeBoard[KnowledgeBase.agentY][KnowledgeBase.agentX-1].okay? 1 : 0;
        safeCount += KnowledgeBase.agentY < 3
                && KnowledgeBase.knowledgeBoard[KnowledgeBase.agentY+1][KnowledgeBase.agentX].okay? 1 : 0;
        safeCount += KnowledgeBase.agentY > 0
                && KnowledgeBase.knowledgeBoard[KnowledgeBase.agentY-1][KnowledgeBase.agentX].okay? 1 : 0;
        if(safeCount > 0) {
            String[] safeSpaces = new String[safeCount];
            int safeIndex = 0;
            if (KnowledgeBase.agentX < 3 && KnowledgeBase.knowledgeBoard[KnowledgeBase.agentY][KnowledgeBase.agentX + 1].okay) {
                safeSpaces[safeIndex] = "move right";
                safeIndex += 1;
            }if (KnowledgeBase.agentX > 0 && KnowledgeBase.knowledgeBoard[KnowledgeBase.agentY][KnowledgeBase.agentX - 1].okay) {
                safeSpaces[safeIndex] = "move left";
                safeIndex += 1;
            }if (KnowledgeBase.agentY < 3 && KnowledgeBase.knowledgeBoard[KnowledgeBase.agentY + 1][KnowledgeBase.agentX].okay) {
                safeSpaces[safeIndex] = "move down";
                safeIndex += 1;
            }if (KnowledgeBase.agentY > 0 && KnowledgeBase.knowledgeBoard[KnowledgeBase.agentY - 1][KnowledgeBase.agentX].okay) {
                safeSpaces[safeIndex] = "move up";
            }
            int move = rand.nextInt(safeCount);
            switch(safeSpaces[move]){
                case("move right"): moveRight();
                    break;
                case("move left"): moveLeft();
                    break;
                case("move up"): moveUp();
                    break;
                case("move down"): moveDown();
                    break;
            }
        }else{
            KnowledgeBase.noSafeMoves = true;
        }
    }
}
