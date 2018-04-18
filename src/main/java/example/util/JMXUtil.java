package example.util;

import example.domain.MonitoringMetricsInfo;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JMXUtil {

    public static Map<ObjectName, Object> getJMXMetrics(String jmxServiceUrl, MonitoringMetricsInfo monitoringMetricsInfo) throws Exception {
        JMXServiceURL url = new JMXServiceURL(jmxServiceUrl);
        JMXConnector jmxConnector = JMXConnectorFactory.connect(url);
        MBeanServerConnection mBeanServerConnection = jmxConnector.getMBeanServerConnection();

        Set<ObjectName> objectNames = mBeanServerConnection.queryNames(new ObjectName(monitoringMetricsInfo.getObjectName()), null);
        Map<ObjectName, Object> metrics = new HashMap<>();
        for(ObjectName objectName : objectNames) {
            metrics.put(objectName, mBeanServerConnection.getAttribute(objectName, monitoringMetricsInfo.getAttributeName()));
        }
        if(metrics.size() == 0) {
            throw new IllegalStateException("can not get jmx metric");
        }
        return metrics;
    }

    public static String makeJMXServiceUrl(String host, int jmxPort) {
        return "service:jmx:rmi:///jndi/rmi://" + host + ":" + jmxPort + "/jmxrmi";
    }
}
