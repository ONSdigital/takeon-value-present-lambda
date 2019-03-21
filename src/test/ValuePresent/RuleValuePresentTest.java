package ValuePresent;

import junit.framework.TestCase;
import uk.gov.ons.validation.entity.InputData;
import uk.gov.ons.validation.service.RuleValuePresent;


public class RuleValuePresentTest extends TestCase {

    public void testGivenStatisticalVariableValueProvideValueFormula() {
        String value = "648213";
        InputData sourceData = new InputData(value);
        String expectedFormula = value + " != ''";
        RuleValuePresent validation = new RuleValuePresent(sourceData);
        assertEquals(expectedFormula, validation.getValueFormula());
    }

    public void testGivenNonBlankValueTriggerValidation() {
        InputData sourceData = new InputData("648213");
        RuleValuePresent validation = new RuleValuePresent(sourceData);
        assertTrue(validation.run());
    }

    public void testGivenBlankValueTriggerValidation() {
        InputData sourceData = new InputData("");
        RuleValuePresent validation = new RuleValuePresent(sourceData);
        assertFalse(validation.run());
    }

    public void testGivenStringWithSpacesTriggerValidation() {
        InputData sourceData = new InputData("   ");
        RuleValuePresent validation = new RuleValuePresent(sourceData);
        assertTrue(validation.run());
    }

    public void testGivenNullInputDataDoNotTriggerValidation() {
        RuleValuePresent validation = new RuleValuePresent(null);
        assertFalse(validation.run());
    }

}