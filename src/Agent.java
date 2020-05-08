import java.util.Random;

public class Agent {
    static int agentX = 0, agentY = 0;
    static int score;
    static String facing = "right";

    public static void play(Slot[][] board){

        displayBoard(board);

        while (!board[agentY][agentX].glitter && !board[agentY][agentX].wumpus && !board[agentY][agentX].pit) {
            System.out.println("score: " + score);
            //check for if neighboring cells are safe
            board[agentY][agentX].okay = true;
            board[agentY][agentX].visited = true;
            if (agentX < 3){
               if(board[agentY][agentX+1].pit) {
                   board[agentY][agentX].breeze = true;
               }else if(board[agentY][agentX+1].wumpus){
                   board[agentY][agentX].stink = true;
               }
            }
            if (agentY < 3){
                if(board[agentY+1][agentX].pit) {
                    board[agentY][agentX].breeze = true;
                }else if(board[agentY+1][agentX].wumpus){
                    board[agentY][agentX].stink = true;
                }
            }
            if (agentX > 0){
                if(board[agentY][agentX-1].pit) {
                    board[agentY][agentX].breeze = true;
                }else if(board[agentY][agentX-1].wumpus){
                    board[agentY][agentX].stink = true;
                }
            }
            if (agentY > 0){
                if(board[agentY-1][agentX].pit) {
                    board[agentY][agentX].breeze = true;
                }else if(board[agentY-1][agentX].wumpus){
                    board[agentY][agentX].stink = true;
                }
            }
            if(!board[agentY][agentX].stink && !board[agentY][agentX].breeze){
                if(agentX<3){
                    board[agentY][agentX+1].okay = true;
                }
                if(agentY<3){
                    board[agentY+1][agentX].okay = true;
                }
                if(agentX>0){
                    board[agentY][agentX-1].okay = true;
                }
                if(agentY>0){
                    board[agentY-1][agentX].okay = true;
                }
            }

            //check if there exists neighboring cell which is safe and not visited
            if(agentX < 3 && board[agentY][agentX+1].okay && !board[agentY][agentX+1].visited){
                moveRight();
            }else if(agentY < 3 && board[agentY+1][agentX].okay && !board[agentY+1][agentX].visited){
                moveDown();
            }else if(agentX > 0 && board[agentY][agentX-1].okay && !board[agentY][agentX-1].visited){
                moveLeft();
            }else if(agentY > 0 && board[agentY-1][agentX].okay && !board[agentY-1][agentX].visited){
                moveUp();
            }else{
                Random rand = new Random();
                //necessary to get out of loops when all adjacent cells are unsafe
                int goToSafe = rand.nextInt(9);
                if(goToSafe < 8){
                    int safeCount = 0;
                    safeCount += agentX < 3 && board[agentY][agentX+1].okay? 1 : 0;
                    safeCount += agentX > 0 && board[agentY][agentX-1].okay? 1 : 0;
                    safeCount += agentY < 3 && board[agentY+1][agentX].okay? 1 : 0;
                    safeCount += agentY > 0 && board[agentY-1][agentX].okay? 1 : 0;
                    if(safeCount > 0) {
                        String[] safeSpaces = new String[safeCount];
                        int safeIndex = 0;
                        if (agentX < 3 && board[agentY][agentX + 1].okay) {
                            safeSpaces[safeIndex] = "move right";
                            safeIndex += 1;
                        }
                        if (agentX > 0 && board[agentY][agentX - 1].okay) {
                            safeSpaces[safeIndex] = "move left";
                            safeIndex += 1;
                        }
                        if (agentY < 3 && board[agentY + 1][agentX].okay) {
                            safeSpaces[safeIndex] = "move down";
                            safeIndex += 1;
                        }
                        if (agentY > 0 && board[agentY - 1][agentX].okay) {
                            safeSpaces[safeIndex] = "move up";
                        }
                        int move = rand.nextInt(safeCount);
                        if (safeSpaces[move].equals("move right")) {
                            moveRight();
                        } else if (safeSpaces[move].equals("move left")) {
                            moveLeft();
                        } else if (safeSpaces[move].equals("move up")) {
                            moveUp();
                        } else if (safeSpaces[move].equals("move down")) {
                            moveDown();
                        }
                    }else{
                        randomMove(board);
                    }
                }else{
                    randomMove(board);
                }
            }
            displayBoard(board);
        }
        if(board[agentY][agentX].glitter){
            score += 1000;
        }else if(board[agentY][agentX].wumpus || board[agentY][agentX].pit){
            score -= 1000;
        }
        System.out.println("Your final score is "+ score);
    }

    public static void displayBoard(Slot[][] board){
        System.out.println("-----------------------------------------");
        for(int i = 0; i < board.length; i++){
            System.out.print("|");
            for(int j = 0; j < board[i].length; j++){
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
                System.out.print(board[i][j]);
                System.out.print("|");
            }
            System.out.println();
            System.out.println("-----------------------------------------");
        }
    }

    public static void randomMove(Slot[][] board){
        Random rand = new Random();
        boolean moveDecided = false;
        while(!moveDecided) {
            int move = rand.nextInt(3);
            switch(move){
                case 0: if(agentX < 3){
                    moveRight();
                    displayBoard(board);
                    moveDecided = true;
                }
                    break;
                case 1: if(agentY < 3){
                    moveDown();
                    displayBoard(board);
                    moveDecided = true;
                }
                    break;
                case 2: if(agentX > 0){
                    moveLeft();
                    displayBoard(board);
                    moveDecided = true;
                }
                    break;
                case 3: if(agentY > 0){
                    moveUp();
                    displayBoard(board);
                    moveDecided = true;
                }
            }
        }
        /*
        Random rand = new Random();
        int action = rand.nextInt(3);

        switch (action) {
            case 0:
                if (facing.equals("left")) {      //turn left
                    facing = "down";
                } else if (facing.equals("down")) {
                    facing = "right";
                } else if (facing.equals("right")) {
                    facing = "up";
                } else if (facing.equals("up")) {
                    facing = "left";
                }
                break;
            case 1:
                if (facing.equals("left")) {      //turn right
                    facing = "up";
                } else if (facing.equals("up")) {
                    facing = "right";
                } else if (facing.equals("right")) {
                    facing = "down";
                } else if (facing.equals("down")) {
                    facing = "left";
                }
                break;
            case 2:
                if (facing.equals("left")) {      //move forward
                    if (agentX > 0) {                     //checks for no "bumps"
                        agentX -= 1;
                    }
                } else if (facing.equals("right")) {
                    if (agentX < 3) {
                        agentX += 1;
                    }
                } else if (facing.equals("up")) {
                    if (agentY > 0) {
                        agentY -= 1;
                    }
                } else if (facing.equals("down")) {
                    if (agentY < 3) {
                        agentY += 1;
                    }
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + action);
        }
         */
    }

    public static void moveRight(){
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

    public static void moveLeft(){
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

    public static void moveUp(){
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

    public static void moveDown(){
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
