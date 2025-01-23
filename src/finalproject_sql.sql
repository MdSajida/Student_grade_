CREATE TABLE IF NOT EXISTS Students_ (
    student_id INT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255),
    gender CHAR(1) NOT NULL CHECK (gender IN ('F', 'M'))
);

CREATE TABLE IF NOT EXISTS Courses_ (
    course_id int PRIMARY KEY,
    course_name VARCHAR(255)NOT NULL,
    course_description TEXT,
    credits Double precision NOT NULL CHECK (credits >= 1 AND credits <= 5)
);

CREATE TABLE IF NOT EXISTS Grades_ (
    grade_id SERIAL PRIMARY KEY,
    student_id INT NOT NULL,
    course_id int NOT NULL,
	gradep INT NOT NULL CHECK (gradep >= 1 AND gradep <= 10),
    FOREIGN KEY (student_id) REFERENCES Students_(student_id)on delete cascade,
    FOREIGN KEY (course_id) REFERENCES Courses_(course_id) on delete cascade
);

CREATE TABLE IF NOT EXISTS all_grades (
    grade_id SERIAL PRIMARY KEY,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    grade int NOT NULL
);
select *from students_;
select *from grades_;
select *from courses_;
select *from all_grades;

-- drop table students_;
-- drop table grades_;
-- drop table courses_;
-- drop table all_grades;