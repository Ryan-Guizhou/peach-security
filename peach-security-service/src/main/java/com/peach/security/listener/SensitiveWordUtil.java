package com.peach.security.listener;

import com.peach.common.util.PeachCollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 使用 DFA 算法实现敏感词过滤（兼容 Java 8）
 */
@Slf4j
@Component
public class SensitiveWordUtil {

    // 敏感词库的 DFA 树
    private final ConcurrentHashMap<Character, Object> sensitiveWordTree = new ConcurrentHashMap<>();


    /**
     * 初始化DFA 树
     * @param sensitiveWords
     */
    public void init(List<String> sensitiveWords) {
        if (PeachCollectionUtil.isEmpty(sensitiveWords)) {
            return;
        }
        for (String word : sensitiveWords) {
            addWord(word.toLowerCase());
        }
    }



    /**
     * 添加单个敏感词到 DFA 树
     * @param word 敏感词（小写）
     */
    private void addWord(String word) {
        if (word == null || word.isEmpty()) {
            return;
        }
        ConcurrentHashMap<Character, Object> currentNode = sensitiveWordTree;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            Object node = currentNode.get(c);
            if (node == null) {
                ConcurrentHashMap<Character, Object> newChild = new ConcurrentHashMap<>();
                currentNode.put(c, newChild);
                currentNode = newChild;
            } else if (node instanceof Map) {
                currentNode = (ConcurrentHashMap<Character, Object>) node;
            }
            if (i == word.length() - 1) {
                currentNode.put('*', null); // 结束标记
            }
        }
    }

    /**
     * 过滤文本中的敏感词
     * @param text 原始文本
     * @return 过滤后的文本
     */
    private String filter(String text) {
        if (text == null || text.isEmpty()) return text;

        StringBuilder result = new StringBuilder();
        int start = 0;
        int position = 0;
        Map<Character, Object> currentBranch = sensitiveWordTree;

        while (position < text.length()) {
            char c = Character.toLowerCase(text.charAt(position));
            Object node = currentBranch.get(c);

            if (node == null) {
                result.append(text.charAt(start));
                position = ++start;
                currentBranch = sensitiveWordTree;
                continue;
            }

            if (node instanceof Map) {
                currentBranch = (Map<Character, Object>) node;

                if (currentBranch.containsKey('*')) {
                    result.append(repeatChar('*', position - start + 1));
                    position++;
                    start = position;
                    currentBranch = sensitiveWordTree;
                    continue;
                }

                position++;
            }

            if (position == text.length()) {
                result.append(text.substring(start));
                break;
            }
        }

        return result.toString();
    }

    /**
     * 检查文本是否包含敏感词
     * @param text 待检查文本
     * @return 是否包含敏感词
     */
    public boolean containsSensitiveWord(String text) {
        if (text == null || text.isEmpty()) return false;

        int start = 0;
        while (start < text.length()) {
            int position = start;
            Map<Character, Object> currentBranch = sensitiveWordTree;

            while (position < text.length()) {
                char c = Character.toLowerCase(text.charAt(position));
                Object node = currentBranch.get(c);

                if (node == null) break;

                currentBranch = (Map<Character, Object>) node;
                if (currentBranch.containsKey('*')) return true;
                position++;
            }

            start++;
        }

        return false;
    }

    /**
     * 获取文本中的所有敏感词
     * @param text 待检测文本
     * @return 敏感词集合
     */
    private Set<String> getSensitiveWords(String text) {
        Set<String> sensitiveWords = new HashSet<>();
        if (text == null || text.isEmpty()) return sensitiveWords;

        int start = 0;
        while (start < text.length()) {
            int position = start;
            Map<Character, Object> currentBranch = sensitiveWordTree;
            StringBuilder currentWord = new StringBuilder();

            while (position < text.length()) {
                char originalChar = text.charAt(position);
                char c = Character.toLowerCase(originalChar);
                Object node = currentBranch.get(c);

                if (node == null) break;

                currentBranch = (Map<Character, Object>) node;
                currentWord.append(originalChar);

                if (currentBranch.containsKey('*')) {
                    sensitiveWords.add(currentWord.toString());
                    break;
                }

                position++;
            }

            start++;
        }

        return sensitiveWords;
    }

    /**
     * 字符重复（兼容 Java 8，无 String.repeat）
     */
    private String repeatChar(char ch, int count) {
        char[] chars = new char[count];
        Arrays.fill(chars, ch);
        return new String(chars);
    }

        public static void main(String[] args) {
        SensitiveWordUtil filter = new SensitiveWordUtil();

        Set<String> sensitiveWords = new HashSet<>(Arrays.asList(
                "敏感词", "测试", "暴力", "色情", "赌博"
        ));
        filter.init(new ArrayList<>(sensitiveWords));

        String text = "这是一段包含敏感词和测试内容的文本，请勿涉及暴力或色情内容！";

        System.out.println("原始文本: " + text);
        System.out.println("过滤结果: " + filter.filter(text));
        System.out.println("是否包含敏感词: " + filter.containsSensitiveWord(text));
        System.out.println("检测到的敏感词: " + filter.getSensitiveWords(text));
    }

}
