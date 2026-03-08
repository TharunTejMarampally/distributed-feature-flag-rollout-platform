//package com.flag.flag_service.configurations;
//
//import com.github.benmanes.caffeine.cache.Caffeine;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
////import org.springframework.cache.caffeine.CaffeineCacheManager;
//
//import java.time.Duration;
//
//@Configuration
//@EnableCaching
//public class CacheConfig {
////    @Bean
////    public CaffeineCacheManager cacheManager() {
////        CaffeineCacheManager cm = new CaffeineCacheManager("flags");
////        cm.setCaffeine(Caffeine.newBuilder()
////                .expireAfterWrite(Duration.ofMinutes(30))
////                .maximumSize(1000));
////        return cm;
////    }
//}
