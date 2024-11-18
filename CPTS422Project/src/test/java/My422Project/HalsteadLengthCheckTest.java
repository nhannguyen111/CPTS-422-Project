package My422Project;

import com.puppycrawl.tools.checkstyle.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class HalsteadLengthCheckTest {
	
	private HalsteadLengthCheck halsteadLengthCheck;

    // Sets up a new instance of HalsteadLengthCheck before each test
    @BeforeEach
    void setUp() {
        halsteadLengthCheck = spy(new HalsteadLengthCheck()); // Spy on the instance
    }

    // Tests that getDefaultTokens returns the expected array of default token types
    @Test
    void testDefaultTokens() {
        int[] expectedTokens = {
                TokenTypes.PLUS, TokenTypes.MINUS, TokenTypes.STAR, TokenTypes.DIV,
                TokenTypes.ASSIGN, TokenTypes.GT, TokenTypes.LT, TokenTypes.BAND,
                TokenTypes.BOR, TokenTypes.EQUAL, TokenTypes.NOT_EQUAL, TokenTypes.LITERAL_INT,
                TokenTypes.STRING_LITERAL, TokenTypes.LITERAL_BOOLEAN, TokenTypes.IDENT, TokenTypes.NUM_INT
        };
        assertEquals(expectedTokens.length, halsteadLengthCheck.getDefaultTokens().length);
        for (int i = 0; i < expectedTokens.length; i++) {
            assertEquals(expectedTokens[i], halsteadLengthCheck.getDefaultTokens()[i]);
        }
    }
    
    // Verifies that getAcceptableTokens returns the same tokens as getDefaultTokens
    @Test
    void testGetAcceptableTokens() {
        int[] expectedTokens = halsteadLengthCheck.getDefaultTokens();
        int[] acceptableTokens = halsteadLengthCheck.getAcceptableTokens();

        assertArrayEquals(expectedTokens, acceptableTokens, "getAcceptableTokens should return the same tokens as getDefaultTokens");
    }

    // Tests that getRequiredTokens returns an empty array, as expected
    @Test
    void testGetRequiredTokens() {
        int[] expectedRequiredTokens = new int[0];
        int[] requiredTokens = halsteadLengthCheck.getRequiredTokens();

        assertArrayEquals(expectedRequiredTokens, requiredTokens, "getRequiredTokens should return an empty array");
    }

    // Tests that visitToken correctly increments the operator count when an operator token is encountered
    @Test
    void testVisitToken_operatorCount() {
        DetailAST operatorToken = mock(DetailAST.class);
        when(operatorToken.getText()).thenReturn("+");
        when(operatorToken.getType()).thenReturn(TokenTypes.PLUS);

        halsteadLengthCheck.visitToken(operatorToken);

        assertEquals(1, halsteadLengthCheck.getNumOperators());
        assertEquals(0, halsteadLengthCheck.getNumOperands());
    }

    // Tests that visitToken correctly increments the operand count when an operand token is encountered
    @Test
    void testVisitToken_operandCount() {
        DetailAST operandToken = mock(DetailAST.class);
        when(operandToken.getText()).thenReturn("int");
        when(operandToken.getType()).thenReturn(TokenTypes.LITERAL_INT);

        halsteadLengthCheck.visitToken(operandToken);

        assertEquals(0, halsteadLengthCheck.getNumOperators());
        assertEquals(1, halsteadLengthCheck.getNumOperands());
    }

    @Test
    void testFinishTree_resetsCountsAndLogsLength() {
        // Create a mock DetailAST
        DetailAST rootAST = mock(DetailAST.class);
        when(rootAST.getLineNo()).thenReturn(1);

        // Set initial values for operators and operands
        halsteadLengthCheck.setNumOperators(3);
        halsteadLengthCheck.setNumOperands(2);

        // Stub the log method to do nothing
        doNothing().when(halsteadLengthCheck).log(anyInt(), anyString());

        // Call finishTree
        halsteadLengthCheck.finishTree(rootAST);

        // Verify the log method is called with the expected message
        int expectedHalsteadLength = 5; // 3 operators + 2 operands
        verify(halsteadLengthCheck).log(1, "Halstead Length: " + expectedHalsteadLength + " NN");

        // Verify the counters are reset
        assertEquals(0, halsteadLengthCheck.getNumOperators());
        assertEquals(0, halsteadLengthCheck.getNumOperands());
    }
    
    // Tests that visitToken increments operand count for a known operand text like "int"
    @Test
    void testVisitToken_withKnownOperandText() {
        DetailAST operandToken = mock(DetailAST.class);
        when(operandToken.getText()).thenReturn("int");
        when(operandToken.getType()).thenReturn(TokenTypes.LITERAL_INT);

        halsteadLengthCheck.visitToken(operandToken);

        assertEquals(1, halsteadLengthCheck.getNumOperands(), "'int' should be recognized as an operand");
    }

    // Tests that visitToken increments operand count for LITERAL_INT type tokens
    @Test
    void testVisitToken_withLiteralIntType() {
        DetailAST literalIntToken = mock(DetailAST.class);
        when(literalIntToken.getType()).thenReturn(TokenTypes.LITERAL_INT);

        halsteadLengthCheck.visitToken(literalIntToken);

        assertEquals(1, halsteadLengthCheck.getNumOperands(), "LITERAL_INT should be recognized as an operand");
    }

    // Tests that visitToken increments operand count for STRING_LITERAL type tokens
    @Test
    void testVisitToken_withStringLiteralType() {
        DetailAST stringLiteralToken = mock(DetailAST.class);
        when(stringLiteralToken.getType()).thenReturn(TokenTypes.STRING_LITERAL);

        halsteadLengthCheck.visitToken(stringLiteralToken);

        assertEquals(1, halsteadLengthCheck.getNumOperands(), "STRING_LITERAL should be recognized as an operand");
    }

    // Tests that visitToken increments operand count for LITERAL_BOOLEAN type tokens
    @Test
    void testVisitToken_withLiteralBooleanType() {
        DetailAST literalBooleanToken = mock(DetailAST.class);
        when(literalBooleanToken.getType()).thenReturn(TokenTypes.LITERAL_BOOLEAN);

        halsteadLengthCheck.visitToken(literalBooleanToken);

        assertEquals(1, halsteadLengthCheck.getNumOperands(), "LITERAL_BOOLEAN should be recognized as an operand");
    }

    // Tests that visitToken increments operand count for IDENT type tokens
    @Test
    void testVisitToken_withIdentType() {
        DetailAST identToken = mock(DetailAST.class);
        when(identToken.getType()).thenReturn(TokenTypes.IDENT);

        halsteadLengthCheck.visitToken(identToken);

        assertEquals(1, halsteadLengthCheck.getNumOperands(), "IDENT should be recognized as an operand");
    }

    // Tests that visitToken increments operand count for NUM_INT type tokens
    @Test
    void testVisitToken_withNumIntType() {
        DetailAST numIntToken = mock(DetailAST.class);
        when(numIntToken.getType()).thenReturn(TokenTypes.NUM_INT);

        halsteadLengthCheck.visitToken(numIntToken);

        assertEquals(1, halsteadLengthCheck.getNumOperands(), "NUM_INT should be recognized as an operand");
    }

    // Tests that visitToken does not recognize an unknown token as an operand
    @Test
    void testVisitToken_withUnknownToken() {
        DetailAST unknownToken = mock(DetailAST.class);
        when(unknownToken.getText()).thenReturn("unknown");
        when(unknownToken.getType()).thenReturn(TokenTypes.PLUS); // not in OPERANDS and not an operand type

        halsteadLengthCheck.visitToken(unknownToken);

        assertEquals(0, halsteadLengthCheck.getNumOperands(), "Unknown token should not be recognized as an operand");
    }
}
