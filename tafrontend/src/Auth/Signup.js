import React, { useState } from 'react';
import { Button, TextField, Typography, MenuItem } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { CognitoUserAttribute } from 'amazon-cognito-identity-js';
import userpool from '../userpool';
import '../CSS/Signup.css'; // Import the CSS file

const Signup = () => {
    const navigate = useNavigate();

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
        <div className="signup">
            <div className="form">
                <Typography variant='h4' className='homeTitle'>Create your TaskSync account</Typography>
                <Typography variant='body2' className='homeDescription'>
                    Organize your tasks and collaborate easily.
                </Typography>

                <div className="formfield-row">
                    <div className="formfield">
                        <TextField
                            fullWidth
                            value={name}
                            onChange={(e) => formInputChange("name", e.target.value)}
                            label="Full Name"
                        />
                    </div>
                    <div className="formfield">
                        <TextField
                            fullWidth
                            select
                            label="Gender"
                            value={gender}
                            onChange={(e) => formInputChange("gender", e.target.value)}
                        >
                            <MenuItem value="Male">Male</MenuItem>
                            <MenuItem value="Female">Female</MenuItem>
                            <MenuItem value="Other">Other</MenuItem>
                        </TextField>

                    </div>
                </div>

                <div className="formfield">
                    <TextField
                        fullWidth
                        value={email}
                        onChange={(e) => formInputChange("email", e.target.value)}
                        label="Email"
                        helperText={emailErr}
                    />
                </div>

                <div className="formfield">
                    <TextField
                        fullWidth
                        value={password}
                        onChange={(e) => formInputChange("password", e.target.value)}
                        type="password"
                        label="Password"
                        helperText={passwordErr}
                    />
                </div>

                <div className='formfield signupButton'>
                    <Button fullWidth type='submit' variant='contained' onClick={handleClick}>
                        Sign Up
                    </Button>
                </div>

                <Typography variant="body2" className="loginRedirect">
                    Already have an account?{' '}
                    <span onClick={() => navigate('/login')} className="loginLink">Log in</span>
                </Typography>
            </div>
        </div>
    );
};

export default Signup;
