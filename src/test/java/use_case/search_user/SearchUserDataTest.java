package use_case.search_user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

class SearchUserDataTest {

    @Test
    void inputDataReturnsKeyword() {
        final SearchUserInputData data = new SearchUserInputData("bob");

        assertEquals("bob", data.getKeyword());
    }

    @Test
    void outputDataReturnsResultsAndKeyword() {
        final List<UserSummary> results = List.of(new UserSummary("bob", "Bob"));
        final SearchUserOutputData data = new SearchUserOutputData(results, "bob");

        assertEquals(results, data.getResults());
        assertEquals("bob", data.getKeyword());
    }
}
