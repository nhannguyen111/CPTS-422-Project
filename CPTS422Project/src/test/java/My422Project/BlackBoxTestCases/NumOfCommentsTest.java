package My422Project.BlackBoxTestCases;

public class NumOfCommentsTest {

    public static void main(String[] args) {
        // This is a single-line comment
        int x = 10; // Inline comment 1
        int y = 20; // Inline comment 2

        /* 
         * Multi-line comment 
         * Line 1 
         * Line 2 
         */
        int z = 30;

        // Another single-line comment
        for (int i = 0; i < 10; i++) { // Inline comment 3
            // Comment inside loop
            System.out.println(i); // Inline comment 4
        }

        /* Single-line block comment format */
        System.out.println("Test complete."); // Inline comment 5

        /*
         * Multi-line comment with
         * only one line of text
         */
    }

}

// Expected Result:
// Total number of comments: 12
// - Single-line comments: 3
// - Inline comments: 5
// - Multi-line comments (counted as blocks, not lines): 4
