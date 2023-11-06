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
import { IFieldAttractivenessFactors } from 'app/shared/model/field-attractiveness-factors.model';
import { getEntities as getFieldAttractivenessFactors } from 'app/entities/field-attractiveness-factors/field-attractiveness-factors.reducer';
import { ILevelThree } from 'app/shared/model/level-three.model';
import { getEntity, updateEntity, createEntity, reset } from './level-three.reducer';

export const LevelThreeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const fieldAttractivenessFactors = useAppSelector(state => state.fieldAttractivenessFactors.entities);
  const levelThreeEntity = useAppSelector(state => state.levelThree.entity);
  const loading = useAppSelector(state => state.levelThree.loading);
  const updating = useAppSelector(state => state.levelThree.updating);
  const updateSuccess = useAppSelector(state => state.levelThree.updateSuccess);

  const handleClose = () => {
    navigate('/level-three');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getUsers({}));
    dispatch(getFieldAttractivenessFactors({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...levelThreeEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user.toString()),
      attractivenessFactors: fieldAttractivenessFactors.find(it => it.id.toString() === values.attractivenessFactors.toString()),
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
          ...levelThreeEntity,
          user: levelThreeEntity?.user?.id,
          attractivenessFactors: levelThreeEntity?.attractivenessFactors?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="valiusaioApp.levelThree.home.createOrEditLabel" data-cy="LevelThreeCreateUpdateHeading">
            <Translate contentKey="valiusaioApp.levelThree.home.createOrEditLabel">Create or edit a LevelThree</Translate>
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
                  id="level-three-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('valiusaioApp.levelThree.identifier')}
                id="level-three-identifier"
                name="identifier"
                data-cy="identifier"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.levelThree.mafCategory')}
                id="level-three-mafCategory"
                name="mafCategory"
                data-cy="mafCategory"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.levelThree.weightingMaf')}
                id="level-three-weightingMaf"
                name="weightingMaf"
                data-cy="weightingMaf"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.levelThree.lowAttractivenessRangeMaf')}
                id="level-three-lowAttractivenessRangeMaf"
                name="lowAttractivenessRangeMaf"
                data-cy="lowAttractivenessRangeMaf"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.levelThree.mediumAttractivenessRangeMaf')}
                id="level-three-mediumAttractivenessRangeMaf"
                name="mediumAttractivenessRangeMaf"
                data-cy="mediumAttractivenessRangeMaf"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.levelThree.highAttractivenessRangeMaf')}
                id="level-three-highAttractivenessRangeMaf"
                name="highAttractivenessRangeMaf"
                data-cy="highAttractivenessRangeMaf"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.levelThree.segmentScoreMaf')}
                id="level-three-segmentScoreMaf"
                name="segmentScoreMaf"
                data-cy="segmentScoreMaf"
                type="text"
              />
              <ValidatedField
                id="level-three-user"
                name="user"
                data-cy="user"
                label={translate('valiusaioApp.levelThree.user')}
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
              <ValidatedField
                id="level-three-attractivenessFactors"
                name="attractivenessFactors"
                data-cy="attractivenessFactors"
                label={translate('valiusaioApp.levelThree.attractivenessFactors')}
                type="select"
              >
                <option value="" key="0" />
                {fieldAttractivenessFactors
                  ? fieldAttractivenessFactors.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/level-three" replace color="info">
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

export default LevelThreeUpdate;
