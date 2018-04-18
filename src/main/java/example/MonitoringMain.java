package example;

import example.domain.MonitoringInfo;
import example.domain.MonitoringMetricsInfo;
import example.domain.TelegramBotInfo;
import example.monitoring.KafkaMonitor;
import example.util.PropertyFileUtil;

import java.util.*;

public class MonitoringMain {

    public void start(KafkaMonitor kafkaMonitor) {
        kafkaMonitor.monitor();
    }

    public static void main(String[] args) {
        PropertyFileUtil propertyFileUtil = new PropertyFileUtil("kafka");
        String telegramApiUrl = propertyFileUtil.getStringValue("telegram.api.url");
        String chatId = propertyFileUtil.getStringValue("telegram.api.chatid");
        TelegramBotInfo telegramBotInfo = new TelegramBotInfo(telegramApiUrl, chatId);

        List<MonitoringMetricsInfo> monitoringMetricsInfos = new ArrayList<>();
        monitoringMetricsInfos.add(new MonitoringMetricsInfo("kafka.server:type=ReplicaManager,name=UnderReplicatedPartitions", "Value", 0.0, 0.0));

        List<MonitoringInfo> monitoringInfos = new ArrayList<>();
        monitoringInfos.add(new MonitoringInfo("localhost",8080, monitoringMetricsInfos));
        monitoringInfos.add(new MonitoringInfo("localhost",8081, monitoringMetricsInfos));
        monitoringInfos.add(new MonitoringInfo("localhost",8082, monitoringMetricsInfos));

        List<MonitoringMetricsInfo> consumerMonitoringMetricsInfos = new ArrayList<>();
        consumerMonitoringMetricsInfos.add(new MonitoringMetricsInfo("kafka.consumer:type=consumer-fetch-manager-metrics,client-id=*", "records-lag-max", 0.0, 10.0));
        monitoringInfos.add(new MonitoringInfo("localhost",9000, consumerMonitoringMetricsInfos));


        KafkaMonitor kafkaMonitor = new KafkaMonitor(telegramBotInfo, monitoringInfos);


        MonitoringMain monitoringMain = new MonitoringMain();
        monitoringMain.start(kafkaMonitor);
    }
}
