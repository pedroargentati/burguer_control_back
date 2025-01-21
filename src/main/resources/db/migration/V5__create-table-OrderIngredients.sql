CREATE TABLE order_ingredients (
    order_id INT NOT NULL,
    ingredient_id INT NOT NULL,
    quantity INT DEFAULT 1, -- Quantidade de cada ingrediente
    PRIMARY KEY (order_id, ingredient_id),
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (ingredient_id) REFERENCES ingredients(id)
);
