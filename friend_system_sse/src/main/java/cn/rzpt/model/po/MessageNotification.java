package cn.rzpt.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageNotification {

    private Long id;

    private Long userId;

    private Long toUserId;

    private Integer noticeType;

    private String noticeContent;

    private Integer readTarget;

    private String processResult;

}
