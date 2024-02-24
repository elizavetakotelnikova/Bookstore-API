create type order_state as enum
    (
        'created',
        'in_process',
        'delivered',
        'cancelled'
    );

create type transaction_type as enum
    (
        'order_created',
        'order_cancelled',
        'logged_in'
        );

CREATE TABLE users (
                      id UUID PRIMARY KEY NOT NULL,
                      password bytea NOT NULL,
                      phone_number VARCHAR(50) NOT NULL,
                      balance INT NOT NULL DEFAULT 0,
                      birthday DATE);

CREATE INDEX users_phone_number_idx ON users (phone_number);

CREATE TABLE product_types(
    id UUID PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL
);
CREATE INDEX product_types_name_idx ON product_types(name);

CREATE TABLE products (
                       id UUID PRIMARY KEY NOT NULL,
                       type_id UUID REFERENCES product_types(id),
                       name VARCHAR(255),
                       price float);

CREATE TABLE shops (
                        id UUID PRIMARY KEY,
                        country varchar(255) NOT NULL,
                        city varchar(255) NOT NULL,
                        street varchar(255) NOT NULL,
                        house_number varchar(255) NOT NULL,
                        building_number int);

CREATE INDEX shops_city_idx ON shops(city);

CREATE TABLE orders (
                        id UUID PRIMARY KEY,
                        user_id UUID REFERENCES users(id),
                        date DATE,
                        shop_id UUID REFERENCES shops(id),
                        state order_state);
CREATE INDEX orders_user_id_idx ON orders(user_id);

CREATE TABLE feature_types (
                        id UUID PRIMARY KEY,
                        name varchar(255));

CREATE INDEX available_features_name_idx ON feature_types(name);

CREATE TABLE feature_value (
                       id UUID PRIMARY KEY,
                       feature_type_id UUID REFERENCES feature_types(id),
                       value varchar(255)
                       );

CREATE TABLE product_features (
                       product_id UUID REFERENCES products(id),
                       feature_value_id UUID REFERENCES feature_value(id),
                       primary key(product_id, feature_value_id)
                       );
CREATE INDEX product_features_product_id_idx ON product_features(product_id);
CREATE INDEX product_features_features_id_idx ON product_features(feature_value_id);

CREATE TABLE order_content (
                               order_id UUID REFERENCES products(id),
                               product_id UUID REFERENCES orders(id),
                               primary key(product_id, order_id)
);