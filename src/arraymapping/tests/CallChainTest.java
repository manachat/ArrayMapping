package arraymapping.tests;

import arraymapping.src.CallChain;
import arraymapping.src.FilterCall;
import arraymapping.src.MapCall;
import arraymapping.src.SYNTAX_ERROR;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CallChainTest {

    @Test
    void testConstructor(){
        CallChain test = new CallChain("filter{(element<0)}%>%map{(element*element)}");
        assertEquals(FilterCall.class, test.getCallChain().get(0).getClass());
        assertEquals(MapCall.class, test.getCallChain().get(1).getClass());
        assertThrows(SYNTAX_ERROR.class, () -> new CallChain("map{element}%>%"));
        assertThrows(SYNTAX_ERROR.class, () -> new CallChain("map{element}%>filter{(1<2)}"));
        assertThrows(SYNTAX_ERROR.class, () -> new CallChain("map{elemnt}"));
    }

    @Test
    void testToString() {
        CallChain test = new CallChain("map{(element+10)}%>%filter{((element>3)&(element<20))}");
        assertEquals("map{(element+10)}%>%filter{((element>3)&(element<20))}", test.toString());
    }
}