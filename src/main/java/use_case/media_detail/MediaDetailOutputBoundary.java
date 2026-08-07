package use_case.media_detail;

/**
 * Output boundary for the Media Detail Use Case.
 */
public interface MediaDetailOutputBoundary {

    /**
     * Prepares the media detail view.
     */
    void prepareSuccessView(MediaDetailOutputData outputData);

    /**
     * Prepares an error view.
     */
    void prepareFailView(String error);

    /**
     * Switches back to the search result view.
     */
    void backToSearchResultView();
}
