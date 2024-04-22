import React, { useState } from 'react';
import { Button, TextField, Typography } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { authenticate } from './authenthicate';
import '../CSS/Login.css'; // Import the CSS file

const Login = () => {
  const navigate = useNavigate();

  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [emailErr, setEmailErr] = useState('');
  const [passwordErr, setPasswordErr] = useState('');
  const [loginErr, setLoginErr] = useState('');

  const formInputChange = (formField, value) => {
    if (formField === "email") {
      setEmail(value);
    }
    if (formField === "password") {
      setPassword(value);
    }
  };

  const validation = () => {
    return new Promise((resolve, reject) => {
      if (email === '' && password === '') {
        setEmailErr("Email is Required");
        setPasswordErr("Password is required");
        resolve({ email: "Email is Required", password: "Password is required" });
      }
      else if (email === '') {
        setEmailErr("Email is Required");
        resolve({ email: "Email is Required", password: "" });
      }
      else if (password === '') {
        setPasswordErr("Password is required");
        resolve({ email: "", password: "Password is required" });
      }
      else if (password.length < 6) {
        setPasswordErr("must be 6 characters");
        resolve({ email: "", password: "must be 6 characters" });
      }
      else {
        resolve({ email: "", password: "" });
      }
    });
  };

  const handleClick = () => {
    setEmailErr("");
    setPasswordErr("");
    validation()
      .then((res) => {
        if (res.email === '' && res.password === '') {
          authenticate(email, password)
            .then((data) => {
              setLoginErr('');
              navigate('/dashboard');
            })
            .catch((err) => {
              console.log(err);
              setLoginErr(err.message);
            });
        }
      })
      .catch((err) => console.log(err));
  };

  return (
    <div>      
   <Button variant="contained" onClick={() => navigate('/')} className="back-button">
        Back
      </Button>
    <div className="login">
    <Typography variant='h3' className='title'>Login to TaskSync</Typography>
    <div className='form'>
        <div className="formfield">
          <TextField
            value={email}
            onChange={(e) => formInputChange("email", e.target.value)}
            label="Email"
            helperText={emailErr}
          />
        </div>
        <div className='formfield'>
          <TextField
            value={password}
            onChange={(e) => formInputChange("password", e.target.value)}
            type="password"
            label="Password"
            helperText={passwordErr}
          />
        </div>
        <div className='formfield'>
          <Button type='submit' variant='contained' onClick={handleClick}>Login</Button>
        </div>
        <Typography variant="body" className="error">{loginErr}</Typography>
      </div>
    </div>
    </div>
  );
};

export default Login;
