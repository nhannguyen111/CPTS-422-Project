package My422Project;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import java.util.HashSet;
import java.util.Set;

public class HalsteadVocabularyCheck extends AbstractCheck {

    private final Set<String> uniqueOperators = new HashSet<>();
    private final Set<String> uniqueOperands = new HashSet<>();

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
        if (OPERATORS_SET.contains(tokenText)) {
            getUniqueOperators().add(tokenText);
        } else if (OPERANDS_SET.contains(tokenText)) {
            getUniqueOperands().add(tokenText);
        }
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        int halsteadVocabulary = getUniqueOperators().size() + getUniqueOperands().size();
        log(rootAST.getLineNo(), "Halstead Vocabulary: " + halsteadVocabulary);
        
        // Clear sets for the next file
        getUniqueOperators().clear();
        getUniqueOperands().clear();
    }

	public Set<String> getUniqueOperators() {
		return uniqueOperators;
	}

	public Set<String> getUniqueOperands() {
		return uniqueOperands;
	}
    
}
