import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFieldPreferredPurchaseChannel } from 'app/shared/model/field-preferred-purchase-channel.model';
import { getEntity, updateEntity, createEntity, reset } from './field-preferred-purchase-channel.reducer';

export const FieldPreferredPurchaseChannelUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const fieldPreferredPurchaseChannelEntity = useAppSelector(state => state.fieldPreferredPurchaseChannel.entity);
  const loading = useAppSelector(state => state.fieldPreferredPurchaseChannel.loading);
  const updating = useAppSelector(state => state.fieldPreferredPurchaseChannel.updating);
  const updateSuccess = useAppSelector(state => state.fieldPreferredPurchaseChannel.updateSuccess);

  const handleClose = () => {
    navigate('/field-preferred-purchase-channel');
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
      ...fieldPreferredPurchaseChannelEntity,
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
          ...fieldPreferredPurchaseChannelEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="valiusaioApp.fieldPreferredPurchaseChannel.home.createOrEditLabel"
            data-cy="FieldPreferredPurchaseChannelCreateUpdateHeading"
          >
            <Translate contentKey="valiusaioApp.fieldPreferredPurchaseChannel.home.createOrEditLabel">
              Create or edit a FieldPreferredPurchaseChannel
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
                  id="field-preferred-purchase-channel-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('valiusaioApp.fieldPreferredPurchaseChannel.preferredPurchaseChannel')}
                id="field-preferred-purchase-channel-preferredPurchaseChannel"
                name="preferredPurchaseChannel"
                data-cy="preferredPurchaseChannel"
                type="text"
              />
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/field-preferred-purchase-channel"
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

export default FieldPreferredPurchaseChannelUpdate;
