import React, { useState, useEffect } from 'react';
import { Button, TextField, Dialog, DialogTitle, DialogContent, DialogActions } from '@mui/material';
import { useParams, useNavigate } from 'react-router-dom';
import userpool from '../userpool';
import DEPLOYED_LINK from '../config';

const EditGroupDialog = ({ open, handleClose, groupId, fetchGroupDetails }) => {
  const [editedGroupName, setEditedGroupName] = useState('');
  const [editedDescription, setEditedDescription] = useState('');
  const user = userpool.getCurrentUser();
  const username = user ? user.username : '';

  useEffect(() => {
    // Fetch group details when dialog opens
    if (open && groupId) {
      fetchGroupDetails(groupId);
    }
  }, [open, groupId, fetchGroupDetails]);

  const handleEditGroupNameChange = (event) => {
    setEditedGroupName(event.target.value);
  };

  const handleEditDescriptionChange = (event) => {
    setEditedDescription(event.target.value);
  };

  const handleEditGroup = async () => {
    try {
      const requestData = {
        name: editedGroupName || null,
        description: editedDescription || null,
        creationDate: Math.floor(Date.now() / 1000),
        creatorId: username
      };

      const response = await fetch(`${DEPLOYED_LINK}/tasks/groups/${groupId}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestData)
      });

      if (response.ok) {
        console.log('Group updated successfully');
        handleClose(); // Close the dialog
        fetchGroupDetails(groupId); // Fetch updated group details
      } else {
        console.error('Failed to update group');
      }
    } catch (error) {
      console.error('Error updating group:', error);
    }
  };

  return (
    <Dialog open={open} onClose={handleClose}>
      <DialogTitle>Edit Group</DialogTitle>
      <DialogContent>
        <TextField
          label="Group Name"
          value={editedGroupName}
          onChange={handleEditGroupNameChange}
        />
        <TextField
          label="Description"
          value={editedDescription}
          onChange={handleEditDescriptionChange}
        />
      </DialogContent>
      <DialogActions>
        <Button onClick={handleClose}>Cancel</Button>
        <Button onClick={handleEditGroup}>Save</Button>
      </DialogActions>
    </Dialog>
  );
};

export default EditGroupDialog;
