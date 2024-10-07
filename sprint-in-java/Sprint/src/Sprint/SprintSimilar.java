package Sprint;
import java.util.Scanner;
import java.util.HashMap;




public class SprintSimilar {
	
	static HashMap<String, Integer> labels = new HashMap<>();
	
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of cells you need:");
        int numberOfCells = scanner.nextInt();
        int[] sprintCode = new int[numberOfCells+1];
        scanner.nextLine();
        
        System.out.print("Enter the number of number of maximum executable instructions you need (maximum possible limit 5000):");
        int maxExInstruction = scanner.nextInt();
        scanner.nextLine();
        if(maxExInstruction > 5000) {
        	maxExInstruction = 5000;
        }
        
        //Input the code
        System.out.print("Enter a series of integer numbers separated by spaces:");
        String input = scanner.nextLine();
        String[] numberStrings = input.split(" ");
        
        //first iteration to generate the hash map for labels
        for (int i = 0; i < numberStrings.length; i++) {
        	if (numberStrings[i].contains(":")) {
    			String[] tempArr = numberStrings[i].split(":");
    			labels.put(tempArr[0], (i+1));
    		}else {
    			continue;
    		}
//            try {
//            	Integer.parseInt(numberStrings[i]);
//            	
//            }catch(Exception e) {
//            	if (e instanceof NumberFormatException) {
//            		
//                }
//            }
        }
        
        //convert the label include code to only number code
        sprintCode[0] = 0;
        for (int i = 0; i < numberStrings.length; i++) {
            try {
            	sprintCode[i + 1] = Integer.parseInt(numberStrings[i]);
            	
            }catch(Exception e1) {
            	if (e1 instanceof NumberFormatException) {
            		if (numberStrings[i].contains(":")) {
            			String[] tempArr = numberStrings[i].split(":");
            			try{
            				sprintCode[i + 1] = Integer.parseInt(tempArr[1]);
            			}catch(Exception e2){
            				Integer labeledCell = labels.get(tempArr[1]);
            				if(labeledCell == null) {
            					System.out.println("No such label : "+tempArr[1] +"at cell "+(i+1));
            					return;
            				}else {
            					sprintCode[i+1] = labels.get(tempArr[1]);
            				}
            			}
            		}else {
            			Integer labeledCell = labels.get(numberStrings[i]);
        				if(labeledCell == null) {
        					System.out.println("No such label : "+numberStrings[i] +"at cell "+(i+1));
        					return;
        				}else {
        					sprintCode[i+1] = labels.get(numberStrings[i]);
        				}
            		}
                }
            }
        }
        
        System.out.println("Inputed Code:");
        for (int loc =1; loc < sprintCode.length; loc++) {
            System.out.println(loc + " --> " + sprintCode[loc]);
        }
        execution(sprintCode, maxExInstruction);
        
        scanner.close();
	}
	
	//execution function
	public static void execution(int sprintCode[], int maxInstruction) {
		
		Scanner scanner = new Scanner(System.in);
		int programCounter = 1;
		int instructionPointer = 1;
		int codeLength = sprintCode.length;
		int instructionExecuted = 0;
		while(programCounter <= codeLength && instructionExecuted < maxInstruction) {
			switch(sprintCode[programCounter]) {
			case 0:
				instructionPointer = programCounter + 3;
				// 0 data location --> copy data in the memory location
				sprintCode[sprintCode[programCounter+2]] = sprintCode[programCounter+1];
				break;
			case 1:
				instructionPointer = programCounter + 4;
				// 1 location1 location2 location3 --> add the values of location1 and location2 and put the sum in loaction3
				sprintCode[sprintCode[programCounter+3]] = sprintCode[sprintCode[programCounter+1]] + sprintCode[sprintCode[programCounter+2]];
				break;
			case 2:
				instructionPointer = programCounter + 4;
				// 2 location1 location2 location3 --> subtract the value of location2 from location1 and put the result in loaction3
				sprintCode[sprintCode[programCounter+3]] = sprintCode[sprintCode[programCounter+1]] - sprintCode[sprintCode[programCounter+2]];
				break;
			case 3:
				// 3 location --> move the instructionPointer to location i.e. next instruction present in location
				instructionPointer = sprintCode[programCounter+1];
				break;
			case 4:
				// 4 location1 location2 location --> move instructionPointer to location3 if the value in location1 is equal to the value in location2
				if(sprintCode[sprintCode[programCounter+1]] == sprintCode[sprintCode[programCounter+2]]) {
					instructionPointer = sprintCode[programCounter + 3];
				}else {
					instructionPointer = programCounter + 4;
				}
				break;
			case 5:
				// 5 location1 location2 location --> move instructionPointer to location3 if the value in location1 is less than the value in location2
				if(sprintCode[sprintCode[programCounter+1]] < sprintCode[sprintCode[programCounter+2]]) {
					instructionPointer = sprintCode[programCounter + 3];
				}else {
					instructionPointer = programCounter + 4;
				}
				break;
			case 6:
				instructionPointer =programCounter + 2;
				// 6 location --> take user input and put the value in location
				System.out.print("Enter the value you wanted to put in cell "+sprintCode[programCounter+1]+" :");
				sprintCode[sprintCode[programCounter+1]] = scanner.nextInt();
				scanner.nextLine();
				break;
			case 7:
				instructionPointer = programCounter + 3;
				// 7 location1 location2 --> copy the value present in location1 and put in location2
				sprintCode[sprintCode[programCounter+2]] = sprintCode[sprintCode[programCounter+1]];
				break;
			case 9:
				//9 --> STOP execution
				break;
			default:
				System.out.println("Invalid instruction in cell:"+programCounter+" i.e. "+sprintCode[programCounter]);
			}
			instructionExecuted++;
			if(sprintCode[programCounter] == 9) {
				//STOP execution
				break;
			}
			programCounter = instructionPointer;
		}
		System.out.println("Instruction executed:"+instructionExecuted);
		System.out.println("Execution completed!\nCode after execution:");
		for (int loc =1; loc < sprintCode.length; loc++) {
            System.out.println(loc + " --> " + sprintCode[loc]);
        }
        scanner.close();
	}
}
