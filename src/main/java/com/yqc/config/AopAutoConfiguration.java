/*
 * Copyright 2012-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yqc.config;

import com.yqc.conditionannotationtest.*;
import com.yqc.service.TestService;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * 拷贝自springboot
 */
@Configuration
@ComponentScan(basePackages = {"com.yqc.aoptest", "com.yqc.controller", "com.yqc.entity", "com.yqc.service"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AopAutoConfiguration {

    @Bean(name = "testService")
    public TestService getTestService() {
        System.out.println("initTestService");
        return new TestService();
    }

    @Bean(name = "service")
    @Conditional(WindowsCondition.class) //里面的类型必须实现Condition接口
    public ListService windowsListService() {
        return new WindowListService();
    }

    @Bean(name = "service")
    @Conditional(LinuxCondition.class)
    public ListService linuxListService() {
        return new LinuxListService();
    }

    /**
     * 定义视图解析器
     *
     * @return
     */
    @Bean
    public org.springframework.web.servlet.view.InternalResourceViewResolver getResolver() {
        return new InternalResourceViewResolver("/WEB-INF/view/", ".jsp");
    }
}
