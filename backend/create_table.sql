USE `e-learning`;
DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `category`;
DROP TABLE IF EXISTS `course`;
CREATE TABLE users (
    id BINARY(16) PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    avatar VARCHAR(255),
    role ENUM('student', 'admin', 'teacher'),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE category (
	id BINARY(16) PRIMARY KEY,
	title VARCHAR(255),
	slug VARCHAR(255),
	created_at TIMESTAMP
);

CREATE TABLE course (
	id BINARY(16) PRIMARY KEY,
	title VARCHAR(255),
	description TEXT,
	thumbnail VARCHAR(255),
	category_id BINARY(16),
	create_by BINARY(16),
	created_at TIMESTAMP,
	FOREIGN KEY (category_id) REFERENCES category(id),
	FOREIGN KEY (create_by) REFERENCES users(id)
);

