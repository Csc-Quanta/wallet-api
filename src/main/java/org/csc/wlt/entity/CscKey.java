package org.csc.wlt.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class CscKey implements Serializable {
	/**
	 * 主键
	 */
	private int autoId;
}
