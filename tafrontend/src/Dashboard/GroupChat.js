import { Button, TextField, Dialog, DialogTitle, DialogContent, DialogActions } from '@mui/material';
import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import userpool from '../userpool';
import '../CSS/groupchat.css';
import DEPLOYED_LINK from '../config';
import EditGroupDialog from './EditGroupDialog'; 

const GroupChat = () => {
  const { groupId } = useParams();
  const navigate = useNavigate();
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState('');
  const [groupDetails, setGroupDetails] = useState(null);
  const [editDialogOpen, setEditDialogOpen] = useState(false);
  const [editedMessageId, setEditedMessageId] = useState(null);
  const [editedMessageContent, setEditedMessageContent] = useState('');
  const user = userpool.getCurrentUser();
  const username = user ? user.username : '';

  useEffect(() => {
    fetchMessages();
    fetchGroupDetails();
  }, [groupId]);

  const fetchMessages = async () => {
    try {
      const response = await fetch(`${DEPLOYED_LINK}/messages/${groupId}/messages`);
      if (response.ok) {
        let data = await response.json();
        data.sort((a, b) => new Date(b.creationDate) - new Date(a.creationDate));
        setMessages(data);
      } else {
        console.error('Failed to fetch messages');
      }
    } catch (error) {
      console.error('Error fetching messages:', error);
    }
  };

  const fetchGroupDetails = async () => {
    try {
      const response = await fetch(`${DEPLOYED_LINK}/tasks/groups/${groupId}`);
      if (response.ok) {
        const data = await response.json();
        setGroupDetails(data);
      } else {
        console.error('Failed to fetch group details');
      }
    } catch (error) {
      console.error('Error fetching group details:', error);
    }
  };

  const handleMessageChange = (event) => {
    setNewMessage(event.target.value);
  };

  const handleSendMessage = async () => {
    try {
      const requestData = {
        content: newMessage,
        groupId: groupId,
        creatorId: username,
        creationDate: Math.floor(Date.now() / 1000)
      };

      const response = await fetch(`${DEPLOYED_LINK}/messages/createMessage`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestData)
      });

      if (response.ok) {
        alert('Task added successfully');
        fetchMessages();
        setNewMessage('');
      } else {
        alert('Failed to create Task');
      }
    } catch (error) {
      console.error('Error Creating Task:', error);
    }
  };

  const handleLeaveGroup = async () => {
    try {
      const response = await fetch(`${DEPLOYED_LINK}/tasks/${groupId}/leave/${username}`, {
        method: 'DELETE'
      });
      if (response.ok) {
        alert('Left the Task group successfully');
        navigate('/dashboard'); 
      } else {
        alert('Failed to leave the group');
      }
    } catch (error) {
      alert('Error leaving the group:', error);
    }
  };

  const handleBackToDashboard = () => {
    navigate('/dashboard');
  };

  const handleOpenEditDialog = (messageId, content) => {
    setEditedMessageId(messageId);
    setEditedMessageContent(content);
    setEditDialogOpen(true);
  };

  const handleCloseEditDialog = () => {
    setEditedMessageId(null);
    setEditedMessageContent('');
    setEditDialogOpen(false);
  };

  const handleEditMessage = async () => {
    try {
      const requestData = {
        content: editedMessageContent,
        groupId: groupId,
        creatorId: username,
        creationDate: Math.floor(Date.now() / 1000)
      };

      const response = await fetch(`${DEPLOYED_LINK}/messages/${editedMessageId}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestData)
      });

      if (response.ok) {
        alert('Task updated successfully');
        handleCloseEditDialog();
        fetchMessages();
      } else {
        alert('Failed to update Task');
      }
    } catch (error) {
      console.error('Error updating message:', error);
    }
  };

  const handleDeleteMessage = async (messageId) => {
    try {
      const response = await fetch(`${DEPLOYED_LINK}/messages/${messageId}`, {
        method: 'DELETE'
      });
      if (response.ok) {
        alert('Task deleted successfully');
        fetchMessages();
      } else {
        alert('Failed to delete the Task');
      }
    } catch (error) {
      console.error('Error deleting the message:', error);
    }
  };

  const handleOpenEditGroupDialog = () => {
    console.log('Open Edit Group Dialog');
  };

  const handleDeleteGroup = async () => {
    try {
      const response = await fetch(`${DEPLOYED_LINK}/tasks/groups/${groupId}`, {
        method: 'DELETE'
      });
      if (response.ok) {
        alert('Group deleted successfully');
        navigate('/dashboard');
      } else {
        alert('Failed to delete the group');
      }
    } catch (error) {
      console.error('Error deleting the group:', error);
    }
  };

 return (
  <div>
        <Button variant='contained' onClick={handleBackToDashboard}>
      Back to Dashboard
    </Button>
  <div className="top-buttons">
    <Button variant='contained' onClick={handleLeaveGroup}>
      Leave Group
    </Button>
  </div>
  <div className='GroupChat'>
  
  <h1>Tasks List</h1>
  <h2>Task Group Details</h2>
  {groupDetails && (
    <div className="group-details">
      <p>Name: {groupDetails.name}</p>
      <p>Description: {groupDetails.description}</p>
      <p>Date Created: {new Date(groupDetails.creationDate*1000).toLocaleString()}</p>
      {groupDetails && groupDetails.creatorId === username && (
    <div className="group-actions">
      <Button variant='contained' onClick={handleOpenEditGroupDialog}>
        Edit Group
      </Button>
      <Button variant='contained' onClick={handleDeleteGroup}>
        Delete Group
      </Button>
    </div>
  )}
    </div>
  )}
    <TextField
    label="Type your Task"
    value={newMessage}
    onChange={handleMessageChange}
    className="message-input"
  />
<Button variant='contained' onClick={handleSendMessage} className="send-button">
  Send
</Button>
  <ul className="message-list">
    {messages.map(message => (
      <li key={message.id} className="message-item">
        <div className="message-content">
          <p>{message.content}</p>
          <p className="message-date">Date: {new Date(message.creationDate *1000).toLocaleString()}</p>
        </div>
        {message.creatorId === username && (
          <div className="message-actions">
            <Button variant='outlined' onClick={() => handleOpenEditDialog(message.id, message.content)}>
              Edit
            </Button>
            <Button variant='outlined' onClick={() => handleDeleteMessage(message.id)}>
              Delete
            </Button>
          </div>
        )}
      </li>
    ))}
  </ul>
  <Dialog open={editDialogOpen} onClose={handleCloseEditDialog}>
    <DialogTitle>Edit Message</DialogTitle>
    <DialogContent>
      <TextField
        label="Message Content"
        value={editedMessageContent}
        onChange={(event) => setEditedMessageContent(event.target.value)}
      />
    </DialogContent>
    <DialogActions>
      <Button onClick={handleCloseEditDialog}>Cancel</Button>
      <Button onClick={handleEditMessage}>Save</Button>
    </DialogActions>
  </Dialog>
</div>
    
</div>
  );
};

export default GroupChat;
