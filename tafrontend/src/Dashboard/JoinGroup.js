import { Button } from '@mui/material';
import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import '../CSS/joingroup.css';
import DEPLOYED_LINK from '../config';

const JoinGroup = () => {
  const [allGroups, setAllGroups] = useState([]);
  const [userGroups, setUserGroups] = useState([]);
  const navigate = useNavigate();

  const userId = sessionStorage.getItem('userId');
  const username = sessionStorage.getItem('username');

 useEffect(() => {
  const fetchAllGroups = async () => {
    try {
      const response = await fetch(`${DEPLOYED_LINK}/tasks/all`);
      if (response.ok) {
        const data = await response.json();
        setAllGroups(data);
      } else {
        console.error('Failed to fetch all groups');
      }
    } catch (error) {
      console.error('Error fetching all groups:', error);
    }
  };

  const fetchUserGroups = async () => {
    try {
      const response = await fetch(`${DEPLOYED_LINK}/tasks/user/${userId}`);
      if (response.ok) {
        const data = await response.json();
        setUserGroups(data);
      } else {
        console.error('Failed to fetch user groups');
      }
    } catch (error) {
      console.error('Error fetching user groups:', error);
    }
  };

  if (userId) {
    fetchAllGroups();
    fetchUserGroups();
  }
}, [userId]);


  const handleJoinGroup = async (groupId) => {
    try {
      const requestData = {
        groupId: groupId,
        userId: userId,
        username: username,
        role: "MEMBER"
      };

      const response = await fetch(`${DEPLOYED_LINK}/tasks/joinGroup`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestData)
      });

      if (response.ok) {
        alert('Group joined successfully');
        navigate('/dashboard');
      } else {
        console.error('Failed to join group');
      }
    } catch (error) {
      console.error('Error joining group:', error);
    }
  };

  // Filter groups the user has not joined
  const joinableGroups = allGroups.filter(
    group => !userGroups.find(userGroup => userGroup.id === group.id)
  );

 return (
  <div className="join-group-page">
    <Button variant="contained" onClick={() => navigate('/dashboard')} className="back-button">
      Back to Dashboard
    </Button>

    <div className="center-wrapper">
      <div className="join-group">
        <h2>Join a Group</h2>
        <ul>
          {joinableGroups.map(group => (
            <li key={group.id}>
              <div>
                <strong>{group.name}</strong>
                <p>{group.description}</p>
              </div>
              <Button variant="contained" onClick={() => handleJoinGroup(group.id)}>
                Join Group
              </Button>
            </li>
          ))}
        </ul>
        {joinableGroups.length === 0 && <p style={{ textAlign: 'center' }}>No joinable groups available</p>}
      </div>
    </div>
  </div>
);

};

export default JoinGroup;
