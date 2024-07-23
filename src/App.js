import React, { useState, useEffect } from 'react';
import './App.css';
import Sidebar from './components/js/Sidebar';
import Modal from './components/js/Modal';
import TaskBoard from './components/js/TaskBoard';
import TaskTable from './components/js/TaskTable';
import axios from 'axios';

function App() {
  const [tasks, setTasks] = useState([]);
  const [modalVisible, setModalVisible] = useState(false);
  const [categories, setCategories] = useState([]);

  // Handle user logout
  const logout = async () => {
    await fetch('/api/auth/logout', { method: 'POST', credentials: 'include' });
    window.location.href = '/login';
  };

  // Open and close modal
  const openModal = () => setModalVisible(true);
  const closeModal = () => setModalVisible(false);

  // Create a new task and link it to a category
  const createTask = async (taskData, categoryId) => {
    try {
      const taskResponse = await axios.post('/api/tasks/create', taskData);
      const taskId = taskResponse.data.id;

      if (categoryId) {
        await axios.post('/api/taskcategories/create', {
          taskId,
          categoryId
        });
      }

      setTasks(prevTasks => [...prevTasks, { ...taskData, id: taskId }]);
    } catch (error) {
      console.error('Error creating task or task category:', error);
    }
  };

  // Toggle the status of a task
  const toggleStatus = (taskId) => {
    setTasks(prevTasks =>
        prevTasks.map(task =>
            task.id === taskId
                ? { ...task, status: task.status === 'to-do' ? 'completed' : 'to-do' }
                : task
        )
    );
  };

  // Fetch categories from the server
  const fetchCategories = async () => {
    try {
      const response = await axios.get('/api/categories/getAll');
      setCategories(response.data);
    } catch (error) {
      console.error('Error fetching categories:', error);
    }
  };

  // Fetch categories on component mount
  useEffect(() => {
    fetchCategories();
  }, []);

  return (
      <div className="App">
        <Sidebar logout={logout} />
        <main className="main-content">
          <TaskBoard openModal={openModal} />
          <TaskTable tasks={tasks} toggleStatus={toggleStatus} />
          <Modal
              show={modalVisible}
              closeModal={closeModal}
              createTask={createTask}
              categories={categories}
              fetchCategories={fetchCategories}
          />
        </main>
      </div>
  );
}

export default App;
