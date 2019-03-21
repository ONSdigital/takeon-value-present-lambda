package uk.gov.ons.validation.entity;

public class InputData {

    public String value = "";
    public Object metaData = "{}";

    public InputData() {
    }

    public InputData(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public Object getMetaData() {
        return metaData;
    }

}
