// Data storage arrays
let orderedItems = [];
let unorderedItems = [];
let definitions = [];

// ==================== ORDERED LIST FUNCTIONS ====================

/**
 * Add a new item to the ordered list
 */
function addOrderedItem() {
    const input = document.getElementById('orderedInput');
    const value = input.value.trim();
    
    if (value) {
        orderedItems.push(value);
        input.value = '';
        renderOrderedList();
    } else {
        input.focus();
    }
}

/**
 * Remove an item from the ordered list by index
 * @param {number} index - The index of the item to remove
 */
function removeOrderedItem(index) {
    orderedItems.splice(index, 1);
    renderOrderedList();
}

/**
 * Clear all items from the ordered list
 */
function clearOrderedList() {
    orderedItems = [];
    renderOrderedList();
}

/**
 * Render the ordered list in the DOM with explicit serial numbers
 */
function renderOrderedList() {
    const list = document.getElementById('orderedList');
    
    if (orderedItems.length === 0) {
        list.innerHTML = '<div class="empty-state">No items yet. Add your first item above!</div>';
        return;
    }
    
    list.innerHTML = orderedItems.map((item, index) => 
        `<li>
            <span class="serial-number">${index + 1}.</span>
            <span class="item-text">${escapeHtml(item)}</span>
            <button class="btn btn-remove" onclick="removeOrderedItem(${index})">Remove</button>
        </li>`
    ).join('');
}

// ==================== UNORDERED LIST FUNCTIONS ====================

/**
 * Add a new item to the unordered list
 */
function addUnorderedItem() {
    const input = document.getElementById('unorderedInput');
    const value = input.value.trim();
    
    if (value) {
        unorderedItems.push(value);
        input.value = '';
        renderUnorderedList();
    } else {
        input.focus();
    }
}

/**
 * Remove an item from the unordered list by index
 * @param {number} index - The index of the item to remove
 */
function removeUnorderedItem(index) {
    unorderedItems.splice(index, 1);
    renderUnorderedList();
}

/**
 * Clear all items from the unordered list
 */
function clearUnorderedList() {
    unorderedItems = [];
    renderUnorderedList();
}

/**
 * Render the unordered list in the DOM
 */
function renderUnorderedList() {
    const list = document.getElementById('unorderedList');
    
    if (unorderedItems.length === 0) {
        list.innerHTML = '<div class="empty-state">No items yet. Add your first item above!</div>';
        return;
    }
    
    list.innerHTML = unorderedItems.map((item, index) => 
        `<li>
            <span class="item-text">${escapeHtml(item)}</span>
            <button class="btn btn-remove" onclick="removeUnorderedItem(${index})">Remove</button>
        </li>`
    ).join('');
}

// ==================== DEFINITION LIST FUNCTIONS ====================

/**
 * Add a new definition to the definition list
 */
function addDefinition() {
    const termInput = document.getElementById('termInput');
    const definitionInput = document.getElementById('definitionInput');
    const term = termInput.value.trim();
    const definition = definitionInput.value.trim();
    
    if (term && definition) {
        definitions.push({ term, definition });
        termInput.value = '';
        definitionInput.value = '';
        renderDefinitionList();
        termInput.focus(); // Focus back to term input for quick adding
    } else {
        if (!term) {
            termInput.focus();
        } else if (!definition) {
            definitionInput.focus();
        }
    }
}

/**
 * Remove a definition from the definition list by index
 * @param {number} index - The index of the definition to remove
 */
function removeDefinition(index) {
    definitions.splice(index, 1);
    renderDefinitionList();
}

/**
 * Clear all definitions from the definition list
 */
function clearDefinitionList() {
    definitions = [];
    renderDefinitionList();
}

/**
 * Render the definition list in the DOM
 */
function renderDefinitionList() {
    const list = document.getElementById('definitionList');
    
    if (definitions.length === 0) {
        list.innerHTML = '<div class="empty-state">No definitions yet. Add your first term and definition above!</div>';
        return;
    }
    
    list.innerHTML = definitions.map(({ term, definition }, index) => 
        `<dt>
            <span class="item-text">${escapeHtml(term)}</span>
            <button class="btn btn-remove" onclick="removeDefinition(${index})">Remove</button>
        </dt>
        <dd>${escapeHtml(definition)}</dd>`
    ).join('');
}

// ==================== UTILITY FUNCTIONS ====================

/**
 * Escape HTML characters to prevent XSS attacks
 * @param {string} text - The text to escape
 * @returns {string} - The escaped text
 */
function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

/**
 * Initialize sample data for demonstration
 */
function initializeSampleData() {
    // Add some sample items to demonstrate the functionality
    orderedItems = [
        'Task 1',
        'Task 2',
        'Task 3',
        'Task 4'
    ];
    
    unorderedItems = [
        'Task 1',
        'Task 2',
        'Task 3',
        'Task 4'
    ];
    
    definitions = [
        { 
            term: 'HTML', 
            definition: 'HyperText Markup Language' 
        },
        { 
            term: 'CSS', 
            definition: 'Cascading Style Sheets' 
        },
        { 
            term: 'JavaScript', 
            definition: 'A high-level, interpreted programming language'
    
        }
];
    
    // Render all lists with sample data
    renderOrderedList();
    renderUnorderedList();
    renderDefinitionList();
}

// ==================== EVENT LISTENERS ====================

/**
 * Set up event listeners when the DOM is loaded
 */
function setupEventListeners() {
    // Enter key functionality for ordered list
    document.getElementById('orderedInput').addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            e.preventDefault();
            addOrderedItem();
        }
    });

    // Enter key functionality for unordered list
    document.getElementById('unorderedInput').addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            e.preventDefault();
            addUnorderedItem();
        }
    });

    // Enter key functionality for definition term input
    document.getElementById('termInput').addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            e.preventDefault();
            document.getElementById('definitionInput').focus();
        }
    });

    // Enter key functionality for definition input (Shift+Enter for new line)
    document.getElementById('definitionInput').addEventListener('keypress', function(e) {
        if (e.key === 'Enter' && !e.shiftKey) {
            e.preventDefault();
            addDefinition();
        }
    });
}

// ==================== INITIALIZATION ====================

/**
 * Initialize the application when the window loads
 */
window.addEventListener('load', function() {
    console.log('Interactive Lists Manager loaded successfully!');
    setupEventListeners();
    initializeSampleData();
    
    // Focus on the first input for better user experience
    document.getElementById('orderedInput').focus();
});

/**
 * Handle page beforeunload to warn about unsaved changes
 */
window.addEventListener('beforeunload', function(e) {
    const hasData = orderedItems.length > 0 || unorderedItems.length > 0 || definitions.length > 0;
    
    if (hasData) {
        const confirmationMessage = 'You have unsaved changes. Are you sure you want to leave?';
        e.returnValue = confirmationMessage;
        return confirmationMessage;
    }
});

// Export functions for potential module use (if needed)
if (typeof module !== 'undefined' && module.exports) {
    module.exports = {
        addOrderedItem,
        removeOrderedItem,
        clearOrderedList,
        addUnorderedItem,
        removeUnorderedItem,
        clearUnorderedList,
        addDefinition,
        removeDefinition,
        clearDefinitionList
    };
}