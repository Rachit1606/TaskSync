import { Button } from '@mui/material';
import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import userpool from '../userpool';
import { logout } from '../Auth/authenthicate';
import '../CSS/dashboard.css';
import DEPLOYED_LINK from '../config';

const Dashboard = () => {
  const Navigate = useNavigate();
  const [groups, setGroups] = useState([]);
  const user = userpool.getCurrentUser();
  const userId = user ? user.username : '';

  console.log(user);
  console.log(userId);
  
  useEffect(() => {
    if (userId) {
      fetchGroups();
    }
  }, [userId]); 

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
      <h1 className="title">Your Task Groups</h1>
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
        <Button variant='contained' onClick={handleLogout}>
          Logout
        </Button>
      </div>
    </div>
  );
};
export default Dashboard;
