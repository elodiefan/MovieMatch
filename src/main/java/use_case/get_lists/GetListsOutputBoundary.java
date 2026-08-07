package use_case.get_lists;

/** The output boundary interface for the List View Use Case. */
public interface GetListsOutputBoundary {
    /** Switches to the Personal Account View. */
    void switchToPersonalAccountView();

    /** Switches to the Other Account View. */
    void switchToOtherAccountView();
}

