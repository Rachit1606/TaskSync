import React, { useEffect } from 'react'
import { BrowserRouter,Routes, Route,Navigate  } from 'react-router-dom'
import Home from './Auth/Home';
import Signup from './Auth/Signup';
import Login from './Auth/Login';
import Dashboard from './Dashboard/dashboard';

import './App.css';
import userpool from './userpool';
import ConfirmEmail from './Auth/confirmEmail';
import CreateGroupForm from './Dashboard/CreateGroup';
import JoinGroup from './Dashboard/JoinGroup';
import GroupChat from './Dashboard/GroupChat';

function App() {

  useEffect(()=>{
    let user=userpool.getCurrentUser();
      if(user){
        <Navigate to="/dashboard" replace />
      }
  },[]);

  return (
    <BrowserRouter>
      <Routes>
        <Route path='/' element={<Home />}/>
        <Route path='/signup' element={<Signup />}/>
        <Route path='/login' element={<Login />}/>
        <Route path="/dashboard" element={<Dashboard/>}/>
        <Route path="/confirmEmail" element={<ConfirmEmail/>}/>
        <Route path="/create-group" element={<CreateGroupForm/>}/>
        <Route path='/join-group' element={<JoinGroup/>}/>
        <Route path='/group-chat/:groupId' element= {<GroupChat/>}/>
      </Routes>
    </BrowserRouter>
  );
}

export default App;