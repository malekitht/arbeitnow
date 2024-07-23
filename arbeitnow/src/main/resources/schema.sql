CREATE TABLE Job (
                     id IDENTITY PRIMARY KEY,
                     slug VARCHAR(255) NOT NULL,
                     company_name VARCHAR(255) NOT NULL,
                     title VARCHAR(255) NOT NULL,
                     description TEXT,
                     remote BOOLEAN NOT NULL,
                     url VARCHAR(255) NOT NULL,
                     location VARCHAR(255) NOT NULL,
                     created_at BIGINT NOT NULL
);

CREATE TABLE Job_Tag (
                         job_id BIGINT NOT NULL,
                         tag VARCHAR(255) NOT NULL,
                         FOREIGN KEY (job_id) REFERENCES Job(id)
);

CREATE TABLE Job_Type (
                          job_id BIGINT NOT NULL,
                          job_type VARCHAR(255) NOT NULL,
                          FOREIGN KEY (job_id) REFERENCES Job(id)
);

INSERT INTO Job (slug, company_name, title, description, remote, url, location, created_at) VALUES
    ('mechatroniker-wilnsdorf-463157', 'RITTER Starkstromtechnik GmbH & Co. KG', 'Mechatroniker (m/w/d)', 'Description here', FALSE, 'https://www.arbeitnow.com/jobs/companies/ritter-starkstromtechnik-gmbh-co-kg/mechatroniker-wilnsdorf-463157', 'Wilnsdorf', 1721731745);

INSERT INTO Job_Tag (job_id, tag) VALUES
    (1, 'Mechatronic Engineering');

INSERT INTO Job_Type (job_id, job_type) VALUES
    (1, 'Full-Time');