package com.shanhaifangzhou.admin.file.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文件管理实体
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_file")
public class SysFile implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文件名称
     */
    @TableField("file_name")
    private String fileName;

    /**
     * 原始文件名
     */
    @TableField("original_name")
    private String originalName;

    /**
     * 文件路径
     */
    @TableField("file_path")
    private String filePath;

    /**
     * 文件URL
     */
    @TableField("file_url")
    private String fileUrl;

    /**
     * 文件大小（字节）
     */
    @TableField("file_size")
    private Long fileSize;

    /**
     * 文件类型
     */
    @TableField("file_type")
    private String fileType;

    /**
     * 文件扩展名
     */
    @TableField("file_extension")
    private String fileExtension;

    /**
     * 存储类型（1：本地存储，2：阿里云OSS，3：腾讯云COS，4：七牛云）
     */
    @TableField("storage_type")
    private Integer storageType;

    /**
     * 存储桶名称
     */
    @TableField("bucket_name")
    private String bucketName;

    /**
     * 文件分类ID
     */
    @TableField("category_id")
    private Long categoryId;

    /**
     * 文件分类名称
     */
    @TableField("category_name")
    private String categoryName;

    /**
     * 文件标签
     */
    @TableField("file_tags")
    private String fileTags;

    /**
     * 文件描述
     */
    @TableField("description")
    private String description;

    /**
     * 文件状态（0：临时文件，1：正式文件，2：已删除）
     */
    @TableField("file_status")
    private Integer fileStatus;

    /**
     * 是否公开（0：私有，1：公开）
     */
    @TableField("is_public")
    private Integer isPublic;

    /**
     * 下载次数
     */
    @TableField("download_count")
    private Integer downloadCount;

    /**
     * 查看次数
     */
    @TableField("view_count")
    private Integer viewCount;

    /**
     * 文件MD5值
     */
    @TableField("file_md5")
    private String fileMd5;

    /**
     * 文件SHA1值
     */
    @TableField("file_sha1")
    private String fileSha1;

    /**
     * 文件宽度（图片）
     */
    @TableField("width")
    private Integer width;

    /**
     * 文件高度（图片）
     */
    @TableField("height")
    private Integer height;

    /**
     * 文件时长（视频/音频）
     */
    @TableField("duration")
    private Integer duration;

    /**
     * 缩略图路径
     */
    @TableField("thumbnail_path")
    private String thumbnailPath;

    /**
     * 缩略图URL
     */
    @TableField("thumbnail_url")
    private String thumbnailUrl;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 创建者
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 更新者
     */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    private String updateBy;
}