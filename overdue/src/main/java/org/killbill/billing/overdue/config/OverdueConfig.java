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

package org.killbill.billing.overdue.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.net.URI;

import org.killbill.xmlloader.ValidatingConfig;
import org.killbill.xmlloader.ValidationErrors;

@XmlRootElement(name = "overdueConfig")
@XmlAccessorType(XmlAccessType.NONE)
public class OverdueConfig extends ValidatingConfig<OverdueConfig> {

    @XmlElement(required = true, name = "accountOverdueStates")
    private OverdueStatesAccount accountOverdueStates = new OverdueStatesAccount();

    public DefaultOverdueStateSet getStateSet() {
        return accountOverdueStates;
    }

    @Override
    public ValidationErrors validate(final OverdueConfig root,
                                     final ValidationErrors errors) {
        return accountOverdueStates.validate(root, errors);
    }

    public OverdueConfig setOverdueStates(final OverdueStatesAccount accountOverdueStates) {
        this.accountOverdueStates = accountOverdueStates;
        return this;
    }


    public URI getURI() {
        return null;
    }

}
