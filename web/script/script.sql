create table account (
                         username varchar(50),
                         password varchar(50),
                         email varchar(50),
                         is_teacher BIT,
                         primary key (username)
)

create table quiz (
                      quiz_id int primary key,
                      quiz_description nvarchar(max),
                      created_by varchar(50),
                      weight int,
                      created_at datetime,
                      updated_at datetime,
                      foreign key (created_by) references account(username)
)

create table quiz_answer (
                             quiz_id int,
                             answer_id int,
                             answer_text varchar(500),
                             is_correct BIT,
                             created_at datetime,
                             updated_at datetime,
                             foreign key (quiz_id) REFERENCES quiz(quiz_id),
                             primary key (quiz_id, answer_id)
)

create table quiz_cluster (
                              cluster_id int identity(1, 1),
                              student_id varchar(50),
                              created_time datetime,
                              ended_time datetime,
                              primary key (cluster_id),
                              foreign key (student_id) references account(username),
)

create table cluster_detail (
                                cluster_id int,
                                quiz_id int,
                                student_choice_answer_id int,
                                foreign key (quiz_id) references quiz(quiz_id),
                                foreign key (cluster_id) references quiz_cluster(cluster_id)
)


create table student_result (
                                score FLOAT,
                                student_id varchar(50),
                                cluster_id int
                                    primary key (student_id, cluster_id)
                                    foreign key (student_id) REFERENCES account(username),
                                foreign key (cluster_id) REFERENCES quiz_cluster(cluster_id)
)