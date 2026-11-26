ALTER TABLE orders
    MODIFY COLUMN order_status ENUM('new','confirmed','shipping','completed','cancelled');