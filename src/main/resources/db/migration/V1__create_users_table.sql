CREATE TABLE customers (
                           customer_id VARCHAR(36) NOT NULL PRIMARY KEY,
                           full_name VARCHAR(255),
                           email VARCHAR(255) NOT NULL,
                           phone VARCHAR(255),
                           created_at DATETIME(6),
                           updated_at DATETIME(6)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE categories(
                           category_id VARCHAR(36) NOT NULL PRIMARY KEY,
                           name varchar(255),
                           description varchar(255),
                           create_at DATETIME(6),
                           update_at DATETIME(6)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE products (
                          product_id VARCHAR(36) NOT NULL PRIMARY KEY,
                          name VARCHAR(200) NOT NULL,
                          decription VARCHAR(200) NOT NULL,
                          price DECIMAL(15, 2) NOT NULL,
                          stock INT,
                          product_status VARCHAR(255),
                          category_id VARCHAR(36) NOT NULL,
                          created_at DATETIME(6),
                          update_at DATETIME(6),
                          CONSTRAINT fk_products_category
                              FOREIGN KEY (category_id) REFERENCES categories(category_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;



CREATE TABLE orders (
                        order_id VARCHAR(36) NOT NULL PRIMARY KEY,
                        customer_id VARCHAR(36) NOT NULL,
                        order_status VARCHAR(255),
                        total_amount DECIMAL(19, 2),
                        note VARCHAR(255),
                        created_at DATETIME(6),
                        updated_at DATETIME(6),
                        CONSTRAINT fk_orders_customer
                            FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE order_items (
                             order_items_id VARCHAR(36) NOT NULL PRIMARY KEY,
                             produc_id VARCHAR(36) NOT NULL,
                             order_id VARCHAR(36) NOT NULL,
                             quantity INT NOT NULL,
                             unit_price DECIMAL(19, 2),
                             CONSTRAINT fk_order_items_product
                                 FOREIGN KEY (produc_id) REFERENCES products(product_id),
                             CONSTRAINT fk_order_items_order
                                 FOREIGN KEY (order_id) REFERENCES orders(order_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
