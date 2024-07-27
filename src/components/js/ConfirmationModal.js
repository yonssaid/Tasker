import React from 'react';
import PropTypes from 'prop-types';
import '../css/ConfirmationModal.css';

const ConfirmationModal = ({ isOpen, message, onConfirm }) => {
    if (!isOpen) return null;

    const handleYes = () => onConfirm(true);
    const handleNo = () => onConfirm(false);

    return (
        <div className="confirmation-modal-overlay">
            <div className="confirmation-modal-content">
                <p>{message}</p>
                <div className="confirmation-modal-actions">
                    <button onClick={handleYes}>Yes</button>
                    <button onClick={handleNo}>No</button>
                </div>
            </div>
        </div>
    );
};

ConfirmationModal.propTypes = {
    isOpen: PropTypes.bool.isRequired,
    message: PropTypes.string.isRequired,
    onConfirm: PropTypes.func.isRequired,
};

export default ConfirmationModal;
