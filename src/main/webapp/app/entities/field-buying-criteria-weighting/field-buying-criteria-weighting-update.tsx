import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFieldBuyingCriteriaWeighting } from 'app/shared/model/field-buying-criteria-weighting.model';
import { getEntity, updateEntity, createEntity, reset } from './field-buying-criteria-weighting.reducer';

export const FieldBuyingCriteriaWeightingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const fieldBuyingCriteriaWeightingEntity = useAppSelector(state => state.fieldBuyingCriteriaWeighting.entity);
  const loading = useAppSelector(state => state.fieldBuyingCriteriaWeighting.loading);
  const updating = useAppSelector(state => state.fieldBuyingCriteriaWeighting.updating);
  const updateSuccess = useAppSelector(state => state.fieldBuyingCriteriaWeighting.updateSuccess);

  const handleClose = () => {
    navigate('/field-buying-criteria-weighting');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...fieldBuyingCriteriaWeightingEntity,
      ...values,
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
          ...fieldBuyingCriteriaWeightingEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="valiusaioApp.fieldBuyingCriteriaWeighting.home.createOrEditLabel"
            data-cy="FieldBuyingCriteriaWeightingCreateUpdateHeading"
          >
            <Translate contentKey="valiusaioApp.fieldBuyingCriteriaWeighting.home.createOrEditLabel">
              Create or edit a FieldBuyingCriteriaWeighting
            </Translate>
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
                  id="field-buying-criteria-weighting-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('valiusaioApp.fieldBuyingCriteriaWeighting.buyingCriteriaWeighting')}
                id="field-buying-criteria-weighting-buyingCriteriaWeighting"
                name="buyingCriteriaWeighting"
                data-cy="buyingCriteriaWeighting"
                type="text"
              />
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/field-buying-criteria-weighting"
                replace
                color="info"
              >
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

export default FieldBuyingCriteriaWeightingUpdate;
