package com.bobwares.databus.common.model;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.util.List;
import java.util.Map;

public class JobExecutionMessage {
    String jobNumber;
    String jobKey;
    JobConfiguration jobConfiguration;
    File inboundFile;
    String line;
    List<MultiValueMap<String, Object>> rows;
    ObjectNode jsonNodes;

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public ObjectNode getJsonNodes() {
        return jsonNodes;
    }

    public void setJsonNodes(ObjectNode jsonNodes) {
        this.jsonNodes = jsonNodes;
    }

    public List<MultiValueMap<String, Object>> getRows() {
        return rows;
    }

    public void setRows(List<MultiValueMap<String, Object>> rows) {
        this.rows = rows;
    }

    public JobConfiguration getJobConfiguration() {
        return jobConfiguration;
    }

    public void setJobConfiguration(JobConfiguration jobConfiguration) {
        this.jobConfiguration = jobConfiguration;
    }

    public File getInboundFile() {
        return inboundFile;
    }

    public void setInboundFile(File inboundFile) {
        this.inboundFile = inboundFile;
    }

    public String getJobKey() {
        return jobKey;
    }

    public void setJobKey(String jobKey) {
        this.jobKey = jobKey;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}
