package use_case.media_detail;

/**
 * Input boundary for the Media Detail Use Case.
 */
public interface MediaDetailInputBoundary {

    /**
     * Displays details for the selected media.
     */
    void execute(MediaDetailInputData inputData);

    /**
     * Switches back to the search result view.
     */
    void backToSearchResultView();
}
