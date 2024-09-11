package cn.rzpt.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendSeeAudit {

    private Long id;

    private Long userId;

    private Long relateId;

    private Integer relateType;

}
