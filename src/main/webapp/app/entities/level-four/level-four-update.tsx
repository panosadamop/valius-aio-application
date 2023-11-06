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
import { ILevelFour } from 'app/shared/model/level-four.model';
import { getEntity, updateEntity, createEntity, reset } from './level-four.reducer';

export const LevelFourUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const levelFourEntity = useAppSelector(state => state.levelFour.entity);
  const loading = useAppSelector(state => state.levelFour.loading);
  const updating = useAppSelector(state => state.levelFour.updating);
  const updateSuccess = useAppSelector(state => state.levelFour.updateSuccess);

  const handleClose = () => {
    navigate('/level-four');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getUsers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...levelFourEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user.toString()),
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
          ...levelFourEntity,
          user: levelFourEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="valiusaioApp.levelFour.home.createOrEditLabel" data-cy="LevelFourCreateUpdateHeading">
            <Translate contentKey="valiusaioApp.levelFour.home.createOrEditLabel">Create or edit a LevelFour</Translate>
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
                  id="level-four-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('valiusaioApp.levelFour.identifier')}
                id="level-four-identifier"
                name="identifier"
                data-cy="identifier"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.levelFour.criticalSuccessFactors')}
                id="level-four-criticalSuccessFactors"
                name="criticalSuccessFactors"
                data-cy="criticalSuccessFactors"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.levelFour.populationSize')}
                id="level-four-populationSize"
                name="populationSize"
                data-cy="populationSize"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.levelFour.statisticalError')}
                id="level-four-statisticalError"
                name="statisticalError"
                data-cy="statisticalError"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.levelFour.confidenceLevel')}
                id="level-four-confidenceLevel"
                name="confidenceLevel"
                data-cy="confidenceLevel"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.levelFour.requiredSampleSize')}
                id="level-four-requiredSampleSize"
                name="requiredSampleSize"
                data-cy="requiredSampleSize"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.levelFour.estimatedResponseRate')}
                id="level-four-estimatedResponseRate"
                name="estimatedResponseRate"
                data-cy="estimatedResponseRate"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.levelFour.surveyParticipantsNumber')}
                id="level-four-surveyParticipantsNumber"
                name="surveyParticipantsNumber"
                data-cy="surveyParticipantsNumber"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                id="level-four-user"
                name="user"
                data-cy="user"
                label={translate('valiusaioApp.levelFour.user')}
                type="select"
              >
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.login}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/level-four" replace color="info">
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

export default LevelFourUpdate;
