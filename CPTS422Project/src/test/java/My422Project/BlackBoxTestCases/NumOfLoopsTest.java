package My422Project.BlackBoxTestCases;

public class NumOfLoopsTest {

    // Simple loop
    public void singleLoop() {
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
        }
    }

    // Nested loops
    public void nestedLoops() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.println(i + "," + j);
            }
        }
    }

    // Loop inside a conditional
    public void conditionalLoop() {
        if (true) {
            while (true) {  // Will never execute, but still a loop
                System.out.println("This won't print.");
            }
        }
    }

    // Recursion
    public void recursiveMethod(int n) {
        if (n > 0) {
            recursiveMethod(n - 1);
        }
    }

    // Loop with recursion inside
    public void loopWithRecursion() {
        for (int i = 0; i < 5; i++) {
            recursiveMethod(3);
        }
    }

    // Loops in separate methods
    public void separateMethodLoops() {
        for (int i = 0; i < 3; i++) {
            loopInAnotherMethod();
        }
    }

    private void loopInAnotherMethod() {
        while (true) {
            break; // Infinite loop with early exit
        }
    }

    // Loop in try-catch
    public void loopInTryCatch() {
        try {
            for (int i = 0; i < 10; i++) {
                System.out.println("Inside try block: " + i);
            }
        } catch (Exception e) {
            System.out.println("Error occurred.");
        }
    }

    /*
    public static void main(String[] args) {
        NumOfLoopsTest test = new NumOfLoopsTest();
        test.singleLoop();
        test.nestedLoops();
        test.conditionalLoop();
        test.loopWithRecursion();
        test.separateMethodLoops();
        test.loopInTryCatch();
    }*/
}

/*
Expected Results:
- singleLoop: 1 loop
- nestedLoops: 2 loops (outer and inner)
- conditionalLoop: 1 loop
- recursiveMethod: 0 loops (recursion is not a loop)
- loopWithRecursion: 1 loop (recursion is not counted as a loop)
- separateMethodLoops: 2 loops (one in main method, one in helper method)
- loopInTryCatch: 1 loop
Total loops in the code: 8 loops
*/
