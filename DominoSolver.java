//Author: Katie Nguyen

package domino_solver;

public class DominoSolver {
	
	public static int dominoReward(int[]p,int i,int j,int m[][]) {
		int q;
		if (m[i][j] > 0) {
			return m[i][j];
		}
		if (i == j) {
			m[i][j] = 0;
		} else {
			for (int k = i; k <= j-1; k++) {
				q = dominoReward(p,i,k,m) + dominoReward(p,k+1,j,m) + p[i-1]*p[k]*p[j];
				if (q > m[i][j]) {
					m[i][j] = q;
				}
			}
		}
		return m[i][j];
	}
	
	public static void main(String[] args) {
		
		for (int x = 0; x < args.length; x++) {
			try {
				Integer.parseInt(args[x]); 
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException();
			}
			if (Integer.parseInt(args[x]) < 0) {
				throw new IllegalArgumentException();
			}
		}
		for (int y = 2; y < args.length; y = y+2) {
			if (Integer.parseInt(args[y]) != Integer.parseInt(args[y-1])) {
				throw new IllegalArgumentException();
			}
		}
		if (args.length % 2 != 0 || args.length < 4) {
			throw new IllegalArgumentException();
		}
			
		int n = ((args.length-2) / 2) + 2;
		int [] values = new int[n];
		values[0] = Integer.parseInt(args[0]);
		values[1] = Integer.parseInt(args[1]);
		for (int x = 2; x < values.length; x++) {
			values[x] = Integer.parseInt(args[2*x-1]);
		}
		
		int m[][] = new int[n][n];
		for (int i = 1; i < n; i++) {
			for (int j = i; j < n; j++) {
				m[i][j] = 0;
			}
		}
		
		System.out.println(dominoReward(values,1,n-1,m));
	}	
}
