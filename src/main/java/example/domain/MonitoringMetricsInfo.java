package example.domain;

public class MonitoringMetricsInfo {
    private final String objectName;
    private final String attributeName;
    private final double minValue;
    private final double maxValue;

    public MonitoringMetricsInfo(String objectName, String attributeName, double minValue, double maxValue) {
        this.objectName = objectName;
        this.attributeName = attributeName;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public String getObjectName() {
        return objectName;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public double getMinValue() {
        return minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    @Override
    public String toString() {
        return "MonitoringMetricsInfo{" +
                "objectName='" + objectName + '\'' +
                ", attributeName='" + attributeName + '\'' +
                ", minValue=" + minValue +
                ", maxValue=" + maxValue +
                '}';
    }
}
