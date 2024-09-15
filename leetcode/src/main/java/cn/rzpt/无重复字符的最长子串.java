package cn.rzpt;

public class 无重复字符的最长子串 {

    public static void main(String[] args) {
        System.out.println(new 无重复字符的最长子串().new Solution().lengthOfLongestSubstring("aaaaa"));
    }

    class Solution {
        public int lengthOfLongestSubstring(String s) {
            char temp = ' ';
            int maxLength = 0;
            for (int i = 0; i < s.length(); i++) {

                // 第一个元素
                if (i == 0) {
                    temp = s.charAt(i);
                    System.out.println(temp);
                }
                if (temp == s.charAt(i)) {
                    temp = s.charAt(i);
                    maxLength += 1;
                    System.out.println(temp);
                }
            }
            return maxLength;
        }
    }

}
