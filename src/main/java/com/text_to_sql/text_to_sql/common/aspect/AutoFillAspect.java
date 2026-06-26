package com.text_to_sql.text_to_sql.common.aspect;

import com.text_to_sql.text_to_sql.common.annotation.AutoFill;
import com.text_to_sql.text_to_sql.common.constant.AutoFillConstant;
import com.text_to_sql.text_to_sql.common.enumeration.OperationType;
import com.text_to_sql.text_to_sql.util.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Collection;

@Aspect
@Component
@Slf4j
public class AutoFillAspect {

	@Pointcut("@annotation(com.text_to_sql.text_to_sql.common.annotation.AutoFill)")
	public void autoFillPointCut() {
	}

	@Before("autoFillPointCut()")
	public void autoFill(JoinPoint joinPoint) {
		log.debug("开始进行公共字段自动填充...");

		Object[] args = joinPoint.getArgs();
		if (args == null || args.length == 0) {
			return;
		}

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		AutoFill annotation = signature.getMethod().getAnnotation(AutoFill.class);
		OperationType operationType = annotation.value();

		LocalDateTime now = LocalDateTime.now();
		Long currentId = UserContext.getUserId();

		for (Object arg : args) {
			if (arg == null) {
				continue;
			}

			if (arg instanceof Collection<?> collection) {
				for (Object item : collection) {
					if (item != null && hasGetIdMethod(item)) {
						fillFields(item, operationType, now, currentId);
					}
				}
			} else if (hasGetIdMethod(arg)) {
				fillFields(arg, operationType, now, currentId);
			}
		}
	}

	private boolean hasGetIdMethod(Object obj) {
		try {
			obj.getClass().getDeclaredMethod("getId");
			return true;
		} catch (NoSuchMethodException e) {
			return false;
		}
	}

	private void fillFields(Object entity, OperationType operationType, LocalDateTime now, Long userId) {
		try {
			if (operationType == OperationType.INSERT) {
				Method getId = entity.getClass().getDeclaredMethod("getId");
				Object id = getId.invoke(entity);

				if (id == null || (id instanceof Number && ((Number) id).longValue() == 0)) {
					Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
					Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
					Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
					Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

					setCreateTime.invoke(entity, now);
					setCreateUser.invoke(entity, userId);
					setUpdateTime.invoke(entity, now);
					setUpdateUser.invoke(entity, userId);

					log.debug("INSERT 操作，自动填充 createUser={}, updateTime={}", userId, now);
				} else {
					Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
					Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

					setUpdateTime.invoke(entity, now);
					setUpdateUser.invoke(entity, userId);

					log.debug("UPDATE 操作，自动填充 updateUser={}, updateTime={}", userId, now);
				}
			} else if (operationType == OperationType.UPDATE) {
				Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
				Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

				setUpdateTime.invoke(entity, now);
				setUpdateUser.invoke(entity, userId);

				log.debug("UPDATE 操作，自动填充 updateUser={}, updateTime={}", userId, now);
			}
		} catch (Exception e) {
			log.error("自动填充失败", e);
		}
	}
}
