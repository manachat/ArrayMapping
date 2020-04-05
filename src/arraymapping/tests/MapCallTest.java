package arraymapping.tests;

import arraymapping.src.Element;
import arraymapping.src.SYNTAX_ERROR;
import arraymapping.src.TYPE_ERROR;
import arraymapping.util.Utilities;
import org.junit.jupiter.api.Test;
import arraymapping.src.MapCall;

import static org.junit.jupiter.api.Assertions.*;

class MapCallTest {

    @Test
    void parseCall(){
        MapCall test = new MapCall("element");
        assertEquals(Element.class, test.getExpression().getClass());
        assertEquals(Utilities.ReturnType.ARITHMETIC, test.getExpression().getReturnType());
        assertThrows(TYPE_ERROR.class, () -> new MapCall("(28|element)"));
        assertThrows(SYNTAX_ERROR.class, () -> new MapCall("element+element"));

    }

    @Test
    void testToString() {
        MapCall test = new MapCall("((16+element)*element)");
        assertEquals("map{((16+element)*element)}", test.toString());
    }
}