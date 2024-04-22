import { Button } from '@mui/material';
import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import userpool from '../userpool';
import '../CSS/joingroup.css';
import DEPLOYED_LINK from '../config';

const JoinGroup = () => {
  const [groups, setGroups] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    fetchGroups();
  }, []);

  const fetchGroups = async () => {
    try {
      const response = await fetch(`${DEPLOYED_LINK}/tasks/all`);
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

  const handleJoinGroup = async (groupId) => {
    try {
      const currentUser = userpool.getCurrentUser();
      const userId = currentUser ? currentUser.username : '';

      const requestData = {
        groupId: groupId,
        userId: userId
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

  return (
    <div>
              <Button variant="contained" onClick={() => navigate('/dashboard')} className="back-button">
        Back to Dashboard
      </Button>
    <div className="join-group">

      <h2>Join a Group</h2>
      <ul>
        {groups.map(group => (
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
    </div>
    </div>
  );
};

export default JoinGroup;
