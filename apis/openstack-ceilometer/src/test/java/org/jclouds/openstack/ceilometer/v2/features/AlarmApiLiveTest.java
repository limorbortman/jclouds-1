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
package org.jclouds.openstack.ceilometer.v2.features;

import org.jclouds.openstack.ceilometer.v2.domain.Alarm;
import org.jclouds.openstack.ceilometer.v2.internal.BaseCeilometerApiLiveTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;

/**
 * Tests parsing and Guice wiring of MeterApi
 */
@Test(groups = "live", testName = "AlarmApiLiveTest")
public class AlarmApiLiveTest extends BaseCeilometerApiLiveTest {

    public static final ArrayList<String> EMPTY_OR_NULL = newArrayList("", null);

    public void testListAlarms() {
        for (String region : regions) {
            AlarmApi alarmApi = api.getAlarmApi(region);

            List<Alarm> alarms = alarmApi.listAlarms();

            validate(alarms);
        }
    }


    private void validate(List<Alarm> alarms) {
        assertThat(alarms).extracting("alarmId").doesNotContainAnyElementsOf(EMPTY_OR_NULL);
        assertThat(alarms).extracting("userId").doesNotContainAnyElementsOf(EMPTY_OR_NULL);
        assertThat(alarms).extracting("timestamp").doesNotContainAnyElementsOf(EMPTY_OR_NULL);
        assertThat(alarms).extracting("state").doesNotContainAnyElementsOf(EMPTY_OR_NULL);
        assertThat(alarms).extracting("name").doesNotContainAnyElementsOf(EMPTY_OR_NULL);
        assertThat(alarms).extracting("stateTimestamp").doesNotContainAnyElementsOf(EMPTY_OR_NULL);

        assertThat(alarms).extracting("projectId").doesNotContainNull();
        assertThat(alarms).extracting("alarmActions").doesNotContainNull();
        assertThat(alarms).extracting("okActions").doesNotContainNull();
        assertThat(alarms).extracting("description").doesNotContainNull();
        assertThat(alarms).extracting("enabled").doesNotContainNull();
        assertThat(alarms).extracting("repeatActions").doesNotContainNull();
        assertThat(alarms).extracting("timeConstraints").doesNotContainNull();
    }
}
