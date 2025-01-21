CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    person_id INT NOT NULL,
    event_id INT NOT NULL,
    meat_doneness ENUM('mal_passado', 'ao_ponto', 'bem_passado') NOT NULL,
    notes TEXT, -- Notas adicionais, como "Sem molho" ou "Com muito queijo"
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (person_id) REFERENCES people(id),
    FOREIGN KEY (event_id) REFERENCES events(id)
);
