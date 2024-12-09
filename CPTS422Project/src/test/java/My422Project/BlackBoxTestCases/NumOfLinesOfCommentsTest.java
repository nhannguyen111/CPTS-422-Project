package My422Project.BlackBoxTestCases;

public class NumOfLinesOfCommentsTest {

    // Single-line comment
    // Another single-line comment

    /*
     * This is a multiline comment
     * that spans three lines.
     */

    /**
     * This is a Javadoc comment.
     * It spans four lines and is
     * commonly used for method or
     * class documentation.
     */

    /*
    A multiline comment
    without asterisks at the start
    of each line. It spans
    four lines total.
    */

    // A comment mixed with code
    int x = 10; // Inline comment
    
    // Single-line comment after code
    int y = 20; // Another inline comment

    /*
    This is a multiline comment
    that starts with no padding
    and has no end-of-line spacing.
    It spans exactly three lines.
    */

    public static void main(String[] args) {
        System.out.println("Testing comment metrics...");
        // Main method comment
    }

    /*
     * Another multiline comment
     * at the bottom of the file.
     */
}

// Expected Result:
// Single-line comments: 5 lines
// Inline comments: 2 lines
// Multiline comment 1: 3 lines
// Javadoc comment: 4 lines
// Multiline comment 2: 4 lines
// Multiline comment 3: 3 lines
// Multiline comment 4: 2 lines
// Total comment lines: 23 lines
