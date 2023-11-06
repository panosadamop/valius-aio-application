import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/shared/reducers/user-management';
import { IFieldBuyingCriteriaWeighting } from 'app/shared/model/field-buying-criteria-weighting.model';
import { getEntities as getFieldBuyingCriteriaWeightings } from 'app/entities/field-buying-criteria-weighting/field-buying-criteria-weighting.reducer';
import { IFieldPreferredPurchaseChannel } from 'app/shared/model/field-preferred-purchase-channel.model';
import { getEntities as getFieldPreferredPurchaseChannels } from 'app/entities/field-preferred-purchase-channel/field-preferred-purchase-channel.reducer';
import { IFieldPreferredCommunicationChannel } from 'app/shared/model/field-preferred-communication-channel.model';
import { getEntities as getFieldPreferredCommunicationChannels } from 'app/entities/field-preferred-communication-channel/field-preferred-communication-channel.reducer';
import { ISurvey } from 'app/shared/model/survey.model';
import { getEntity, updateEntity, createEntity, reset } from './survey.reducer';

export const SurveyUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const fieldBuyingCriteriaWeightings = useAppSelector(state => state.fieldBuyingCriteriaWeighting.entities);
  const fieldPreferredPurchaseChannels = useAppSelector(state => state.fieldPreferredPurchaseChannel.entities);
  const fieldPreferredCommunicationChannels = useAppSelector(state => state.fieldPreferredCommunicationChannel.entities);
  const surveyEntity = useAppSelector(state => state.survey.entity);
  const loading = useAppSelector(state => state.survey.loading);
  const updating = useAppSelector(state => state.survey.updating);
  const updateSuccess = useAppSelector(state => state.survey.updateSuccess);

  const handleClose = () => {
    navigate('/survey');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getUsers({}));
    dispatch(getFieldBuyingCriteriaWeightings({}));
    dispatch(getFieldPreferredPurchaseChannels({}));
    dispatch(getFieldPreferredCommunicationChannels({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...surveyEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user.toString()),
      buyingCriteriaWeighting: fieldBuyingCriteriaWeightings.find(it => it.id.toString() === values.buyingCriteriaWeighting.toString()),
      preferredPurchaseChannel: fieldPreferredPurchaseChannels.find(it => it.id.toString() === values.preferredPurchaseChannel.toString()),
      preferredCommunicationChannel: fieldPreferredCommunicationChannels.find(
        it => it.id.toString() === values.preferredCommunicationChannel.toString()
      ),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...surveyEntity,
          user: surveyEntity?.user?.id,
          buyingCriteriaWeighting: surveyEntity?.buyingCriteriaWeighting?.id,
          preferredPurchaseChannel: surveyEntity?.preferredPurchaseChannel?.id,
          preferredCommunicationChannel: surveyEntity?.preferredCommunicationChannel?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="valiusaioApp.survey.home.createOrEditLabel" data-cy="SurveyCreateUpdateHeading">
            <Translate contentKey="valiusaioApp.survey.home.createOrEditLabel">Create or edit a Survey</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="survey-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('valiusaioApp.survey.consumerAssessedBrands')}
                id="survey-consumerAssessedBrands"
                name="consumerAssessedBrands"
                data-cy="consumerAssessedBrands"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.survey.criticalSuccessFactors')}
                id="survey-criticalSuccessFactors"
                name="criticalSuccessFactors"
                data-cy="criticalSuccessFactors"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.survey.additionalBuyingCriteria')}
                id="survey-additionalBuyingCriteria"
                name="additionalBuyingCriteria"
                data-cy="additionalBuyingCriteria"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.survey.consumerSegmentGroup')}
                id="survey-consumerSegmentGroup"
                name="consumerSegmentGroup"
                data-cy="consumerSegmentGroup"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.survey.segmentCsf')}
                id="survey-segmentCsf"
                name="segmentCsf"
                data-cy="segmentCsf"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.survey.gender')}
                id="survey-gender"
                name="gender"
                data-cy="gender"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.survey.ageGroup')}
                id="survey-ageGroup"
                name="ageGroup"
                data-cy="ageGroup"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.survey.location')}
                id="survey-location"
                name="location"
                data-cy="location"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.survey.education')}
                id="survey-education"
                name="education"
                data-cy="education"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.survey.occupation')}
                id="survey-occupation"
                name="occupation"
                data-cy="occupation"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.survey.netPromoterScore')}
                id="survey-netPromoterScore"
                name="netPromoterScore"
                data-cy="netPromoterScore"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField id="survey-user" name="user" data-cy="user" label={translate('valiusaioApp.survey.user')} type="select">
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.login}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="survey-buyingCriteriaWeighting"
                name="buyingCriteriaWeighting"
                data-cy="buyingCriteriaWeighting"
                label={translate('valiusaioApp.survey.buyingCriteriaWeighting')}
                type="select"
              >
                <option value="" key="0" />
                {fieldBuyingCriteriaWeightings
                  ? fieldBuyingCriteriaWeightings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="survey-preferredPurchaseChannel"
                name="preferredPurchaseChannel"
                data-cy="preferredPurchaseChannel"
                label={translate('valiusaioApp.survey.preferredPurchaseChannel')}
                type="select"
              >
                <option value="" key="0" />
                {fieldPreferredPurchaseChannels
                  ? fieldPreferredPurchaseChannels.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="survey-preferredCommunicationChannel"
                name="preferredCommunicationChannel"
                data-cy="preferredCommunicationChannel"
                label={translate('valiusaioApp.survey.preferredCommunicationChannel')}
                type="select"
              >
                <option value="" key="0" />
                {fieldPreferredCommunicationChannels
                  ? fieldPreferredCommunicationChannels.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/survey" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default SurveyUpdate;
