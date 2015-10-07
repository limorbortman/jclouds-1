/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jclouds.openstack.ceilometer.v2.domain;

import com.google.inject.name.Named;

import java.beans.ConstructorProperties;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An Alarm the Ceilometer server knows about
 *
 * @see <a href= "http://developer.openstack.org/api-ref-telemetry-v2.html" />
 * @see <a href= "https://github.com/openstack/ceilometer/tree/master/" />
 */

public class Alarm {
    @Named("alarm_actions")
    private final List<String> alarmActions;
    @Named("insufficient_data_actions")
    private final List<String> insufficientDataActions;
    @Named("ok_actions")
    private final List<String> okActions;
    @Named("alarm_id")
    private final String alarmId;
    private final String name;
    private final String description;
    private final boolean enabled;
    @Named("repeat_actions")
    private final boolean repeatActions;
    @Named("project_id")
    private final String projectId;
    private final String state;
    private final String severity;
    @Named("state_timestamp")
    private final Date stateTimestamp;
    @Named("threshold_rule")
    private final Map<String, Object> thresholdRule;
    @Named("time_constraints")
    private final Set<Map<String, Object>> timeConstraints;
    private final Date timestamp;
    private final String userId;

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder().fromAlarm(this);
    }

    public static class Builder {

        private List<String> alarmActions;
        private List<String> insufficientDataActions;
        private List<String> okActions;
        private String alarmId;
        private String name;
        private String description;
        private boolean enabled;
        private boolean repeatActions;
        private String projectId;
        private String state;
        private String severity;
        private Date stateTimestamp;
        private Map<String, Object> thresholdRule;
        private Set<Map<String, Object>> timeConstraints;
        private Date timestamp;
        private String userId;

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder timestamp(Date timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder timeConstraints(Set<Map<String, Object>> timeConstraints) {
            this.timeConstraints = timeConstraints;
            return this;
        }

        public Builder thresholdRule(Map<String, Object> thresholdRule) {
            this.thresholdRule = thresholdRule;
            return this;
        }

        public Builder stateTimestamp(Date stateTimestamp) {
            this.stateTimestamp = stateTimestamp;
            return this;
        }

        public Builder severity(String severity) {
            this.severity = severity;
            return this;
        }

        public Builder state(String state) {
            this.state = state;
            return this;
        }

        public Builder projectId(String projectId) {
            this.projectId = projectId;
            return this;
        }

        public Builder repeatActions(boolean repeatActions) {
            this.repeatActions = repeatActions;
            return this;
        }

        public Builder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder alarmId(String alarmId) {
            this.alarmId = alarmId;
            return this;
        }

        public Builder okActions(List<String> okActions) {
            this.okActions = okActions;
            return this;
        }

        public Builder alarmActions(List<String> alarmActions) {
            this.alarmActions = alarmActions;
            return this;
        }

        public Builder insufficientDataActions(List<String> insufficientDataActions) {
            this.insufficientDataActions = insufficientDataActions;
            return this;
        }

        public Alarm build() {
            return new Alarm(alarmActions, insufficientDataActions, okActions, alarmId, name,
                    description, enabled, repeatActions, projectId, state, severity,
                    stateTimestamp, thresholdRule, timeConstraints, timestamp, userId);
        }

        public Builder fromAlarm(Alarm alarm) {
            return this.alarmActions(alarm.getAlarmActions()).
                    alarmId(alarm.getAlarmId()).
                    description(alarm.getDescription()).
                    enabled(alarm.isEnabled()).
                    insufficientDataActions(alarm.getInsufficientDataActions()).
                    name(alarm.getName()).
                    okActions(alarm.getOkActions()).
                    projectId(alarm.getProjectId()).
                    repeatActions(alarm.isRepeatActions()).
                    severity(alarm.getSeverity()).
                    state(alarm.getState()).
                    stateTimestamp(alarm.getStateTimestamp()).
                    thresholdRule(alarm.getThresholdRule()).
                    timeConstraints(alarm.getTimeConstraints()).
                    userId(alarm.getUserId()).
                    timestamp(alarm.getTimestamp());
        }

    }

    @ConstructorProperties({"alarm_actions", "insufficient_data_actions", "ok_actions",
            "alarm_id", "name", "description", "enabled", "repeat_actions",
            "project_id", "state", "severity", "state_timestamp", "threshold_rule",
            "time_constraints", "timestamp", "user_id"})
    public Alarm(Object alarmActions, Object insufficientDataActions, Object okActions, String alarmId,
                 String name, String description, boolean enabled, boolean repeatActions, String projectId, String state,
                 String severity, Date stateTimestamp, Map<String, Object> thresholdRule, Set<Map<String, Object>> timeConstraints,
                 Date timestamp, String userId) {

        this.alarmActions = (List<String>) alarmActions;
        this.insufficientDataActions = (List<String>) insufficientDataActions;
        this.okActions = (List<String>) okActions;
        this.alarmId = alarmId;
        this.name = name;
        this.description = description;
        this.enabled = enabled;
        this.repeatActions = repeatActions;
        this.projectId = projectId;
        this.state = state;
        this.severity = severity;
        this.stateTimestamp = stateTimestamp;
        this.thresholdRule = thresholdRule;
        this.timeConstraints = timeConstraints;
        this.timestamp = timestamp;
        this.userId = userId;
    }

    public List<String> getAlarmActions() {
        return alarmActions;
    }

    public List<String> getInsufficientDataActions() {
        return insufficientDataActions;
    }

    public List<String> getOkActions() {
        return okActions;
    }

    public String getAlarmId() {
        return alarmId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isRepeatActions() {
        return repeatActions;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getState() {
        return state;
    }

    public String getSeverity() {
        return severity;
    }

    public Date getStateTimestamp() {
        return stateTimestamp;
    }

    public Map<String, Object> getThresholdRule() {
        return thresholdRule;
    }

    public Set<Map<String, Object>> getTimeConstraints() {
        return timeConstraints;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getUserId() {
        return userId;
    }
}

