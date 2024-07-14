-- Create homes table
CREATE TABLE IF NOT EXISTS homes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    uuid VARCHAR(36) NOT NULL,
    name VARCHAR(50) NOT NULL,
    world VARCHAR(50) NOT NULL,
    x DOUBLE NOT NULL,
    y DOUBLE NOT NULL,
    z DOUBLE NOT NULL,
    yaw FLOAT NOT NULL,
    pitch FLOAT NOT NULL
);

-- Create user_settings table
CREATE TABLE IF NOT EXISTS user_settings (
    uuid VARCHAR(36) PRIMARY KEY,
    vanish_message BOOLEAN DEFAULT TRUE,
    vanish_vanished BOOLEAN DEFAULT FALSE,
    feedback BOOLEAN DEFAULT TRUE,
    allowtpas BOOLEAN DEFAULT TRUE,
    nick VARCHAR(50),
    lastback TEXT,
    isback BOOLEAN DEFAULT FALSE
);

-- Create warps table
CREATE TABLE IF NOT EXISTS warps (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE,
    world VARCHAR(50) NOT NULL,
    x DOUBLE NOT NULL,
    y DOUBLE NOT NULL,
    z DOUBLE NOT NULL,
    yaw FLOAT NOT NULL,
    pitch FLOAT NOT NULL
);

-- Create ranks table
CREATE TABLE IF NOT EXISTS ranks (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE,
    prefix VARCHAR(50),
    uuids TEXT
);

-- Create features table
CREATE TABLE IF NOT EXISTS features (
    name VARCHAR(50) PRIMARY KEY,
    state BOOLEAN NOT NULL DEFAULT TRUE
);

-- Insert default features
INSERT INTO features (name, state) VALUES
('warp_system', TRUE),
('home_system', TRUE),
('rank_system', TRUE),
('tpa_system', TRUE),
('join_leave_system', TRUE);