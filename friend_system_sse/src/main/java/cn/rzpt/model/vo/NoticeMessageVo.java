package cn.rzpt.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeMessageVo {
    private Long id;
    private Long userId;
    private Integer noticeType;
    private String title;
    private Integer readTarget;
    private String processResult;
    private String noticeContent;
}
