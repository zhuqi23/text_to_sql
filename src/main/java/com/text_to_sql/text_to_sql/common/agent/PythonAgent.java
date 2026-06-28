package com.text_to_sql.text_to_sql.common.agent;

import jep.Jep;
import jep.SharedInterpreter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class PythonAgent {

    private static final String PYTHON_SCRIPT_PATH = "D:/Project_TextToSql/agent/test1.py";

    private final ThreadLocal<Jep> jepThreadLocal = new ThreadLocal<>();
    private final List<Jep> allJeps = new ArrayList<>();

    @PostConstruct
    public void init() {
        try {
            File scriptFile = new File(PYTHON_SCRIPT_PATH);
            if (!scriptFile.exists()) {
                log.error("Python脚本文件不存在: {}", PYTHON_SCRIPT_PATH);
                throw new RuntimeException("Python脚本文件不存在: " + PYTHON_SCRIPT_PATH);
            }

            String scriptDir = scriptFile.getParent().replace("\\", "/");

            Jep jep = new SharedInterpreter();
            jep.eval("import sys; sys.stdout.reconfigure(encoding='utf-8'); sys.stderr.reconfigure(encoding='utf-8')");
            jep.eval("sys.path.insert(0, '" + scriptDir + "')");
            jep.runScript(PYTHON_SCRIPT_PATH);

            jepThreadLocal.set(jep);
            synchronized (allJeps) {
                allJeps.add(jep);
            }

            log.info("Python MySQLJudgeAgent 初始化成功");
        } catch (Exception e) {
            log.error("Python 环境初始化失败: {}", e.getMessage(), e);
            throw new RuntimeException("Python 环境初始化失败: " + e.getMessage());
        }
    }

    private Jep getJep() {
        Jep jep = jepThreadLocal.get();
        if (jep == null) {
            try {
                String scriptDir = new File(PYTHON_SCRIPT_PATH).getParent().replace("\\", "/");
                jep = new SharedInterpreter();
                jep.eval("import sys; sys.stdout.reconfigure(encoding='utf-8'); sys.stderr.reconfigure(encoding='utf-8')");
                jep.eval("sys.path.insert(0, '" + scriptDir + "')");
                jep.runScript(PYTHON_SCRIPT_PATH);
                jepThreadLocal.set(jep);
                synchronized (allJeps) {
                    allJeps.add(jep);
                }
            } catch (Exception e) {
                log.error("创建Jep实例失败: {}", e.getMessage(), e);
                throw new RuntimeException("创建Jep实例失败", e);
            }
        }
        return jep;
    }

    public String generateQuestion(String difficulty, List<String> knowledge, String other) {
        try {
            Jep jep = getJep();
            String knowledgeStr = String.join(",", knowledge);
            Object result = jep.invoke("agent.generate_question", difficulty, knowledgeStr, other);
            return result != null ? result.toString() : "";
        } catch (Exception e) {
            log.error("生成题目失败: {}", e.getMessage(), e);
            return "生成题目失败: " + e.getMessage();
        }
    }

    public String generateAnswerAnalysis(List<String> knowledge, String content) {
        try {
            Jep jep = getJep();
            String knowledgeStr = String.join(",", knowledge);
            Object result = jep.invoke("agent.generate_answer_analysis", knowledgeStr, content);
            return result != null ? result.toString() : "";
        } catch (Exception e) {
            log.error("生成答案解析失败: {}", e.getMessage(), e);
            return "生成答案解析失败: " + e.getMessage();
        }
    }

    public String judgeSqlAnswer(String question, String standardAnswer, String userSql) {
        try {
            Jep jep = getJep();
            Object result = jep.invoke("agent.judge_sql_answer", question, standardAnswer, userSql);
            return result != null ? result.toString() : "";
        } catch (Exception e) {
            log.error("判题失败: {}", e.getMessage(), e);
            return "判题失败: " + e.getMessage();
        }
    }

    @PreDestroy
    public void destroy() {
        synchronized (allJeps) {
            for (Jep jep : allJeps) {
                try {
                    jep.close();
                } catch (Exception e) {
                    log.error("关闭 Jep 失败: {}", e.getMessage(), e);
                }
            }
            allJeps.clear();
        }
        log.info("Python Jep 连接已全部关闭");
    }
}
