package com.epam.demo.logging.starter.autoconfigure;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.context.properties.bind.validation.BindValidationException;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

public class LoggingAutoConfigurationIT {

    @Test
    void shouldAutoConfigurationBeAppliedToWebApplication() {
        new WebApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(LoggingAutoConfiguration.class))
                .run(context -> {
                    assertThat(context).hasNotFailed()
                            .hasSingleBean(CommonsRequestLoggingFilter.class)
                            .hasSingleBean(LoggingProperties.class)
                            .getBean(LoggingProperties.class).hasNoNullFieldsOrProperties();
                });
    }

    @Test
    void shouldAutoConfigurationBeFailedDueToValidationConstraints() {
        new WebApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(LoggingAutoConfiguration.class))
                .withPropertyValues(
                        "demo.logging.message-prefix=>"
                )
                .run(context -> {
                    assertThat(context).hasFailed().getFailure()
                            .hasRootCauseInstanceOf(BindValidationException.class)
                            .hasStackTraceContaining("'demo.logging' on field 'messagePrefix'");
                });
    }

    @Test
    void shouldLoggingFilterBeNotAppliedToContext() {
        new WebApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(LoggingAutoConfiguration.class))
                .withUserConfiguration(TestConfiguration.class)
                .run(context -> {
                    assertThat(context).hasNotFailed()
                            .hasSingleBean(CommonsRequestLoggingFilter.class)
                            .hasSingleBean(MyRequestLoggingFilter.class);
                });
    }

    @Test
    void shouldAutoConfigurationBeNotAppliedWhenItIsNotWebApplication() {
        new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(LoggingAutoConfiguration.class))
                .run(context -> {
                    assertThat(context).hasNotFailed()
                            .doesNotHaveBean(LoggingAutoConfiguration.class)
                            .doesNotHaveBean(CommonsRequestLoggingFilter.class)
                            .doesNotHaveBean(LoggingProperties.class);
                });
    }

    private static class TestConfiguration {

        @Bean
        public MyRequestLoggingFilter myRequestLoggingFilter() {
            return new MyRequestLoggingFilter();
        }

    }

    private static class MyRequestLoggingFilter extends CommonsRequestLoggingFilter {

        @Override
        protected boolean shouldLog(HttpServletRequest request) {
            return true;
        }

    }

}
