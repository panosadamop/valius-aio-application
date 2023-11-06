import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './survey.reducer';

export const SurveyDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const surveyEntity = useAppSelector(state => state.survey.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="surveyDetailsHeading">
          <Translate contentKey="valiusaioApp.survey.detail.title">Survey</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{surveyEntity.id}</dd>
          <dt>
            <span id="consumerAssessedBrands">
              <Translate contentKey="valiusaioApp.survey.consumerAssessedBrands">Consumer Assessed Brands</Translate>
            </span>
          </dt>
          <dd>{surveyEntity.consumerAssessedBrands}</dd>
          <dt>
            <span id="criticalSuccessFactors">
              <Translate contentKey="valiusaioApp.survey.criticalSuccessFactors">Critical Success Factors</Translate>
            </span>
          </dt>
          <dd>{surveyEntity.criticalSuccessFactors}</dd>
          <dt>
            <span id="additionalBuyingCriteria">
              <Translate contentKey="valiusaioApp.survey.additionalBuyingCriteria">Additional Buying Criteria</Translate>
            </span>
          </dt>
          <dd>{surveyEntity.additionalBuyingCriteria}</dd>
          <dt>
            <span id="consumerSegmentGroup">
              <Translate contentKey="valiusaioApp.survey.consumerSegmentGroup">Consumer Segment Group</Translate>
            </span>
          </dt>
          <dd>{surveyEntity.consumerSegmentGroup}</dd>
          <dt>
            <span id="segmentCsf">
              <Translate contentKey="valiusaioApp.survey.segmentCsf">Segment Csf</Translate>
            </span>
          </dt>
          <dd>{surveyEntity.segmentCsf}</dd>
          <dt>
            <span id="gender">
              <Translate contentKey="valiusaioApp.survey.gender">Gender</Translate>
            </span>
          </dt>
          <dd>{surveyEntity.gender}</dd>
          <dt>
            <span id="ageGroup">
              <Translate contentKey="valiusaioApp.survey.ageGroup">Age Group</Translate>
            </span>
          </dt>
          <dd>{surveyEntity.ageGroup}</dd>
          <dt>
            <span id="location">
              <Translate contentKey="valiusaioApp.survey.location">Location</Translate>
            </span>
          </dt>
          <dd>{surveyEntity.location}</dd>
          <dt>
            <span id="education">
              <Translate contentKey="valiusaioApp.survey.education">Education</Translate>
            </span>
          </dt>
          <dd>{surveyEntity.education}</dd>
          <dt>
            <span id="occupation">
              <Translate contentKey="valiusaioApp.survey.occupation">Occupation</Translate>
            </span>
          </dt>
          <dd>{surveyEntity.occupation}</dd>
          <dt>
            <span id="netPromoterScore">
              <Translate contentKey="valiusaioApp.survey.netPromoterScore">Net Promoter Score</Translate>
            </span>
          </dt>
          <dd>{surveyEntity.netPromoterScore}</dd>
          <dt>
            <Translate contentKey="valiusaioApp.survey.user">User</Translate>
          </dt>
          <dd>{surveyEntity.user ? surveyEntity.user.login : ''}</dd>
          <dt>
            <Translate contentKey="valiusaioApp.survey.buyingCriteriaWeighting">Buying Criteria Weighting</Translate>
          </dt>
          <dd>{surveyEntity.buyingCriteriaWeighting ? surveyEntity.buyingCriteriaWeighting.id : ''}</dd>
          <dt>
            <Translate contentKey="valiusaioApp.survey.preferredPurchaseChannel">Preferred Purchase Channel</Translate>
          </dt>
          <dd>{surveyEntity.preferredPurchaseChannel ? surveyEntity.preferredPurchaseChannel.id : ''}</dd>
          <dt>
            <Translate contentKey="valiusaioApp.survey.preferredCommunicationChannel">Preferred Communication Channel</Translate>
          </dt>
          <dd>{surveyEntity.preferredCommunicationChannel ? surveyEntity.preferredCommunicationChannel.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/survey" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/survey/${surveyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SurveyDetail;
