-- User table creation script
CREATE TABLE `User` (
    `userId` CHAR(5) PRIMARY KEY,
    `password` VARCHAR(255) NOT NULL,
    `userName` VARCHAR(50) NOT NULL
);

-- sample user (password is 'password' encoded with bcrypt)
INSERT INTO `User` (`userId`, `password`, `userName`) VALUES
('admin', '$2a$10$7qk9gQxJ05Z0miEJeZVrVOe2gypKekJy5oQ1OtS9DD35JtIhXCT8y', '관리자');
