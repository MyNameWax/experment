package cn.rzpt.model.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChunkUploadBo {

    /**
     * 块文件
     */
    private MultipartFile chunkFile;

    /**
     * 块编号
     */
    private Long chunkNum;

    /**
     * 块大小
     */
    private Long chunkSize;

    /**
     * 文件块偏移量-主要用于断点续传
     */
    private Long offset;

    /**
     * 文件总大小
     */
    private Long fileSize;

    /**
     * 整个文件的md5值，防止传输篡改
     */
    private String fileMd5;

    /**
     * 是否是文件的最后一块
     */
    private boolean isLastChunk;

}
