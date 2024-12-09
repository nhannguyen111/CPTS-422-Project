package My422Project;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.HashSet;
import java.util.Set;

public class HalsteadVolumeCheck extends AbstractCheck {

    private int programLength = 0;  // Total occurrences of operators and operands (N)
    private Set<String> vocabulary = new HashSet<>();  // Unique operators and operands (n)

    private static final String[] OPERATORS = {
            "+", "-", "*", "/", "=", ">", "<", "&&", "||", "==", "!=", 
            "(", ")", ",", "[", "]", "if", ";", "for", "<=", ">=", "++", "--", "return", "{", "}"
    };

    private static final String[] OPERANDS = {
            "int", "float", "double", "String", "boolean", "null"
    };
    
    // Define operator and operand tokens as specified
    private static final Set<String> OPERATORS_SET = new HashSet<>();
    private static final Set<String> OPERANDS_SET = new HashSet<>();
    
    static {
        for (String operator : OPERATORS) {
            OPERATORS_SET.add(operator);
        }
        for (String operand : OPERANDS) {
            OPERANDS_SET.add(operand);
        }
    }

    @Override
    public int[] getDefaultTokens() {
    	return new int[]{
                TokenTypes.PLUS,
                TokenTypes.MINUS,
                TokenTypes.STAR,
                TokenTypes.DIV,
                TokenTypes.ASSIGN,
                TokenTypes.GT,
                TokenTypes.LT,
                TokenTypes.BAND,
                TokenTypes.BOR,
                TokenTypes.EQUAL,
                TokenTypes.NOT_EQUAL,
                TokenTypes.LPAREN,
                TokenTypes.RPAREN,
                TokenTypes.COMMA,
                // TokenTypes.LBRACK,		// Left Bracket doesn't exist apparently..
                TokenTypes.RBRACK,
                TokenTypes.LITERAL_IF,
                TokenTypes.SEMI,
                TokenTypes.LITERAL_FOR,
                TokenTypes.LE,
                TokenTypes.GE,
                TokenTypes.INC,
                TokenTypes.DEC,
                TokenTypes.LITERAL_RETURN,
                TokenTypes.LCURLY,
                TokenTypes.RCURLY,
                TokenTypes.LITERAL_INT,
                TokenTypes.STRING_LITERAL,
                TokenTypes.LITERAL_BOOLEAN,
                TokenTypes.IDENT,
                TokenTypes.NUM_INT
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
        if (OPERATORS_SET.contains(tokenText) || OPERANDS_SET.contains(tokenText)) {
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
