// package com.softcamp.estr.utils;

// import java.lang.reflect.InvocationTargetException;
// import java.util.Map;

// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.springframework.boot.test.context.SpringBootTest;

// import lombok.extern.slf4j.Slf4j;

// @Slf4j
// @SpringBootTest
// public class PhoenixMapTest {

// @Test
// @DisplayName("PhoenixMapTest")
// public void phoenixMap() throws InstantiationException,
// IllegalAccessException, IllegalArgumentException,
// InvocationTargetException, NoSuchMethodException, SecurityException {

// PhoenixMap<String, Object> phoenixMap = new PhoenixMap<>(10, 3000, new
// Ignitable<Map<String, Object>>() {

// @Override
// public void ignitionTask(Map<String, Object> buffer, Object... params) {
// log.info("ignitionTask!");

// }
// });

// for (int i = 0; i < 100; i++) {
// phoenixMap.add("testNo : " + i, i);
// }

// while (true) {
// }
// }
// }
