package My422Project;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import java.util.HashSet;
import java.util.Set;

public class HalsteadVocabularyCheck extends AbstractCheck {

    private static final Set<String> OPERATORS = new HashSet<>();
    private static final Set<String> OPERANDS = new HashSet<>();
    private final Set<String> uniqueOperators = new HashSet<>();
    private final Set<String> uniqueOperands = new HashSet<>();

    // Initialize common operators and operands
    static {
        // Operators
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

        // Operands
        OPERANDS.add("int");
        OPERANDS.add("float");
        OPERANDS.add("double");
        OPERANDS.add("String");
        OPERANDS.add("boolean");
        OPERANDS.add("null");
    }
    
    @Override
    public int[] getDefaultTokens() {
        return new int[] {
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
        if (OPERATORS.contains(tokenText)) {
            getUniqueOperators().add(tokenText);
        } else if (OPERANDS.contains(tokenText)) {
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
