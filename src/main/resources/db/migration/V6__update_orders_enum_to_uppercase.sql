UPDATE orders
SET meat_doneness = UPPER(meat_doneness);

ALTER TABLE orders
MODIFY COLUMN meat_doneness ENUM('MAL_PASSADO', 'AO_PONTO', 'BEM_PASSADO') NOT NULL;
