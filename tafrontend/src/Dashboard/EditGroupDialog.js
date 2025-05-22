import React, { useState, useEffect } from 'react';
import { Button, TextField, Dialog, DialogTitle, DialogContent, DialogActions } from '@mui/material';
import DEPLOYED_LINK from '../config';

const EditGroupDialog = ({ open, handleClose, groupId, fetchGroupDetails }) => {
  const [editedGroupName, setEditedGroupName] = useState('');
  const [editedDescription, setEditedDescription] = useState('');

  const userId = sessionStorage.getItem('userId');
  const username = sessionStorage.getItem('username');

  useEffect(() => {
    if (open && groupId) {
      fetchGroupDetails(groupId);
    }
  }, [open, groupId, fetchGroupDetails]);

  const handleEditGroup = async () => {
    try {
      const requestData = {
        name: editedGroupName,
        description: editedDescription,
        creationDate: Math.floor(Date.now() / 1000),
        creatorId: userId,
        creatorUsername: username
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
        fetchGroupDetails(groupId); // Refresh updated details
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
          fullWidth
          label="Group Name"
          value={editedGroupName}
          onChange={(e) => setEditedGroupName(e.target.value)}
          margin="dense"
        />
        <TextField
          fullWidth
          label="Description"
          value={editedDescription}
          onChange={(e) => setEditedDescription(e.target.value)}
          margin="dense"
          multiline
          rows={4}
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
