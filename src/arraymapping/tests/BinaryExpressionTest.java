package arraymapping.tests;

import arraymapping.src.ConstantExpression;
import arraymapping.src.Element;
import org.junit.jupiter.api.Test;
import arraymapping.src.BinaryExpression;
import arraymapping.src.SYNTAX_ERROR;
import arraymapping.src.TYPE_ERROR;
import arraymapping.util.Utilities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static arraymapping.util.Utilities.Operation;

class BinaryExpressionTest {

    @Test
    void parseContent() {
        BinaryExpression test = new BinaryExpression("(28+element)<54");
        assertEquals(BinaryExpression.class ,test.getLeftOp().getClass());
        assertEquals(ConstantExpression.class, test.getRightOp().getClass());
        assertEquals(Utilities.Operation.LESS ,test.getOperation());
        assertEquals(Utilities.ReturnType.LOGICAL, test.getReturnType());
        assertEquals(54, ((ConstantExpression)test.getRightOp()).getNumber());
        assertEquals(Element.class, ((BinaryExpression)test.getLeftOp()).getRightOp().getClass());
        assertThrows(TYPE_ERROR.class, () -> new BinaryExpression("24|5"));
        assertThrows(TYPE_ERROR.class, () -> new BinaryExpression("(6<3)*(5>element)"));
        assertThrows(SYNTAX_ERROR.class, () -> new BinaryExpression("alement<3"));
        assertThrows(SYNTAX_ERROR.class, () -> new BinaryExpression("8+ 15"));
        assertThrows(SYNTAX_ERROR.class, () -> new BinaryExpression("8/4"));
        test = new BinaryExpression("((((28+element)-6)-element)+1)*(element-42)");
        assertEquals(Operation.MULTIPLICATION, test.getOperation());
    }

    @Test
    void testToString() {
        BinaryExpression test = new BinaryExpression("24<36");
        assertEquals("(24<36)", test.toString());
        test = new BinaryExpression("54+26");
        assertEquals("(54+26)", test.toString());
    }
}