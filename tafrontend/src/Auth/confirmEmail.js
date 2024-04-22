import React, { useState } from 'react';
import { Button, TextField, Typography } from '@mui/material';
import { useNavigate, useLocation } from 'react-router-dom';
import { confirmSignUp } from './authenthicate';
import '../CSS/confirmEmail.css'; 

const ConfirmEmail = () => {
    const navigate = useNavigate();
    const location = useLocation(); 
    const [confirmationCode, setConfirmationCode] = useState('');
    const [error, setError] = useState('');

    const handleConfirmCode = async () => {
        if (!confirmationCode) {
            setError('Confirmation code is required.');
            return;
        }

        try {
            const email = location.state.email; 
            console.log(email);
            if (!email) {
                setError('Email not found.');
                return;
            }

            await confirmSignUp(email, confirmationCode); 
            navigate('/dashboard');
        } catch (err) {
            console.error('Confirmation failed:', err);
            setError('Invalid confirmation code. Please try again.');
        }
    };

    return (
        <div className="confirm-email-container">
            <Typography variant="h2" className="confirm-email-title">Confirm Your Email</Typography>
            <TextField
                value={confirmationCode}
                onChange={(e) => setConfirmationCode(e.target.value)}
                label="Confirmation Code"
                error={!!error}
                helperText={error}
            />
            <Button variant="contained" className="confirm-email-button" onClick={handleConfirmCode}>Confirm</Button>
        </div>
    );
};

export default ConfirmEmail;
