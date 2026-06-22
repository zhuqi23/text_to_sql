package com.text_to_sql.text_to_sql;

import com.text_to_sql.text_to_sql.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 测试枚举类型转换
 * 测试数据库枚举转换
 * 测试前后端枚举转换
 */
//@SpringBootTest
public class EnumTypeTransformationTest {

    @Autowired
    private QuestionMapper questionMapper;

	// 前后端枚举转换, 数据库枚举转换完成

    /**
     * 测试 UserType 枚举转换
     */
//    @Test
//    public void testUserTypeEnum() {
//        // 创建测试用户
//        user testUser = user.builder()
//                .account("test_user")
//                .password("123456")
//                .name("测试用户")
//                .type(UserType.USER)  // 使用枚举
//                .createTime(LocalDateTime.now())
//                .build();
//
//        // 插入数据库（会自动将 UserType.USER 转换为 0）
//        questionMapper.insertUser(testUser);
//        System.out.println("插入用户成功，ID: " + testUser.getId());
//
//        // 从数据库查询（会自动将 0 转换回 UserType.USER）
//        user queriedUser = questionMapper.selectUserById(testUser.getId());
//        System.out.println("查询用户: " + queriedUser.getName());
//        System.out.println("用户类型: " + queriedUser.getType());
//        System.out.println("类型代码: " + queriedUser.getType().getCode());
//        System.out.println("类型描述: " + queriedUser.getType().getDescription());
//
//        // 验证
//        assert queriedUser.getType() == UserType.USER;
//    }
//
//    /**
//     * 测试 Difficulty 枚举转换
//     */
//    @Test
//    public void testDifficultyEnum() {
//        // 创建测试题目
//        questions testQuestion = questions.builder()
//                .title("测试题目")
//                .difficulty(Difficulty.MEDIUM)  // 使用枚举
//                .createUser(1L)
//                .updateUser(1L)
//                .createTime(LocalDateTime.now())
//                .updateTime(LocalDateTime.now())
//                .build();
//
//        // 插入数据库（会自动将 Difficulty.MEDIUM 转换为 2）
//        questionMapper.insertQuestion(testQuestion);
//        System.out.println("插入题目成功，ID: " + testQuestion.getId());
//
//        // 从数据库查询（会自动将 2 转换回 Difficulty.MEDIUM）
//        questions queriedQuestion = questionMapper.selectQuestionById(testQuestion.getId());
//        System.out.println("查询题目: " + queriedQuestion.getTitle());
//        System.out.println("难度: " + queriedQuestion.getDifficulty());
//        System.out.println("难度代码: " + queriedQuestion.getDifficulty().getCode());
//        System.out.println("难度描述: " + queriedQuestion.getDifficulty().getDescription());
//
//        // 验证
//        assert queriedQuestion.getDifficulty() == Difficulty.MEDIUM;
//    }
}
