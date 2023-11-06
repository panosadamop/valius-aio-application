import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFieldAttractivenessFactors } from 'app/shared/model/field-attractiveness-factors.model';
import { getEntity, updateEntity, createEntity, reset } from './field-attractiveness-factors.reducer';

export const FieldAttractivenessFactorsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const fieldAttractivenessFactorsEntity = useAppSelector(state => state.fieldAttractivenessFactors.entity);
  const loading = useAppSelector(state => state.fieldAttractivenessFactors.loading);
  const updating = useAppSelector(state => state.fieldAttractivenessFactors.updating);
  const updateSuccess = useAppSelector(state => state.fieldAttractivenessFactors.updateSuccess);

  const handleClose = () => {
    navigate('/field-attractiveness-factors');
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
      ...fieldAttractivenessFactorsEntity,
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
          ...fieldAttractivenessFactorsEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="valiusaioApp.fieldAttractivenessFactors.home.createOrEditLabel" data-cy="FieldAttractivenessFactorsCreateUpdateHeading">
            <Translate contentKey="valiusaioApp.fieldAttractivenessFactors.home.createOrEditLabel">
              Create or edit a FieldAttractivenessFactors
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
                  id="field-attractiveness-factors-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('valiusaioApp.fieldAttractivenessFactors.attractivenessFactors')}
                id="field-attractiveness-factors-attractivenessFactors"
                name="attractivenessFactors"
                data-cy="attractivenessFactors"
                type="text"
              />
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/field-attractiveness-factors"
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

export default FieldAttractivenessFactorsUpdate;
