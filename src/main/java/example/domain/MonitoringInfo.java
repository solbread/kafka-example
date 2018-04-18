package example.domain;

import java.util.List;

public class MonitoringInfo {
    private final String hostName;
    private final int jmxPort;
    private final List<MonitoringMetricsInfo> monitoringMetricsInfos;

    public MonitoringInfo(String hostName, int jmxPort, List<MonitoringMetricsInfo> monitoringMetricsInfos) {
        this.hostName = hostName;
        this.jmxPort = jmxPort;
        this.monitoringMetricsInfos = monitoringMetricsInfos;
    }

    public String getHostName() {
        return hostName;
    }

    public int getJmxPort() {
        return jmxPort;
    }

    public List<MonitoringMetricsInfo> getMonitoringMetricsInfos() {
        return monitoringMetricsInfos;
    }

    @Override
    public String toString() {
        return "MonitoringInfo{" +
                "hostName='" + hostName + '\'' +
                ", jmxPort=" + jmxPort +
                ", monitoringMetricsInfos=" + monitoringMetricsInfos +
                '}';
    }
}
