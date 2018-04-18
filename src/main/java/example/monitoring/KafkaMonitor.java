package example.monitoring;

import example.domain.MonitoringInfo;
import example.domain.MonitoringMetricsInfo;
import example.domain.TelegramBotInfo;
import example.util.AlarmUtil;
import example.util.HttpResponseData;
import example.util.JMXUtil;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;

import javax.management.ObjectName;
import java.util.List;
import java.util.Map;


public class KafkaMonitor {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final List<MonitoringInfo> monitoringInfos;
    private final TelegramBotInfo telegramBotInfo;

    public KafkaMonitor(TelegramBotInfo telegramBotInfo, List<MonitoringInfo> monitoringInfos) {
        this.telegramBotInfo = telegramBotInfo;
        this.monitoringInfos = monitoringInfos;
    }

    public void monitor() {
        for(MonitoringInfo monitoringInfo : this.monitoringInfos) {
            monitorKafka(monitoringInfo);
        }
    }

    private void monitorKafka(MonitoringInfo monitoringInfo) {
        String alertMessage = null;
        String jmxServiceUrl = JMXUtil.makeJMXServiceUrl(monitoringInfo.getHostName(), monitoringInfo.getJmxPort());
        for(MonitoringMetricsInfo monitoringMetricsInfo : monitoringInfo.getMonitoringMetricsInfos()) {
            boolean isWrongStatus = true;
            try {
                Map<ObjectName, Object> metrics = JMXUtil.getJMXMetrics(jmxServiceUrl, monitoringMetricsInfo);
                for(Map.Entry<ObjectName, Object> metricsEntry : metrics.entrySet()) {
                    double metricValue = Double.parseDouble(String.valueOf(metricsEntry.getValue()));
                    if(metricValue < monitoringMetricsInfo.getMinValue() || metricValue > monitoringMetricsInfo.getMaxValue()) {
                        alertMessage = makeWarningMessage(monitoringInfo, metricsEntry);
                        logger.error(alertMessage);
                    } else {
                        String okMessage = makeOkMessage(monitoringInfo, metricsEntry);
                        logger.info(okMessage);
                        isWrongStatus = false;
                    }
                }
            } catch (Exception e) {
                alertMessage = makeErrorMessage(monitoringInfo, monitoringMetricsInfo.getObjectName(), e.getMessage());
                logger.error(alertMessage, e);
            }
            if(isWrongStatus) {
                alertWarning(alertMessage);
            }
        }
    }

    private String makeWarningMessage(MonitoringInfo monitoringInfo, Map.Entry<ObjectName, Object> metricsEntry) {
        return monitoringInfo.getHostName() + ":" + monitoringInfo.getJmxPort() + " " + metricsEntry.getKey() + " is wrong : " + metricsEntry.getValue();
    }

    private String makeOkMessage(MonitoringInfo monitoringInfo, Map.Entry<ObjectName, Object> metricsEntry) {
        return monitoringInfo.getHostName() + ":" + monitoringInfo.getJmxPort() + " " + metricsEntry.getKey() + " is ok : " + metricsEntry.getValue();
    }

    private String makeErrorMessage(MonitoringInfo monitoringInfo, String metricName, String errorMessage) {
        return monitoringInfo.getHostName() + ":" + monitoringInfo.getJmxPort() + " monitor kafka metrics failed : metric name - " + metricName + ", error message - " + errorMessage;
    }

    private void alertWarning(String message) {
        try {
            HttpResponseData httpResponseData = AlarmUtil.alertTelegram(this.telegramBotInfo, message);;
            if (httpResponseData.getCode() == HttpStatus.SC_OK) {
                logger.info("alert warning success : alerted message - " + message);
            } else {
                logger.error("alert warning failed : alerted message - " + message + ", " + httpResponseData.toString());
            }

        } catch(Exception e) {
            logger.error("alert warning error : alerted message - " + message + ", error message - " + e.getMessage(), e);
        }
    }
}
