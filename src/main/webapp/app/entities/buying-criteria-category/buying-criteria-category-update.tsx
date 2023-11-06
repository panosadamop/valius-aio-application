import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBuyingCriteriaCategory } from 'app/shared/model/buying-criteria-category.model';
import { Language } from 'app/shared/model/enumerations/language.model';
import { getEntity, updateEntity, createEntity, reset } from './buying-criteria-category.reducer';

export const BuyingCriteriaCategoryUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const buyingCriteriaCategoryEntity = useAppSelector(state => state.buyingCriteriaCategory.entity);
  const loading = useAppSelector(state => state.buyingCriteriaCategory.loading);
  const updating = useAppSelector(state => state.buyingCriteriaCategory.updating);
  const updateSuccess = useAppSelector(state => state.buyingCriteriaCategory.updateSuccess);
  const languageValues = Object.keys(Language);

  const handleClose = () => {
    navigate('/buying-criteria-category');
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
      ...buyingCriteriaCategoryEntity,
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
          ...buyingCriteriaCategoryEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="valiusaioApp.buyingCriteriaCategory.home.createOrEditLabel" data-cy="BuyingCriteriaCategoryCreateUpdateHeading">
            <Translate contentKey="valiusaioApp.buyingCriteriaCategory.home.createOrEditLabel">
              Create or edit a BuyingCriteriaCategory
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
                  id="buying-criteria-category-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('valiusaioApp.buyingCriteriaCategory.value')}
                id="buying-criteria-category-value"
                name="value"
                data-cy="value"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.buyingCriteriaCategory.description')}
                id="buying-criteria-category-description"
                name="description"
                data-cy="description"
                type="textarea"
              />
              <ValidatedField
                label={translate('valiusaioApp.buyingCriteriaCategory.language')}
                id="buying-criteria-category-language"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/buying-criteria-category" replace color="info">
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

export default BuyingCriteriaCategoryUpdate;
