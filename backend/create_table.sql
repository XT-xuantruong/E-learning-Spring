USE `e-learning`;

DROP TABLE IF EXISTS `lecture`;
DROP TABLE IF EXISTS `quiz_result`;
DROP TABLE IF EXISTS `answer`;
DROP TABLE IF EXISTS `question`;
DROP TABLE IF EXISTS `quiz`;
DROP TABLE IF EXISTS `course_enrollment`;
DROP TABLE IF EXISTS `course`;
DROP TABLE IF EXISTS `category`;
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

ALTER TABLE course 
ADD COLUMN price FLOAT NOT NULL DEFAULT 0.0;
ALTER TABLE course_enrollment 
ADD COLUMN price FLOAT NOT NULL DEFAULT 0.0;
-- Add user_id column
ALTER TABLE quiz_result 
ADD COLUMN user_id VARCHAR(36) NOT NULL;

-- Add foreign key constraint
ALTER TABLE quiz_result
ADD CONSTRAINT fk_quiz_result_user
FOREIGN KEY (user_id) REFERENCES users(id);
select * from category where slug = "react-js"; 
ALTER TABLE course_enrollment 
ADD CONSTRAINT unique_user_course UNIQUE (user_id, course_id);
ALTER TABLE course_enrollment DROP INDEX unique_user_course;
SELECT * 
FROM course_enrollment 
WHERE user_id = '3da4028f-e361-42f3-ad0a-019610dbd1ec' 
  AND course_id = '0dd67f4b-e737-45f3-97d8-454f47774881';
CREATE TABLE course_enrollment (
    id VARCHAR(36) PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL,
    course_id VARCHAR(36) NOT NULL,
    enroll_at TIMESTAMP,
    is_paid BOOLEAN NOT NULL DEFAULT FALSE,
    is_finish BOOLEAN,
    last_lecture VARCHAR(255),
    last_quiz VARCHAR(255),
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