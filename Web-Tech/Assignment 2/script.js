// User data storage and configuration
let users = [];
let userIdCounter = 1;

// DOM elements
const userForm = document.getElementById('userForm');
const userTableBody = document.getElementById('userTableBody');
const totalUsersElement = document.getElementById('totalUsers');
const avgAgeElement = document.getElementById('avgAge');
const totalCitiesElement = document.getElementById('totalCities');
const notificationContainer = document.getElementById('notification-container');

// Initialize the application
document.addEventListener('DOMContentLoaded', function() {
    initializeApp();
});

// Initialize application
function initializeApp() {
    setupFormHandlers();
    updateStats();
    addInputAnimations();
    console.log('🚀 User Data Management System initialized successfully!');
}

// Setup form event handlers
function setupFormHandlers() {
    userForm.addEventListener('submit', handleFormSubmission);
    
    // Add real-time validation
    const inputs = userForm.querySelectorAll('input');
    inputs.forEach(input => {
        input.addEventListener('blur', validateInput);
        input.addEventListener('input', clearErrors);
    });
}

// Handle form submission
function handleFormSubmission(e) {
    e.preventDefault();
    
    if (!validateForm()) {
        return;
    }
    
    // Get form data
    const formData = new FormData(userForm);
    const userData = {
        id: userIdCounter++,
        name: capitalizeWords(formData.get('name').trim()),
        age: parseInt(formData.get('age')),
        city: capitalizeWords(formData.get('city').trim()),
        mobile: formData.get('mobile').trim(),
        timestamp: new Date().toLocaleString()
    };
    
    // Validate mobile number format
    if (!isValidMobileNumber(userData.mobile)) {
        showNotification('Please enter a valid 10-digit mobile number', 'error');
        focusInput('mobile');
        return;
    }
    
    // Check for duplicate mobile number
    if (isDuplicateMobile(userData.mobile)) {
        showNotification('This mobile number is already registered', 'error');
        focusInput('mobile');
        return;
    }
    
    // Add user to the system
    addUser(userData);
}

// Validate entire form
function validateForm() {
    const inputs = userForm.querySelectorAll('input[required]');
    let isValid = true;
    
    inputs.forEach(input => {
        if (!validateInput({ target: input })) {
            isValid = false;
        }
    });
    
    return isValid;
}

// Validate individual input
function validateInput(e) {
    const input = e.target;
    const value = input.value.trim();
    
    // Clear previous errors
    clearInputError(input);
    
    if (!value && input.required) {
        showInputError(input, 'This field is required');
        return false;
    }
    
    // Specific validations
    switch (input.type) {
        case 'number':
            if (input.name === 'age') {
                if (value < 1 || value > 150) {
                    showInputError(input, 'Age must be between 1 and 150');
                    return false;
                }
            }
            break;
        case 'tel':
            if (value && !isValidMobileNumber(value)) {
                showInputError(input, 'Please enter a valid 10-digit mobile number');
                return false;
            }
            break;
        case 'text':
            if (input.name === 'name' && value.length < 2) {
                showInputError(input, 'Name must be at least 2 characters long');
                return false;
            }
            if (input.name === 'city' && value.length < 2) {
                showInputError(input, 'City name must be at least 2 characters long');
                return false;
            }
            break;
    }
    
    return true;
}

// Clear input errors
function clearErrors(e) {
    clearInputError(e.target);
}

// Show input-specific error
function showInputError(input, message) {
    input.style.borderColor = '#ff6b6b';
    input.style.boxShadow = '0 0 0 4px rgba(255, 107, 107, 0.2)';
    
    // Remove existing error message
    const existingError = input.parentNode.querySelector('.error-message');
    if (existingError) {
        existingError.remove();
    }
    
    // Add new error message
    const errorDiv = document.createElement('div');
    errorDiv.className = 'error-message';
    errorDiv.textContent = message;
    errorDiv.style.cssText = `
        color: #ff6b6b;
        font-size: 0.85rem;
        margin-top: 5px;
        animation: slideIn 0.3s ease-out;
    `;
    
    input.parentNode.appendChild(errorDiv);
}

// Clear input error styling
function clearInputError(input) {
    input.style.borderColor = '';
    input.style.boxShadow = '';
    
    const errorMessage = input.parentNode.querySelector('.error-message');
    if (errorMessage) {
        errorMessage.remove();
    }
}

// Focus on specific input
function focusInput(inputName) {
    const input = document.getElementById(inputName);
    if (input) {
        input.focus();
        input.select();
    }
}

// Validate mobile number format
function isValidMobileNumber(mobile) {
    return /^\d{10}$/.test(mobile);
}

// Check for duplicate mobile numbers
function isDuplicateMobile(mobile) {
    return users.some(user => user.mobile === mobile);
}

// Add user to the system
function addUser(userData) {
    users.push(userData);
    updateTable();
    updateStats();
    resetForm();
    showNotification(`Welcome ${userData.name}! User added successfully.`, 'success');
    
    // Log activity
    console.log('✅ User added:', userData);
}

// Update the user table
function updateTable() {
    userTableBody.innerHTML = '';
    
    if (users.length === 0) {
        showEmptyState();
        return;
    }
    
    users.forEach((user, index) => {
        const row = createUserRow(user, index);
        userTableBody.appendChild(row);
    });
}

// Show empty state in table
function showEmptyState() {
    userTableBody.innerHTML = `
        <tr class="empty-state">
            <td colspan="5">
                <div class="empty-content">
                    <div class="empty-icon">📊</div>
                    <p>No users added yet.</p>
                    <small>Use the form to add your first user!</small>
                </div>
            </td>
        </tr>
    `;
}

// Create user table row
function createUserRow(user, index) {
    const row = document.createElement('tr');
    row.className = 'new-row';
    row.innerHTML = `
        <td title="Added: ${user.timestamp}">${user.name}</td>
        <td>${user.age} years</td>
        <td>${user.city}</td>
        <td>${formatMobileNumber(user.mobile)}</td>
        <td>
            <button class="delete-btn" onclick="confirmDeleteUser(${user.id})" title="Delete ${user.name}">
                🗑️ Delete
            </button>
        </td>
    `;
    
    // Add hover effect data
    row.addEventListener('mouseenter', () => {
        row.style.transform = 'scale(1.01)';
    });
    
    row.addEventListener('mouseleave', () => {
        row.style.transform = 'scale(1)';
    });
    
    return row;
}

// Format mobile number for display
function formatMobileNumber(mobile) {
    return mobile.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3');
}

// Confirm user deletion
function confirmDeleteUser(id) {
    const user = users.find(u => u.id === id);
    if (!user) return;
    
    const isConfirmed = confirm(`Are you sure you want to delete ${user.name}?\n\nThis action cannot be undone.`);
    
    if (isConfirmed) {
        deleteUser(id);
    }
}

// Delete user from the system
function deleteUser(id) {
    const userIndex = users.findIndex(user => user.id === id);
    if (userIndex === -1) return;
    
    const deletedUser = users[userIndex];
    users.splice(userIndex, 1);
    
    updateTable();
    updateStats();
    showNotification(`${deletedUser.name} has been removed from the database.`, 'error');
    
    // Log activity
    console.log('🗑️ User deleted:', deletedUser);
}

// Update statistics display
function updateStats() {
    const totalUsers = users.length;
    const avgAge = totalUsers > 0 ? Math.round(users.reduce((sum, user) => sum + user.age, 0) / totalUsers) : 0;
    const uniqueCities = new Set(users.map(user => user.city.toLowerCase())).size;
    
    // Animate number changes
    animateNumber(totalUsersElement, parseInt(totalUsersElement.textContent) || 0, totalUsers);
    animateNumber(avgAgeElement, parseInt(avgAgeElement.textContent) || 0, avgAge);
    animateNumber(totalCitiesElement, parseInt(totalCitiesElement.textContent) || 0, uniqueCities);
}

// Animate number changes in statistics
function animateNumber(element, start, end, duration = 1000) {
    if (start === end) return;
    
    const range = end - start;
    const increment = range / (duration / 16);
    let current = start;
    
    const timer = setInterval(() => {
        current += increment;
        
        if ((increment > 0 && current >= end) || (increment < 0 && current <= end)) {
            current = end;
            clearInterval(timer);
        }
        
        element.textContent = Math.round(current);
    }, 16);
}

// Reset form to initial state
function resetForm() {
    userForm.reset();
    
    // Clear any remaining error styles
    const inputs = userForm.querySelectorAll('input');
    inputs.forEach(clearInputError);
    
    // Focus on first input
    const firstInput = userForm.querySelector('input');
    if (firstInput) {
        setTimeout(() => firstInput.focus(), 100);
    }
}

// Show notification to user
function showNotification(message, type) {
    const notification = document.createElement('div');
    notification.className = `notification ${type}`;
    notification.textContent = message;
    
    notificationContainer.appendChild(notification);
    
    // Auto-remove notification
    setTimeout(() => {
        notification.style.animation = 'slideOutRight 0.4s cubic-bezier(0.4, 0, 0.2, 1) forwards';
        setTimeout(() => {
            if (notification.parentNode) {
                notification.parentNode.removeChild(notification);
            }
        }, 400);
    }, 4000);
}

// Add input animations and enhancements
function addInputAnimations() {
    const inputs = userForm.querySelectorAll('input');
    
    inputs.forEach(input => {
        // Add floating label effect
        input.addEventListener('focus', () => {
            input.parentNode.classList.add('focused');
        });
        
        input.addEventListener('blur', () => {
            if (!input.value) {
                input.parentNode.classList.remove('focused');
            }
        });
        
        // Add input validation indicators
        input.addEventListener('input', (e) => {
            const value = e.target.value.trim();
            if (value) {
                e.target.style.borderColor = 'rgba(78, 205, 196, 0.6)';
            } else {
                e.target.style.borderColor = '';
            }
        });
    });
}

// Utility function to capitalize words
function capitalizeWords(str) {
    return str.replace(/\b\w/g, char => char.toUpperCase());
}

// Export data functionality
function exportToCSV() {
    if (users.length === 0) {
        showNotification('No data to export', 'error');
        return;
    }
    
    const headers = ['Name', 'Age', 'City', 'Mobile Number', 'Date Added'];
    const csvContent = [
        headers.join(','),
        ...users.map(user => [
            `"${user.name}"`,
            user.age,
            `"${user.city}"`,
            user.mobile,
            `"${user.timestamp}"`
        ].join(','))
    ].join('\n');
    
    const blob = new Blob([csvContent], { type: 'text/csv' });
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = `users_${new Date().toISOString().split('T')[0]}.csv`;
    a.click();
    window.URL.revokeObjectURL(url);
    
    showNotification('Data exported successfully!', 'success');
}

// Search functionality
function searchUsers(query) {
    const filteredUsers = users.filter(user => {
        return user.name.toLowerCase().includes(query.toLowerCase()) ||
               user.city.toLowerCase().includes(query.toLowerCase()) ||
               user.mobile.includes(query);
    });
    
    displayFilteredUsers(filteredUsers);
}

// Display filtered users
function displayFilteredUsers(filteredUsers) {
    userTableBody.innerHTML = '';
    
    if (filteredUsers.length === 0) {
        userTableBody.innerHTML = `
            <tr class="empty-state">
                <td colspan="5">
                    <div class="empty-content">
                        <div class="empty-icon">🔍</div>
                        <p>No users found.</p>
                        <small>Try adjusting your search criteria.</small>
                    </div>
                </td>
            </tr>
        `;
        return;
    }
    
    filteredUsers.forEach(user => {
        const row = createUserRow(user);
        userTableBody.appendChild(row);
    });
}

// Keyboard shortcuts
document.addEventListener('keydown', function(e) {
    // Alt + N: Focus on name input
    if (e.altKey && e.key === 'n') {
        e.preventDefault();
        document.getElementById('name').focus();
    }
    
    // Alt + A: Focus on age input
    if (e.altKey && e.key === 'a') {
        e.preventDefault();
        document.getElementById('age').focus();
    }
    
    // Alt + C: Focus on city input
    if (e.altKey && e.key === 'c') {
        e.preventDefault();
        document.getElementById('city').focus();
    }
    
    // Alt + M: Focus on mobile input
    if (e.altKey && e.key === 'm') {
        e.preventDefault();
        document.getElementById('mobile').focus();
    }
    
    // Escape: Clear form
    if (e.key === 'Escape') {
        resetForm();
        showNotification('Form cleared', 'success');
    }
});

// Advanced statistics
function getAdvancedStats() {
    if (users.length === 0) return null;
    
    const ages = users.map(user => user.age);
    const cities = users.map(user => user.city);
    
    return {
        totalUsers: users.length,
        averageAge: Math.round(ages.reduce((a, b) => a + b, 0) / ages.length),
        minAge: Math.min(...ages),
        maxAge: Math.max(...ages),
        uniqueCities: [...new Set(cities.map(city => city.toLowerCase()))].length,
        mostCommonCity: getMostCommonCity(cities),
        ageGroups: {
            teens: ages.filter(age => age >= 13 && age <= 19).length,
            twenties: ages.filter(age => age >= 20 && age <= 29).length,
            thirties: ages.filter(age => age >= 30 && age <= 39).length,
            forties: ages.filter(age => age >= 40 && age <= 49).length,
            fiftyPlus: ages.filter(age => age >= 50).length
        }
    };
}

// Get most common city
function getMostCommonCity(cities) {
    const cityCount = {};
    cities.forEach(city => {
        const normalizedCity = city.toLowerCase();
        cityCount[normalizedCity] = (cityCount[normalizedCity] || 0) + 1;
    });
    
    return Object.keys(cityCount).reduce((a, b) => 
        cityCount[a] > cityCount[b] ? a : b
    );
}

// Initialize tooltips and accessibility features
function initializeAccessibility() {
    // Add ARIA labels
    document.getElementById('name').setAttribute('aria-label', 'Enter full name');
    document.getElementById('age').setAttribute('aria-label', 'Enter age in years');
    document.getElementById('city').setAttribute('aria-label', 'Enter city name');
    document.getElementById('mobile').setAttribute('aria-label', 'Enter 10-digit mobile number');
    
    // Add form validation messages
    const form = document.getElementById('userForm');
    form.setAttribute('novalidate', 'true'); // Use custom validation
}

// Initialize application when DOM is loaded
document.addEventListener('DOMContentLoaded', function() {
    initializeApp();
    initializeAccessibility();
    
    // Log welcome message
    console.log(`
    🎉 Welcome to User Data Management System!
    
    Keyboard Shortcuts:
    • Alt + N: Focus on Name field
    • Alt + A: Focus on Age field  
    • Alt + C: Focus on City field
    • Alt + M: Focus on Mobile field
    • Escape: Clear form
    
    Features:
    • Real-time form validation
    • Duplicate detection
    • Data export to CSV
    • Responsive design
    • Accessibility support
    `);
});

// Window load event for final setup
window.addEventListener('load', function() {
    // Smooth fade-in animation for the entire page
    document.body.style.opacity = '0';
    document.body.style.transition = 'opacity 0.5s ease-in-out';
    
    setTimeout(() => {
        document.body.style.opacity = '1';
    }, 100);
    
    console.log('🚀 Application fully loaded and ready!');
});