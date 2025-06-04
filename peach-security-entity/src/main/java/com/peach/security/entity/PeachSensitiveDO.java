package com.peach.security.entity;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/6/4 23:55
 */
@Data
@Table(name = "PEACH_SENSITIVE")
public class PeachSensitiveDO implements Serializable {
    private static final long serialVersionUID = 1L;
}
