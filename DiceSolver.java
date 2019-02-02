//Author: Katie Nguyen

package dice_solver;

public class DiceSolver {

	public static void main(String[] args) {
		
		int numberOfDice = 5;
		int numberOfGames = 1000000;
		int numberOfWins = 0;
		
		if (args.length != 0) {
			numberOfDice = Integer.parseInt(args[0]);
		}
		for (int i = 1; i <= numberOfGames; i++) {
			int [] dice = new int[numberOfDice];
			do {
				for (int n = 0; n < numberOfDice; n++) {
					int roll = (int)((java.lang.Math.random() * 6) + 1);
					dice[n] = roll;
				} 
			} while(sixes(dice) != 1 && sixes(dice) != 0);
			if (sixes(dice) == 1) {
				numberOfWins++;
			}
		}
		double probabilityOfWinning = (double) numberOfWins / numberOfGames;
		System.out.println(probabilityOfWinning);
	}
	
	public static int sixes (int[] values) {
		int numberOfSixes = 0;
		for (int i = 0; i < values.length; i++) {
			if (values[i] == 6) {
				numberOfSixes++;
			}
		}
		if (numberOfSixes == 1) {
			return 1;
		} else if (numberOfSixes == 0) {
			return 0;
		}
		return 6;
	}
}