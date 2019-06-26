/*
 * Copyright 2019 Enterprise Proxy Authors
 *
 * Licensed under the Apache License, Version 2.0 (the “License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an “AS IS” BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.klaytn.enterpriseproxy.fee.config;

import com.klaytn.enterpriseproxy.tx.model.TransactionResponse;
import com.klaytn.enterpriseproxy.tx.model.TransactionResponseCode;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import com.klaytn.enterpriseproxy.fee.handler.FeeDelegatedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class FeePayerAspect {
    @Autowired
    private FeeDelegatedHandler handler;




    /**
     * Validation of Fee payers
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.klaytn.enterpriseproxy.fee.delegated..*.*(..))")
    public Object validate(ProceedingJoinPoint joinPoint) throws Throwable {
        TransactionResponse result = handler.feePayerWalletValidation();
        if (TransactionResponseCode.SUCCESS.getCode() == result.getCode()) {
            return joinPoint.proceed();
        }

        return result;
    }
}