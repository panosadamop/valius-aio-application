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
import { IFieldBuyingCriteria } from 'app/shared/model/field-buying-criteria.model';
import { getEntities as getFieldBuyingCriteria } from 'app/entities/field-buying-criteria/field-buying-criteria.reducer';
import { ILevelTwo } from 'app/shared/model/level-two.model';
import { getEntity, updateEntity, createEntity, reset } from './level-two.reducer';

export const LevelTwoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const fieldBuyingCriteria = useAppSelector(state => state.fieldBuyingCriteria.entities);
  const levelTwoEntity = useAppSelector(state => state.levelTwo.entity);
  const loading = useAppSelector(state => state.levelTwo.loading);
  const updating = useAppSelector(state => state.levelTwo.updating);
  const updateSuccess = useAppSelector(state => state.levelTwo.updateSuccess);

  const handleClose = () => {
    navigate('/level-two');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getUsers({}));
    dispatch(getFieldBuyingCriteria({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...levelTwoEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user.toString()),
      buyingCriteria: fieldBuyingCriteria.find(it => it.id.toString() === values.buyingCriteria.toString()),
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
          ...levelTwoEntity,
          user: levelTwoEntity?.user?.id,
          buyingCriteria: levelTwoEntity?.buyingCriteria?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="valiusaioApp.levelTwo.home.createOrEditLabel" data-cy="LevelTwoCreateUpdateHeading">
            <Translate contentKey="valiusaioApp.levelTwo.home.createOrEditLabel">Create or edit a LevelTwo</Translate>
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
                  id="level-two-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('valiusaioApp.levelTwo.identifier')}
                id="level-two-identifier"
                name="identifier"
                data-cy="identifier"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.levelTwo.targetMarket')}
                id="level-two-targetMarket"
                name="targetMarket"
                data-cy="targetMarket"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.levelTwo.currentMarketSegmentation')}
                id="level-two-currentMarketSegmentation"
                name="currentMarketSegmentation"
                data-cy="currentMarketSegmentation"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.levelTwo.segmentName')}
                id="level-two-segmentName"
                name="segmentName"
                data-cy="segmentName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.levelTwo.marketSegmentationType')}
                id="level-two-marketSegmentationType"
                name="marketSegmentationType"
                data-cy="marketSegmentationType"
                type="text"
              />
              <ValidatedField
                label={translate('valiusaioApp.levelTwo.uniqueCharacteristic')}
                id="level-two-uniqueCharacteristic"
                name="uniqueCharacteristic"
                data-cy="uniqueCharacteristic"
                type="text"
              />
              <ValidatedField
                label={translate('valiusaioApp.levelTwo.segmentDescription')}
                id="level-two-segmentDescription"
                name="segmentDescription"
                data-cy="segmentDescription"
                type="textarea"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.levelTwo.buyingCriteriaCategory')}
                id="level-two-buyingCriteriaCategory"
                name="buyingCriteriaCategory"
                data-cy="buyingCriteriaCategory"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.levelTwo.competitorProductName')}
                id="level-two-competitorProductName"
                name="competitorProductName"
                data-cy="competitorProductName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.levelTwo.competitorCompanyName')}
                id="level-two-competitorCompanyName"
                name="competitorCompanyName"
                data-cy="competitorCompanyName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.levelTwo.competitorBrandName')}
                id="level-two-competitorBrandName"
                name="competitorBrandName"
                data-cy="competitorBrandName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.levelTwo.competitorProductDescription')}
                id="level-two-competitorProductDescription"
                name="competitorProductDescription"
                data-cy="competitorProductDescription"
                type="textarea"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.levelTwo.competitorMaturityPhase')}
                id="level-two-competitorMaturityPhase"
                name="competitorMaturityPhase"
                data-cy="competitorMaturityPhase"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.levelTwo.competitorCompetitivePosition')}
                id="level-two-competitorCompetitivePosition"
                name="competitorCompetitivePosition"
                data-cy="competitorCompetitivePosition"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField id="level-two-user" name="user" data-cy="user" label={translate('valiusaioApp.levelTwo.user')} type="select">
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
                id="level-two-buyingCriteria"
                name="buyingCriteria"
                data-cy="buyingCriteria"
                label={translate('valiusaioApp.levelTwo.buyingCriteria')}
                type="select"
              >
                <option value="" key="0" />
                {fieldBuyingCriteria
                  ? fieldBuyingCriteria.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/level-two" replace color="info">
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

export default LevelTwoUpdate;
