package io.hhplus.speciallecture.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(basePackages = ["io.hhplus.speciallecture.infrastructure"])
class JpaConfig
