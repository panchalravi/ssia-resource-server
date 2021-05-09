package com.ssia.demo.resourceserver.config

import com.nimbusds.jose.util.Base64
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import java.security.KeyFactory
import java.security.interfaces.RSAPublicKey
import java.util.Base64.getDecoder


@Configuration
class WebSecurityConfig : WebSecurityConfigurerAdapter() {
    @Value("\${jwt.key}")
    lateinit var jwtKey: String


    @Value("\${key.location}")
    lateinit var publicKey: RSAPublicKey


    override fun configure(http: HttpSecurity?) {
        http {
            authorizeRequests { authorize(anyRequest, authenticated) }

            oauth2ResourceServer {
                //opaqueToken {  }

                jwt {
                    jwtDecoder = jwtDecoder()
                }
            }
        }
    }

    // For symmetric key
    /*
    @Bean
    fun jwtDecoder(): JwtDecoder {
        val key: ByteArray = jwtKey.toByteArray()
        val originalKey: SecretKey = SecretKeySpec(key, 0, key.size, "AES")
        return NimbusJwtDecoder.withSecretKey(originalKey)
            .build()
    }*/


    @Bean
    fun jwtDecoder(): JwtDecoder = NimbusJwtDecoder.withPublicKey(publicKey).build()

}