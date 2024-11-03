package My422Project;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.HashSet;
import java.util.Set;

public class HalsteadVolumeCheck extends AbstractCheck {

    private int programLength = 0;  // Total occurrences of operators and operands (N)
    private Set<String> vocabulary = new HashSet<>();  // Unique operators and operands (n)

    // Define operators and operands
    private static final Set<String> OPERATORS = new HashSet<>();
    private static final Set<String> OPERANDS = new HashSet<>();

    static {
        // Add common operators
        OPERATORS.add("+");
        OPERATORS.add("-");
        OPERATORS.add("*");
        OPERATORS.add("/");
        OPERATORS.add("=");
        OPERATORS.add(">");
        OPERATORS.add("<");
        OPERATORS.add("&&");
        OPERATORS.add("||");
        OPERATORS.add("==");
        OPERATORS.add("!=");

        // Add common operands (keywords, literals)
        OPERANDS.add("int");
        OPERANDS.add("float");
        OPERANDS.add("double");
        OPERANDS.add("String");
        OPERANDS.add("boolean");
        OPERANDS.add("null");
    }

    @Override
    public int[] getDefaultTokens() {
        // Define tokens that represent identifiers and keywords
        return new int[] {
            TokenTypes.IDENT,           // For identifiers like variable names
            TokenTypes.NUM_INT,         // Integer literals
            TokenTypes.NUM_DOUBLE,      // Double literals
            TokenTypes.STRING_LITERAL,  // String literals
            TokenTypes.PLUS,            // Operator: +
            TokenTypes.MINUS,           // Operator: -
            TokenTypes.STAR,            // Operator: *
            TokenTypes.DIV,             // Operator: /
            TokenTypes.ASSIGN,          // Operator: =
            TokenTypes.GT,              // Operator: >
            TokenTypes.LT,              // Operator: <
            TokenTypes.LAND,            // Operator: &&
            TokenTypes.LOR,             // Operator: ||
            TokenTypes.EQUAL,           // Operator: ==
            TokenTypes.NOT_EQUAL,       // Operator: !=
            TokenTypes.LITERAL_INT,     // Keyword: int
            TokenTypes.LITERAL_FLOAT,   // Keyword: float
            TokenTypes.LITERAL_DOUBLE,  // Keyword: double
            TokenTypes.LITERAL_BOOLEAN, // Keyword: boolean
            TokenTypes.LITERAL_NULL     // Keyword: null
        };
    }
    
    @Override
	public int[] getAcceptableTokens() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRequiredTokens() {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public void visitToken(DetailAST ast) {
        String tokenText = ast.getText();

        // Check if the token is an operator or operand based on the provided sets
        if (OPERATORS.contains(tokenText) || OPERANDS.contains(tokenText)) {
            // Increment program length for each occurrence
            programLength++;

            // Add unique operators and operands to vocabulary
            vocabulary.add(tokenText);
        }
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        // Calculate program vocabulary (n) as the size of the vocabulary set
        int vocabularySize = vocabulary.size();
        
        // Calculate Halstead Volume if vocabulary size is greater than 0 to avoid log(0) error
        double halsteadVolume = vocabularySize > 0 ? programLength * (Math.log(vocabularySize) / Math.log(2)) : 0;

        // Log the Halstead Volume
        log(0, "Halstead Volume: " + halsteadVolume);

        // Reset for the next file
        programLength = 0;
        vocabulary.clear();
    }
    
    public int getProgramLength() {
        return programLength;
    }

    public int getVocabularySize() {
        return vocabulary.size();
    }

}
