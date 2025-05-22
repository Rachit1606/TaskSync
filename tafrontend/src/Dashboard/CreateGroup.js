import { Button, TextField } from '@mui/material';
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../CSS/creategroup.css';
import DEPLOYED_LINK from '../config';

const CreateGroupForm = () => {
  const Navigate = useNavigate();
  const [groupName, setGroupName] = useState('');
  const [groupDescription, setGroupDescription] = useState('');

  const handleCreateGroup = async () => {
    try {
      const creatorId = sessionStorage.getItem('userId');
      const creatorUsername = sessionStorage.getItem('username');

      const creationDate = Math.floor(Date.now() / 1000);

      const groupData = {
        name: groupName,
        description: groupDescription,
        creationDate: creationDate,
        creatorId: creatorId,
        creatorUsername: creatorUsername
      };

      const response = await fetch(`${DEPLOYED_LINK}/tasks/createGroup`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(groupData)
      });

      if (response.ok) {
        alert("Group Created Successfully");
        Navigate('/dashboard');
      } else {
        console.error('Failed to create group');
      }
    } catch (error) {
      console.error('Error creating group:', error);
    }
  };

return (
  <div className="create-group-page">
    <Button variant="contained" onClick={() => Navigate('/dashboard')} className="back-button">
      Back to Dashboard
    </Button>

    <div className="center-content">
      <div className="create-group-form">
        <TextField
          label="Group Name"
          value={groupName}
          onChange={(e) => setGroupName(e.target.value)}
          variant="outlined"
          className="text-field"
        />
        <TextField
          label="Group Description"
          value={groupDescription}
          onChange={(e) => setGroupDescription(e.target.value)}
          variant="outlined"
          multiline
          rows={4}
          className="text-field"
        />
        <Button variant="contained" onClick={handleCreateGroup} className="create-button">
          Create Group
        </Button>
      </div>
    </div>
  </div>
);

};

export default CreateGroupForm;