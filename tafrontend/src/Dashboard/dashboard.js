import { Button } from '@mui/material';
import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { logout } from '../Auth/authenthicate';
import '../CSS/dashboard.css';
import DEPLOYED_LINK from '../config';

const Dashboard = () => {
  const Navigate = useNavigate();
  const [groups, setGroups] = useState([]);

  const userId = sessionStorage.getItem('userId');
  const username = sessionStorage.getItem('username');

useEffect(() => {
  const fetchGroups = async () => {
    try {
      const response = await fetch(`${DEPLOYED_LINK}/tasks/user/${userId}`);
      if (response.ok) {
        const data = await response.json();
        setGroups(data);
      } else {
        console.error('Failed to fetch groups');
      }
    } catch (error) {
      console.error('Error fetching groups:', error);
    }
  };

  if (userId) {
    fetchGroups();
  }
}, [userId]);


  const handleLogout = () => {
    logout();
  };

  const handleCreateGroup = () => {
    Navigate('/create-group');
  };

  const handleJoinGroup = () => {
    Navigate('/join-group');
  };

  const handleOpenGroupChat = (groupId) => {
    Navigate(`/group-chat/${groupId}`);
  };

  return (
    <div className='Dashboard'>
    <Button variant="contained" className="logout-button" onClick={handleLogout}>
      Logout
    </Button>

    <h1 className="title">Welcome {username?.split('@')[0]}</h1>
    <h2 className="subtitle">Here are your task groups</h2>

    <div className="groups-list">
      {groups.map((group, index) => (
        <div key={group.id} className="group-tile">
          <div className="group-info">
            <p className="group-number">{index + 1}</p>
            <p>{group.name}</p>
          </div>
          <Button variant='contained' onClick={() => handleOpenGroupChat(group.id)}>
            Open Group
          </Button>
        </div>
      ))}
    </div>

    <div className="actions">
      <Button variant='contained' onClick={handleCreateGroup}>
        Create Group
      </Button>
      <Button variant='contained' onClick={handleJoinGroup}>
        Join Group
      </Button>
    </div>
  </div>
  );
};

export default Dashboard;
