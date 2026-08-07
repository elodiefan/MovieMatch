package use_case.get_profile;

/** Input Boundary for actions related to getting user profile. */
public interface GetProfileInputBoundary {

    /** Executes the get user profile use case. */
    void execute(GetProfileInputData getProfileInputData);
}
