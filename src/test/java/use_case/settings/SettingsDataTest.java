package use_case.settings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SettingsDataTest {

    @Test
    void inputDataReturnsSettings() {
        final SettingsInputData data = new SettingsInputData(true, 22, true);

        assertTrue(data.isDarkMode());
        assertEquals(22, data.getTextSize());
        assertTrue(data.isAllowAdultContent());
    }

    @Test
    void outputDataReturnsSettings() {
        final SettingsOutputData data = new SettingsOutputData(true, 22, true);

        assertTrue(data.isDarkMode());
        assertEquals(22, data.getTextSize());
        assertTrue(data.isAllowAdultContent());
    }
}
