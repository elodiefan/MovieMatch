package use_case.search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class SearchInputDataTest {

    @Test
    void keywordOnlyConstructorStartsAtPageOne() {
        final SearchInputData inputData = new SearchInputData("arrival");

        assertEquals("arrival", inputData.getKeyword());
        assertEquals(1, inputData.getStartPage());
    }

    @Test
    void pagingConstructorPreservesRequestedValues() {
        final SearchInputData inputData = new SearchInputData("dark", 7);

        assertEquals("dark", inputData.getKeyword());
        assertEquals(7, inputData.getStartPage());
    }

    @Test
    void inputDataDoesNotSilentlyRewriteNullKeyword() {
        assertNull(new SearchInputData(null).getKeyword());
    }
}
