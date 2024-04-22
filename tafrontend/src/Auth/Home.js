import React from 'react';
import { Button, Typography } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import '../CSS/Home.css';

const Home = () => {
    const navigate = useNavigate();

    return (
        <div className='home'>
            <div className='content'>
                <Typography variant='h3' className='homeTitle'>Welcome to TaskSync</Typography>
                <Typography variant='body1' className='homeDescription'>TaskSync is a collaborative task management platform</Typography>
                <div className='homeButtons'>
                    <Button variant='contained' className='homeButton' onClick={() => navigate('/signup')}>
                        Signup
                    </Button>
                    <Button variant='contained' className='homeButton' onClick={() => navigate('/login')}>
                        Login
                    </Button>
                </div>
            </div>
        </div>
    );
};

export default Home;
