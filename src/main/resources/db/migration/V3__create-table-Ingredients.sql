CREATE TABLE ingredients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(50), -- Ex: Carne, Salada, Molho, etc.
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
