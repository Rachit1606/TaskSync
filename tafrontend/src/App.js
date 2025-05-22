import React, { useEffect } from 'react';
import { BrowserRouter, Routes, Route, Navigate, useLocation } from 'react-router-dom';
import Signup from './Auth/Signup';
import Login from './Auth/Login';
import Dashboard from './Dashboard/dashboard';

import './App.css';
import ConfirmEmail from './Auth/confirmEmail';
import CreateGroupForm from './Dashboard/CreateGroup';
import JoinGroup from './Dashboard/JoinGroup';
import GroupChat from './Dashboard/GroupChat';

function App() {
  const userId = sessionStorage.getItem('userId');
  const username = sessionStorage.getItem('username');
  const location = window.location.pathname;

  if (location === '/' && userId && username) {
    window.location.replace('/dashboard');
  } else if (location === '/') {
    window.location.replace('/signup');
  }

  return (
    <BrowserRouter>
      <Routes>
        <Route path='/signup' element={<Signup />} />
        <Route path='/login' element={<Login />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/confirmEmail" element={<ConfirmEmail />} />
        <Route path="/create-group" element={<CreateGroupForm />} />
        <Route path='/join-group' element={<JoinGroup />} />
        <Route path='/group-chat/:groupId' element={<GroupChat />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
