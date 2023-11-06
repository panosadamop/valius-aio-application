import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMarketSegmentationTypeB2bAlt } from 'app/shared/model/market-segmentation-type-b-2-b-alt.model';
import { Language } from 'app/shared/model/enumerations/language.model';
import { getEntity, updateEntity, createEntity, reset } from './market-segmentation-type-b-2-b-alt.reducer';

export const MarketSegmentationTypeB2bAltUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const marketSegmentationTypeB2bAltEntity = useAppSelector(state => state.marketSegmentationTypeB2bAlt.entity);
  const loading = useAppSelector(state => state.marketSegmentationTypeB2bAlt.loading);
  const updating = useAppSelector(state => state.marketSegmentationTypeB2bAlt.updating);
  const updateSuccess = useAppSelector(state => state.marketSegmentationTypeB2bAlt.updateSuccess);
  const languageValues = Object.keys(Language);

  const handleClose = () => {
    navigate('/market-segmentation-type-b-2-b-alt');
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
      ...marketSegmentationTypeB2bAltEntity,
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
          ...marketSegmentationTypeB2bAltEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="valiusaioApp.marketSegmentationTypeB2bAlt.home.createOrEditLabel"
            data-cy="MarketSegmentationTypeB2bAltCreateUpdateHeading"
          >
            <Translate contentKey="valiusaioApp.marketSegmentationTypeB2bAlt.home.createOrEditLabel">
              Create or edit a MarketSegmentationTypeB2bAlt
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
                  id="market-segmentation-type-b-2-b-alt-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('valiusaioApp.marketSegmentationTypeB2bAlt.value')}
                id="market-segmentation-type-b-2-b-alt-value"
                name="value"
                data-cy="value"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.marketSegmentationTypeB2bAlt.description')}
                id="market-segmentation-type-b-2-b-alt-description"
                name="description"
                data-cy="description"
                type="textarea"
              />
              <ValidatedField
                label={translate('valiusaioApp.marketSegmentationTypeB2bAlt.language')}
                id="market-segmentation-type-b-2-b-alt-language"
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
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/market-segmentation-type-b-2-b-alt"
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

export default MarketSegmentationTypeB2bAltUpdate;
