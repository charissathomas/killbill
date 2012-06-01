/*
 * Copyright 2010-2011 Ning, Inc.
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

package com.ning.billing.payment.glue;

import java.util.Properties;

import org.skife.config.ConfigurationObjectFactory;

import com.google.inject.AbstractModule;
import com.ning.billing.config.PaymentConfig;
import com.ning.billing.payment.RequestProcessor;
import com.ning.billing.payment.api.DefaultPaymentApi;
import com.ning.billing.payment.api.PaymentApi;
import com.ning.billing.payment.api.PaymentService;
import com.ning.billing.payment.core.AccountProcessor;
import com.ning.billing.payment.core.PaymentMethodProcessor;
import com.ning.billing.payment.core.PaymentProcessor;
import com.ning.billing.payment.core.RefundProcessor;
import com.ning.billing.payment.dao.AuditedPaymentDao;
import com.ning.billing.payment.dao.PaymentDao;
import com.ning.billing.payment.provider.DefaultPaymentProviderPluginRegistry;
import com.ning.billing.payment.provider.PaymentProviderPluginRegistry;
import com.ning.billing.payment.retry.FailedPaymentRetryService;
import com.ning.billing.payment.retry.FailedPaymentRetryService.FailedPaymentRetryServiceScheduler;
import com.ning.billing.payment.retry.TimedoutPaymentRetryService;

public class PaymentModule extends AbstractModule {
    private final Properties props;

    public PaymentModule() {
        this.props = System.getProperties();
    }

    public PaymentModule(Properties props) {
        this.props = props;
    }

    protected void installPaymentDao() {
        bind(PaymentDao.class).to(AuditedPaymentDao.class).asEagerSingleton();
    }

    protected void installPaymentProviderPlugins(PaymentConfig config) {
    }

    protected void installRetryEngines() {
        bind(FailedPaymentRetryService.class).asEagerSingleton();
        bind(TimedoutPaymentRetryService.class).asEagerSingleton(); 
        bind(FailedPaymentRetryServiceScheduler.class).asEagerSingleton();
    }
    
    protected void installProcessors() {
        bind(AccountProcessor.class).asEagerSingleton();
        bind(PaymentProcessor.class).asEagerSingleton();
        bind(RefundProcessor.class).asEagerSingleton();
        bind(PaymentMethodProcessor.class).asEagerSingleton();
    }

    @Override
    protected void configure() {
        final ConfigurationObjectFactory factory = new ConfigurationObjectFactory(props);
        final PaymentConfig paymentConfig = factory.build(PaymentConfig.class);

        bind(PaymentConfig.class).toInstance(paymentConfig);
        bind(PaymentProviderPluginRegistry.class).to(DefaultPaymentProviderPluginRegistry.class).asEagerSingleton();
        bind(PaymentApi.class).to(DefaultPaymentApi.class).asEagerSingleton();
        bind(RequestProcessor.class).asEagerSingleton();
        bind(PaymentService.class).to(DefaultPaymentService.class).asEagerSingleton();
        installPaymentProviderPlugins(paymentConfig);
        installPaymentDao();
        installProcessors();
        installRetryEngines();
    }
}