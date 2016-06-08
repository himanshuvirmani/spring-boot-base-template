package com.springboot.demo.config;

import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import org.springframework.boot.actuate.autoconfigure.ExportMetricWriter;
import org.springframework.boot.actuate.metrics.jmx.JmxMetricWriter;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jmx.export.MBeanExporter;


@Configuration
@EnableMetrics(proxyTargetClass = true)
@Profile("!" + Constants.SPRING_PROFILE_TEST)

public class MetricsConfiguration {
    @Bean
    @ExportMetricWriter
    MetricWriter metricWriter(MBeanExporter exporter) {
        return new JmxMetricWriter(exporter);
    }
}
