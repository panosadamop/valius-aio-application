package io.valius.app.service.impl;

import io.valius.app.domain.Survey;
import io.valius.app.repository.SurveyRepository;
import io.valius.app.service.SurveyService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Survey}.
 */
@Service
@Transactional
public class SurveyServiceImpl implements SurveyService {

    private final Logger log = LoggerFactory.getLogger(SurveyServiceImpl.class);

    private final SurveyRepository surveyRepository;

    public SurveyServiceImpl(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @Override
    public Survey save(Survey survey) {
        log.debug("Request to save Survey : {}", survey);
        return surveyRepository.save(survey);
    }

    @Override
    public Survey update(Survey survey) {
        log.debug("Request to update Survey : {}", survey);
        return surveyRepository.save(survey);
    }

    @Override
    public Optional<Survey> partialUpdate(Survey survey) {
        log.debug("Request to partially update Survey : {}", survey);

        return surveyRepository
            .findById(survey.getId())
            .map(existingSurvey -> {
                if (survey.getConsumerAssessedBrands() != null) {
                    existingSurvey.setConsumerAssessedBrands(survey.getConsumerAssessedBrands());
                }
                if (survey.getCriticalSuccessFactors() != null) {
                    existingSurvey.setCriticalSuccessFactors(survey.getCriticalSuccessFactors());
                }
                if (survey.getAdditionalBuyingCriteria() != null) {
                    existingSurvey.setAdditionalBuyingCriteria(survey.getAdditionalBuyingCriteria());
                }
                if (survey.getConsumerSegmentGroup() != null) {
                    existingSurvey.setConsumerSegmentGroup(survey.getConsumerSegmentGroup());
                }
                if (survey.getSegmentCsf() != null) {
                    existingSurvey.setSegmentCsf(survey.getSegmentCsf());
                }
                if (survey.getGender() != null) {
                    existingSurvey.setGender(survey.getGender());
                }
                if (survey.getAgeGroup() != null) {
                    existingSurvey.setAgeGroup(survey.getAgeGroup());
                }
                if (survey.getLocation() != null) {
                    existingSurvey.setLocation(survey.getLocation());
                }
                if (survey.getEducation() != null) {
                    existingSurvey.setEducation(survey.getEducation());
                }
                if (survey.getOccupation() != null) {
                    existingSurvey.setOccupation(survey.getOccupation());
                }
                if (survey.getNetPromoterScore() != null) {
                    existingSurvey.setNetPromoterScore(survey.getNetPromoterScore());
                }

                return existingSurvey;
            })
            .map(surveyRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Survey> findAll(Pageable pageable) {
        log.debug("Request to get all Surveys");
        return surveyRepository.findAll(pageable);
    }

    public Page<Survey> findAllWithEagerRelationships(Pageable pageable) {
        return surveyRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Survey> findOne(Long id) {
        log.debug("Request to get Survey : {}", id);
        return surveyRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Survey : {}", id);
        surveyRepository.deleteById(id);
    }
}
