package My422Project;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.*;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MethodLimitCheckTest {

    private MethodLimitCheck methodLimitCheck;
    private DetailAST mockClassDef;
    private DetailAST mockObjBlock;

    @BeforeEach
    void setUp() {
        methodLimitCheck = new MethodLimitCheck();
        mockClassDef = mock(DetailAST.class);
        mockObjBlock = mock(DetailAST.class);

        // Mock the findFirstToken behavior
        when(mockClassDef.findFirstToken(TokenTypes.OBJBLOCK)).thenReturn(mockObjBlock);
    }
    
    @Test
    void testGetAcceptableTokens() {
        int[] expectedTokens = { TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF };
        assertArrayEquals(expectedTokens, methodLimitCheck.getAcceptableTokens(),
            "getAcceptableTokens should return CLASS_DEF and INTERFACE_DEF");
    }

    @Test
    void testGetRequiredTokens() {
        int[] expectedTokens = new int[0];
        assertArrayEquals(expectedTokens, methodLimitCheck.getRequiredTokens(),
            "getRequiredTokens should return an empty array");
    }

    @Test
    void testGetDefaultTokens() {
        int[] expectedTokens = { TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF };
        assertArrayEquals(expectedTokens, methodLimitCheck.getDefaultTokens(),
            "getDefaultTokens should return CLASS_DEF and INTERFACE_DEF");
    }
    
    @Test
    void testVisitToken_WhenMethodCountExceedsMax_ShouldCoverLogLine() {
        methodLimitCheck.setMax(1);

        // Mock the child count for METHOD_DEF
        when(mockObjBlock.getChildCount(TokenTypes.METHOD_DEF)).thenReturn(2);

        // Mock the line number
        when(mockClassDef.getLineNo()).thenReturn(10);

        // Spy on the check to stub log
        MethodLimitCheck spyCheck = spy(methodLimitCheck);

        // Stub the log method to do nothing
        doNothing().when(spyCheck).log(anyInt(), anyString(), anyInt());

        // Execute the method
        spyCheck.visitToken(mockClassDef);

        // Verify log method was called (ensuring coverage)
        verify(spyCheck).log(10, "methodlimit", 1);
    }
    
}
