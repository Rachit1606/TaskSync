import {
  Button, TextField, Dialog, DialogTitle, DialogContent, DialogActions, MenuItem, Select, InputLabel,
  FormControl, Checkbox, ListItemText, OutlinedInput
} from '@mui/material';
import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import '../CSS/groupchat.css';
import DEPLOYED_LINK from '../config';
import EditGroupDialog from './EditGroupDialog';

const GroupChat = () => {
  const { groupId } = useParams();
  const navigate = useNavigate();
 // const username = sessionStorage.getItem('username');
  const userId = sessionStorage.getItem('userId');

  const [messages, setMessages] = useState([]);
  const [groupDetails, setGroupDetails] = useState(null);
  const [groupMembers, setGroupMembers] = useState([]);

  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [status, setStatus] = useState('ACTIVE');
  const [startDate, setStartDate] = useState('');
  const [dueDate, setDueDate] = useState('');
  const [assignees, setAssignees] = useState([]);

  const [editGroupOpen, setEditGroupOpen] = useState(false);
  const [showMembers, setShowMembers] = useState(false);
  const [currentUserRole, setCurrentUserRole] = useState('');
  const [openTaskDialog, setOpenTaskDialog] = useState(false);

useEffect(() => {
  const fetchData = async () => {
    try {
      const [msgRes, detailRes, memberRes] = await Promise.all([
        fetch(`${DEPLOYED_LINK}/messages/${groupId}/messages`),
        fetch(`${DEPLOYED_LINK}/tasks/groups/${groupId}`),
        fetch(`${DEPLOYED_LINK}/tasks/${groupId}/members`)
      ]);

      if (msgRes.ok) {
        const data = await msgRes.json();
        setMessages(data);
      }

      if (detailRes.ok) {
        const data = await detailRes.json();
        setGroupDetails(data);
      }

      if (memberRes.ok) {
        const data = await memberRes.json();
        setGroupMembers(data);
        const currentUser = data.find(m => m.userId === userId);
        if (currentUser) setCurrentUserRole(currentUser.role);
      }
    } catch (error) {
      console.error("Error fetching group data:", error);
    }
  };

  fetchData();
}, [groupId, userId]);

  const fetchMessages = async () => {
    const response = await fetch(`${DEPLOYED_LINK}/messages/${groupId}/messages`);
    if (response.ok) {
      const data = await response.json();
      setMessages(data);
    }
  };

  const fetchGroupDetails = async () => {
    const response = await fetch(`${DEPLOYED_LINK}/tasks/groups/${groupId}`);
    if (response.ok) {
      const data = await response.json();
      setGroupDetails(data);
    }
  };

  // const fetchGroupMembers = async () => {
  //   const response = await fetch(`${DEPLOYED_LINK}/tasks/${groupId}/members`);
  //   if (response.ok) {
  //     const data = await response.json();
  //     setGroupMembers(data);
  //     const currentUser = data.find(m => m.userId === userId);
  //     if (currentUser) setCurrentUserRole(currentUser.role);
  //   }
  // };

  const handleSendMessage = async () => {
    const selectedUsernames = groupMembers
      .filter(member => assignees.includes(member.userId))
      .map(member => member.username);

    const messageData = {
      title,
      description,
      groupId,
      creatorId: userId,
      creationDate: new Date().toISOString().split('T')[0],
      status,
      assignees: selectedUsernames,
      startDate,
      dueDate
    };

    const response = await fetch(`${DEPLOYED_LINK}/messages/createMessage`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(messageData)
    });

    if (response.ok) {
      alert('Task created');
      fetchMessages();
      setTitle('');
      setDescription('');
      setStatus('ACTIVE');
      setStartDate('');
      setDueDate('');
      setAssignees([]);
    }
  };

  const handleDeleteGroup = async () => {
    if (window.confirm('Are you sure you want to delete this group?')) {
      const response = await fetch(`${DEPLOYED_LINK}/tasks/groups/${groupId}`, {
        method: 'DELETE'
      });
      if (response.ok) {
        alert('Group deleted successfully');
        navigate('/dashboard');
      }
    }
  };

  const handleLeaveGroup = async () => {
    if (window.confirm('Are you sure you want to leave this group?')) {
      const response = await fetch(`${DEPLOYED_LINK}/tasks/${groupId}/leave/${userId}`, {
        method: 'DELETE'
      });
      if (response.ok) {
        alert('Left the group successfully');
        navigate('/dashboard');
      }
    }
  };

  return (
    <div className="GroupChat">
      <Button
        variant="contained"
        onClick={() => navigate('/dashboard')}
        className="back-button"
      >
        Back to Dashboard
      </Button>
      <h1>Task Group: {groupDetails?.name}</h1>

      <div className="group-actions">
        <Button variant='contained' onClick={() => setEditGroupOpen(true)}>Edit Group</Button>
        <Button variant='outlined' onClick={() => setShowMembers(!showMembers)}>Show Members</Button>
        {currentUserRole === 'ADMIN' && (
          <Button variant='contained' color='error' onClick={handleDeleteGroup}>Delete Group</Button>
        )}
        {currentUserRole === 'MEMBER' && (
          <Button variant='contained' color='warning' onClick={handleLeaveGroup}>Leave Group</Button>
        )}
      </div>

      {showMembers && (
        <div className="member-list">
          <h3>Group Members</h3>
          <ul>
            {groupMembers.map((member) => (
              <li key={member.userId}>{member.username} ({member.role})</li>
            ))}
          </ul>
        </div>
      )}

      <ul className="message-list">
        {messages.map(task => (
          <li key={task.id} className="message-item">
            <div className="message-content">
              <p><strong>{task.title}</strong></p>
              <p>{task.description}</p>
              <p>Status: {task.status}</p>
              <p>Assignees: {task.assignees?.join(', ')}</p>
              <p>Start: {task.startDate}</p>
              <p>Due: {task.dueDate}</p>
            </div>
          </li>
        ))}
      </ul>

      <Button className="fab" onClick={() => setOpenTaskDialog(true)}>+</Button>

      <Dialog open={openTaskDialog} onClose={() => setOpenTaskDialog(false)} fullWidth maxWidth="sm">
        <DialogTitle>Create New Task</DialogTitle>
        <DialogContent dividers>
          <TextField label="Title" fullWidth value={title} onChange={e => setTitle(e.target.value)} className="text-field" />
          <TextField label="Description" fullWidth multiline rows={3} value={description} onChange={e => setDescription(e.target.value)} className="text-field" />
          <FormControl fullWidth className="text-field">
            <InputLabel>Status</InputLabel>
            <Select value={status} onChange={e => setStatus(e.target.value)}>
              <MenuItem value="ACTIVE">ACTIVE</MenuItem>
              <MenuItem value="COMPLETED">COMPLETED</MenuItem>
            </Select>
          </FormControl>
          <TextField label="Start Date" type="date" value={startDate} onChange={e => setStartDate(e.target.value)} InputLabelProps={{ shrink: true }} fullWidth className="text-field" />
          <TextField label="Due Date" type="date" value={dueDate} onChange={e => setDueDate(e.target.value)} InputLabelProps={{ shrink: true }} fullWidth className="text-field" />
          <FormControl fullWidth className="text-field">
            <InputLabel>Assignees</InputLabel>
            <Select
              multiple
              value={assignees}
              onChange={e => setAssignees(e.target.value)}
              input={<OutlinedInput />}
              renderValue={(selected) =>
                groupMembers.filter(m => selected.includes(m.userId)).map(m => m.username).join(', ')
              }
            >
              {groupMembers.map(member => (
                <MenuItem key={member.userId} value={member.userId}>
                  <Checkbox checked={assignees.includes(member.userId)} />
                  <ListItemText primary={member.username} />
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setOpenTaskDialog(false)}>Cancel</Button>
          <Button variant="contained" onClick={() => { handleSendMessage(); setOpenTaskDialog(false); }}>
            Create
          </Button>
        </DialogActions>
      </Dialog>

      <EditGroupDialog
        open={editGroupOpen}
        handleClose={() => setEditGroupOpen(false)}
        groupId={groupId}
        fetchGroupDetails={fetchGroupDetails}
      />
    </div>
  );
};

export default GroupChat;
