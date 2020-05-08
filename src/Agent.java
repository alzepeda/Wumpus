import java.util.Random;

public class Agent {
    static int agentX = 0, agentY = 0;
    static String facing = "right";

    public static void play(Slot[][] board){
        int score = 0;

        displayBoard(board);

        while (!board[agentY][agentX].glitter && !board[agentY][agentX].wumpus && !board[agentY][agentX].pit) {
            System.out.println("score: " + score);
            //check for stench or breeze in neighboring cells
            board[agentY][agentX].okay = true;
            if (agentX < 3){
               if(board[agentY][agentX+1].pit) {
                   board[agentY][agentX].breeze = true;
               } else if(board[agentY][agentX+1].wumpus){
                    board[agentY][agentX].stink = true;
               }
            }
            if (agentY < 3){
                if(board[agentY+1][agentX].pit) {
                    board[agentY][agentX].breeze = true;
                } else if(board[agentY+1][agentX].wumpus){
                    board[agentY][agentX].stink = true;
                }
            }
            if (agentX > 0){
                if(board[agentY][agentX-1].pit) {
                    board[agentY][agentX].breeze = true;
                } else if(board[agentY][agentX-1].wumpus){
                    board[agentY][agentX].stink = true;
                }
            }
            if (agentY > 0){
                if(board[agentY-1][agentX].pit) {
                    board[agentY][agentX].breeze = true;
                } else if(board[agentY-1][agentX].wumpus){
                    board[agentY][agentX].stink = true;
                }
            }

            //if breeze/stink, go back to safe spot
            //if no breeze/stink, random next move
            if(board[agentY][agentX].stink || board[agentY][agentX].breeze) {
                if (agentX < 3 && board[agentY][agentX + 1].okay) {
                    if (facing.equals("left")) {
                        facing = "right";
                        score -= 2;
                    } else if (facing.equals("up") || facing.equals("down")) {
                        facing = "right";
                        score -= 1;
                    }
                    agentX += 1;
                    score -= 1;
                } else if (agentX > 0 && board[agentY][agentX - 1].okay) {
                    if (facing.equals("right")) {
                        facing = "left";
                        score -= 2;
                    } else if (facing.equals("up") || facing.equals("down")) {
                        facing = "left";
                        score -= 1;
                    }
                    agentX += 1;
                    score -= 1;
                } else if (agentY < 3 && board[agentY + 1][agentX].okay) {
                    if (facing.equals("up")) {
                        facing = "down";
                        score -= 2;
                    } else if (facing.equals("left") || facing.equals("right")) {
                        facing = "down";
                        score -= 1;
                    }
                    agentX += 1;
                    score -= 1;
                } else if (agentY > 0 && board[agentY - 1][agentX].okay) {
                    if (facing.equals("down")) {
                        facing = "up";
                        score -= 2;
                    } else if (facing.equals("left") || facing.equals("right")) {
                        facing = "up";
                        score -= 1;
                    }
                    agentX += 1;
                    score -= 1;
                } else{
                    randomMove();
                    score -= 1;
                }
                displayBoard(board);
            }else{
                randomMove();
                score -= 1;
                displayBoard(board);
            }
        }
        if(board[agentY][agentX].glitter){
            score += 1000;
        }else if(board[agentY][agentX].wumpus || board[agentY][agentX].pit){
            score -= 1000;
        }
        System.out.println("Your final score is "+ score);
    }

    public static void displayBoard(Slot[][] board){
        System.out.println("------------------------------------");
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
            System.out.println("------------------------------------");
        }
    }

    public static void randomMove(){
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
    }
}
