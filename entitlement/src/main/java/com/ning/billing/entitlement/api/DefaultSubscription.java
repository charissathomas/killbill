/*
 * Copyright 2010-2013 Ning, Inc.
 *
 * Ning licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.ning.billing.entitlement.api;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

public class DefaultSubscription extends DefaultEntitlement implements Subscription {

    private final List<BlockingState> blockingStates;

    DefaultSubscription(final DefaultEntitlement entitlement, final List<BlockingState> blockingStates) {
        super(entitlement);
        this.blockingStates = blockingStates;
    }

    @Override
    public LocalDate getBillingStartDate() {
        return new LocalDate(getSubscriptionBase().getStartDate(), getAccount().getTimeZone());
    }

    @Override
    public LocalDate getBillingEndDate() {
        final DateTime futureOrCurrentEndDateForSubscription = getSubscriptionBase().getEndDate() != null ? getSubscriptionBase().getEndDate() : getSubscriptionBase().getFutureEndDate();
        final DateTime futureOrCurrentEndDateForBaseSubscription = getEventsStream().getBaseSubscription().getEndDate() != null ? getEventsStream().getBaseSubscription().getEndDate() : getEventsStream().getBaseSubscription().getFutureEndDate();

        final DateTime futureOrCurrentEndDate;
        if (futureOrCurrentEndDateForBaseSubscription != null && futureOrCurrentEndDateForBaseSubscription.isBefore(futureOrCurrentEndDateForSubscription)) {
            futureOrCurrentEndDate = futureOrCurrentEndDateForBaseSubscription;
        } else {
            futureOrCurrentEndDate = futureOrCurrentEndDateForSubscription;
        }

        return futureOrCurrentEndDate != null ? new LocalDate(futureOrCurrentEndDate, getAccount().getTimeZone()) : null;
    }

    @Override
    public LocalDate getChargedThroughDate() {
        return getSubscriptionBase().getChargedThroughDate() != null ? new LocalDate(getSubscriptionBase().getChargedThroughDate(), getAccount().getTimeZone()) : null;
    }

    @Override
    public String getCurrentStateForService(final String serviceName) {

        if (blockingStates == null) {
            return null;
        }
        for (BlockingState cur : blockingStates) {
            if (cur.getService().equals(serviceName)) {
                return cur.getStateName();
            }
        }
        return null;
    }
}
