package io.valius.app.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, io.valius.app.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, io.valius.app.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, io.valius.app.domain.User.class.getName());
            createCache(cm, io.valius.app.domain.Authority.class.getName());
            createCache(cm, io.valius.app.domain.User.class.getName() + ".authorities");
            createCache(cm, io.valius.app.domain.LevelOne.class.getName());
            createCache(cm, io.valius.app.domain.LevelTwo.class.getName());
            createCache(cm, io.valius.app.domain.LevelThree.class.getName());
            createCache(cm, io.valius.app.domain.LevelFour.class.getName());
            createCache(cm, io.valius.app.domain.Survey.class.getName());
            createCache(cm, io.valius.app.domain.PyramidData.class.getName());
            createCache(cm, io.valius.app.domain.ExternalReports.class.getName());
            createCache(cm, io.valius.app.domain.InternalReports.class.getName());
            createCache(cm, io.valius.app.domain.InformationPages.class.getName());
            createCache(cm, io.valius.app.domain.FieldCompanyObjectives.class.getName());
            createCache(cm, io.valius.app.domain.FieldCompanyObjectives.class.getName() + ".jsons");
            createCache(cm, io.valius.app.domain.FieldKpi.class.getName());
            createCache(cm, io.valius.app.domain.FieldKpi.class.getName() + ".jsons");
            createCache(cm, io.valius.app.domain.FieldProductype.class.getName());
            createCache(cm, io.valius.app.domain.FieldProductype.class.getName() + ".jsons");
            createCache(cm, io.valius.app.domain.FieldBuyingCriteria.class.getName());
            createCache(cm, io.valius.app.domain.FieldBuyingCriteria.class.getName() + ".jsons");
            createCache(cm, io.valius.app.domain.FieldAttractivenessFactors.class.getName());
            createCache(cm, io.valius.app.domain.FieldAttractivenessFactors.class.getName() + ".jsons");
            createCache(cm, io.valius.app.domain.FieldBuyingCriteriaWeighting.class.getName());
            createCache(cm, io.valius.app.domain.FieldBuyingCriteriaWeighting.class.getName() + ".jsons");
            createCache(cm, io.valius.app.domain.FieldPreferredPurchaseChannel.class.getName());
            createCache(cm, io.valius.app.domain.FieldPreferredPurchaseChannel.class.getName() + ".jsons");
            createCache(cm, io.valius.app.domain.FieldPreferredCommunicationChannel.class.getName());
            createCache(cm, io.valius.app.domain.FieldPreferredCommunicationChannel.class.getName() + ".jsons");
            createCache(cm, io.valius.app.domain.InfoPageCategory.class.getName());
            createCache(cm, io.valius.app.domain.RequiredSampleSize.class.getName());
            createCache(cm, io.valius.app.domain.Territory.class.getName());
            createCache(cm, io.valius.app.domain.Industry.class.getName());
            createCache(cm, io.valius.app.domain.Country.class.getName());
            createCache(cm, io.valius.app.domain.TargetMarket.class.getName());
            createCache(cm, io.valius.app.domain.OrganisationType.class.getName());
            createCache(cm, io.valius.app.domain.NoOfEmployees.class.getName());
            createCache(cm, io.valius.app.domain.Revenues.class.getName());
            createCache(cm, io.valius.app.domain.CompanyObjectives.class.getName());
            createCache(cm, io.valius.app.domain.StrategicFocus.class.getName());
            createCache(cm, io.valius.app.domain.Kpis.class.getName());
            createCache(cm, io.valius.app.domain.MarketingBudget.class.getName());
            createCache(cm, io.valius.app.domain.MaturityPhase.class.getName());
            createCache(cm, io.valius.app.domain.CompetitivePosition.class.getName());
            createCache(cm, io.valius.app.domain.ProductType.class.getName());
            createCache(cm, io.valius.app.domain.CurrentMarketSegmentation.class.getName());
            createCache(cm, io.valius.app.domain.MarketSegmentationTypeB2b.class.getName());
            createCache(cm, io.valius.app.domain.MarketSegmentationTypeB2bAlt.class.getName());
            createCache(cm, io.valius.app.domain.MarketSegmentationTypeB2bCategories.class.getName());
            createCache(cm, io.valius.app.domain.MarketSegmentationTypeB2c.class.getName());
            createCache(cm, io.valius.app.domain.MarketSegmentationTypeB2cAlt.class.getName());
            createCache(cm, io.valius.app.domain.MarketSegmentationTypeB2cCategories.class.getName());
            createCache(cm, io.valius.app.domain.BuyingCriteria.class.getName());
            createCache(cm, io.valius.app.domain.BuyingCriteriaCategory.class.getName());
            createCache(cm, io.valius.app.domain.MarketSegmentationType.class.getName());
            createCache(cm, io.valius.app.domain.SegmentUniqueCharacteristic.class.getName());
            createCache(cm, io.valius.app.domain.CoreProductElements.class.getName());
            createCache(cm, io.valius.app.domain.RelatedServiceElements.class.getName());
            createCache(cm, io.valius.app.domain.IntangibleElements.class.getName());
            createCache(cm, io.valius.app.domain.CompetitorMaturityPhase.class.getName());
            createCache(cm, io.valius.app.domain.CompetitorCompetitivePosition.class.getName());
            createCache(cm, io.valius.app.domain.EconomicFactors.class.getName());
            createCache(cm, io.valius.app.domain.CompetitiveFactors.class.getName());
            createCache(cm, io.valius.app.domain.SocialFactors.class.getName());
            createCache(cm, io.valius.app.domain.PopulationSize.class.getName());
            createCache(cm, io.valius.app.domain.StatisticalError.class.getName());
            createCache(cm, io.valius.app.domain.ConfidenceLevel.class.getName());
            createCache(cm, io.valius.app.domain.BuyingCriteriaWeighting.class.getName());
            createCache(cm, io.valius.app.domain.AgeGroup.class.getName());
            createCache(cm, io.valius.app.domain.Occupation.class.getName());
            createCache(cm, io.valius.app.domain.PreferredCommunicationChannel.class.getName());
            createCache(cm, io.valius.app.domain.Location.class.getName());
            createCache(cm, io.valius.app.domain.Education.class.getName());
            createCache(cm, io.valius.app.domain.PreferredPurchaseChannel.class.getName());
            createCache(cm, io.valius.app.domain.NetPromoterScore.class.getName());
            createCache(cm, io.valius.app.domain.MarketAttractivenessFactorsCategory.class.getName());
            createCache(cm, io.valius.app.domain.SegmentScoreMAF.class.getName());
            createCache(cm, io.valius.app.domain.AttractivenessFactors.class.getName());
            createCache(cm, io.valius.app.domain.Category.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
