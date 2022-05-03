package com.william.model;

import java.io.Serializable;

/**
 * Created by william on 17-9-9.
 */
public class AgentTicketReport implements Serializable {
    private static final long serialVersionUID = 1661645548392360180L;

    private String  username;
    private String  mobile;
    private String  processDuration;
    private Integer processCount;
    private Double  onceSolvedRate;
    private Integer onceUnconfirmCount;
    private Integer untracedCount;
    private Integer reprocessUnconfirmCount;
    private Integer reconfirmSolvedCount;
    private Integer reconfirmUnsolvedCount;
    private Integer reconfirmUnconnectCount;
    private Double  totalSolvedRate;

    public AgentTicketReport() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProcessDuration() {
        return processDuration;
    }

    public void setProcessDuration(String processDuration) {
        this.processDuration = processDuration;
    }

    public Integer getProcessCount() {
        return processCount;
    }

    public void setProcessCount(Integer processCount) {
        this.processCount = processCount;
    }

    public Double getOnceSolvedRate() {
        return onceSolvedRate;
    }

    public void setOnceSolvedRate(Double onceSolvedRate) {
        this.onceSolvedRate = onceSolvedRate;
    }

    public Integer getOnceUnconfirmCount() {
        return onceUnconfirmCount;
    }

    public void setOnceUnconfirmCount(Integer onceUnconfirmCount) {
        this.onceUnconfirmCount = onceUnconfirmCount;
    }

    public Integer getUntracedCount() {
        return untracedCount;
    }

    public void setUntracedCount(Integer untracedCount) {
        this.untracedCount = untracedCount;
    }

    public Integer getReprocessUnconfirmCount() {
        return reprocessUnconfirmCount;
    }

    public void setReprocessUnconfirmCount(Integer reprocessUnconfirmCount) {
        this.reprocessUnconfirmCount = reprocessUnconfirmCount;
    }

    public Integer getReconfirmSolvedCount() {
        return reconfirmSolvedCount;
    }

    public void setReconfirmSolvedCount(Integer reconfirmSolvedCount) {
        this.reconfirmSolvedCount = reconfirmSolvedCount;
    }

    public Integer getReconfirmUnsolvedCount() {
        return reconfirmUnsolvedCount;
    }

    public void setReconfirmUnsolvedCount(Integer reconfirmUnsolvedCount) {
        this.reconfirmUnsolvedCount = reconfirmUnsolvedCount;
    }

    public Integer getReconfirmUnconnectCount() {
        return reconfirmUnconnectCount;
    }

    public void setReconfirmUnconnectCount(Integer reconfirmUnconnectCount) {
        this.reconfirmUnconnectCount = reconfirmUnconnectCount;
    }

    public Double getTotalSolvedRate() {
        return totalSolvedRate;
    }

    public void setTotalSolvedRate(Double totalSolvedRate) {
        this.totalSolvedRate = totalSolvedRate;
    }

    @Override
    public String toString() {
        return "AgentTicketReport{" +
                "username='" + username + '\'' +
                ", mobile='" + mobile + '\'' +
                ", processDuration='" + processDuration + '\'' +
                ", processCount=" + processCount +
                ", onceSolvedRate=" + onceSolvedRate +
                ", onceUnconfirmCount=" + onceUnconfirmCount +
                ", untracedCount=" + untracedCount +
                ", reprocessUnconfirmCount=" + reprocessUnconfirmCount +
                ", reconfirmSolvedCount=" + reconfirmSolvedCount +
                ", reconfirmUnsolvedCount=" + reconfirmUnsolvedCount +
                ", reconfirmUnconnectCount=" + reconfirmUnconnectCount +
                ", totalSolvedRate=" + totalSolvedRate +
                '}';
    }
}
