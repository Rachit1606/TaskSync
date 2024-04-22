import React, { useState } from 'react';
import { Button, TextField, Typography } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { CognitoUserAttribute } from 'amazon-cognito-identity-js';
import userpool from '../userpool';
import '../CSS/Signup.css'; // Import the CSS file

const Signup = () => {
    const navigate = useNavigate(); // Import useNavigate hook

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [name, setName] = useState('');
    const [gender, setGender] = useState('');
    const [emailErr, setEmailErr] = useState('');
    const [passwordErr, setPasswordErr] = useState('');

    const formInputChange = (formField, value) => {
        if (formField === "email") {
            setEmail(value);
        }
        if (formField === "password") {
            setPassword(value);
        }
        if (formField === "name") {
            setName(value);
        }
        if (formField === "gender") {
            setGender(value);
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
                setPasswordErr("must be 6 character");
                resolve({ email: "", password: "must be 6 character" });
            }
            else {
                resolve({ email: "", password: "" });
            }
            reject('')
        });
    };

    const handleClick = (e) => {
        setEmailErr("");
        setPasswordErr("");
        validation()
            .then((res) => {
                if (res.email === '' && res.password === '') {
                    const attributeList = [];
                    attributeList.push(
                        new CognitoUserAttribute({
                            Name: 'email',
                            Value: email,
                        }),
                        new CognitoUserAttribute({
                            Name: 'name',
                            Value: name,
                        }),
                        new CognitoUserAttribute({
                            Name: 'gender',
                            Value: gender,
                        })
                    );
                    let username = email;
                    userpool.signUp(username, password, attributeList, null, (err, data) => {
                        if (err) {
                            console.log(err);
                            alert("Couldn't sign up");
                        } else {
                            console.log(data);
                            alert('User added successfullt. Please confirm email');
                            navigate('/confirmEmail', { state: { email: email } });
                        }
                    });
                }
            }, err => console.log(err))
            .catch(err => console.log(err));
    };

    return (
        <div>
               <Button variant="contained" onClick={() => navigate('/')} className="back-button">
        Back
      </Button>
        <div className="signup">
            <Typography variant='h3' className='homeTitle'>Welcome to TaskSync</Typography>
            <Typography variant='body1' className='homeDescription'>TaskSync is a collaborative task management platform</Typography>
            <div className='form'>
                <div className="formfield-row">
                    <div className="formfield">
                        <TextField
                            value={name}
                            onChange={(e) => formInputChange("name", e.target.value)}
                            label="Name"
                        />
                    </div>
                    <div className="formfield">
                        <TextField
                            value={gender}
                            onChange={(e) => formInputChange("gender", e.target.value)}
                            label="Gender"
                        />
                    </div>
                </div>
                <div className="formfield-row">
                    <div className="formfield">
                        <TextField
                            value={email}
                            onChange={(e) => formInputChange("email", e.target.value)}
                            label="Email"
                            helperText={emailErr}
                        />
                    </div>
                    <div className="formfield">
                        <TextField
                            value={password}
                            onChange={(e) => { formInputChange("password", e.target.value) }}
                            type="password"
                            label="Password"
                            helperText={passwordErr}
                        />
                    </div>
                </div>
                <div className='formfield signupButton'>
                    <Button type='submit' variant='contained' onClick={handleClick}>Signup</Button>
                </div>
            </div>
        </div>
        </div>
    );
};

export default Signup;
