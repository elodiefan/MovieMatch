package use_case.customize;

/**
 * Input boundary for actions related to customize.
 */
public interface CustomizeInputBoundary {

    /**
     * Executes the colour customize change.
     * @param customizeInputData the input data for the use case.
     */
    void executeColourChange(CustomizeInputData customizeInputData);

    /**
     * Executes the check for customizability.
     */
    void executeCheckCustomizability();
}
