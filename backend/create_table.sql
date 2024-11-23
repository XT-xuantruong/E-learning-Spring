USE `e-learning`;

DROP TABLE IF EXISTS `comments`;
DROP TABLE IF EXISTS `lecture`;
DROP TABLE IF EXISTS `quiz_result`;
DROP TABLE IF EXISTS `answer`;
DROP TABLE IF EXISTS `question`;
DROP TABLE IF EXISTS `quiz`;
DROP TABLE IF EXISTS `course_enrollment`;
DROP TABLE IF EXISTS `course`;
DROP TABLE IF EXISTS `category`;
DROP TABLE IF EXISTS `notification`;
DROP TABLE IF EXISTS `paypal_payment`;
DROP TABLE IF EXISTS `users`;

CREATE TABLE users (
    id VARCHAR(36) PRIMARY KEY,
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
	id VARCHAR(36) PRIMARY KEY,
	title VARCHAR(255),
	slug VARCHAR(255),
	created_at TIMESTAMP
);

CREATE TABLE course (
	id VARCHAR(36) PRIMARY KEY,
	title VARCHAR(255),
	description TEXT,
	thumbnail VARCHAR(255),
	category_id VARCHAR(36),
	create_by VARCHAR(36),
	created_at TIMESTAMP,
	FOREIGN KEY (category_id) REFERENCES category(id),
	FOREIGN KEY (create_by) REFERENCES users(id)
);

CREATE TABLE course_enrollment (
   id VARCHAR(36) PRIMARY KEY,
   user_id VARCHAR(36) NOT NULL,
   course_id VARCHAR(36) NOT NULL,
   enroll_at TIMESTAMP,
   ispaid BOOLEAN NOT NULL,
   FOREIGN KEY (user_id) REFERENCES users(id),
   FOREIGN KEY (course_id) REFERENCES course(id)
);
CREATE TABLE quiz (
   id VARCHAR(36) PRIMARY KEY,
   title VARCHAR(255) NOT NULL,
   course_id VARCHAR(36) NOT NULL,
   created_at TIMESTAMP,
   FOREIGN KEY (course_id) REFERENCES course(id)
);

CREATE TABLE question (
   id VARCHAR(36) PRIMARY KEY,
   question_text TEXT NOT NULL,
   quiz_id VARCHAR(36) NOT NULL,
   created_at TIMESTAMP,
   FOREIGN KEY (quiz_id) REFERENCES quiz(id)
);

CREATE TABLE answer (
    id VARCHAR(36) PRIMARY KEY,
    question_id VARCHAR(36) NOT NULL,
    answer_text TEXT NOT NULL, 
    is_correct BOOLEAN NOT NULL,
    FOREIGN KEY (question_id) REFERENCES question(id)
);
CREATE TABLE quiz_result (
    id VARCHAR(36) PRIMARY KEY,
    score INT NOT NULL,
    quiz_id VARCHAR(36) NOT NULL,
    course_id VARCHAR(36) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    FOREIGN KEY (quiz_id) REFERENCES quiz(id),
    FOREIGN KEY (course_id) REFERENCES course(id)
);
CREATE TABLE lecture (
    id VARCHAR(36) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    course_id VARCHAR(36) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    FOREIGN KEY (course_id) REFERENCES course(id)
);

CREATE TABLE comments(
    id VARCHAR(36) PRIMARY KEY,
    rating INT NOT NULL,
    comment TEXT NOT NULL,
    user_id VARCHAR(36) NOT NULL,
    course_id VARCHAR(36) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (course_id) REFERENCES course(id)
);

CREATE TABLE notification (
    id VARCHAR(36) PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL,
    message TEXT NOT NULL,
    is_read BOOLEAN NOT NULL,
    created_at TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE paypal_payment (
    id VARCHAR(36) PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL,
    paypal_payment_id VARCHAR(255) NOT NULL,
    amount FLOAT NOT NULL,
    currency VARCHAR(10) NOT NULL,
    state VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP,
    updated_at TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
