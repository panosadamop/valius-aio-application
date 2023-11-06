import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './market-segmentation-type-b-2-c-alt.reducer';

export const MarketSegmentationTypeB2cAltDeleteDialog = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const marketSegmentationTypeB2cAltEntity = useAppSelector(state => state.marketSegmentationTypeB2cAlt.entity);
  const updateSuccess = useAppSelector(state => state.marketSegmentationTypeB2cAlt.updateSuccess);

  const handleClose = () => {
    navigate('/market-segmentation-type-b-2-c-alt');
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(marketSegmentationTypeB2cAltEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="marketSegmentationTypeB2cAltDeleteDialogHeading">
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="valiusaioApp.marketSegmentationTypeB2cAlt.delete.question">
        <Translate
          contentKey="valiusaioApp.marketSegmentationTypeB2cAlt.delete.question"
          interpolate={{ id: marketSegmentationTypeB2cAltEntity.id }}
        >
          Are you sure you want to delete this MarketSegmentationTypeB2cAlt?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button
          id="jhi-confirm-delete-marketSegmentationTypeB2cAlt"
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

export default MarketSegmentationTypeB2cAltDeleteDialog;
