import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './field-preferred-communication-channel.reducer';

export const FieldPreferredCommunicationChannelDeleteDialog = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const fieldPreferredCommunicationChannelEntity = useAppSelector(state => state.fieldPreferredCommunicationChannel.entity);
  const updateSuccess = useAppSelector(state => state.fieldPreferredCommunicationChannel.updateSuccess);

  const handleClose = () => {
    navigate('/field-preferred-communication-channel');
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(fieldPreferredCommunicationChannelEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="fieldPreferredCommunicationChannelDeleteDialogHeading">
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="valiusaioApp.fieldPreferredCommunicationChannel.delete.question">
        <Translate
          contentKey="valiusaioApp.fieldPreferredCommunicationChannel.delete.question"
          interpolate={{ id: fieldPreferredCommunicationChannelEntity.id }}
        >
          Are you sure you want to delete this FieldPreferredCommunicationChannel?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button
          id="jhi-confirm-delete-fieldPreferredCommunicationChannel"
          data-cy="entityConfirmDeleteButton"
          color="danger"
          onClick={confirmDelete}
        >
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default FieldPreferredCommunicationChannelDeleteDialog;
