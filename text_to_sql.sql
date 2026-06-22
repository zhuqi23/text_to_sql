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

-- 7. 提交表
CREATE TABLE `submit` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '提交ID',
    `question_id` INT UNSIGNED NOT NULL COMMENT '题目ID',
    `user_id` INT UNSIGNED NOT NULL COMMENT '提交人ID',
    `content` TEXT NOT NULL COMMENT '提交内容',
    `judgment` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '判断：0=错误，1=正确',
    `judge_id` INT UNSIGNED NOT NULL DEFAULT 1 COMMENT '判官ID：默认为1表示管理员判题',
    `analysis` TEXT COMMENT '分析',
    `submit_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '提交时间',
    `judgment_time` DATETIME COMMENT '判断时间',
    PRIMARY KEY (`id`),
    KEY `idx_question_id` (`question_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_judge_id` (`judge_id`),
    CONSTRAINT `fk_submit_question` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `fk_submit_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT `fk_submit_judge` FOREIGN KEY (`judge_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='提交表';

-- 插入用户数据
INSERT INTO `user` (`account`, `password`, `name`, `type`, `create_time`) VALUES ('admin', 'admin', '管理员', 1, NOW());

INSERT INTO `knowledge` (`name`, `create_user`, `update_user`, `create_time`, `update_time`) VALUES ('排序', 1, 1, NOW(), NOW());

-- 插入一道数据库题目
INSERT INTO `questions` (`title`, `difficulty`, `create_user`, `update_user`, `create_time`, `update_time`)
VALUES ('查询每个部门的平均工资', 2, 1, 1, NOW(), NOW());

-- 插入题目详情
INSERT INTO `question_detail` (`question_id`, `content`)
VALUES (1, '现有员工表 employees 和部门表 departments，结构如下：\n\nemployees 表：\n- id: 员工ID\n- name: 员工姓名\n- salary: 工资\n- department_id: 部门ID\n\ndepartments 表：\n- id: 部门ID\n- dept_name: 部门名称\n\n请编写 SQL 查询每个部门的名称和该部门的平均工资，结果按平均工资降序排列。');

-- 插入题目与知识点的关联（题目ID=1，知识点ID=1）
INSERT INTO `question_knowledge` (`question_id`, `knowledge_id`, `create_user`, `update_user`, `create_time`, `update_time`)
VALUES (1, 1, 1, 1, NOW(), NOW());

-- 插入一个提交记录（MySQL SQL 语句）
INSERT INTO `submit` (`question_id`, `user_id`, `content`, `judgment`, `judge_id`, `analysis`, `submit_time`, `judgment_time`)
VALUES (1, 1, 'SELECT \n    d.dept_name,\n    AVG(e.salary) AS avg_salary\nFROM employees e\nINNER JOIN departments d ON e.department_id = d.id\nGROUP BY d.id, d.dept_name\nORDER BY avg_salary DESC;', 1, 1, 'SQL 语句正确，使用了 INNER JOIN 连接两张表，通过 GROUP BY 按部门分组，AVG() 函数计算平均工资，并按平均工资降序排列。', NOW(), NOW());
