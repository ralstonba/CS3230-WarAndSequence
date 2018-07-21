package cs3230_finalproject;

public class WarDriver {
    public static void main(String[] args)
    {
	War WarGame = new War();
	WarGame.initialize();
	WarGame.play();
	WarGame.displayWinner();
    }
}
