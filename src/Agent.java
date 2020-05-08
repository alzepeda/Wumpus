import java.util.Random;

class Agent {
    private static int agentX = 0, agentY = 0;
    private static int score;
    private static String facing = "right";

    static void play(Slot[][] board, KnowledgeBase[][] knowledge){

        displayBoard(board);

        while (!board[agentY][agentX].gold && !board[agentY][agentX].wumpus && !board[agentY][agentX].pit) {
            System.out.println("score: " + score);
            //perceptions made, is it stinky or breezy?
            knowledge[agentY][agentX].okay = true;
            knowledge[agentY][agentX].visited = true;
            if (agentX < 3){
               if(board[agentY][agentX+1].pit) {
                   knowledge[agentY][agentX].breeze = true;
               }else if(board[agentY][agentX+1].wumpus){
                   knowledge[agentY][agentX].stink = true;
               }
            }
            if (agentY < 3){
                if(board[agentY+1][agentX].pit) {
                    knowledge[agentY][agentX].breeze = true;
                }else if(board[agentY+1][agentX].wumpus){
                    knowledge[agentY][agentX].stink = true;
                }
            }
            if (agentX > 0){
                if(board[agentY][agentX-1].pit) {
                    knowledge[agentY][agentX].breeze = true;
                }else if(board[agentY][agentX-1].wumpus){
                    knowledge[agentY][agentX].stink = true;
                }
            }
            if (agentY > 0){
                if(board[agentY-1][agentX].pit) {
                    knowledge[agentY][agentX].breeze = true;
                }else if(board[agentY-1][agentX].wumpus){
                    knowledge[agentY][agentX].stink = true;
                }
            }
            if(!knowledge[agentY][agentX].stink && !knowledge[agentY][agentX].breeze){
                if(agentX<3){
                    knowledge[agentY][agentX+1].okay = true;
                }
                if(agentY<3){
                    knowledge[agentY+1][agentX].okay = true;
                }
                if(agentX>0){
                    knowledge[agentY][agentX-1].okay = true;
                }
                if(agentY>0){
                    knowledge[agentY-1][agentX].okay = true;
                }
            }

            //check if there exists neighboring cell which is safe and not visited
            if(agentX < 3 && knowledge[agentY][agentX+1].okay && !knowledge[agentY][agentX+1].visited){
                moveRight();
            }else if(agentY < 3 && knowledge[agentY+1][agentX].okay && !knowledge[agentY+1][agentX].visited){
                moveDown();
            }else if(agentX > 0 && knowledge[agentY][agentX-1].okay && !knowledge[agentY][agentX-1].visited){
                moveLeft();
            }else if(agentY > 0 && knowledge[agentY-1][agentX].okay && !knowledge[agentY-1][agentX].visited){
                moveUp();
            }else{
                Random rand = new Random();
                //necessary to get out of loops when all adjacent cells are unsafe
                int goToSafe = rand.nextInt(9);
                if(goToSafe < 8){
                    int safeCount = 0;
                    safeCount += agentX < 3 && knowledge[agentY][agentX+1].okay? 1 : 0;
                    safeCount += agentX > 0 && knowledge[agentY][agentX-1].okay? 1 : 0;
                    safeCount += agentY < 3 && knowledge[agentY+1][agentX].okay? 1 : 0;
                    safeCount += agentY > 0 && knowledge[agentY-1][agentX].okay? 1 : 0;
                    if(safeCount > 0) {
                        String[] safeSpaces = new String[safeCount];
                        int safeIndex = 0;
                        if (agentX < 3 && knowledge[agentY][agentX + 1].okay) {
                            safeSpaces[safeIndex] = "move right";
                            safeIndex += 1;
                        }
                        if (agentX > 0 && knowledge[agentY][agentX - 1].okay) {
                            safeSpaces[safeIndex] = "move left";
                            safeIndex += 1;
                        }
                        if (agentY < 3 && knowledge[agentY + 1][agentX].okay) {
                            safeSpaces[safeIndex] = "move down";
                            safeIndex += 1;
                        }
                        if (agentY > 0 && knowledge[agentY - 1][agentX].okay) {
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
                        randomMove(knowledge);
                    }
                }else{
                    randomMove(knowledge);
                }
            }
            displayKnowledge(knowledge);
        }
        if(board[agentY][agentX].gold){
            score += 1000;
        }else if(board[agentY][agentX].wumpus || board[agentY][agentX].pit){
            score -= 1000;
        }
        System.out.println("Your final score is "+ score);
    }

    private static void displayKnowledge(KnowledgeBase[][] knowledge){
        System.out.println("-----------------------------");
        for(int i = 0; i < knowledge.length; i++){
            System.out.print("|");
            for(int j = 0; j < knowledge[i].length; j++){
                if(i == agentY && agentX == j){
                    if(facing.equals("left")){
                        System.out.print("<");
                    }if(facing.equals("right")){
                        System.out.print(">");
                    }if(facing.equals("up")){
                        System.out.print("^");
                    }if(facing.equals("down")){
                        System.out.print(".");
                    }
                }
                System.out.print(knowledge[i][j]);
                System.out.print("|");
            }
            System.out.println();
            System.out.println("-----------------------------");
        }
    }

    private static void displayBoard(Slot[][] board){
        System.out.println("-------------------------");
        for(int i = 0; i < board.length; i++){
            System.out.print("|");
            for(int j = 0; j < board[i].length; j++){
                System.out.print(board[i][j]);
                System.out.print("|");
            }
            System.out.println();
            System.out.println("--------------------------");
        }
    }

    private static void randomMove(KnowledgeBase[][] knowledge){
        Random rand = new Random();
        boolean moveDecided = false;
        while(!moveDecided) {
            int move = rand.nextInt(3);
            switch(move){
                case 0: if(agentX < 3){
                    moveRight();
                    displayKnowledge(knowledge);
                    moveDecided = true;
                }
                    break;
                case 1: if(agentY < 3){
                    moveDown();
                    displayKnowledge(knowledge);
                    moveDecided = true;
                }
                    break;
                case 2: if(agentX > 0){
                    moveLeft();
                    displayKnowledge(knowledge);
                    moveDecided = true;
                }
                    break;
                case 3: if(agentY > 0){
                    moveUp();
                    displayKnowledge(knowledge);
                    moveDecided = true;
                }
            }
        }
    }

    private static void moveRight(){
        System.out.println(facing);
        if(facing.equals("left")){
            score -= 2;
        }if(facing.equals("up") || facing.equals("down")){
            score -= 1;
        }
        facing = "right";
        agentX += 1;
        score -= 1;
    }

    private static void moveLeft(){
        System.out.println(facing);
        if(facing.equals("right")){
            score -= 2;
        }if(facing.equals("up") || facing.equals("down")){
            score -= 1;
        }
        facing = "left";
        agentX -= 1;
        score -= 1;
    }

    private static void moveUp(){
        System.out.println(facing);
        if(facing.equals("down")){
            score -= 2;
        }if(facing.equals("left") || facing.equals("right")){
            score -= 1;
        }
        facing = "up";
        agentY -= 1;
        score -= 1;
    }

    private static void moveDown(){
        System.out.println(facing);
        if(facing.equals("up")){
            score -= 2;
        }if(facing.equals("left") || facing.equals("right")){
            score -= 1;
        }
        facing = "down";
        agentY += 1;
        score -= 1;
    }
}
