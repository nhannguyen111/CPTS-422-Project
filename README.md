# CPTS-422-Project

HalsteadLengthCheck:
- Halstead Operators are actions that can be done to entities.
- Halstead Operands are entities that can be acted on by Operators

- Examples of operators are any mathmatical operation such as addition and multiplication. They can also be logical operations (such as greater than / less than), and they can be assignment operators (=, -=, +=).
- Examples of operands are pretty much any numbers, variables, or even booleans.

NumOfCommentsCheck:
- This function basically identifies comments and counts them
- We identify comments simply by using TokenTypes.SINGLE_LINE_COMMENT, and TokenTypes.BLOCK_COMMENT_BEGIN
- We do not need TokenTypes.BLOCK_COMMENT_END the beginning is enough to identify them, and to prevent counting block comments twice, it is not included.
