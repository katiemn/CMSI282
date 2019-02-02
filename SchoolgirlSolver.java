package schoolgirl_solver;

public class SchoolgirlSolver{

	public static final int ROWS = 5;
	public static final int DAYS = 7;
	public static final int GIRLS_PER_ROW = 3;
	public static final int NUMBER_OF_GIRLS = 15;
    public static int [][] pairs = new int[NUMBER_OF_GIRLS][NUMBER_OF_GIRLS];
    public static boolean [][] alreadyWalked = new boolean [DAYS][NUMBER_OF_GIRLS];
    public static int [][][] formation = new int[ROWS][DAYS][GIRLS_PER_ROW];
    private static String[] names = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O"};

    public static void adjustPairs(int currentRow, int currentDay, int lastPosition, boolean state) {
        int toLeft, first;
        int currentGirl = formation[currentRow][currentDay][lastPosition];
        if (lastPosition == 1 || lastPosition == 2) {
        	toLeft = formation[currentRow][currentDay][lastPosition -1];
        	if (state) {
            	pairs[currentGirl][toLeft] = pairs[toLeft][currentGirl] = 1;
            } else {
                pairs[currentGirl][toLeft] = pairs[toLeft][currentGirl] = 0;
            }
        	if (lastPosition == 2) {
        		first = formation[currentRow][currentDay][lastPosition -2];
        		if (state) {
        			pairs[currentGirl][first] = pairs[first][currentGirl] = 1;
        		} else {
        			pairs[currentGirl][first] = pairs[first][currentGirl] = 0;
        		}
        	}
        }
    }

    public static boolean isValidInRow (int row, int day, int girl, int possible) {
    	if (girl == 0) {
    		return true;
    	} else {
    		if (girl == 2) {
				if (pairs[possible][formation[row][day][girl-2]] == 1) {
					return false;
				}
			}
    		if (pairs[possible][formation[row][day][girl-1]] == 1) {
    			return false;
    		}
    	}
    	return true;
    }
    
    public static void printSolution () {
        for (int row = 0; row < ROWS; row++) {
            for (int day = 0; day < DAYS; day ++) {
                for (int girl = 0; girl < GIRLS_PER_ROW; girl++) {
                	if (girl == 0) {
                		System.out.print("[");
                	}
                    System.out.printf(toLetter(formation[row][day][girl],names));
                    if (girl == 2) {
                    	System.out.printf("%-3s","]");
                    }
                }
            }
            System.out.println("");
        }
        System.out.println("");
    }

    public static String toLetter (int val, String[] names) {
    	return names[val];
    }

    public static int getStartValue (int row, int day, int girl) {
    	int start = 0;
    	if (girl == 0 && row > 0) {
    		start = formation[row-1][day][girl] + 1;
    	} else {
    		if (row == 0 && day > 0 && girl == 1) {
    			start = formation[row][day-1][girl] + 1;
    		} else {
    			start = formation[row][day][girl - 1] + 1;
    		}
    	}
    	return start;
    }
    
    public static boolean canWalk (int row, int day, int girl, int possibleGirl) {
    	if (!alreadyWalked[day][possibleGirl] && isValidInRow(row,day,girl,possibleGirl)) {
    		return true;
    	}
    	return false;
    }
    
    public static int placeFrom(int currentRow, int currentDay, int currentEntry){
        int possibleGirl;
        int currentGirl = formation[currentRow][currentDay][currentEntry];
        if (currentGirl!= 0) {
            adjustPairs(currentRow, currentDay, currentEntry, false);
            alreadyWalked[currentDay][currentGirl] = false;
            possibleGirl = currentGirl + 1;
        } else {
        	possibleGirl = getStartValue(currentRow,currentDay,currentEntry);
        }
        if (currentEntry != 2 && possibleGirl >= 12) {
        	return -1;
        }
        if ((currentEntry == 0 && possibleGirl > currentRow * 3) || (possibleGirl + (3 - currentEntry) >= 15)) {
            return -1;
        }
        boolean havePossible = false;
        for (int i = possibleGirl; i < NUMBER_OF_GIRLS; i++) {
            if (canWalk(currentRow,currentDay,currentEntry,i)) {
                possibleGirl = i;
                havePossible = true;
                break;
            }
        }
        if (!havePossible) {
            return -1;
        }
        return possibleGirl;
    }

   public static int[] getLocation (int location) {
	   int [] step = new int[3];
	   int row = (location % NUMBER_OF_GIRLS) / GIRLS_PER_ROW;
       int day = location / NUMBER_OF_GIRLS;
       int girl = location % GIRLS_PER_ROW;
       step[0] = row; step[1] = day; step[2] = girl;
       return step;
   }
    
    public static void main (String [] args) {
    	
    	int nameCount = 0;
		for (int row = 0; row < ROWS; row++) {
			for (int girl = 0; girl < GIRLS_PER_ROW; girl++) {
				formation[row][0][girl] = nameCount;
				nameCount++;
			}
		}
		for (int i = 1; i <= names.length - 2; i += 3) {
			pairs[i][i-1] = pairs[i-1][i] = 1;
			pairs[i-1][i+1] = pairs[i+1][i-1] = 1;
			pairs[i][i+1] = pairs[i+1][i] = 1;
		}
        for (int day = 1; day < DAYS; day++) {
            for (int row = 0; row < GIRLS_PER_ROW; row++) {
                formation[row][day][0] = row;
            }
        }
        for (int day = 0; day < DAYS; day++) {
            for (int girl = 0; girl < GIRLS_PER_ROW; girl++) {
                alreadyWalked[day][girl] = true;
            }
        }
        int location = NUMBER_OF_GIRLS;
        int possibleEntry, currentEntry, currentRow, currentDay, currentGirl;
        int [] step = new int[3];

        while (formation[4][6][2] == 0) {
            if (location % GIRLS_PER_ROW == 0 && ((location % NUMBER_OF_GIRLS) <= GIRLS_PER_ROW *(GIRLS_PER_ROW - 1))) {
            	location++;
            }
            step = getLocation(location);
            currentRow = step[0];
            currentDay = step[1];
            currentGirl = step[2];
            currentEntry = location % NUMBER_OF_GIRLS;
            possibleEntry = placeFrom(currentRow,currentDay,currentGirl);

            if (possibleEntry == -1) {
                if (formation[currentRow][currentDay][currentGirl] != 0) {
                    adjustPairs(currentRow, currentDay, currentGirl, false);
                    alreadyWalked[currentDay][formation[currentRow][currentDay][currentGirl]] = false;
                }
                formation[currentRow][currentDay][currentGirl] = 0;
                if ((currentGirl == 1) && ((currentEntry) < 9)) {
                	location -= 2;
                    continue;
                }
                if (currentEntry == 1) {
                	location -= 2;
                } else {
                	location -= 1;
                }
            } else {
                alreadyWalked[currentDay][possibleEntry] = true;
                formation[currentRow][currentDay][currentGirl] = possibleEntry;
                adjustPairs(currentRow, currentDay, currentGirl, true);
                location++;
            }
        }
        printSolution();
    }
}
