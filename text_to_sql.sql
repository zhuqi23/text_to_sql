-- 删除表（顺序：先子表后父表）
DROP TABLE IF EXISTS `question_knowledge`;
DROP TABLE IF EXISTS `question_detail`;
DROP TABLE IF EXISTS `solution`;
DROP TABLE IF EXISTS `questions`;
DROP TABLE IF EXISTS `knowledge`;
DROP TABLE IF EXISTS `user`;

-- 1. 用户表（只有创建时间，没有修改时间；只有创建人，没有修改人）
CREATE TABLE `user` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `account` VARCHAR(50) NOT NULL COMMENT '账号',
    `password` VARCHAR(255) NOT NULL COMMENT '密码',
    `name` VARCHAR(50) NOT NULL COMMENT '昵称',
    `type` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '类型：0=用户，1=管理员',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_account` (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 2. 知识点表
CREATE TABLE `knowledge` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '知识点ID',
    `name` VARCHAR(100) NOT NULL COMMENT '知识点名称',
    `create_user` INT UNSIGNED NOT NULL COMMENT '创建人ID',
    `update_user` INT UNSIGNED NOT NULL COMMENT '修改人ID',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_name` (`name`),
    CONSTRAINT `fk_knowledge_create` FOREIGN KEY (`create_user`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT `fk_knowledge_update` FOREIGN KEY (`update_user`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='知识点表';

-- 3. 题目表
CREATE TABLE `questions` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '题目ID',
    `title` VARCHAR(200) NOT NULL COMMENT '题目名称',
    `difficulty` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '难度：0其他 1简单 2中等 3困难',
    `create_user` INT UNSIGNED NOT NULL COMMENT '创建人ID',
    `update_user` INT UNSIGNED NOT NULL COMMENT '修改人ID',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`),
    KEY `idx_difficulty` (`difficulty`),
    CONSTRAINT `fk_questions_create` FOREIGN KEY (`create_user`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT `fk_questions_update` FOREIGN KEY (`update_user`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='题目表';

-- 4. 题目详细表（没有时间字段和用户字段）
CREATE TABLE `question_detail` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '详情ID',
    `question_id` INT UNSIGNED NOT NULL COMMENT '题目ID',
    `content` TEXT NOT NULL COMMENT '题目内容',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_question_id` (`question_id`),
    CONSTRAINT `fk_detail_question` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='题目详细表';

-- 5. 题解表
CREATE TABLE `solution` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '题解ID',
    `question_id` INT UNSIGNED NOT NULL COMMENT '题目ID',
    `content` TEXT NOT NULL COMMENT '题解内容',
    `create_user` INT UNSIGNED NOT NULL COMMENT '创建人ID',
    `update_user` INT UNSIGNED NOT NULL COMMENT '修改人ID',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`),
    KEY `idx_question_id` (`question_id`),
    CONSTRAINT `fk_solution_question` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `fk_solution_create` FOREIGN KEY (`create_user`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT `fk_solution_update` FOREIGN KEY (`update_user`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='题解表';

-- 6. 题目-知识点关系表
CREATE TABLE `question_knowledge` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '关联ID',
    `question_id` INT UNSIGNED NOT NULL COMMENT '题目ID',
    `knowledge_id` INT UNSIGNED NOT NULL COMMENT '知识点ID',
    `create_user` INT UNSIGNED NOT NULL COMMENT '创建人ID',
    `update_user` INT UNSIGNED NOT NULL COMMENT '修改人ID',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_question_knowledge` (`question_id`, `knowledge_id`),
    KEY `idx_knowledge_question` (`knowledge_id`, `question_id`),
    CONSTRAINT `fk_qk_question` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `fk_qk_knowledge` FOREIGN KEY (`knowledge_id`) REFERENCES `knowledge` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `fk_qk_create` FOREIGN KEY (`create_user`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT `fk_qk_update` FOREIGN KEY (`update_user`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='题目知识点关联表';