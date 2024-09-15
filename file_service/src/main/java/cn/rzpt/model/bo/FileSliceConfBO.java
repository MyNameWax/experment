package cn.rzpt.model.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileSliceConfBO implements Serializable {

    /**
     * 文件名 - 合并块时使用
     */
    private String fileName;

    /**
     * 文件MD5值 防止篡改
     */
    private String fileMd5;

    /**
     * 文件总大小
     */
    private Long fileSize;

    /**
     * 每个块大小
     */
    private Long chunkSize;

    /**
     * 块的总数量
     */
    private Long totalChunks;

}
