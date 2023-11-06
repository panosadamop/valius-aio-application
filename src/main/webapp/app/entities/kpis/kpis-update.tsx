import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IKpis } from 'app/shared/model/kpis.model';
import { Language } from 'app/shared/model/enumerations/language.model';
import { getEntity, updateEntity, createEntity, reset } from './kpis.reducer';

export const KpisUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const kpisEntity = useAppSelector(state => state.kpis.entity);
  const loading = useAppSelector(state => state.kpis.loading);
  const updating = useAppSelector(state => state.kpis.updating);
  const updateSuccess = useAppSelector(state => state.kpis.updateSuccess);
  const languageValues = Object.keys(Language);

  const handleClose = () => {
    navigate('/kpis');
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
      ...kpisEntity,
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
          language: 'ENGLISH',
          ...kpisEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="valiusaioApp.kpis.home.createOrEditLabel" data-cy="KpisCreateUpdateHeading">
            <Translate contentKey="valiusaioApp.kpis.home.createOrEditLabel">Create or edit a Kpis</Translate>
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
                  id="kpis-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('valiusaioApp.kpis.value')}
                id="kpis-value"
                name="value"
                data-cy="value"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.kpis.checkBoxValue')}
                id="kpis-checkBoxValue"
                name="checkBoxValue"
                data-cy="checkBoxValue"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('valiusaioApp.kpis.description')}
                id="kpis-description"
                name="description"
                data-cy="description"
                type="textarea"
              />
              <ValidatedField
                label={translate('valiusaioApp.kpis.language')}
                id="kpis-language"
                name="language"
                data-cy="language"
                type="select"
              >
                {languageValues.map(language => (
                  <option value={language} key={language}>
                    {translate('valiusaioApp.Language.' + language)}
                  </option>
                ))}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/kpis" replace color="info">
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

export default KpisUpdate;
