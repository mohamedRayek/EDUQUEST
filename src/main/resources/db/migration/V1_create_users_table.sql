CREATE TABLE admin (
                       admin_id INTEGER AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       phone VARCHAR(20),
                       card_number VARCHAR(20),
                       role VARCHAR(20)
);

CREATE TABLE teacher (
                         teacher_id INTEGER AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         email VARCHAR(255) UNIQUE NOT NULL,
                         password VARCHAR(255) NOT NULL,
                         subject VARCHAR(255),
                         bio VARCHAR(255),
                         phone VARCHAR(20),
                         card_number VARCHAR(20),
                         rate FLOAT,
                         role VARCHAR(20)
);

CREATE TABLE student (
                         student_id INTEGER AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         email VARCHAR(255) UNIQUE NOT NULL,
                         password VARCHAR(255) NOT NULL,
                         phone VARCHAR(20),
                         card_number VARCHAR(20),
                         level INTEGER,
                         role VARCHAR(20)
);

CREATE TABLE course (
                        course_id INTEGER AUTO_INCREMENT PRIMARY KEY,
                        title VARCHAR(255) NOT NULL,
                        sub_title VARCHAR(255) NOT NULL,
                        subject VARCHAR(255) NOT NULL,
                        description TEXT,
                        price FLOAT,
                        discount FLOAT,
                        teacher_id INTEGER,
                        lesson_number INTEGER,
                        student_number INTEGER,
                        rate FLOAT,
                        goals VARCHAR(2000),
                        requirements VARCHAR(2000),
                        includes VARCHAR(2000),
                        expiration_time DATE,
                        FOREIGN KEY (teacher_id) REFERENCES teacher(teacher_id) ON DELETE SET NULL
);


CREATE TABLE course_student (
                                student_id INTEGER,
                                course_id INTEGER,
                                PRIMARY KEY (student_id, course_id),
                                FOREIGN KEY (student_id) REFERENCES student(student_id) ON DELETE CASCADE,
                                FOREIGN KEY (course_id) REFERENCES course(course_id) ON DELETE CASCADE
);

CREATE TABLE question_bank (
                               question_bank_id INTEGER AUTO_INCREMENT PRIMARY KEY,
                               subject VARCHAR(255) NOT NULL
);

CREATE TABLE quiz (
                      quiz_id INTEGER AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      subject VARCHAR(255) NOT NULL,
                      time TIME NOT NULL,
                      teacher_id INTEGER,
                      FOREIGN KEY (teacher_id) REFERENCES teacher(teacher_id) ON DELETE SET NULL
);

CREATE TABLE question (
                          question_id INTEGER AUTO_INCREMENT PRIMARY KEY,
                          question TEXT NOT NULL,
                          choice_a TEXT NOT NULL,
                          choice_b TEXT NOT NULL,
                          choice_c TEXT NOT NULL,
                          choice_d TEXT NOT NULL,
                          right_choice CHAR(1) NOT NULL,
                          subject VARCHAR(255) NOT NULL,
                          course VARCHAR(255),
                          lesson_name VARCHAR(255),
                          question_bank_id INTEGER,
                          quiz_id INTEGER,
                          FOREIGN KEY (question_bank_id) REFERENCES question_bank(question_bank_id) ON DELETE CASCADE,
                          FOREIGN KEY (quiz_id) REFERENCES quiz(quiz_id) ON DELETE CASCADE
);

CREATE TABLE quiz_student (
                              quiz_id INTEGER,
                              student_id INTEGER,
                              PRIMARY KEY (quiz_id, student_id),
                              FOREIGN KEY (quiz_id) REFERENCES quiz(quiz_id) ON DELETE CASCADE,
                              FOREIGN KEY (student_id) REFERENCES student(student_id) ON DELETE CASCADE
);

