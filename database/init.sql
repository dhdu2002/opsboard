CREATE TABLE IF NOT EXISTS server_asset (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(120) NOT NULL,
    ip_address VARCHAR(64) NOT NULL,
    environment VARCHAR(30) NOT NULL,
    usage_description VARCHAR(255) NOT NULL,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS site_asset (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    server_id BIGINT NOT NULL,
    site_name VARCHAR(150) NOT NULL,
    site_url VARCHAR(300) NOT NULL,
    admin_url VARCHAR(300) NOT NULL,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_site_server FOREIGN KEY (server_id) REFERENCES server_asset(id)
);

CREATE TABLE IF NOT EXISTS account_asset (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    site_id BIGINT NOT NULL,
    account_id VARCHAR(120) NOT NULL,
    account_password VARCHAR(255) NOT NULL,
    contact_owner VARCHAR(120),
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_account_site FOREIGN KEY (site_id) REFERENCES site_asset(id)
);

CREATE TABLE IF NOT EXISTS issue_item (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    issue_title VARCHAR(200) NOT NULL,
    issue_status VARCHAR(30) NOT NULL,
    assignee VARCHAR(120),
    related_server_id BIGINT,
    related_site_id BIGINT,
    related_account_id BIGINT,
    details TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_issue_server FOREIGN KEY (related_server_id) REFERENCES server_asset(id),
    CONSTRAINT fk_issue_site FOREIGN KEY (related_site_id) REFERENCES site_asset(id),
    CONSTRAINT fk_issue_account FOREIGN KEY (related_account_id) REFERENCES account_asset(id)
);
