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

package org.killbill.billing.entitlement;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import org.killbill.billing.account.api.Account;
import org.killbill.billing.entitlement.api.Entitlement;
import org.killbill.billing.subscription.api.user.SubscriptionBaseBundle;

// Wrapper object to save on DAO calls
public interface AccountEntitlements {

    public Account getAccount();

    // Map bundle id -> bundle
    public Map<UUID, SubscriptionBaseBundle> getBundles();

    // Map bundle id -> entitlements
    public Map<UUID, Collection<Entitlement>> getEntitlements();
}
